(ns integral.trapezium-rule)

(defn area [f x_i step]
  (* (/ (+ (f x_i)
           (f (+ x_i step)))
        2)
     step))

(defn integral [f area-fn step]
  #(let [steps (/ % step)]
     (reduce (fn [acc i]
               (+ acc
                  (area-fn f
                           (* step i)
                           step)))
             0
             (range steps))))

(defn integral-simple [f step]
  (integral f area step))

