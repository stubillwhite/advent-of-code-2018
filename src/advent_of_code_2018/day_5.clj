(ns advent-of-code-2018.day-5
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-5-input.txt")))))

(defn- opposite-polarity? [a b]
  (and (not (nil? a))
       (not (nil? b))
       (not= a b)
       (or (= (Character/toUpperCase a) b) (= (Character/toLowerCase a) b))))

(defn- react [polymer]
  (loop [left  nil
         right (seq polymer)]
    (if (empty? right)
      (string/join (reverse left))
      (let [l (first left)
            r (first right)]
        (recur (if (opposite-polarity? l r) (rest left) (cons r left))
               (rest right))))))

(defn solution-part-one [input]
  (count (react input)))

;; Part two
