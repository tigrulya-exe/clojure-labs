(ns dnf.evaluation.operators
  (:require [dnf.operator.unary-operators :refer :all]
            [dnf.operator.shared :refer :all]))

(defn- eval-args [expr eval-fn]
  (->> (args expr)
       (map eval-fn)))

(defn eval-disjunction [eval-fn expr]
  (some identity (eval-args expr eval-fn)))

(defn eval-conjunction [eval-fn expr]
  (every? identity (eval-args expr eval-fn)))

(defn eval-negation [eval-fn expr]
  (->> (eval-args expr eval-fn)
       (first)
       (not)))

(defn eval-variable [vars expr]
  (vars (variable-name expr)))
