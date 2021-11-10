(ns logical-expressions.dnf.transform.remove-implication-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.dnf.transform.remove-implication :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

(def input-outputs (list [(implication var-a var-b)
                          (disjunction (negation var-a)
                                       var-b)]
                         [(implication var-a var-b var-c)
                          (disjunction (negation var-a)
                                       (disjunction (negation var-b)
                                                    var-c))]))

(def test-case {:transforms    (list remove-implication-transforms)
                :name          "Remove implication transform test"
                :input-outputs input-outputs})

(deftest test-remove-implication-transform
  (run-test-case test-case))