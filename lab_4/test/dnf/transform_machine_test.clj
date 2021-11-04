(ns dnf.transform-machine-test
  (:require [clojure.test :refer :all]
            [dnf.operator.unary-operators :refer :all]
            [dnf.operator.print :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.transformation.dnf-transformations :refer :all]
            [dnf.transform-machine :refer :all]))

; (a & (b | c)) -> d
(def implication-expr (implication (conjunction (variable :a)
                                                (disjunction (variable :b)
                                                             (variable :c)))
                                   (variable :d)))

; a -> b -> c
(def implication-expr-2 (implication (variable :a)
                                     (variable :b)
                                     (variable :c)))

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

(deftest a-test
  (testing "FIXME"
    (let [result (transform-expr dnf-transformations implication-expr-2)]
      (println (expr-str result)))))
