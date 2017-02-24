import sys
from .api import app

if __name__ == '__main__':
    if '--initial' not in sys.argv:
        app.run(host='0.0.0.0')
