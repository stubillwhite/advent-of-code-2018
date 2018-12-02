(ns advent-of-code-2018.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

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

