
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


(defn main []
  (def resources-path "./resources/")
  (def categories (get-categories resources-path))
  (def all-words (flatten (map #(load-articles  (str resources-path %1)) categories)))
  (println all-words))

(main)
