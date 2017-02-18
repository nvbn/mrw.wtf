from flask import Flask, request, jsonify
from .nlp import get_sentiment

app = Flask('mrw-nlp')


@app.route('/api/v1/sentiment/', methods=['POST'])
def sentiment():
    """Get sentiment for text."""
    return jsonify({
        'sentiment': get_sentiment(request.form['text']),
    })
