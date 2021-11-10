(ns logical-expressions.dnf.transform.remove-implication
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.core.transform.shared :refer :all]))

(defn- remove-implication [transform-fn expr]
  (let [[first-arg & rest] (args expr)]
    (disjunction
      (negation (transform-fn first-arg))
      (transform-fn (apply implication rest)))))

; 1st step of transforming expression to DNF:
; removing implications
(def remove-implication-transforms
  (let [transform-fn #(apply-transform % remove-implication-transforms)]
    (cons [implication?
           (partial remove-implication transform-fn)]
          (default-transforms transform-fn))))