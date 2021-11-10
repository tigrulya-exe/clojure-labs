(ns logical-expressions.dnf.expr-evaluator-test
  (:require [clojure.test :refer :all]
            [logical-expressions.test-shared :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.dnf.expr-evaluator :refer :all]))

(def variables {:a true
                :b false
                :c true
                :d false})

; (a & (b | c)) -> d
(def implication-expr (implication (conjunction var-a
                                                (disjunction var-b
                                                             var-c))
                                   var-d))

; a -> b -> c
(def implication-expr-2 (implication var-a var-b var-c))

; a -> b
(def implication-expr-3 (implication var-a var-b))

; 1 -> 0 -> c
(def implication-expr-4 (implication true-expr false-expr var-c))

; 1 -> 0
(def implication-expr-5 (implication true-expr false-expr))

; ~(a & b)
(def negation-expr (negation (conjunction var-a var-b)))

; ~(a & (b | c))
(def negation-expr-2 (negation (conjunction var-a
                                            (disjunction var-b var-c))))

; (a & (b & c))
(def several-ops-expr (conjunction var-a
                                   (conjunction var-b var-c)))

; (a & (b & 1))
(def several-ops-expr-2 (conjunction var-a
                                     (conjunction var-b false-expr)))

(defn build-test-case [name vars input-outputs]
  {:name          name
   :input-outputs input-outputs
   :vars          vars})

(defn check-input [vars [input expected]]
  (let [result (eval-expr vars input)]
    (println (str "\"" (expr-str input)
                  "\" evaluated to \""
                  result "\". Check equality with \""
                  expected "\"\n"))
    (is (= expected result))))

(defn run-test-case [test-case]
  (testing
    (test-case :name)
    (->> (test-case :input-outputs)
         (run! (partial check-input
                        (test-case :vars))))))

(deftest test-expr-evaluator
  (run-test-case
    (build-test-case "Expr-evaluator test"
                     variables
                     (list
                       [implication-expr false]
                       [implication-expr-2 true]
                       [implication-expr-3 false]
                       [implication-expr-4 true]
                       [implication-expr-5 false]
                       [negation-expr true]
                       [negation-expr-2 false]
                       [several-ops-expr false]
                       [several-ops-expr-2 false]))))
