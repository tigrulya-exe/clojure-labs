(ns dnf.transformation.remove-brackets
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]
            [dnf.transform-engine :refer :all]
            [dnf.transformation.shared :refer :all]))

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