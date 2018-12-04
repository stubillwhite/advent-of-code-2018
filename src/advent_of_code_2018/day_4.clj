(ns advent-of-code-2018.day-4
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (->> (string/trim (slurp (io/resource "day-4-input.txt")))))

(defn- parse-long [s]
  (Long/parseLong s))

(defn- parse-record [s]
  (let [pattern #"\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (.+)"
        matches (re-seq pattern s)
        [_ year month day hour minute event] (first matches)]
    {:year   (parse-long year)
     :month  (parse-long month)
     :day    (parse-long day)
     :hour   (parse-long hour)
     :minute (parse-long minute)
     :event  event}))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-record)
       (sort-by (juxt :year :month :day :hour :minute))))

(defn- record-type [event]
  (cond
    (string/ends-with? event "begins shift") :shift-start
    (= event "falls asleep")                 :falls-asleep
    (= event "wakes up")                     :wakes-up))

(defn- guard-id [event]
  (->> (re-seq #"Guard #(\d+) begins shift" event)
       (first)
       (fnext)
       (parse-long)))

(defn- add-time-slept [start end data]
  (reduce (fn [acc x] (update acc x (fnil inc 0)))
          data
          (range start end)))

(defn compile-sleep-schedule [records]
  (:guard-data
   (reduce 
    (fn [{:keys [current-guard sleep-start] :as acc} {:keys [event minute] :as x}]
      (condp = (record-type event)
        :shift-start  (assoc acc :current-guard (guard-id event))
        :falls-asleep (assoc acc :sleep-start   minute)
        :wakes-up     (update-in acc [:guard-data current-guard] (partial add-time-slept sleep-start minute))
        ))
    {:current-guard nil
     :sleep-start   nil
     :guard-data    {}}
    records)))

(defn sleepiest-guard-id [schedule]
  (->> schedule
       (sort-by (fn [[k v]] (apply + (vals v))))
       (last)
       (first)))

(defn- sleepiest-minute [guard-sleep-schedule]
  (->> guard-sleep-schedule
       (sort-by (fn [[k v]] v))
       (last)
       (first)))

(defn solution-part-one [input]
  (let [schedule (compile-sleep-schedule (parse-input input))
        id       (sleepiest-guard-id schedule)
        minute   (sleepiest-minute (get schedule id))]
    (* id minute)))
