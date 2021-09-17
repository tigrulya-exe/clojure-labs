(ns permutations.naive)

(defn enrich-collection
  [collection alphabet]
  (when (not-empty alphabet)
    (let [recur-enrich-collection (enrich-collection collection
                                                     (rest alphabet))]
      (if (not= (first alphabet) (last collection))
        (conj recur-enrich-collection
              (concat collection (list (first alphabet))))
        recur-enrich-collection))))

(defn enrich-collections
  [collections alphabet]
  (when (not-empty collections)
    (concat (enrich-collections (rest collections) alphabet)
            (enrich-collection (first collections) alphabet))))

(defn permutations
  [n alphabet]
  (if (not= 1 n)
    (enrich-collections (permutations (dec n)
                                      alphabet)
                        alphabet)
    (map #(list %) alphabet)))

(defn -main
  [& x]
  (permutations 2 '("a" (:b [:c "d"]))))
