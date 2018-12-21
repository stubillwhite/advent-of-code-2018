(ns advent-of-code-2018.day-7-test
  (:require [advent-of-code-2018.day-7 :refer :all]
            [advent-of-code-2018.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(defn- into-graph [map]
  (into (sorted-map) map))

(deftest topological-sort-given-acyclic-graph-then-sorted-order
  (let [graph (into-graph {:a #{:b :d}
                           :b #{:c :d}
                           :c #{}
                           :d #{:c}})]
    (is (= [:c :d :b :a] (topological-sort graph)))))

(deftest topological-sort-given-satisfied-graph-then-alphabetic-order
  (let [graph (into-graph {:d #{}
                           :a #{}
                           :b #{}
                           :c #{}})]
    (is (= [:a :b :c :d] (topological-sort graph)))))

(deftest topological-sort-given-cyclic-graph-then-throws
  (let [graph (into-graph {:a #{:b :c}
                           :b #{:c}
                           :c #{:a}})]
    (is (thrown-with-msg? RuntimeException #"Graph contains cycles or is incomplete" (topological-sort graph)))))

(deftest topological-sort-given-incomplete-graph-then-throws
  (let [graph (into-graph {:a #{:b :c}
                           :c #{}})]
    (is (thrown-with-msg? RuntimeException #"Graph contains cycles or is incomplete" (topological-sort graph)))))

(def- example-input
  (string/join "\n"
               ["Step C must be finished before step A can begin."
                "Step C must be finished before step F can begin."
                "Step A must be finished before step B can begin."
                "Step A must be finished before step D can begin."
                "Step B must be finished before step E can begin."
                "Step D must be finished before step E can begin."
                "Step F must be finished before step E can begin."]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= "CABDFE" (solution-part-one example-input))))
