(ns logical-expressions.dnf.eval.rules-test
  (:require [clojure.test :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.eval.engine :refer :all]
            [logical-expressions.dnf.eval.rules :refer :all]))

(defn build-test-case [name vars input-outputs]
  {:transforms    eval-dnf-rules
   :name          name
   :input-outputs input-outputs
   :vars          vars})

(def simple-test-case (build-test-case
                        "simple-test-case"
                        {:a true
                         :b false
                         :c false}
                        (list [(disjunction (conjunction (variable :a)
                                                         (negation (variable :b)))
                                            (variable :a)
                                            (variable :c))
                               true]
                              [(disjunction (variable :a)
                                            (variable :c))
                               true]
                              [(conjunction (variable :a)
                                            (variable :c))
                               false])))

(defn check-input [transforms vars [input expected]]
  (let [result (eval-expr transforms vars input)]
    (println (str "Vars: " vars))
    (println (str "\"" (expr-str input)
                  "\" evaluated to \""
                  result "\". Check equality with \""
                  expected "\""))
    (is (= expected result))))

(defn run-test-case [test-case]
  (testing
    (test-case :name)
    (->> (test-case :input-outputs)
         (run! (partial check-input
                        (test-case :transforms)
                        (test-case :vars))))))

;TODO добавить тесты на взятие переменной из мапы

(deftest test-eval-dnf-rules
  (run-test-case simple-test-case))