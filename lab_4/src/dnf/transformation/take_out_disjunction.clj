(ns dnf.transformation.take-out-disjunction
  (:require [dnf.unary-operators :refer :all]
            [dnf.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]))

(defn first-arg-disjunction? [expr]
  (and (conjunction? expr)
       (disjunction? (first (args expr)))))

(defn take-out-disjunction [transform-fn expr]
  (let [[disj & conj-rest] (args expr)]
    (apply-to-args disjunction
                   #(->> (apply conj conj-rest)
                         (transform-fn)
                         (apply conjunction %))
                   disj)))

; 3 этап - вынесесние дизъюнкции наружу
(def take-out-disjunction-transform
  (let [transform-fn (partial converse-to-dnf
                              (list take-out-disjunction-transform))]
    (list [first-arg-disjunction?
           (partial take-out-disjunction transform-fn)]
          ; TODO mb make as default
          [disjunction?
           (partial apply-to-args disjunction transform-fn)]
          [conjunction?
           (partial apply-to-args conjunction transform-fn)]
          [negation?
           (partial apply-to-args negation transform-fn)]
          [constant? identity]
          [variable? identity])))

