(ns advent-of-code-2018.day-3-test
  (:require [advent-of-code-2018.day-3 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n"
   ["#1 @ 1,3: 4x4"
    "#2 @ 3,1: 4x4"
    "#3 @ 5,5: 2x2"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 4 (solution-part-one example-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= "3" (solution-part-two example-input))))
