; 2.1
(ns integral.trapezium-rule-memo)

(def area-memo (memoize (fn area [f x_i step]
                          (if (not= 0 x_i)
                            (+ (area-memo f (- x_i step) step)
                               (* (/ (+ (f x_i)
                                        (f (- x_i step)))
                                     2)
                                  step))
                            0.0))))

(defn integral-memo [f step]
  #(area-memo f
              (* step (quot % step))
              step))
