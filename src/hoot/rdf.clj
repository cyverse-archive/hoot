(ns hoot.rdf
  (:import [com.hp.hpl.jena.rdf.model ModelFactory AnonId SimpleSelector]))

(def accepted-languages ["RDF/XML"
                         "RDF/XML-ABBREV"
                         "N-TRIPLE"
                         "TURTLE"
                         "TTL"
                         "N3"])

(defn anon-id
  ([] (AnonId/create))
  ([^String new-id] (AnonId/create new-id)))

(defn model
  "Creates a Jena Model from an input-stream. The URI base and language are optional.
   You'll need an instance of this first in order to be able to do anything."
  ([input-stream]
    (model input-stream nil nil))
  ([input-stream language]
    (model input-stream language nil))
  ([input-stream language uri-base]
    (.read (ModelFactory/createDefaultModel) input-stream uri-base language)))

(defn map->statement
  "Converts a map to a statement that corresponds to the subject, predicate, and 
   object of a triple. The input map should be formatted like the following:
       {
         :subject    \"String\"
         :predicate  \"URI as string\"
         :object     \"String\"
       }
   The statement can be used with the (.add Model) method."
  [model {:keys [subject predicate object]}]
  (.createStatement model 
    (.createResource model subject)
    (.createProperty model predicate)
    object))

(defn str-repr
  [obj]
  (cond
    (.isAnon obj)        (.getLabelString (.getId (.asResource obj)))
    (.isLiteral obj)     (.getLexicalForm (.asLiteral obj))
    (.isURIResource obj) (str (.getURI (.asResource obj)))
    (.isResource obj)    (str (.asResource obj))
    :else                (str obj)))

(defn statement->map
  "Converts a Jena statement into a map that looks like the following:
       {
         :subject   \"String\"
         :predicate \"URI as string\"
         :object    \"String\"
       }
   This is the reverse of (map->statement)."
  [statement]
  {:subject   (str-repr (.getSubject statement))
   :predicate (str-repr (.getPredicate statement))
   :object    (str-repr (.getObject statement))})

(defn statements
  "Returns a list of all statements in the model."
  [model]
  (map statement->map (.toList (.listStatements model))))

(defn model-size
  "Returns the number of statements in a model."
  [model]
  (.size model))

(defn query->model
  "Returns a new model containing the statements matching a query."
  [model {:keys [subject predicate object]}]
  (let [resource (.createResource model subject)
        property (if-not (nil? predicate) (.createProperty model predicate))]
    (.query model (SimpleSelector. resource property object))))

(defn query->maps
  "Returns all of the statements that match a query."
  [model query-map]
  (statements (query->model model query-map)))




