; 1.4
(ns permutations.map-filter)

(defn enrich-word [word alphabet]
  (map #(conj word %1)
       (filter #(not= %1 (first word))
               alphabet)))

(defn enrich-words [words alphabet]
  (reduce concat
          (map #(enrich-word %1 alphabet) words)))

(defn permutations [n alphabet]
  (reduce (fn [acc _] (enrich-words acc alphabet))
          '(())
          (range 1 (inc n))))

(defn -main []
  (permutations 2 '("a" (:b [:c "d"]))))
