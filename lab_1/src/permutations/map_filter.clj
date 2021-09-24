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
  (nth (iterate #(enrich-words %1 alphabet)
                '(()))
       n))

(defn -main []
  (permutations 2 '("a" (:b [:c "d"]))))
