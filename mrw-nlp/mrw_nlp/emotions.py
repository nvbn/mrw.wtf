from nltk.tokenize import word_tokenize
from nltk import NaiveBayesClassifier, download
from .utils import dataset, cached

download('punkt')


def to_features(text):
    return {word: True for word in word_tokenize(text)}


def get_trains_set():
    with dataset('text_emotion') as emotions:
        for row in emotions:
            yield (to_features(row['content']), row['sentiment'])


@cached('emotions')
def get_classifier():
    train_set = get_trains_set()
    return NaiveBayesClassifier.train(train_set)


classifier = get_classifier()


def get_sentiment(text: str) -> str:
    """Get sentiment for text."""
    return classifier.classify(to_features(text))
