from sklearn.datasets import fetch_20newsgroups
from pprint import pprint
import os
import os.path


categories = ['alt.atheism' , 'rec.motorcycles', 'rec.sport.hockey', 'sci.electronics', 'talk.politics.misc']
counter = 0
for category in categories:

    newsgroups_train = fetch_20newsgroups(subset='test', remove=('heads', 'footers', 'quotes'), categories=[category], return_X_y=True)[0]
    for article in newsgroups_train:

        print(newsgroups_train[0])
        counter += 1
        if not os.path.isdir('resources'):
            os.mkdir('resources')
        if not os.path.isdir('resources/'+category):
            os.mkdir('resources/'+category)
        with open(f"resources/{category}/{counter}", 'w', encoding="UTF-8") as file:
            file.write(article)
