(ns logical-expressions.dnf.transform.transformations-test
  (:require [clojure.test :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.core.transform.shared :refer :all]
            [logical-expressions.dnf.transform.transformations :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

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
