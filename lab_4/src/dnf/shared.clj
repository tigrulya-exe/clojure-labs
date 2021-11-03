(ns dnf.shared)

(defn is? [expr type]
  (= type (first expr)))

; TODO
(defn args [expr]
  (rest expr))

(defn apply-to-args [supplier fn expr]
  (->> (args expr)
       (map fn)
       (apply supplier)))

