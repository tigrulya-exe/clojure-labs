(ns logical-expressions.dnf.transform.transformations
  (:require [logical-expressions.dnf.transform.remove-implication :refer :all]
            [logical-expressions.dnf.transform.bring-negation-in :refer :all]
            [logical-expressions.dnf.transform.take-out-disjunction :refer :all]
            [logical-expressions.dnf.transform.simplifications :refer :all]
            [logical-expressions.dnf.transform.remove-brackets :refer :all]
            [logical-expressions.core.transform.engine :refer :all]))

; List of transformations, required to turn expression to DNF
(def dnf-transformations
  (list remove-implication-transforms
        bring-negation-transforms
        take-out-disjunction-transforms
        remove-brackets-transforms
        simplify-expr-transforms))

; Transforms expression to DNF
(def transform-dnf-expr
  (partial transform-expr dnf-transformations))

