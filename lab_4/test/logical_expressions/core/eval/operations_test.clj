(ns logical-expressions.core.eval.operations-test
  (:require [clojure.test :refer :all]
            [logical-expressions.core.transform.shared :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.eval.operations :refer :all]))

(defn check-eval-fn [eval-fn [expr expected]]
  (is (= expected (eval-fn identity expr))))

(defn test-eval-fn [test-name eval-fn pairs]
  (testing
    test-name
    (map (partial check-eval-fn eval-fn)
         pairs)))

(deftest test-eval-disjunction
  (test-eval-fn
    "Check eval disjunction"
    eval-disjunction
    (list [(disjunction true-expr true-expr false-expr)
           true]
          [(disjunction false-expr false-expr)
           false])))

(deftest test-eval-conjunction
  (test-eval-fn
    "Check eval conjunction"
    eval-conjunction
    (list [(conjunction true-expr true-expr false-expr)
           false]
          [(conjunction false-expr false-expr)
           false]
          [(conjunction true-expr true-expr)
           true])))

(deftest test-eval-negation
  (test-eval-fn
    "Check eval negation"
    eval-negation
    (list [(negation false-expr)
           true]
          [(negation true-expr)
           false])))