from mrw_nlp.vader import get_vader


def test_get_vader():
    vader = get_vader('got a gift')
    assert vader == {'compound': 0.4404,
                     'neg': 0.0,
                     'pos': 0.744,
                     'neu': 0.256}
