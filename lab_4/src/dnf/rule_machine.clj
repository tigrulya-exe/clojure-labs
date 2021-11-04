(ns dnf.rule-machine
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]))

(defn transform-dnf [rules expr]
  (traverse-expr expr (partial transform-dnf rules)))

(defn pred-suppliers (list [disjunction? disjunction]
                           [conjunction? conjunction]
                           [negation? negation]))

(defn step-down-fn [[pred supplier]]
  (fn [expr apply-fn]
    (if (pred expr)
      (let [new-expr (partial apply-to-args traverse-expr supplier)]
        (apply-fn new-expr))
      false)))

(defn traverse-expr [expr apply-fn]
  (cond
    (or (constant? expr)
        (variable? expr)) identity
    :else ((some step-down-fn pred-suppliers)
           expr
           apply-fn)))