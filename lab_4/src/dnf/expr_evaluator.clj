(ns dnf.expr-evaluator
  (:require [dnf.transform-engine :refer :all]
            [dnf.evaluation.dnf-rules :refer :all]
            [dnf.operator.str :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.transformation.dnf-transformations :refer :all]))

(defn eval-expr [variables expr]
  (println (str "Try to eval expr: " (expr-str expr)) "vars:" variables)
  (let [dnf (transform-expr dnf-transformations expr)]
    (println (str "DNF: " (expr-str dnf)))
    (if (constant? dnf)
      (constant-value dnf)
      (apply-eval eval-dnf-rules variables dnf))))