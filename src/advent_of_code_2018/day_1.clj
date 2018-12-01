(ns advent-of-code-2018.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-1-input.txt")))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map #(Long/parseLong %))))

(defn solution-part-one [input]
  (reduce + (parse-input input)))

;; Part two

(defn solution-part-two [input]
  (->> (cycle (parse-input input))
       (reductions + 0)
       (reduce (fn [acc x]
                 (if (contains? acc x)
                   (reduced x)
                   (conj acc x)))
               #{})))



