(defproject hoot "0.1.0-SNAPSHOT"
  :description "A library for interactng with Apache Jena."
  :url "http://github.com/iPlantCollaborativeOpenSource/hoot"
  :license {:name "BSD Standard License"
            :url "http://www.iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :scm {:connection "scm:git:git@github.com:iPlantCollaborativeOpenSource/kameleon.git"
        :developerConnection "scm:git:git@github.com:iPlantCollaborativeOpenSource/kameleon.git"
        :url "git@github.com:iPlantCollaborativeOpenSource/kameleon.git"}
  :pom-addition [:developers
                 [:developer
                  [:url "https://github.com/orgs/iPlantCollaborativeOpenSource/teams/iplant-devs"]]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.apache.jena/jena-core "2.10.0"]
                 [org.apache.jena/jena-arq "2.10.0"
                  :exclusions [[org.slf4j/jcl-over-slf4j]]]
                 [org.clojure/data.csv "0.1.2"]
                 [com.cemerick/url "0.0.7"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [lein-midje "3.1.1"]]}}
  :repositories [["Apache Repository"
                 {:url "https://repository.apache.org/content/repositories/releases/"}]
                 ["sonatype-nexus-snapshots"
                  {:url "https://oss.sonatype.org/content/repositories/snapshots"}]
                 ["sonatype-nexus-staging"
                  {:url "https://oss.sonatype.org/service/local/staging/deploy/maven2/"}]])
