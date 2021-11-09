(ns dnf.transformation.bring-negation-in-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.bring-negation-in :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(negation (negation (variable :a)))
                          (variable :a)]
                         [(negation (disjunction (variable :a)
                                                 (variable :b)
                                                 (variable :c)))
                          (conjunction (negation (variable :a))
                                       (negation (variable :b))
                                       (negation (variable :c)))]
                         [(negation (conjunction (variable :a)
                                                 (disjunction (variable :b)
                                                              (constant true))))
                          (disjunction (negation (variable :a))
                                       (conjunction (negation (variable :b))
                                                    (negation (constant true))))]))

(def test-case {:transforms    (list bring-negation-transforms)
                :name          "Bring negation in transform test"
                :input-outputs input-outputs})

(deftest test-bring-negation-in-transform
  (run-test-case test-case))