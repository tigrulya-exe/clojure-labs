(ns dnf.transformation.shared
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]))


(defn pred-suppliers (list [disjunction? disjunction]
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