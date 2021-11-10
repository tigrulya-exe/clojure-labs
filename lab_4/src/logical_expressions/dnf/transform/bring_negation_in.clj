(ns logical-expressions.dnf.transform.bring-negation-in
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.core.transform.shared :refer :all]))

(defn- apply-under-negation [transform-fn producer expr]
  (let [arg (first (args expr))]
    (apply-to-args producer
                   (comp transform-fn negation)
                   arg)))

(defn- under-negation? [predicate expr]
  (and (negation? expr)
       (predicate (first (args expr)))))

(def double-negation?
  (partial under-negation? negation?))

(def conjunction-under-negation?
  (partial under-negation? conjunction?))

(def disjunction-under-negation?
  (partial under-negation? disjunction?))

; 2nd step of transforming expression to DNF:
; bringing negations inside and getting rid of double negations
(def bring-negation-transforms
  (let [transform-fn #(apply-transform % bring-negation-transforms)
        expand-negation-fn (partial apply-under-negation
                                    transform-fn)]
    (conj (default-transforms transform-fn)
          [disjunction-under-negation?
           (partial expand-negation-fn conjunction)]
          [conjunction-under-negation?
           (partial expand-negation-fn disjunction)]
          [double-negation?
           (comp transform-fn first args first args)])))


