(ns logical-expressions.core.operation.binary
  (:require [logical-expressions.core.operation.shared :refer [is?]]))

; Conjunction API

(defn conjunction
  "Creates conjunction expression.
  Returns itself if single expression is provided"
  [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::conj expr rest)))

(defn conjunction?
  "Checks if expression is conjunction"
  [expr]
  (is? expr ::conj))

; Disjunction API

(defn disjunction
  "Creates disjunction expression.
  Returns itself if single expression is provided"
  [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::disj expr rest)))

(defn disjunction?
  "Checks if expression is disjunction"
  [expr]
  (is? expr ::disj))

; Implication API

(defn implication
  "Creates implication expression.
  Returns itself if single expression is provided"
  [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::impl expr rest)))

(defn implication?
  "Checks if expression is implication"
  [expr]
  (is? expr ::impl))