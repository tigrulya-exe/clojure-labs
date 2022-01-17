(ns logical-expressions.dnf.expr-evaluator
  (:require [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.transform.engine :refer :all]
            [logical-expressions.dnf.transform.transformations :refer :all]
            [logical-expressions.dnf.eval.rules :refer :all]))

(defn eval-expr
  "Evaluates provided expression by transforming it to DNF
   and injecting variables values, provided in variables map
   (key - variable name, value - variable value)."
  [variables expr]
  (println (str "Try to eval expr: " (expr-str expr)) "vars:" variables)
  (let [dnf (transform-expr dnf-transformations expr)]
    (println (str "DNF: " (expr-str dnf)))
    (if (constant? dnf)
      (constant-value dnf)
      (eval-dnf-expr variables dnf))))