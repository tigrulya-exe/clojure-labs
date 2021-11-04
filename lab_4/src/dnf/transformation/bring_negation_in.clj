(ns dnf.transformation.bring-negation-in
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]
            [dnf.transformation.shared :refer :all]))

(defn under-negation? [predicate expr]
  (and (negation? expr)
       (predicate (first (args expr)))))

(def double-negation?
  (partial under-negation? negation?))

(def conjunction-under-negation?
  (partial under-negation? conjunction?))

(def disjunction-under-negation?
  (partial under-negation? disjunction?))

; 2 этап - занесение отрицания внутрь и избавление от двойного отрицания
(defn bring-negation-transforms
  (let [transform-fn (partial transform-dnf
                              (list bring-negation-transforms))]
    (conj (default-transforms transform-fn)
          [disjunction-under-negation?
           (partial apply-to-args
                    conjunction
                    (comp transform-fn negation))]
          [conjunction-under-negation?
           (partial apply-to-args
                    disjunction
                    (comp transform-fn negation))]
          [double-negation?
           (comp transform-fn first args)])))


