import time
from mrw_nlp import nlp


def test_get_sentiment():
    sentiment = nlp.get_sentiment("I lost my watch")
    assert sentiment == 'worry'
