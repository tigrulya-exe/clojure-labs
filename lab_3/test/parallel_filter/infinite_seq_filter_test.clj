(ns parallel-filter.infinite-seq-filter-test
  (:require [clojure.test :refer :all]
            [parallel-filter.infinite-seq-filter :refer :all]))

(defn test-predicate [elem]
  (Thread/sleep 100)
  (odd? elem))

(defn test-filter-fn [filter-fn]
  (time (->> (iterate inc 0)
             (take 50)
             (filter-fn test-predicate)
             ;(println)
             (doall))))

(deftest filter-fn-test
  (testing "Check default filter"
    (test-filter-fn filter))
  (testing "Check parallel non-finite filter"
    (doall (test-filter-fn infinite-filter))))
