
(defn get-words [text]
  (flatten (map #(str/split %1 #" ") text)))

(defn get-categories [resources-path]
  (seq
    (.list (clojure.java.io/file resources-path))))

(defn count-words [words]
  (reduce
    (fn [acc item]
      (if (contains? acc item)
        (into acc {item (inc (acc item))})
        (into acc {item 1}))
      )
    {} words))


(defn get-category-occurences [category-name best-words resources-path]
  (def category-words (count-words (load-articles (str resources-path category-name))))
  (str (str/join "," (map (fn [word] (or (get category-words (first word)) 0)) best-words)) "," category-name))

(defn categories-attr [categories] (str "{" (str/join "," categories) "}"))

(defn arff-string [best-words categories resources-path]
          (def file-header "@RELATION iris\n\n")
          (def attrs (str/join "\n" (map #(str "@ATTRIBUTE " (first %1) " NUMERIC") best-words)))
          (def cat-attr (categories-attr categories))
          (def data (str/join "\n" (map #(get-category-occurences %1 best-words resources-path) categories)))
          (str file-header attrs cat-attr "\n\n" data))

(defn save-arff [content filename]
  (with-open [w (clojure.java.io/writer  filename :append true)]
    (.write w content)))

(defn main []
  (def resources-path "./resources/")
  (def categories (get-categories resources-path))
  (def all-words (flatten (map #(load-articles  (str resources-path %1)) categories)))
  (def best-words (take 10000 (sort-by second #(compare %2 %1) (count-words all-words))))
  (def arff-text (arff-string best-words categories resources-path))
  (save-arff arff-text "data.arff")
  )

(main)
