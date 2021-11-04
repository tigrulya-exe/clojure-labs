(ns dnf.rules
  (:require [dnf.transformation.remove-implication :refer :all]
            [dnf.transformation.bring-negation-in :refer :all]
            [dnf.rule-machine :refer :all]))

(def dnf-transformations
  (list remove-implication-transforms
        bring-negation-transforms

        ))