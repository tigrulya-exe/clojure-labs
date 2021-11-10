(ns logical-expressions.dnf.eval.rules
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.binary :refer :all]
            [logical-expressions.core.eval.engine :refer :all]
            [logical-expressions.core.eval.operations :refer :all]))

(defn- build-evaluate-fn [eval-fn recur-eval-fn]
  (fn [vars expr] (recur-eval-fn (partial eval-fn vars)
                                 expr)))

; DNF expression evaluation rules
(def eval-dnf-rules
  (let [recur-eval-fn #(eval-expr eval-dnf-rules %1 %2)
        ; why it doesn't work (ﾉಥ益ಥ)ﾉ
        ; (partial apply-eval-expr eval-dnf-rules)
        build-eval-fn (partial build-evaluate-fn recur-eval-fn)]
    (list [variable? eval-variable]
          [disjunction?
           (build-eval-fn eval-disjunction)]
          [conjunction?
           (build-eval-fn eval-conjunction)]
          [negation?
           (build-eval-fn eval-negation)])))

; Evaluates given DNF expression
(def eval-dnf-expr
  (partial eval-expr eval-dnf-rules))