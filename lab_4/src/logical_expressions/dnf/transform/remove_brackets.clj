(ns logical-expressions.dnf.transform.remove-brackets
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.core.transform.shared :refer :all]))

(defn- flatten-expr [transform-fn predicate expr]
  (loop [expr-args (->> (args expr)
                        (map transform-fn)
                        (reverse))
         result '()]
    (if (empty? expr-args)
      result
      (let [arg (first expr-args)]
        (recur (rest expr-args)
               (if (predicate arg)
                 (apply conj result (args arg))
                 (conj result arg)))))))

; 4th step of transforming expression to DNF:
; removing unnecessary brackets (nesting)
(def remove-brackets-transforms
  (let [transform-fn #(apply-transform % remove-brackets-transforms)
        flatten-fn (partial flatten-expr transform-fn)]
    (conj (default-transforms transform-fn)
          [disjunction?
           (comp (partial apply disjunction)
                 (partial flatten-fn disjunction?))]
          [conjunction?
           (comp (partial apply conjunction)
                 (partial flatten-fn conjunction?))])))