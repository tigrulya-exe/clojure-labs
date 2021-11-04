(ns dnf.transformation.remove-implication
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]
            [dnf.transform-machine :refer :all]
            [dnf.transformation.shared :refer :all]))

(defn remove-implication [transform-fn expr]
  (let [[first-arg & rest] (args expr)]
    (disjunction
      (negation (transform-fn first-arg))
      (transform-fn (apply implication rest)))))

; 1 этап - избавление от импликаций
; слева предикат для срабатываения, справа трансформация (fn принимает expr, возвращает expr)
(def remove-implication-transforms
  (let [transform-fn #(apply-transform % remove-implication-transforms)]
    (cons [implication?
           (partial remove-implication transform-fn)]
          (default-transforms transform-fn))))