(ns integral.trapezoidal-seq-test
  (:require [clojure.test :refer :all]
            [integral.test-shared :refer :all]
            [integral.trapezium-rule-seq :refer :all]))

(deftest integral-seq-test
  (test-integral integral-seq 0.000001 0.0001))

(deftest integral-seq-reductions-test
  (test-integral (partial integral-seq generate-area-seq-reductions)
                 0.000001
                 0.0001))

(deftest integral-lazy-seq-test
  (test-integral (partial integral-seq generate-area-lazy-seq)
                 0.000001
                 0.0001))
