(ns logical-expressions.dnf.transform.take-out-disjunction
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.core.transform.shared :refer :all]))

(defn take-out-disjunction [transform-fn expr]
  (let [{disj-args  true
         other-args false} (->> (args expr)
                                (map transform-fn)
                                (group-by disjunction?))]
    (if (nil? disj-args)
      (apply conjunction other-args)
      (apply-to-args disjunction
                     #(->> (apply conjunction
                                  %
                                  (concat other-args (rest disj-args)))
                           (transform-fn))
                     (first disj-args)))))

; 3 этап - вынесесние дизъюнкции наружу
(def take-out-disjunction-transforms
  (let [transform-fn #(apply-transform % take-out-disjunction-transforms)]
    (cons [conjunction?
           (partial take-out-disjunction transform-fn)]
          (default-transforms transform-fn))))

