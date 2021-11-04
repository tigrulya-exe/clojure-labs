(ns dnf.operator.print
  (:require [clojure.string :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.operator.shared :refer :all]))

(defn pretty-args-str [str-fn expr separator]
  (str "(" (->> (rest expr)
                (map str-fn)
                (join (str " " separator " ")))
       ")"))

(defn expr-str [expr]
  (let [args-str (partial pretty-args-str expr-str)]
    (cond
      (constant? expr) (str (second expr))
      (variable? expr) (str (second expr))
      (negation? expr) (str "~" (expr-str (second expr)))
      (disjunction? expr) (args-str expr "v")
      (conjunction? expr) (args-str expr "&")
      (implication? expr) (args-str expr "->"))))

(defn print-expr [expr]
  (println (expr-str expr)))