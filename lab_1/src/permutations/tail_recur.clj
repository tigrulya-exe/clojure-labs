;1.2
(ns permutations.tail_recur
  (:gen-class))

(defn enrich-word [word alphabet]
  (loop [alphabet alphabet
         result ()]
    (if (not-empty alphabet)
      (let [current-element (first alphabet)
            current-res (if (not= current-element (first word))
                          (cons (conj word current-element)
                                result)
                          result)]
        (recur (rest alphabet) current-res))
      result)))

(defn enrich-words [words alphabet]
  (loop [words words
         result ()]
    (if (not-empty words)
      (recur (rest words)
             (concat result
                     (enrich-word (first words)
                                  alphabet)))
      result)))

(defn permutations [n alphabet]
  (loop [n n
         result '(())]
    (if (> n 0)
      (recur (dec n)
             (enrich-words result alphabet))
      result)))

(defn -main []
  (permutations 2 '("a" (:b [:c "d"]))))
