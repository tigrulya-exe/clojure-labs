(ns parallel-filter.lazy-parallel-filter-test
  (:require [clojure.test :refer :all]
            [parallel-filter.lazy-parallel-filter :refer :all]
            [parallel-filter.test-shared :refer :all]))

(defn test-predicate [elem]
  (Thread/sleep 100)
  (odd? elem))

(defn test-filter-fn [filter-fn]
  (time (->> (range)
             (filter-fn test-predicate)
             (take 25)
             (doall))))

(deftest filter-fn-test
  (testing "Check default filter"
    (test-filter-fn filter))
  (testing "Check parallel non-finite filter"
    (test-filter-fn lazy-pfilter)))

(deftest finite-case-test
  (run-finite-test-cases lazy-pfilter
                         [simple-number-test-case simple-str-test-case]))

(deftest infinite-case-test
  (run-infinite-test-cases lazy-pfilter
                           [infinite-number-test-case]))
