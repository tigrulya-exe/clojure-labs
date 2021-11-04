(ns dnf.transformation.transformations
  (:require [dnf.operators.unary-operators :refer :all]
            [dnf.operators.binary-operators :refer :all]
            [dnf.shared :refer :all]
            [dnf.rule-machine :refer :all]))

(defn remove-implication [transform-fn expr]
  (let [[first-arg & rest] (args expr)]
    conjunction
    (negation (transform-fn first-arg))
    (transform-fn (apply implication rest))))

(defn under-negation? [predicate expr]
  (and (negation? expr)
       (predicate (first (args expr)))))

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

(defn transformations [transform-recur-fn]
  (list
    ; 1 этап - избавление от импликаций
    [implication?
     (partial remove-implication transform-recur-fn)]

    ; 2 этап - занесение отрицания внутрь и избавление от двойного отрицания
    [(partial under-negation? disjunction?)
     (partial apply-to-args
              conjunction
              (comp transform-recur-fn negation))]
    [(partial under-negation? conjunction?)
     (partial apply-to-args
              disjunction
              (comp transform-recur-fn negation))]
    [(partial under-negation? negation?)
     (comp transform-recur-fn first args)]

    ; 3 этап - вынесесние дизъюнкции наружу
    [first-arg-disjunction?
     (partial take-out-disjunction transform-recur-fn)]

    ; TODO По умолчанию возвращаем то, что есть, т.к. обход снизу вверх
    [(constantly true) identity]))