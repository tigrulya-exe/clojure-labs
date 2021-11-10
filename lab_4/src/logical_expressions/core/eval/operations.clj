(ns logical-expressions.core.eval.operations
  (:require [logical-expressions.core.operation.unary :refer :all]
            [logical-expressions.core.operation.shared :refer :all]))

(defn- eval-args
  "Evaluate arguments of given expression with provided eval function"
  [expr eval-fn]
  (->> (args expr)
       (map eval-fn)))

(defn eval-disjunction
  "Recursively evaluates disjunction expression with provided eval function"
  [eval-fn expr]
  (some identity (eval-args expr eval-fn)))

(defn eval-conjunction
  "Recursively evaluates conjunction expression with provided eval function"
  [eval-fn expr]
  (every? identity (eval-args expr eval-fn)))

(defn eval-negation
  "Recursively evaluates negation expression with provided eval function"
  [eval-fn expr]
  (->> (eval-args expr eval-fn)
       (first)
       (not)))

(defn eval-variable
  "Recursively evaluates negation expression with provided eval function"
  [vars expr]
  (vars (variable-name expr)))
