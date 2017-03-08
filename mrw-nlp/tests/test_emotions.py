from mrw_nlp import emotions


def test_get_sentiment():
    sentiment = emotions.get_sentiment("I lost my watch")
    assert sentiment == 'worry'
