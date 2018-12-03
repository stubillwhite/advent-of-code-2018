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

(defn- fabric-used [{:keys [x y width height]}]
  (for [x' (range x (+ x width))
        y' (range y (+ y height))] [x' y']))

(defn- claim-fabric [fabric claim]
  (reduce (fn [acc x] (update acc x (fnil inc 0)))
          fabric
          (fabric-used claim)))

(defn- process-all-claims [claims]
  (reduce (fn [acc x] (claim-fabric acc x)) {} claims))

(defn- select-keys-and-values [pred m]
  (into {} (for [[k v] m :when (pred k v)] [k v])))

(defn solution-part-one [input]
  (->> (parse-input input)
       (process-all-claims)
       (select-keys-and-values (fn [k v] (>= v 2)))
       (count)))

;; Part two
;;
;; Simplest solution seems to be to just reprocess all claims and select only the one where single cells are selected

(defn- find-non-overlapping-claim [claimed-fabric claims]
  (when-let [[claim & rest] (seq claims)]
    (if (every? #(= 1 %) (vals (select-keys claimed-fabric (fabric-used claim))))
      claim
      (find-non-overlapping-claim claimed-fabric rest))))

(defn solution-part-two [input]
  (let [claims         (parse-input input)
        claimed-fabric (process-all-claims claims)]
    (:id (find-non-overlapping-claim claimed-fabric claims))))
