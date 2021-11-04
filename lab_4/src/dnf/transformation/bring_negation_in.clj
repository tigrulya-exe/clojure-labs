(ns dnf.transformation.bring-negation-in
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]
            [dnf.transform-machine :refer :all]
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

(defn apply-under-negation [transform-fn producer expr]
  (let [arg (first (args expr))]
    (apply-to-args producer
                   (comp transform-fn negation)
                   arg)))

; 2 этап - занесение отрицания внутрь и избавление от двойного отрицания
(def bring-negation-transforms
  (let [transform-fn #(apply-transform % bring-negation-transforms)
        expand-negation-fn (partial apply-under-negation
                                    transform-fn)]
    (conj (default-transforms transform-fn)
          [disjunction-under-negation?
           (partial expand-negation-fn conjunction)]
          [conjunction-under-negation?
           (partial expand-negation-fn disjunction)]
          [double-negation?
           (comp transform-fn first args)])))


