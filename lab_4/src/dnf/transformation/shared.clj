(ns dnf.transformation.shared
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]))

(defn apply-to-args [producer fn expr]
  (->> (args expr)
       (map fn)
       (apply producer)))

(def pred-suppliers (list [disjunction? disjunction]
                           [conjunction? conjunction]
                           [negation? negation]))

(defn default-transforms [transform-fn]
  (concat (map (fn [[pred supplier]]
                 [pred (partial apply-to-args
                                supplier
                                transform-fn)])
               pred-suppliers)
          (list [constant? identity]
                [variable? identity])))