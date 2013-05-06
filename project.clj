(defproject hoot "0.1.0-SNAPSHOT"
  :description "A library for interactng with Apache Jena."
  :url "http://github.com/iPlantCollaborativeOpenSource/hoot"
  :license {:name "BSD"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.apache.jena/jena-core "2.10.0"]
                 [org.apache.jena/jena-arq "2.10.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [com.cemerick/url "0.0.7"]]
  :profiles {:dev {:dependencies [[midje "1.4.0"]
                                  [lein-midje "2.0.0-SNAPSHOT"]]}}
  :repositories {"Apache Repository"
                 "https://repository.apache.org/content/repositories/releases/"})
