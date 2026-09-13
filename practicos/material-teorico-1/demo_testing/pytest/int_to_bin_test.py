from vector import Vector
from utils import int_to_bin


def test_int_to_bin_zero():
	result = int_to_bin(0)
	assert result == Vector([0], True)
    
def test_int_to_bin_three():
	result = int_to_bin(3)
	assert result == Vector([1,1], True)

