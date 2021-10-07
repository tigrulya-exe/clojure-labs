(ns integral.core
  (:require [integral.trapezium-rule :refer [integral-simple]]
            [integral.trapezium-rule-seq :refer [integral-seq]]
            [integral.trapezium-rule-memo :refer [integral-memo]]))

(def step 0.001)

(defn test-fn [x]
  (do
    (Thread/sleep 1)
    (double
      (/ (* (Math/sin x)
            (Math/cosh (* 2 x)))
         (Math/pow x 12)))))

(defmacro my-time [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr]
     ret#
     (double (/ (- (. System (nanoTime))
                   start#)
                1000000.0))
     ))

(defn print-result [name time i]
  (println (str "Iteration [" i "] " name " : " time " sec")))

(defn -main []
  (let [primitives {:simple (integral-simple test-fn step)
                    :memo   (integral-memo test-fn step)
                    :seq    (integral-seq test-fn step)}]
    (doall (for [delta (range 10)]
             (map (fn [[name primitive]]
                    (print-result name
                                  (my-time (primitive (+ 5 delta)))
                                  delta))
                  primitives)))))
