(ns logical-expressions.core.transform.engine)

(defn apply-transform
  "Transforms expression with provided transformation table.
   Transformation table is sequence of pairs [predicate-fn, transformation-fn],
   where predicate-fn is function, which took expression and returns boolean,
   meaning should transform be applied or not.
   Transformation-fn is function, taking and returning expression.
   Apply-transform applies only first transformation, which predicate returns true on provided expr.
   By default, passes expression to found transformation as argument if no 'args' provided"
  ([expr transform-table]
   (apply-transform expr
                    transform-table
                    (list expr)))
  ([expr transform-table args]
   (apply (some (fn [[pred transform]]
                  (if (pred expr) transform false))
                transform-table)
          args)))

(defn transform-expr
  "Transforms expression with provided transformations.
  See apply-transform for more details."
  [transforms expr]
  (reduce apply-transform
          expr
          transforms))