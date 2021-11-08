(ns dnf.operator.unary-operators
  (:require [dnf.operator.shared :refer [is?]]))

; Constant API

(defn constant [value]
  (list ::const value))

(defn constant? [expr]
  (is? expr ::const))

(defn constant-value [expr]
  (second expr))

; Variable API

(defn variable [name]
  (:pre [(keyword? name)])
  (list ::var name))

(defn variable? [expr]
  (is? expr ::var))

(defn variable-name [expr]
  (second expr))

; Negation API

(defn negation [expr]
  (list ::negation expr))

(defn negation? [expr]
  (is? expr ::negation))