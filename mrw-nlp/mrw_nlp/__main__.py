import sys
from flask import Flask, request, jsonify
from .nlp import get_sentiment

app = Flask(__name__)


@app.route('/api/v1/sentiment/', methods=['POST'])
def hello_world():
    return jsonify({
        'sentiment': get_sentiment(request.form['text']),
    })


if __name__ == '__main__':
    if '--initial' not in sys.argv:
        app.run()
