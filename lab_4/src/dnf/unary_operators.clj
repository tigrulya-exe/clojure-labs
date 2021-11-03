(ns dnf.unary-operators
  (:require [dnf.shared :refer [is?]]))

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

(defn same-variables? [lhr rhr]
  (and
    (variable? lhr)
    (variable? rhr)
    (= (variable-name lhr)
       (variable-name rhr))))

; Negation API

(defn negation [expr]
  (list ::negation expr))

(defn negation? [expr]
  (is? expr ::negation))