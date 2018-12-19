(ns advent-of-code-2018.day-6-test
  (:require [advent-of-code-2018.day-6 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
   ["1, 1"
    "1, 6"
    "8, 3"
    "3, 4"
    "5, 5"
    "8, 9"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 17 (solution-part-one example-input))))
