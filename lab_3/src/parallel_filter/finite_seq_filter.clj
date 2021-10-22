(ns parallel-filter.finite-seq-filter)

(def cpu-count (.availableProcessors (Runtime/getRuntime)))

(defn partition-step [coll chunk-sizes]
  (let [chunk-size (first chunk-sizes)]
    (lazy-seq (cons (take chunk-size coll)
                    ; split?
                    (partition-step (drop chunk-size coll)
                                    (rest chunk-sizes))))))

(defn my-partition [chunk-count coll]
  (let [coll-size (count coll)
        chunk-size (quot coll-size chunk-count)
        remainder (rem coll-size chunk-count)]
    (->> (iterate inc 0)
         (map #(if (< % remainder) (inc chunk-size) chunk-size))
         (partition-step coll)
         (take chunk-count))))

(defn finite-filter [pred coll]
  (->> (my-partition cpu-count coll)
       (map #(future (doall (filter pred %))))
       (doall)
       (mapcat deref)))