import signal
from hypothesis import given, settings
from hypothesis import strategies as st
from utils import int_to_bin, bin_to_int


@given(st.integers(min_value=0, max_value=2**4))
@settings(max_examples=50)
def test_int_to_bin_to_int(x):
    assert bin_to_int(int_to_bin(x)) == x

