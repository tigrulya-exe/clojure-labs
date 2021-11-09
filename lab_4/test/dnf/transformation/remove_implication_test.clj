(ns dnf.transformation.remove-implication-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.remove-implication :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(implication (variable :a) (variable :b))
                          (disjunction (negation (variable :a))
                                       (variable :b))]
                         [(implication (variable :a) (variable :b) (variable :c))
                          (disjunction (negation (variable :a))
                                       (disjunction (negation (variable :b))
                                                    (variable :c)))]))

(def test-case {:transforms    (list remove-implication-transforms)
                :name          "Remove implication transform test"
                :input-outputs input-outputs})

(deftest test-remove-implication-transform
  (run-test-case test-case))