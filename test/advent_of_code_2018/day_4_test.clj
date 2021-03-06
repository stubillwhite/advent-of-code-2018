(ns advent-of-code-2018.day-4-test
  (:require [advent-of-code-2018.day-4 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
               ["[1518-11-01 00:00] Guard #10 begins shift"
                "[1518-11-01 00:05] falls asleep"
                "[1518-11-01 00:25] wakes up"
                "[1518-11-01 00:30] falls asleep"
                "[1518-11-01 00:55] wakes up"
                "[1518-11-01 23:58] Guard #99 begins shift"
                "[1518-11-02 00:40] falls asleep"
                "[1518-11-02 00:50] wakes up"
                "[1518-11-03 00:05] Guard #10 begins shift"
                "[1518-11-03 00:24] falls asleep"
                "[1518-11-03 00:29] wakes up"
                "[1518-11-04 00:02] Guard #99 begins shift"
                "[1518-11-04 00:36] falls asleep"
                "[1518-11-04 00:46] wakes up"
                "[1518-11-05 00:03] Guard #99 begins shift"
                "[1518-11-05 00:45] falls asleep"
                "[1518-11-05 00:55] wakes up"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 240 (solution-part-one example-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 4455 (solution-part-two example-input))))
