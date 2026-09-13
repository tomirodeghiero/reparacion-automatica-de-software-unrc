from triangle import categorize, TriangleType

def test_invalid_negative():
    assert categorize(-1, 2, 3) == TriangleType.INVALID

def test_invalid_triangle_inequality():
    assert categorize(1, 2, 3) == TriangleType.INVALID

def test_equilateral():
    assert categorize(3, 3, 3) == TriangleType.EQUILATERAL

def test_isosceles():
    assert categorize(3, 3, 2) == TriangleType.ISOSCELES

def test_scalene():
    assert categorize(3, 4, 5) == TriangleType.SCALENE