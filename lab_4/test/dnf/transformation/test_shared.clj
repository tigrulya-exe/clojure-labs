(ns dnf.transformation.test-shared
  (:require [clojure.test :refer :all]
            [dnf.transform-engine :refer :all]
            [dnf.operator.str :refer :all]))

(defn check-input [transforms [input expected]]
  (let [result (transform-expr transforms input)]
    (println (str "\"" (expr-str input)
                  "\" transformed to \""
                  (expr-str result) "\". Check equality with \""
                  (expr-str expected) "\""))
    (is (= expected result))))

(defn run-test-case [test-case]
  (testing
    (test-case :name)
    (->> (test-case :input-outputs)
         (run! (partial check-input
                        (test-case :transforms))))))

(defn run-test-cases [test-cases]
  (run! run-test-case test-cases))
