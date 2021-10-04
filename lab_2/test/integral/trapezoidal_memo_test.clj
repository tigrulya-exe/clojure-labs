(ns integral.trapezoidal-memo-test
  (:require [clojure.test :refer :all]
            [integral.trapezoidal_memo :refer :all]))

(defn test-fn [x]
  (do
    (Thread/sleep 1)
    (double (Math/log
              (/ (* (Math/sin x)
                    (Math/cosh (* 2 x)))
                 (Math/pow x 12))))))

(deftest integral-memo-test
  (testing
    (let [integral-fn (integral-memo test-fn)]
      (doall (for [delta (range 20)]
               (time (integral-fn (+ 10 delta))))))))

(deftest integral-simple-test
  (testing
    (let [integral-fn (integral-simple test-fn)]
      (doall (for [delta (range 20)]
               (time (integral-fn (+ 10 delta))))))))


