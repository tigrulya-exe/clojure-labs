(ns integral.trapezoidal-seq-test
  (:require [clojure.test :refer :all]
            [integral.trapezoidal_seq :refer :all]))

(defn test-fn [x]
  (do
    (Thread/sleep 1)
    (double (Math/log
              (/ (* (Math/sin x)
                    (Math/cosh (* 2 x)))
                 (Math/pow x 12))))))

(deftest integral-memo-test
  (testing
    (let [integral-fn (integral test-fn 0.001)]
      (doall (for [delta (range 20)]
               (time (integral-fn (+ 10 delta))))))))

