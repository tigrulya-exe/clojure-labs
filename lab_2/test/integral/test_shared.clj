(ns integral.test-shared
  (:require [clojure.test :refer :all]))

(defn abs [n] (max n (- n)))

(def identity-values
  {:function identity
   :values   [[2 2]
              [5/2 3125/1000]
              [4670/1000 109045/10000]
              [5121/1000 131123/10000]]})

(def sin-values
  {:function #(Math/sin %)
   :values   [[1 4597/10000]
              [122/100 65635/100000]
              [3198/1000 19984/10000]
              [5086/1000 63502/100000]]})

; xsin(e/(x+1))cosh(Power[x,2])/(Power[x,x] + 2)
(def difficult-f-values
  {:function #(/ (* %
                    (Math/sin (/ Math/E (+ % 1)))
                    (Math/cosh (Math/pow % 2)))
                 (+ (Math/pow % %) 2))
   :values   [[1 201276/1000000]
              [16/100 2884/1000000]
              [18/10 14536/10000]
              [298/100 635486/10000]]})

(defn check-test-fn-results [primary-fn expected-results error]
  (testing
    (doall (map (fn [[input expected]]
                  (do
                    (println (str "test for input " input))
                    (is (< (abs (- expected (primary-fn input)))
                           error))))
                expected-results))))

(defn test-integral [integral-fn step error]
  (doall (map #(check-test-fn-results (integral-fn (% :function) step)
                                      (% :values)
                                      error)
              [identity-values
               sin-values
               difficult-f-values])))

