(ns dnf.transformation.remove-implication
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]
            [dnf.transformation.shared :refer :all]))

(defn remove-implication [transform-fn expr]
  (let [[first-arg & rest] (args expr)]
    conjunction
    (negation (transform-fn first-arg))
    (transform-fn (apply implication rest))))

; 1 этап - избавление от импликаций
; слева предикат для срабатываения, справа трансформация (fn принимает expr, возвращает expr)
(def remove-implication-transforms
  (let [transform-fn (partial transform-dnf
                              remove-implication-transforms)]
    (cons [implication?
           (partial remove-implication transform-fn)]
          (default-transforms transform-fn))))