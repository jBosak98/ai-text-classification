run:
	cat load_article.clj textsToArff.clj | lein exec

download_data:
	python3 generateArticles.py

clear:
	rm *.arff
	rm -r resources
