(ns parallel-filter.test-shared
  (:require [clojure.test :refer :all]))

(defn rand-str [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(def simple-number-test-case
  {:name      "Check isOdd in range 100"
   :predicate odd?
   :input     (range 100)
   :result    (range 1 100 2)})

(def simple-str-test-case
  {:name      "Check equality for str in rand strs"
   :predicate #(= "test_str" %)
   :input     (concat (rand-str 256) '("test_str"))
   :result    '("test_str")})

(def infinite-number-test-case
  {:name      "Check isOdd in infinite range (take 1024)"
   :predicate odd?
   :input-gen range
   :bound     1024
   :result    (range 1 2048 2)})

(defn test-finite-case [filter-fn test-case]
  (testing (test-case :name)
    (println-str test-case)
    (is (= (filter-fn (test-case :predicate) (test-case :input))
           (test-case :result)))))

(defn test-infinite-case [filter-fn test-case]
  (testing (test-case :name)
    (println-str test-case)
    (is (= (->> ((test-case :input-gen))
                (filter-fn (test-case :predicate))
                (take (test-case :bound)))
           (test-case :result)))))

(defn run-test-cases [filter-fn test-fn cases]
  (run! (partial test-fn filter-fn) cases))

(defn run-finite-test-cases [filter-fn cases]
  (run-test-cases filter-fn test-finite-case cases))

(defn run-infinite-test-cases [filter-fn cases]
  (run-test-cases filter-fn test-infinite-case cases))