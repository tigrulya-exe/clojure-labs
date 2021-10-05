; 2.2
(ns integral.trapezium-rule-seq
  (:require [integral.trapezium-rule :refer [area]]))

(defn generate-area-seq [f step]
  (map first
       (iterate (fn [[areas x_i]]
                  [(+ areas (area f x_i step)) (+ x_i step)])
                [0 0])))

(defn integral [f step]
  (let [seq (generate-area-seq f step)]
    (fn [x] (nth seq (/ x step)))))

(defn integral-seq [f step]
  (integral f step))


