(ns load_article
  (:require [clojure.string :as str]))

(def split-regex #"\W")
;or #"\.| |\,|~|-|!|@|#|\\|\}|\%|\)|\(|\?|\<|\>|\_|\"|\/|\:|\*|\]|\["

(defn get-all-files [path]
  (def directory (clojure.java.io/file path))
  (file-seq directory))

(defn clear-articles [loaded-files]
  (def articles
    (filter some?
            (map (fn [article]
                   (def cleared-article (str/join " " article))
                   (second (re-matches #"^.*Lines:\s\d*\s(.*)$" cleared-article)))
                 loaded-files)))
  (map #(str/lower-case %1) articles))



(defn articles-to-words [articles]
  (def words (map (fn [article] (str/split article split-regex)) articles))
  (remove clojure.string/blank? (flatten words)))

(defn read-words [files]
  (map
    (fn [file]
        (with-open [w (clojure.java.io/reader file)]
          (reduce conj [] (line-seq w))))
      (drop 1 files)))

(defn load-articles [path]
  (def files (get-all-files path))
  (def loaded-files (read-words files))
  (def articles (clear-articles loaded-files))
  (articles-to-words articles))
