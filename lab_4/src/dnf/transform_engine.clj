(ns dnf.transform-engine)

(defn- apply-transform-fn [transform-table args expr]
  (apply (some (fn [[pred transform]]
                 (if (pred expr) transform false))
               transform-table)
         args))

(defn apply-transform [expr transform-table]
  (apply-transform-fn transform-table
                      (list expr)
                      expr))

(defn transform-expr [transforms expr]
  (reduce apply-transform
          expr
          transforms))

; TODO перенести
(defn apply-eval [transform-table vars expr]
  (apply-transform-fn transform-table
                      (list vars expr)
                      expr))
