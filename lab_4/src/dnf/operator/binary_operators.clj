(ns dnf.operator.binary-operators
  (:require [dnf.operator.shared :refer [is?]]))

; Conjunction API

(defn conjunction [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::conj expr rest)))

(defn conjunction? [expr]
  (is? expr ::conj))

; Disjunction API

(defn disjunction [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::disj expr rest)))

(defn disjunction? [expr]
  (is? expr ::disj))

; Implication API

(defn implication [expr & rest]
  (if (empty? rest)
    expr
    (apply list ::impl expr rest)))

(defn implication? [expr]
  (is? expr ::impl))