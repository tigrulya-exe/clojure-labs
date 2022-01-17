(ns logical-expressions.dnf.transform.take-out-disjunction-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.dnf.transform.take-out-disjunction :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]))

(def input-outputs (list [(conjunction var-a var-b)
                          (conjunction var-a var-b)]
                         [(conjunction var-a
                                       var-b
                                       var-c
                                       (disjunction var-d
                                                    (variable :e)))
                          (disjunction (conjunction var-d
                                                    var-a
                                                    var-b
                                                    var-c)
                                       (conjunction (variable :e)
                                                    var-a
                                                    var-b
                                                    var-c))]
                         [(conjunction var-a
                                       (disjunction var-b var-c))
                          (disjunction (conjunction var-b var-a)
                                       (conjunction var-c var-a))]
                         [(conjunction (disjunction var-a var-b)
                                       (disjunction var-c var-d))
                          (disjunction (disjunction
                                         (conjunction var-c var-a)
                                         (conjunction var-d var-a))
                                       (disjunction
                                         (conjunction var-c var-b)
                                         (conjunction var-d var-b)))]))

(def test-case {:transforms    (list take-out-disjunction-transforms)
                :name          "Take out disjunction transform test"
                :input-outputs input-outputs})

(deftest test-take-out-disjunction-transform
  (run-test-case test-case))