(ns dnf.transformation.take-out-disjunction
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]
            [dnf.transform-engine :refer :all]
            [dnf.transformation.shared :refer :all]))

(defn conjunction-of-disjunction? [expr]
  (and (conjunction? expr)
       (disjunction? (first (args expr)))))

(defn take-out-disjunction [transform-fn conj]
  (let [[disj & conj-rest-list] (args conj)
        ;TODO мб стоит делать трансформ для элементов конж один раз только
        conj-rest (->> (apply conjunction conj-rest-list)
                       (transform-fn))]
    (apply-to-args disjunction
                   #(->> (apply conjunction % conj-rest)
                         (transform-fn))
                   disj)))

; 3 этап - вынесесние дизъюнкции наружу
(def take-out-disjunction-transforms
  (let [transform-fn #(apply-transform % take-out-disjunction-transforms)]
    (cons [conjunction-of-disjunction?
           (partial take-out-disjunction transform-fn)]
          (default-transforms transform-fn))))

