(ns parallel-filter.lazy-parallel-filter
  (:require [parallel-filter.parallel-filter :refer [pfilter thread-count]]))

(def default-chunk-size 64)

(defn split-seq [chunk-size coll]
  (let [[chunk rest-coll] (split-at chunk-size coll)]
    (when (not (empty? coll))
      (lazy-seq (cons chunk
                      (split-seq chunk-size rest-coll))))))

(defn lazy-pfilter
  ([pred coll]
   (lazy-pfilter pred coll default-chunk-size))
  ([pred coll chunk-size]
   (->> (split-seq (* chunk-size thread-count)
                   coll)
        (mapcat (partial pfilter pred)))))