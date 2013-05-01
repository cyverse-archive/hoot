(ns hoot.ontology
  (:require [cemerick.url :as url])
  (:import [com.hp.hpl.jena.ontology OntDocumentManager OntModel OntModelSpec]))

(defn ontology
  ([uri]
    (ontology uri OntModelSpec/OWL_DL_MEM))
  ([uri spec]
    (.getOntology (OntDocumentManager/getInstance) uri spec)))

(defn imported-uris
  "Lists the imported ontology URIs. If transitive? is false (the default)
   then the transitive imports (the imports of the imports) will not be listed."
  ([ont]
    (imported-uris ont false))
  ([ont transitive?]
    (seq (.listImportedOntologyURIs ont transitive?))))

(defn class->map
  "Turns a class from an ontology into a map. Note that :uri may be nil."
  [cls]
  {:namespace (.getNameSpace cls)
   :localname (.getLocalName cls)
   :uri       (.getURI cls)})

(defn map->class
  "Turns a map of the following structure into a ontology class:
     {
       :namespace \"The namespace of the class\"
       :localname \"The localname of the class\"
     }
   The returned class is not associated with an ontological model."
  [ont cls-map]
  (let [namespace (:namespace cls-map)
        localname (:localname cls-map)]
    (.createClass ont (str (assoc (url/url namespace) :anchor localname)))))

(defn classes
  "Lists all of the classes in the ontology. Classes are the types of the things
   defined in the ontology."
  [ont]
  (seq (.toList (.listClasses ont))))

(defn sclass-seq
  [cls]
  (or (seq (.toList (.listSuperClasses cls))) []))

(defn superclasses
  "Lists all of the superclasses of a particular ontology class"
  [cls]
  (loop [sclasses    (sclass-seq cls)
         accumulator []]
    (if (every? #(not (.hasSuperClass %)) sclasses)
      (concat accumulator sclasses)
      (let [new-sclasses (flatten (map sclass-seq sclasses))]
        (recur new-sclasses (concat accumulator sclasses))))))

(defn properties
  "Lists all of the properties defined in the ontology."
  [ont]
  (seq (.toList (.listAllOntProperties ont))))

(defn prop-applies?
  "Returns true if one of the classes is a domain class for the property 'prp'."
  [classes prp]
  (some #(.hasDomain prp %) classes))

(defn possible-class-properties
  "Lists all of the applicable properties (a.k.a. predicates) that are available
   for the given class."
  [ont cls]
  (filter 
    (partial prop-applies? (conj (superclasses cls) cls)) 
    (properties ont)))

