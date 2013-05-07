(ns hoot.csv
  (:require [clojure.string :as string]))

(def csv-types ["csv" "tsv"])

(defn read-csv
  [instream & {:keys [separator] :or {separator #","}}]
  (mapv #(string/split %1 separator) (string/split-lines (slurp instream))))

(defn read-tsv [instream] (read-csv instream :separator #"\t"))

(defn rows->triples
  [rows]
  (mapv #(hash-map :subject (nth %1 0 "") :predicate (nth %1 1 "") :object (nth %1 2 "")) rows))

(defn statements
  [instream type]
  (let [parser (if (= (string/lower-case type) "tsv") read-tsv read-csv)]
    (rows->triples (parser instream))))
