(ns dnf.transformation.dnf-transformations
  (:require [dnf.transformation.remove-implication :refer :all]
            [dnf.transformation.bring-negation-in :refer :all]
            [dnf.transformation.take-out-disjunction :refer :all]
            [dnf.transformation.simplifications :refer :all]
            [dnf.transformation.remove-brackets :refer :all]))

(def dnf-transformations
  (list remove-implication-transforms
        bring-negation-transforms
        take-out-disjunction-transforms
        remove-brackets-transforms
        simplify-expr-transforms))


