; 2.1
(ns integral.trapezium-rule-memo)

(defn integral-memo [f step]
  (let [area (memoize (fn [rec-fn f x_i step]
                        (if (not= 0 x_i)
                          (+ (rec-fn rec-fn f (- x_i step) step)
                             (* (/ (+ (f x_i)
                                      (f (- x_i step)))
                                   2)
                                step))
                          0.0)))
        area-memo (partial area area)]
    #(area-memo f
                (* step (quot % step))
                step)))
