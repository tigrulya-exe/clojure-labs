(ns logical-expressions.core.transform.engine)

(defn apply-transform
  ([expr transform-table]
   (apply-transform expr
                    transform-table
                    (list expr)))
  ([expr transform-table args]
   (apply (some (fn [[pred transform]]
                  (if (pred expr) transform false))
                transform-table)
          args)))

(defn transform-expr [transforms expr]
  (reduce apply-transform
          expr
          transforms))