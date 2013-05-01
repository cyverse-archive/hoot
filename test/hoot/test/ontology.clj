(ns hoot.test.ontology
  (:use [hoot.ontology] :reload)
  (:use [midje.sweet]
        [clojure.java.io :as io])
  (:import [com.hp.hpl.jena.ontology OntProperty OntModel OntModelSpec OntClass]))

(fact (ontology "test/resources/shakespeare.owl") => #(instance? OntModel %))

(def m1 (ontology "test/resources/shakespeare.owl"))

(fact (classes m1) => (has every? #(instance? OntClass %)))

(fact (filter #(.hasSuperClass %) (classes m1)) => #(= 2 (count %)))

(def super-classed (first (filter #(.hasSuperClass %) (classes m1))))

(fact (superclasses super-classed) => (has every? #(instance? OntClass %)))

(fact (class->map super-classed) => {:namespace "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"
                                     :localname "Author"
                                     :uri "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Author"})

(def the-super-class (first (superclasses super-classed)))

(fact (class->map the-super-class) => {:namespace "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"
                                       :localname "Person"
                                       :uri "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Person"})

(facts 
  (map->class m1 {:namespace "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"
                  :localname "Person"
                  :uri "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Person"}) =>
  #(= (:uri (class->map %)) "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Person")
  
  (map->class m1 {:namespace "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"
                  :localname "Person"
                  :uri "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Person"}) =>
  #(= (:localname (class->map %)) "Person")
  
  (map->class m1 {:namespace "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"
                  :localname "Person"
                  :uri "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#Person"}) =>
  #(= (:namespace (class->map %)) "http://www.workingontologist.org/Examples/Chapter3/shakespeare.owl#"))


(facts
  (properties m1) => (has every? #(instance? OntProperty %))
  (count (properties m1)) => 7
  (possible-class-properties m1 super-classed) => (has every? #(instance? OntProperty %))
  (count (possible-class-properties m1 super-classed)) => 4)






