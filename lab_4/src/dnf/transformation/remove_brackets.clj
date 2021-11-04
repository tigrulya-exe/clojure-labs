(ns dnf.transformation.remove-brackets
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]
            [dnf.transformation.shared :refer :all]))

(defn flatten [predicate supplier transform-fn expr]
  (apply supplier (->> (args expr)
                       (map transform-fn)
                       (mapcat #(if (predicate %)
                                  (args %)
                                  %)))))

(def remove-brackets-transforms
  (let [transform-fn (partial transform-dnf
                              remove-brackets-transforms)]
    (conj (default-transforms transform-fn)
          [disjunction?
           (partial flatten disjunction? disjunction transform-fn)]
          [conjunction?
           (partial flatten conjunction? conjunction transform-fn)])))