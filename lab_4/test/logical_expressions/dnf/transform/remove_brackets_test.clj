(ns logical-expressions.dnf.transform.remove-brackets-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.dnf.transform.remove-brackets :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

(def input-outputs (list [(disjunction (disjunction var-a var-b)
                                       var-c)
                          (disjunction var-b var-a var-c)]
                         [(conjunction (conjunction var-a
                                                    var-b)
                                       var-c)
                          (conjunction var-b var-a var-c)]))

(def test-case {:transforms    (list remove-brackets-transforms)
                :name          "Remove brackets transform test"
                :input-outputs input-outputs})

(deftest test-remove-brackets-transform
  (run-test-case test-case))