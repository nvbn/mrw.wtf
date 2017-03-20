from flask import Flask, request, jsonify
from .emotions import get_sentiment
from .vader import get_vader

app = Flask('mrw-nlp')


@app.route('/api/v1/sentiment/', methods=['GET'])
def sentiment():
    """Get sentiment for text."""
    return jsonify({'sentiment': get_sentiment(request.args['text'])})


@app.route('/api/v1/vader/', methods=['GET'])
def vader():
    """Get vader for text."""
    return jsonify(get_vader(request.args['text']))
