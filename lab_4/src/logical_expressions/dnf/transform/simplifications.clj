(ns logical-expressions.dnf.transform.simplifications
  (:require [clojure.set :refer [intersection]]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.operation.shared :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.core.transform.shared :refer :all]))

(defn- variable-under-negation? [expr]
  (and (negation? expr)
       (variable? (first (args expr)))))


(defn- disj-arg-partition [arg]
  (cond
    (= true-expr arg) ::true
    (= false-expr arg) ::false
    (variable? arg) ::var
    (variable-under-negation? arg) ::var-neg
    :else ::rest))

(defn- intersect? [vars vars-under-negation]
  (not-empty (intersection (set vars)
                           (->> (map (comp first args) vars-under-negation)
                                (set)))))

(defn- simplify-disjunction-args [expr transform-fn]
  (let [{true-exprs     ::true
         vars           ::var
         vars-under-neg ::var-neg
         rest-args      ::rest} (->> (args expr)
                                     (map transform-fn)
                                     (group-by disj-arg-partition))
        distinct-vars (distinct vars)]
    (cond
      (not (nil? true-exprs)) (list true-expr)
      (and (not (nil? vars-under-neg))
           (intersect? vars vars-under-neg)) (list true-expr)
      :else (concat vars-under-neg rest-args distinct-vars))))

(defn- simplify-conjunction-args [expr transform-fn]
  (let [{false-exprs    ::false
         vars           ::var
         vars-under-neg ::var-neg
         rest-args      ::rest} (->> (args expr)
                                     (map transform-fn)
                                     (group-by disj-arg-partition))
        distinct-vars (distinct vars)]
    (cond
      (not (nil? false-exprs)) (list false-expr)
      (and (not (nil? vars-under-neg))
           (intersect? vars vars-under-neg)) (list false-expr)
      :else (concat vars-under-neg rest-args distinct-vars))))

(defn- simplify-disjunction [transform-fn expr]
  (let [simplified-args (simplify-disjunction-args expr transform-fn)]
    (if (empty? simplified-args)
      false-expr
      (apply disjunction simplified-args))))

(defn- simplify-conjunction [transform-fn expr]
  (let [simplified-args (simplify-conjunction-args expr transform-fn)]
    (if (empty? simplified-args)
      true-expr
      (apply conjunction simplified-args))))

(defn- simplify-negation [transform-fn expr]
  (let [arg (->> (args expr)
                 (first)
                 (transform-fn))]
    (cond
      (= false-expr arg) true-expr
      (= true-expr arg) false-expr
      :else (negation arg))))

; 5th step of transforming expression to DNF:
; simplifying expression by revealing identically false/true expressions, etc
(def simplify-expr-transforms
  (let [transform-fn #(apply-transform % simplify-expr-transforms)]
    (conj (default-transforms transform-fn)
          [disjunction?
           (partial simplify-disjunction transform-fn)]
          [conjunction?
           (partial simplify-conjunction transform-fn)]
          [negation?
           (partial simplify-negation transform-fn)])))