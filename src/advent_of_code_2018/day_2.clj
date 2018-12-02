(ns advent-of-code-2018.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-2-input.txt")))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)))

(defn- letter-count [s]
  (reduce (fn [acc x] (update acc x (fnil inc 0)))
          {}
          s))

(defn- contains-val? [v m]
  (some #{v} (vals m)))

(defn- checksum [letter-counts]
  (let [n-occurrences (fn [n] (->> letter-counts (filter (partial contains-val? n)) (count)))]
    (* (n-occurrences 2) (n-occurrences 3))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map letter-count)
       (checksum)))

;; Part two

(defn- different-characters [a b]
  (->> (interleave a b)
       (partition 2)
       (filter (fn [[a b]] (not= a b)))))

(defn- matching-characters [a b]
  (->> (interleave a b)
       (partition 2)
       (filter (fn [[a b]] (= a b)))))

(defn- find-correct-boxes [input]
  (first
   (for [a input
         b input
         :when (and (not= a b)
                    (= 1 (count (different-characters a b))))]
     [a b])))

(defn- find-matching-characters [[a b]]
  (->> (matching-characters a b)
       (map first)
       (string/join)))

(defn solution-part-two [input]
  (->> (parse-input input)
       (find-correct-boxes)
       (find-matching-characters)))

