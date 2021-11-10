(ns logical-expressions.dnf.expr-evaluator-test
  (:require [clojure.test :refer :all]
            [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.util :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [dnf.transformation.transformations :refer :all]
            [dnf.expr-evaluator :refer :all]
            [dnf.transform-engine :refer :all]))

; (a & (b | c)) -> d
(def implication-expr (implication (conjunction (variable :a)
                                                (disjunction (variable :b)
                                                             (variable :c)))
                                   (variable :d)))

; a -> b -> c
(def implication-expr-2 (implication (variable :a)
                                     (variable :b)
                                     (variable :c)))

; a -> b
(def implication-expr-3 (implication (variable :a)
                                     (variable :b)))

; 1 -> 0 -> c
(def implication-expr-4 (implication (constant true)
                                     (constant false)))

; ~(a & b)
(def negation-expr (negation (conjunction (variable :a)
                                          (variable :b))))

; ~(a & (b | c))
(def negation-expr-2 (negation (conjunction (variable :a)
                                            (disjunction (variable :b)
                                                         (variable :c)))))

; (a & (b & c))
(def several-ops-expr (conjunction (variable :a)
                                   (conjunction (variable :b)
                                                (variable :c))))

; (a & (b & 1))
(def several-ops-expr-2 (conjunction (variable :a)
                                     (conjunction (variable :b)
                                                  (constant false))))

;(deftest a-test
;  (testing "FIXME"
;    (let [result (transform-expr dnf-transformations implication-expr-3)]
;      (println (expr-str result)))))
;
;(deftest b-test
;  (testing "FIXME"
;    (let [result (eval-expr {:a true :b false :c true} several-ops-expr)]
;      (println result))))
