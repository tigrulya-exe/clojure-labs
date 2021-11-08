(ns dnf.transformation.shared
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]))

(def true-expr (constant true))
(def false-expr (constant false))

(defn apply-to-args [producer fn expr]
  (->> (args expr)
       (map fn)
       (apply producer)))

(def predicate-supplier-pairs (list [disjunction? disjunction]
                                    [conjunction? conjunction]
                                    [negation? negation]))

(defn default-transforms [transform-fn]
  (conj (map (fn [[pred supplier]]
               [pred (partial apply-to-args
                              supplier
                              transform-fn)])
             predicate-supplier-pairs)
        [constant? identity]
        [variable? identity]))