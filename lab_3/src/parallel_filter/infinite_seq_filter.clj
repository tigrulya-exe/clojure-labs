(ns parallel-filter.infinite-seq-filter)

(def chunk-size 5)
(def cpu-count (.availableProcessors (Runtime/getRuntime)))

(defn partition-step [chunk-size coll]
  [(drop chunk-size coll)
   (take chunk-size coll)])

(defn my-partition [chunk-size coll]
  (->> (iterate #(partition-step chunk-size
                                 (first %))
                [coll '()])
       (map second)
       (drop 1)
       (take-while not-empty)))

;(defn my-partition [chunk-size coll]
;  (->> (iterate (partial drop chunk-size) coll)
;       (map (partial take chunk-size))
;       (drop 1)))

(defn lazy-step [partitions-for-threads pred]
  (->> (map #(future (filter pred %)) partitions-for-threads)
       (doall)
       (map deref)))

(defn infinite-filter [pred coll]
  (->> (my-partition chunk-size coll)
       (my-partition cpu-count)
       (map #(lazy-step % pred))
       (flatten)))

