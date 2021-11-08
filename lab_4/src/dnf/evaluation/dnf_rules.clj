(ns dnf.evaluation.dnf-rules
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.binary-operators :refer :all]
            [dnf.transform-engine :refer :all]
            [dnf.evaluation.operators :refer :all]))

(defn- build-evaluate-fn [eval-fn recur-eval-fn]
  (fn [vars expr] (recur-eval-fn (partial eval-fn vars)
                                 expr)))

(def eval-dnf-rules
  (let [recur-eval-fn #(apply-eval eval-dnf-rules %1 %2)
        ; why it doesn't work (ﾉಥ益ಥ)ﾉ
        ; (partial apply-eval eval-dnf-rules)
        build-eval-fn (partial build-evaluate-fn recur-eval-fn)]
    (list [variable? eval-variable]
          [disjunction?
           (build-eval-fn eval-disjunction)]
          [conjunction?
           (build-eval-fn eval-conjunction)]
          [negation?
           (build-eval-fn eval-negation)])))