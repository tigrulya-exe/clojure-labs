(ns dnf.transform-machine)

(defn apply-transform [expr transform-table]
  ((some (fn [[pred transform]]
           (if (pred expr) transform false))
         transform-table)
   expr))

(defn transform-expr [transforms expr]
  (reduce apply-transform
          expr
          transforms))