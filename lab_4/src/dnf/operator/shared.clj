(ns dnf.operator.shared)

(defn is? [expr type]
  (= type (first expr)))

(defn args [expr]
  (rest expr))
