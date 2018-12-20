(ns advent-of-code-2018.day-6
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

;; Data types

(defn- make-cell [coords]
  {:coords coords})

(defn- make-grid [coords]
  {:left        (apply min (map first coords))
   :right       (apply max (map first coords))
   :top         (apply min (map last coords))
   :bottom      (apply max (map last coords))
   :coordinates coords
   :cells       {}})

(defn- make-coordinate [x y]
  [x y])

;; Input parsing

(def problem-input
  (->> (string/trim (slurp (io/resource "day-6-input.txt")))))

(defn- parse-coordinate [s]
  (let [parse-long (fn [s] (Long/parseLong s))
        [x y]      (string/split s #", ")]
    (make-coordinate (parse-long x) (parse-long y))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-coordinate)))

;; Part one
;;
;; There's probably a more concise way to handle this; I feel this is a bit overblown.

(defn- grid-indices [{:keys [left right top bottom]}]
  (for [y (range top  (inc bottom))
        x (range left (inc right))]
    [x y]))

(defn- manhattan-distance [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

(defn- distances-from [xy coordinates]
  (into {} (for [coord coordinates] [coord (manhattan-distance xy coord)])))

(defn- fill-grid-by [f-cell {:keys [cells coordinates] :as grid}]
  (assoc grid :cells (into {} (for [xy (grid-indices grid)]
                                [xy (f-cell (distances-from xy coordinates))]))))

(defn- sort-by-val [kv]
  (into (sorted-map-by
         (fn [k1 k2] (compare [(get kv k1) k1]
                             [(get kv k2) k2])))
        kv))

(defn- closest-unique-coordinate [distances]
  (let [[[k1 v1] [k2 v2]] (seq (sort-by-val distances))]
    (when (not= v1 v2)
      (make-cell #{k1}))))

(defn- largest-contiguous-area [grid]
  (->> (vals (:cells grid))
       (map :coords)
       (group-by identity)
       (vals)
       (map count)
       (apply max)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (make-grid)
       (fill-grid-by closest-unique-coordinate)
       (largest-contiguous-area)))

;; Part two

(defn- total-distance-less-than [limit distances]
  (when (< (apply + (vals distances)) limit)
    (make-cell (keys distances))))

(defn- total-area [grid]
  (->> (vals (:cells grid))
       (filter identity)
       (count)))

(defn solution-part-two [input limit]
  (->> (parse-input input)
       (make-grid)
       (fill-grid-by (partial total-distance-less-than limit))
       (total-area)))


