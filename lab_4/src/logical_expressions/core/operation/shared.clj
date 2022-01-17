(ns logical-expressions.core.operation.shared)

(defn is? [expr type]
  (= type (first expr)))

(defn args
  "Returns arguments of expression"
  [expr]
  (rest expr))
