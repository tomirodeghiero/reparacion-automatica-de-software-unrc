import json
import pytest
from vector import Vector
from utils import int_to_bin

def load_cases():
    with open("int_to_bin_cases.json") as f:
        data = json.load(f)
    return [(c["v"], Vector(c["expected"], True)) for c in data]


@pytest.mark.parametrize("v,expected", load_cases())
def test_int_to_bin(v, expected):
    result = int_to_bin(v)
    assert result == expected

