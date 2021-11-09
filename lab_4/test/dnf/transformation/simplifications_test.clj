(ns dnf.transformation.simplifications-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.shared :refer :all]
            [dnf.transformation.simplifications :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(disjunction (variable :a)
                                       (variable :c))
                          (disjunction (variable :a)
                                       (variable :c))]
                         [(disjunction (variable :a)
                                       (variable :a))
                          (variable :a)]
                         [(disjunction (variable :a)
                                       (negation (variable :a)))
                          true-expr]
                         [(disjunction (variable :a)
                                       (negation (variable :a))
                                       true-expr)
                          true-expr]
                         [(conjunction (variable :b)
                                       (variable :b)
                                       (variable :b))
                          (variable :b)]
                         [(conjunction (variable :a)
                                       (negation (variable :a)))
                          false-expr]
                         [(disjunction (conjunction (variable :a)
                                                    (negation (variable :a)))
                                       (conjunction (variable :b)
                                                    (variable :b)
                                                    (variable :b))
                                       false-expr)
                          (variable :b)]))

(def test-case {:transforms    (list simplify-expr-transforms)
                :name          "Simplifications transform test"
                :input-outputs input-outputs})

(deftest test-simplifications-transform
  (run-test-case test-case))
