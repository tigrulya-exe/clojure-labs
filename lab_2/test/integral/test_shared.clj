(ns integral.test-shared
  (:require [clojure.test :refer :all]))

(def step 0.000001)

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

; xsin(e/(x+1))cosh(Power[x,2])/(Power[x,x] + 2)
(def difficult-f-values
  {:function #(/ (* %
                    (Math/sin (/ Math/E (+ % 1)))
                    (Math/cosh (Math/pow % 2)))
                 (+ (Math/pow % %) 2))
   :values   [[1 0.201276]
              [0.16 0.00288381]
              [3.98 5949.7947]
              [1.8 1.45355]]})

(defn equal-with-error [a b]
  (< (Math/abs (- a b)) 0.0001))

(defn check-test-fn-results [primary-fn expected-results]
  (testing
    (doall (map (fn [[input expected]]
                  (is (equal-with-error expected (primary-fn input))))
                expected-results))))

(defn test-integral [integral-fn]
  (doall (map #(check-test-fn-results (integral-fn (% :function) step)
                                      (% :values))
              [identity-values
               sin-values
               difficult-f-values])))

