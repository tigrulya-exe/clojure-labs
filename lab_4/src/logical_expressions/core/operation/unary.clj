(ns logical-expressions.core.operation.unary
  (:require [logical-expressions.core.operation.shared :refer [is?]]))

; Constant API

(defn constant
  "Creates constant expression"
  [value]
  (list ::const value))

(defn constant?
  "Checks if expression is variable"
  [expr]
  (is? expr ::const))

(defn constant-value
  "Provides value of constant expression"
  [expr]
  (second expr))

; Variable API

(defn variable
  "Creates variable expression"
  [name]
  (:pre [(keyword? name)])
  (list ::var name))

(defn variable?
  "Checks if expression is variable"
  [expr]
  (is? expr ::var))

(defn variable-name
  "Provides name of variable expression"
  [expr]
  (second expr))

; Negation API

(defn negation
  "Creates negation expression"
  [expr]
  (list ::negation expr))

(defn negation?
  "Checks if expression is negation"
  [expr]
  (is? expr ::negation))