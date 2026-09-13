import signal
from hypothesis import given, settings
from hypothesis import strategies as st
from utils import int_to_bin, bin_to_int


def alarm_handler(signum, frame):
    raise TimeoutError("Function took too long")
    
@given(st.just(-1))
def test_int_to_bin_to_int(x):
    signal.signal(signal.SIGALRM, alarm_handler)
    signal.alarm(1)  # 1 second timeout

    try:
        assert bin_to_int(int_to_bin(x)) == x
    finally:
        signal.alarm(0)

