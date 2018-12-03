(ns advent-of-code-2018.day-3
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-3-input.txt")))))

(defn- parse-claim [s]
  (let [pattern #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)"
        matches (re-seq pattern s)
        [_ id x y width height] (first matches)
        parse-long #(Long/parseLong %)]
    {:id     id
     :x      (parse-long x)
     :y      (parse-long y)
     :width  (parse-long width)
     :height (parse-long height)}))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-claim)))

(def test-input
  (string/join "\n"
   ["#1 @ 1,3: 4x4"
    "#2 @ 3,1: 4x4"
    "#3 @ 5,5: 2x2"]))

(defn- fabric-used [{:keys [x y width height]}]
  (for [x' (range x (+ x width))
        y' (range y (+ y height))] [x' y']))

(defn- claim-fabric [fabric claim]
  (reduce (fn [acc x] (update acc x (fnil inc 0)))
          fabric
          (fabric-used claim)))

(defn- select-keys-and-values [pred m]
  (into {} (for [[k v] m :when (pred k v)] [k v])))

(defn solution-part-one [input]
  (->> (parse-input input)
       (reduce (fn [acc x] (claim-fabric acc x)) {})
       (select-keys-and-values (fn [k v] (>= v 2)))
       (count)))

