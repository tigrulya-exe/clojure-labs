(ns logical-expressions.core.transform.shared
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]))

(defn apply-to-args
  "Applies fn to arguments of expr and passes result to producer function"
  [producer fn expr]
  (->> (args expr)
       (map fn)
       (apply producer)))

(defn default-transforms
  "Returns default transformations for logical operations"
  [transform-fn]
  (conj (map (fn [[pred supplier]]
               [pred (partial apply-to-args
                              supplier
                              transform-fn)])
             (list [disjunction? disjunction]
                   [conjunction? conjunction]
                   [negation? negation]))
        [constant? identity]
        [variable? identity]))