import json


def test_sentiment(client):
    response = client.post('/api/v1/sentiment/',
                           data={'text': 'I lost my watch'})
    assert response.status_code == 200
    data = json.loads(response.data.decode())
    assert data == {'sentiment': 'worry'}
