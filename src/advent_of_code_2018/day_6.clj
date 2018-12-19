(ns advent-of-code-2018.day-6
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as string]))

;; Data types

(defn- make-cell [closest dist]
  {:closest closest
   :dist    dist})

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

(defn- min-by-distance [cell-a cell-b]
  (cond
    (nil? cell-a)     cell-b
    (nil? cell-b)     cell-a
    (< (:dist cell-a) (:dist cell-b)) cell-a
    (= (:dist cell-a) (:dist cell-b)) (update cell-b :closest (partial set/union (:closest cell-a)))
    :else             cell-b))

(defn- closest-coordinate-to [coordinates xy]
  (reduce (fn [curr coord] (min-by-distance curr (make-cell #{coord} (manhattan-distance xy coord))))
          (make-cell #{(first coordinates)} (manhattan-distance xy (first coordinates)))
          (rest coordinates)))

(defn- fill-grid [{:keys [cells coordinates] :as grid}]
  (assoc grid :cells (reduce (fn [acc xy]
                               (assoc acc xy (min-by-distance (get acc xy) (closest-coordinate-to coordinates xy))))
                             cells
                             (grid-indices grid))))

(defn- map-vals [f kv]
  (into {} (for [[k v] kv] [k (f v)])))

(defn- coordinate-areas [grid]
  (->> (vals (:cells grid))
       (map :closest)
       (filter (fn [x] (= 1 (count x))))
       (group-by identity)
       (map-vals count)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (make-grid)
       (fill-grid)
       (coordinate-areas)
       (vals)
       (apply max)))
