; 2.1
(ns integral.trapezium-rule-memo
  (:require [integral.trapezium-rule :refer [integral area]]))

(def area-memo (memoize area))

(defn integral-memo [f step]
  (integral f area-memo step))

