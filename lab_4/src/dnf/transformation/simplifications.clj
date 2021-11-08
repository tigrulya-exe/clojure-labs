(ns dnf.transformation.simplifications
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]
            [dnf.transform-engine :refer :all]
            [dnf.transformation.shared :refer :all]))

; refactor after tests
(defn simplify-disjunction-args [expr transform-fn]
  (loop [result '()
         [arg & rest-args] (args expr)]
    (if (nil? arg)
      result
      (let [transformed-arg (transform-fn arg)]
        (cond
          (= true-expr transformed-arg)
            (list true-expr)
          (= false-expr transformed-arg)
            (recur result rest-args)
          :else
            (recur (conj result transformed-arg)
                 rest-args))))))

(defn simplify-conjunction-args [expr transform-fn]
  (loop [result '()
         [arg & rest-args] (args expr)]
    (if (nil? arg)
      result
      (let [transformed-arg (transform-fn arg)]
        (cond
          (= false-expr transformed-arg)
            (list false-expr)
          (= true-expr transformed-arg)
            (recur result rest-args)
          :else
            (recur (conj result transformed-arg)
                 rest-args))))))

(defn simplify-disjunction [transform-fn expr]
  (let [simplified-args (simplify-disjunction-args expr transform-fn)]
    (if (empty? simplified-args)
      false-expr
      (apply disjunction simplified-args))))

(defn simplify-conjunction [transform-fn expr]
  (let [simplified-args (simplify-conjunction-args expr transform-fn)]
    (if (empty? simplified-args)
      true-expr
      (apply conjunction simplified-args))))

(defn simplify-negation [transform-fn expr]
  (let [arg (->> (args expr)
                 (first)
                 (transform-fn))]
    (cond
      (= false-expr arg) true-expr
      (= true-expr arg) false-expr
      :else (negation arg))))

; Упрощение выражений
(def simplify-expr-transforms
  (let [transform-fn #(apply-transform % simplify-expr-transforms)]
    (conj (default-transforms transform-fn)
          [disjunction?
           (partial simplify-disjunction transform-fn)]
          [conjunction?
           (partial simplify-conjunction transform-fn)]
          [negation?
           (partial simplify-negation transform-fn)])))