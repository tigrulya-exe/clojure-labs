(ns parallel-filter.parallel-filter-test
  (:require [clojure.test :refer :all]
            [parallel-filter.parallel-filter :refer :all]
            [parallel-filter.test-shared :refer :all]))

(defn test-predicate [elem]
  (Thread/sleep 100)
  (odd? elem))

(defn test-filter-fn [filter-fn]
  (time (->> (range)
             (take 50)
             (filter-fn test-predicate)
             (doall))))

(deftest filter-fn-test
  (testing "Check default filter"
    (test-filter-fn filter))
  (testing "Check parallel non-finite filter"
    (test-filter-fn pfilter)))

(deftest finite-case-test
  (run-finite-test-cases pfilter
                         [simple-number-test-case simple-str-test-case]))