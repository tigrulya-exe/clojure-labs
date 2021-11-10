(ns logical-expressions.dnf.transform.remove-implication-test
  (:require [clojure.test :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.dnf.transform.remove-implication :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

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