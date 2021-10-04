; 2.1
(ns integral.trapezoidal_memo)

(defn area [f x_i step]
  (* (/ (+ (f x_i)
           (f (+ x_i step)))
        2)
     step))

(def area-memo (memoize area))

(defn integral [f area-fn step]
  (fn [x]
    (let [steps (inc (/ x step))]
      (reduce #(+ %1
                  (area-fn f
                          (* step %2)
                          step))
              (range steps)))))

(defn integral-memo [f]
  (integral f area-memo 0.001))

(defn integral-simple [f]
  (integral f area 0.001))


