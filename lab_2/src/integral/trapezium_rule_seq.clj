; 2.2
(ns integral.trapezium-rule-seq
  (:require [integral.trapezium-rule :refer [area]]))

(defn generate-area-seq [f step]
  (map first
       (iterate (fn [[areas x_i]]
                  [(+ areas (area f x_i step)) (+ x_i step)])
                [0 0])))

(defn generate-area-seq-reductions [f step]
  (reductions
    (fn [areas i]
      (+ areas (area f (* i step) step)))
    0
    (range)))

(defn generate-area-lazy-seq
  ([f step]
   (generate-area-lazy-seq f step 0 0.0))
  ([f step i areas]
   (lazy-seq
     (cons areas
           (generate-area-lazy-seq f
                                   step
                                   (inc i)
                                   (+ (area f (* i step) step)
                                      areas))))))

(defn integral-seq
  ([f step]
   (integral-seq generate-area-seq f step))
  ([area-fn f step]
   (let [seq (area-fn f step)]
     (fn [x] (nth seq (/ x step))))))


