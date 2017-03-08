from pathlib import Path
import pytest
from mrw_nlp import api


@pytest.fixture
def app():
    return api.app


@pytest.fixture(autouse=True)
def clear_cached(request):
    def _clear():
        path = Path(__file__) \
            .parent \
            .parent \
            .joinpath('cached')
        for cached in path.glob('*.pickle'):
            cached.unlink()

    request.addfinalizer(_clear)
