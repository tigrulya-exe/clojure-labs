(ns logical-expressions.dnf.transform.transformations-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.core.transform.shared :refer :all]
            [logical-expressions.dnf.transform.transformations :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

(def input-outputs (list [(negation (negation var-a))
                          var-a]
                         [(implication (conjunction var-a
                                                    (disjunction var-b
                                                                 var-c))
                                       var-d)
                          (disjunction (negation var-a)
                                       (conjunction (negation var-b)
                                                    (negation var-c))
                                       var-d)]))

(def test-case {:transforms    dnf-transformations
                :name          "DNF transforms test"
                :input-outputs input-outputs})

(deftest test-dnf-transformations
  (run-test-case test-case))
