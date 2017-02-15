import csv
from pathlib import Path
import pickle
from nltk.tokenize import word_tokenize
from nltk import NaiveBayesClassifier, download

download('punkt')


def to_features(text):
    return {word: True for word in word_tokenize(text)}


def get_trains_set():
    with Path(__file__).parent.joinpath('text_emotion.csv').open() as f:
        reader = csv.DictReader(f)
        for row in reader:
            yield (to_features(row['content']), row['sentiment'])


def get_classifier():
    classifier_path = Path(__file__).parent.joinpath('classifier.pickle')
    try:
        with classifier_path.open('rb') as cached_classifier:
            return pickle.load(cached_classifier)
    except Exception as e:
        print("Can't load cached classifier", e)
        train_set = get_trains_set()
        classifier = NaiveBayesClassifier.train(train_set)
        with classifier_path.open('wb') as classifier_cache:
            pickle.dump(classifier, classifier_cache)
        return classifier


classifier = get_classifier()


def get_sentiment(text):
    return classifier.classify(to_features(text))
