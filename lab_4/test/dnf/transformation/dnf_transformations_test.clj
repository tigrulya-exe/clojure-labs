(ns dnf.transformation.dnf-transformations-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.shared :refer :all]
            [dnf.transformation.dnf-transformations :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(negation (negation (variable :a)))
                          (variable :a)]
                         [(implication (conjunction (variable :a)
                                                    (disjunction (variable :b)
                                                                 (variable :c)))
                                       (variable :d))
                          (disjunction (negation (variable :a))
                                       (conjunction (negation (variable :b))
                                                    (negation (variable :c)))
                                       (variable :d))]))

(def test-case {:transforms    dnf-transformations
                :name          "DNF transforms test"
                :input-outputs input-outputs})

(deftest test-dnf-transformations
  (run-test-case test-case))
