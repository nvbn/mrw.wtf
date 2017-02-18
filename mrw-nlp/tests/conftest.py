import pytest
from mrw_nlp import api


@pytest.fixture
def app():
    return api.app
