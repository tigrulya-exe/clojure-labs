(ns parallel-filter.parallel-filter)

(def thread-count (.availableProcessors (Runtime/getRuntime)))

(defn split-seq [coll chunk-sizes]
  (let [chunk-size (first chunk-sizes)
        split-coll (split-at chunk-size coll)]
    (lazy-seq (cons (first split-coll)
                    (split-seq (second split-coll)
                               (rest chunk-sizes))))))

(defn partition [chunk-count coll]
  (let [coll-size (count coll)
        chunk-size (quot coll-size chunk-count)
        remainder (rem coll-size chunk-count)]
    (->> (range)
         (map #(if (< % remainder)
                 (inc chunk-size)
                 chunk-size))
         (split-seq coll)
         (take chunk-count))))

(defn pfilter [pred coll]
  (->> (partition thread-count coll)
       (map #(future (doall (filter pred %))))
       (doall)
       (mapcat deref)))