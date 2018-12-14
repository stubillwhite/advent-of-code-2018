(ns advent-of-code-2018.day-5-test
  (:require [advent-of-code-2018.day-5 :refer :all]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input "dabAcCaCBAcCcaDA")

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 10 (solution-part-one example-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 4 (solution-part-two example-input))))
