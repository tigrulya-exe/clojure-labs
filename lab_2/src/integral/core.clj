(ns integral.core
  (:require [integral.trapezium-rule :refer [integral-simple]]
            [integral.trapezium-rule-seq :refer [integral-seq]]
            [integral.trapezium-rule-memo :refer [integral-memo]]))

(def step 1/100)

(defn test-fn [x]
  (do
    (Thread/sleep 10)
    (double
      (/ (* (Math/sin x)
            (Math/cosh (* 2 x)))
         (Math/pow (+ x 1) 12)))))

(defmacro my-time [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr]
     ret#
     (double (/ (- (. System (nanoTime))
                   start#)
                1000000.0))))

(defn print-result [name time i]
  (println (str "Iteration [" i "] " name " : " time " msecs")))

(defn -main []
  (let [primitives {:simple (integral-simple test-fn step)
                    :memo   (integral-memo test-fn step)
                    :seq    (integral-seq test-fn step)}]
    (doall (for [delta (range 30)]
             (map (fn [[name primitive]]
                    (print-result name
                                  (my-time (primitive (+ 1 (/ delta 10))))
                                  delta))
                  primitives)))))
