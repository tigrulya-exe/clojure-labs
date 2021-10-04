; 2.2
(ns integral.trapezoidal_seq)

(defn area [f x_i step]
  (* (/ (+ (f x_i)
           (f (+ x_i step)))
        2)
     step))

(defn generate-area-seq [f step]
  (map first
       (iterate (fn [[areas x_i]]
                  [(+ areas (area f x_i step)) (+ x_i step)])
                [0 0])))

(defn integral [f step]
  (let [seq (generate-area-seq f step)]
    (fn [x] (nth seq (/ x step)))))

(defn integral-seq [f]
  (integral f 0.00001))


