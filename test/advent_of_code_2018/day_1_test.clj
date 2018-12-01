(ns advent-of-code-2018.day-1-test
  (:require [advent-of-code-2018.day-1 :refer :all]
            [clojure.test :refer :all]
            [clojure.string :as string]))

(defn- test-input [s]
  (string/replace s ", " "\n"))

(deftest solution-part-one-given-example-input-then-example-result
  (is (=  3 (solution-part-one (test-input "+1, -2, +3, +1"))))
  (is (=  3 (solution-part-one (test-input "+1, +1, +1"))))
  (is (=  0 (solution-part-one (test-input "+1, +1, -2"))))
  (is (= -6 (solution-part-one (test-input "-1, -2, -3")))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (=  2 (solution-part-two (test-input "+1, -2, +3, +1"))))  
  (is (=  0 (solution-part-two (test-input "+1, -1"))))
  (is (= 10 (solution-part-two (test-input "+3, +3, +4, -2, -4"))))
  (is (=  5 (solution-part-two (test-input "-6, +3, +8, +5, -6"))))
  (is (= 14 (solution-part-two (test-input "+7, +7, -2, -7, -4")))))

