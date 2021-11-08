(ns dnf.transform-machine-test
  (:require [clojure.test :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.str :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.transformation.dnf-transformations :refer :all]
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

; 1 -> 0 -> c
(def implication-expr-3 (implication (constant true)
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

(deftest a-test
  (testing "FIXME"
    (let [result (transform-expr dnf-transformations negation-expr-2)]
      (println (expr-str result)))))

(deftest a-test
  (testing "FIXME"
    (let [result (eval-expr {:a true :b false :c true} several-ops-expr)]
      (println result))))
