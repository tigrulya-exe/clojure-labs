(ns dnf.transformation.remove-implication
  (:require [dnf.unary-operators :refer :all]
            [dnf.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]))

(defn remove-implication [transform-fn expr]
  (let [[first-arg & rest] (args expr)]
    conjunction
    (negation (transform-fn first-arg))
    (transform-fn (apply implication rest))))

; 1 этап - избавление от импликаций
; слева предикат для срабатываения, справа трансформация (fn принимает expr, возвращает expr)
(def remove-implication-transform
  (let [transform-fn (partial converse-to-dnf
                              (list remove-implication-transform))]
    (list [implication?
           (partial remove-implication transform-fn)]
          [disjunction?
           (partial apply-to-args disjunction transform-fn)]
          [conjunction?
           (partial apply-to-args conjunction transform-fn)]
          [negation?
           (partial apply-to-args negation transform-fn)]
          ; todo
          [constant? identity]
          [variable? identity])))


