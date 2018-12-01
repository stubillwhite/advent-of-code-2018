(ns advent-of-code-2018.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-1-input.txt")))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map #(Integer/parseInt %))))

(defn solution-part-one [input]
  (reduce + (parse-input input)))

