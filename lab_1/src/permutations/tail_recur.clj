(ns permutations.tail_recur)

(defn enrich-word
  ([word alphabet] (enrich-word word alphabet ()))
  ([word alphabet result]
   (if (not-empty alphabet)
     (let [current-element (first alphabet)
           current-res (if (not= current-element (last word))
                         (cons (concat word (list current-element))
                               result)
                         result)]
       (recur word (rest alphabet) current-res))
     result)))

(defn enrich-words
  ([words alphabet] (enrich-words words alphabet ()))
  ([words alphabet result]
   (if (not-empty words)
     (recur (rest words)
            alphabet
            (concat result
                    (enrich-word (first words)
                                 alphabet)))
     result)))

(defn permutations
  ([n alphabet] (permutations n
                              alphabet
                              (map #(list %) alphabet)))
  ([n alphabet result]
   (if (not= 1 n)
     (recur (dec n)
            alphabet
            (enrich-words result alphabet))
     result)))

(defn -main
  []
  (permutations 2 '("a" (:b [:c "d"]))))
