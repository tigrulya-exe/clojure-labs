(ns dnf.transformation.bring-negation-in
  (:require [dnf.unary-operators :refer :all]
            [dnf.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]))

(defn under-negation? [predicate expr]
  (and (negation? expr)
       (predicate (first (args expr)))))

; 2 этап - занесение отрицания внутрь и избавление от двойного отрицания
(def bring-negation-transform
  (let [transform-fn (partial converse-to-dnf
                              (list bring-negation-transform))]
    (list [(partial under-negation? disjunction?)
           (partial apply-to-args
                    conjunction
                    (comp transform-fn negation))]
          [(partial under-negation? conjunction?)
           (partial apply-to-args
                    disjunction
                    (comp transform-fn negation))]
          [(partial under-negation? negation?)
           (comp transform-fn first args)]
          ; TODO mb make as default
          [disjunction?
           (partial apply-to-args disjunction transform-fn)]
          [conjunction?
           (partial apply-to-args conjunction transform-fn)]
          [negation?
           (partial apply-to-args negation transform-fn)]
          [constant? identity]
          [variable? identity])))


