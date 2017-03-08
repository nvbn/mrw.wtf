import json


def test_sentiment(client):
    response = client.post('/api/v1/sentiment/',
                           data={'text': 'I lost my watch'})
    assert response.status_code == 200
    data = json.loads(response.data.decode())
    assert data == {'sentiment': 'worry'}


def test_vader(client):
    response = client.post('/api/v1/vader/',
                           data={'text': 'saw worst movie'})
    assert response.status_code == 200
    data = json.loads(response.data.decode())
    assert data == {'compound': -0.6249,
                    'neg': 0.672,
                    'neu': 0.328,
                    'pos': 0.0}
