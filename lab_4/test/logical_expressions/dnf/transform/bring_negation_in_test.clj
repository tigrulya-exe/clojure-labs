(ns logical-expressions.dnf.transform.bring-negation-in-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.dnf.transform.bring-negation-in :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

(def input-outputs (list [(negation (negation var-a))
                          var-a]
                         [(negation (disjunction var-a var-b var-c))
                          (conjunction (negation var-a)
                                       (negation var-b)
                                       (negation var-c))]
                         [(negation (conjunction var-a
                                                 (disjunction var-b
                                                              (constant true))))
                          (disjunction (negation var-a)
                                       (conjunction (negation var-b)
                                                    (negation (constant true))))]))

(def test-case {:transforms    (list bring-negation-transforms)
                :name          "Bring negation in transform test"
                :input-outputs input-outputs})

(deftest test-bring-negation-in-transform
  (run-test-case test-case))