(ns text-classification.texts-to-arff
  (:require [clojure.string :as str]))

(defn load-text [path]
  (with-open [w (clojure.java.io/reader path)]
    (reduce conj [] (line-seq w))))

(defn get-words [text]
  (flatten (map #(str/split %1 #" ") text)))

(defn count-words [words]
  (reduce
    (fn [acc item]
      (if (contains? acc item)
        (into acc {item (inc (acc item))})
        (into acc {item 1}))
      )
    {} words)
  )

(defn inspect [& args] (println args) args)                 ;side effect!

(defn -main []
  (inspect
    (count-words
      (get-words
        (load-text "resources/179116"))))
  )
(-main)
