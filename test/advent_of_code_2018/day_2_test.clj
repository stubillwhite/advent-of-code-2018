(ns advent-of-code-2018.day-2-test
  (:require [advent-of-code-2018.day-2 :refer :all]
            [advent-of-code-2018.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(defn- test-input [lines]
  (string/join "\n" lines))

(def- example-input ["abcdef"
                     "bababc"
                     "abbcde"
                     "abcccd"
                     "aabcdd"
                     "abcdee"
                     "ababab"])

(deftest solution-part-one-given-example-input-then-example-result
  (is (=  12 (solution-part-one (test-input example-input)))))
