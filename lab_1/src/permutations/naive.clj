(ns permutations.naive)

(defn enrich-with-element
  [element collection]
  (when (not= (last collection) element)
    (concat collection (list element))))

(defn enrich-collection
  [collection alphabet]
  (when (not= 0 (count alphabet))
    (conj (enrich-collection collection (rest alphabet))
          (enrich-with-element (first alphabet) collection))
    ))

(defn enrich-collections
  [collections alphabet]
  (when (not= 0 (count collections))
    (conj (enrich-collections (rest collections) alphabet)
          (enrich-collection (first collections) alphabet))
    ))

(defn permutations
  [n alphabet]
  (if (not= 0 n)
    (let [result (permutations (dec n) alphabet)]
      (concat (enrich-collections result alphabet) result))
    alphabet))

(defn -main
  [& x]
  (permutations 2 '("a" (:b [:c "d"]))))
