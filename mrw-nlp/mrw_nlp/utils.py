from pathlib import Path
from contextlib import contextmanager
from functools import wraps
import csv
import pickle
import logging


@contextmanager
def dataset(name):
    path = Path(__file__) \
        .parent \
        .parent \
        .joinpath('datasets') \
        .joinpath('{}.csv'.format(name))

    print(path)
    with path.open() as f:
        yield csv.DictReader(f)


def cached(name):
    def decorator(fn):
        @wraps(fn)
        def wrapper():
            path = Path(__file__) \
                .parent \
                .parent \
                .joinpath('cached') \
                .joinpath('{}.pickle'.format(name))

            try:
                with path.open('rb') as f:
                    return pickle.load(f)
            except Exception as e:
                logging.info("Can't load cached object %s: %s", name, e)
                val = fn()

                with path.open('wb') as f:
                    pickle.dump(val, f)

                return val

        return wrapper

    return decorator
