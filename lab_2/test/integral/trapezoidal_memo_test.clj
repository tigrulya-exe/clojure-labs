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
    (doall
      (for [delta (range 20)]
        (time ((integral-memo test-fn) (+ 10 delta)))))))

(deftest integral-simple-test
  (testing
    (doall
      (for [delta (range 20)]
        (time ((integral-simple test-fn) (+ 10 delta)))))))

