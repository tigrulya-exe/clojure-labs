(ns logical-expressions.core.operation.util
  (:require [clojure.string :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]))

(def true-expr (constant true))
(def false-expr (constant false))

(defn- expr-args-str [str-fn expr separator]
  (str "(" (->> (rest expr)
                (map str-fn)
                (join (str " " separator " ")))
       ")"))

(defn expr-str
  "Returns string representation of expression"
  [expr]
  (let [args-str (partial expr-args-str expr-str)]
    (cond
      (constant? expr) (str (second expr))
      (variable? expr) (str (second expr))
      (negation? expr) (str "~" (expr-str (second expr)))
      (disjunction? expr) (args-str expr "v")
      (conjunction? expr) (args-str expr "&")
      (implication? expr) (args-str expr "->"))))

(defn print-expr
  "Prints expression to stout"
  [expr]
  (println (expr-str expr)))