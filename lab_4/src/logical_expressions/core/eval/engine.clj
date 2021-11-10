(ns logical-expressions.core.eval.engine
  (:require [logical-expressions.core.transform.engine :refer :all]))

(defn eval-expr
  "Evaluate given expression with provided transformation table
   and variable values"
  [transform-table vars expr]
  (apply-transform expr
                   transform-table
                   (list vars expr)))

