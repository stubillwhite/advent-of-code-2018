(ns advent-of-code-2018.day-7
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-7-input.txt")))))

(defn- parse-dependency [s]
  (let [[[_ b a]] (re-seq #"Step (\S+) must be finished before step (\S+) can begin\." s)]
    [a b]))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-dependency)))

(defn- map-vals [f map]
  (into {} (for [[k v] map] [k (f v)])))

(defn- fill-satisfied-dependencies [graph]
  (let [all-keys (into #{} (apply concat (vals graph)))]
    (merge
     (into {} (for [k all-keys] [k #{}]))
     graph)))

(defn- build-dependency-graph [input]
  (->> input
       (group-by first)
       (map-vals (fn [x] (map fnext x)))
       (map-vals (fn [x] (into #{} x)))
       (fill-satisfied-dependencies)
       (into (sorted-map))))

;; Simple topological sort

(defn- first-satisfied-node [graph]
  (some (fn [[k v]] (when (empty? v) k)) graph))

(defn- map-kv [f m]
  (reduce-kv (fn [acc k v] (assoc acc k (f k v))) m m))

(defn- remove-node [graph node]
  (map-kv (fn [k v] (disj v node))
          (dissoc graph node)))

(defn topological-sort [graph]
  (loop [graph  graph
         sorted []]
    (if (empty? graph)
      sorted
      (let [node (first-satisfied-node graph)]
        (if-not node
          (throw (RuntimeException. "Graph contains cycles or is incomplete"))
          (recur (remove-node graph node)
                 (conj sorted node)))))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (build-dependency-graph)
       (topological-sort)
       (apply str)))

