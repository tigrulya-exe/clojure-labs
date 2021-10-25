(ns parallel-filter.lazy-parallel-filter
  (:require [parallel-filter.parallel-filter :refer [pfilter thread-count]]))

(def default-chunk-size 64)

(defn split-seq [chunk-size coll]
  (let [split-coll (split-at chunk-size coll)]
   (lazy-seq (cons (first split-coll)
                  (split-seq chunk-size
                             (second split-coll))))))

(defn lazy-pfilter
  ([pred coll]
   (lazy-pfilter pred coll default-chunk-size))
  ([pred coll chunk-size]
   (->> (split-seq (* chunk-size thread-count)
                   coll)
        (take-while not-empty)
        (mapcat (partial pfilter pred)))))