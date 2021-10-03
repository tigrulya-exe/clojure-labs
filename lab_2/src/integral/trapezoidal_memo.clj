(ns integral.trapezoidal_memo)

(defn area [f x_i x_i+1]
  (* (double (/ (+ (f x_i) (f x_i+1))
                2))
     (- x_i+1 x_i)))

(def area-memo (memoize area))

(defn integral [f area-f steps]
  (fn [x]
    (let [step (double (/ x steps))]
      (reduce #(+ %1
                  (area-f f
                          (* step %2)
                          (* step (inc %2))))
              (range steps)))))

(defn integral-with-step [f area-f step]
  (fn [x]
    (let [steps (double (/ x step))]
      (reduce #(+ %1
                  (area-f f
                          (* step %2)
                          (* step (inc %2))))
              (range steps)))))

(defn integral-memo [f]
  (integral-with-step f area-memo 0.01))

(defn integral-simple [f]
  (integral-with-step f area 0.01))


