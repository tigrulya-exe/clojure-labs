(ns integral.trapezoidal-memo-test
  (:require [clojure.test :refer :all]
            [integral.test-shared :refer :all]
            [integral.trapezium-rule-memo :refer :all]))

(deftest integral-memo-test
  (test-integral integral-memo))