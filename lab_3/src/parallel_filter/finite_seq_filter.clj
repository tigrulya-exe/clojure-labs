(ns parallel-filter.finite-seq-filter)

(def cpu-count (.availableProcessors (Runtime/getRuntime)))

(defn partition-step [chunk-size coll]
  [(drop chunk-size coll)
   (take chunk-size coll)])

(defn my-partition [chunk-count coll]
  (let [coll-size (count coll)
        chunk-size (quot coll-size chunk-count)
        remainder (rem coll-size chunk-count)]
    (->> (iterate inc 0)
         (map #(if (< % remainder)
                 (inc chunk-size)
                 chunk-size))
         (reductions (fn [[iter _] ch-sz]
                       (partition-step ch-sz iter))
                     [coll, '()])
         (drop 1)
         (take chunk-count)
         (map second))))

(defn finite-filter [pred coll]
  (->> (my-partition cpu-count coll)
       (map #(future (filter pred %)))
       (doall)
       (map deref)
       (flatten)))

