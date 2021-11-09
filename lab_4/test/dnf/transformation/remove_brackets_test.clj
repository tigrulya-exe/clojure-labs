(ns dnf.transformation.remove-brackets-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.remove-brackets :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(disjunction (disjunction (variable :a)
                                                    (variable :b))
                                       (variable :c))
                          (disjunction (variable :b)
                                       (variable :a)
                                       (variable :c))]
                         [(conjunction (conjunction (variable :a)
                                                    (variable :b))
                                       (variable :c))
                          (conjunction (variable :b)
                                       (variable :a)
                                       (variable :c))]))

(def test-case {:transforms    (list remove-brackets-transforms)
                :name          "Remove brackets transform test"
                :input-outputs input-outputs})

(deftest test-remove-brackets-transform
  (run-test-case test-case))