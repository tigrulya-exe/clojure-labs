(ns dnf.transformation.take-out-disjunction-test
  (:require [clojure.test :refer :all]
            [dnf.transformation.test-shared :refer :all]
            [dnf.transformation.take-out-disjunction :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]))

(def input-outputs (list [(conjunction (variable :a)
                                       (variable :b))
                          (conjunction (variable :a)
                                       (variable :b))]
                         [(conjunction (variable :a)
                                       (variable :b)
                                       (variable :c)
                                       (disjunction (variable :d)
                                                    (variable :e)))
                          (disjunction (conjunction (variable :d)
                                                    (variable :a)
                                                    (variable :b)
                                                    (variable :c))
                                       (conjunction (variable :e)
                                                    (variable :a)
                                                    (variable :b)
                                                    (variable :c)))]
                         [(conjunction (variable :a)
                                       (disjunction (variable :b)
                                                    (variable :c)))
                          (disjunction (conjunction (variable :b)
                                                    (variable :a))
                                       (conjunction (variable :c)
                                                    (variable :a)))]
                         [(conjunction (disjunction (variable :a)
                                                    (variable :b))
                                       (disjunction (variable :c)
                                                    (variable :d)))
                          (disjunction (disjunction
                                         (conjunction (variable :c)
                                                      (variable :a))
                                         (conjunction (variable :d)
                                                      (variable :a)))
                                       (disjunction
                                         (conjunction (variable :c)
                                                      (variable :b))
                                         (conjunction (variable :d)
                                                      (variable :b))))]))

(def test-case {:transforms    (list take-out-disjunction-transforms)
                :name          "Take out disjunction transform test"
                :input-outputs input-outputs})

(deftest test-take-out-disjunction-transform
  (run-test-case test-case))