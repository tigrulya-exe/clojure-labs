;1.1
(ns permutations.naive)

(defn enrich-word [word alphabet]
  (when (not-empty alphabet)
    (let [recur-result (enrich-word word
                                    (rest alphabet))]
      (if (not= (first alphabet) (first word))
        (conj recur-result
              (conj word (first alphabet)))
        recur-result))))

(defn enrich-words [words alphabet]
  (when (not-empty words)
    (concat (enrich-words (rest words) alphabet)
            (enrich-word (first words) alphabet))))

(defn permutations [n alphabet]
  (if (> n 0)
    (enrich-words (permutations (dec n) alphabet)
                  alphabet)
    '(())))

(defn -main []
  (permutations 2 '("a" (:b [:c "d"]))))
