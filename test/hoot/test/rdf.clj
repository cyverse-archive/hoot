(ns hoot.test.rdf
  (:import [com.hp.hpl.jena.rdf.model Model ModelFactory AnonId SimpleSelector Statement])
  (:use [hoot.rdf] :reload)
  (:use [midje.sweet]
        [clojure.java.io :as io]))

(fact (model (io/input-stream "test/resources/test2.n3") "N3") => #(instance? Model %))

(def m1 (model (io/input-stream "test/resources/test2.n3") "N3"))

(fact (map->statement m1 {:subject "http://www.google.com/"
                          :predicate "http://www.google.com/"
                          :object "http://www.google.com"}) => #(instance? Statement %))

(def s1 (map->statement m1 {:subject "http://www.google.com/"
                            :predicate "http://www.google.com/"
                            :object "http://www.google.com"}))

(fact (statement->map s1) => {:subject "http://www.google.com/"
                              :predicate "http://www.google.com/"
                              :object "http://www.google.com"})

(fact (statements m1) => (has every? #(contains? % :subject)))
(fact (statements m1) => (has every? #(contains? % :predicate)))
(fact (statements m1) => (has every? #(contains? % :object)))

