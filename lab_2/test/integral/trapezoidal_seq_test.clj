(ns integral.trapezoidal-seq-test
  (:require [clojure.test :refer :all]
            [integral.test-shared :refer :all]
            [integral.trapezium-rule-seq :refer :all]))

(deftest integral-seq-test
  (test-integral integral-seq))