(ns integral.test-shared
  (:require [clojure.test :refer :all]))

(def step 0.0001)

(def identity-values
  {:function identity
   :values   [[2 2]
              [2.5 3.125]
              [5.123 13.1226]
              [4.686 10.9793]]})

(def sin-values
  {:function #(Math/sin %)
   :values   [[1 0.4597]
              [1.22 0.65635]
              [6.212 0.002533]
              [3.198 1.99841]]})

(defn equal-with-error [a b]
  (< (Math/abs (- a b)) 0.001))

(defn check-test-fn-results [primary-fn expected-results]
  (testing
    (doall (map (fn [[input expected]]
                  (is (equal-with-error expected (primary-fn input))))
                expected-results))))

(defn test-integral [integral-fn]
  (doall (map #(check-test-fn-results (integral-fn (% :function) step)
                                      (% :values))
              [identity-values
               sin-values])))

