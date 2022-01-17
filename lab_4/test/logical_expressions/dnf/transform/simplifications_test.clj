(ns logical-expressions.dnf.transform.simplifications-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.dnf.transform.test-shared :refer :all]
            [logical-expressions.core.transform.shared :refer :all]
            [logical-expressions.dnf.transform.simplifications :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.util :refer :all]))

(def input-outputs (list [(disjunction var-a var-c)
                          (disjunction var-a var-c)]
                         [(disjunction var-a var-a)
                          var-a]
                         [(disjunction var-a
                                       (negation var-a))
                          true-expr]
                         [(disjunction var-a
                                       (negation var-a)
                                       true-expr)
                          true-expr]
                         [(conjunction var-b var-b var-b)
                          var-b]
                         [(conjunction var-a
                                       (negation var-a))
                          false-expr]
                         [(disjunction (conjunction var-a
                                                    (negation var-a))
                                       (conjunction var-b var-b var-b)
                                       false-expr)
                          var-b]))

(def test-case {:transforms    (list simplify-expr-transforms)
                :name          "Simplifications transform test"
                :input-outputs input-outputs})

(deftest test-simplifications-transform
  (run-test-case test-case))
