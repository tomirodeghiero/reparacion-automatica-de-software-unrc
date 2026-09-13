'''
A simple module to categorize triangles based on form and angles
'''

from enum import Enum


class TriangleType(Enum):
    EQUILATERAL = "Equilateral"
    ISOSCELES = "Isosceles"
    SCALENE = "Scalene"
    INVALID = "Invalid"

def categorize(a: int, b: int, c: int) -> TriangleType:
    if a <= 0 or b <= 0 or c <= 0:
        return TriangleType.INVALID

    if a + b <= c or a + c <= b or b + c <= a:
        return TriangleType.INVALID

    if a == b == c:
        return TriangleType.EQUILATERAL

    if a == b or b == c or a == c:
        return TriangleType.ISOSCELES

    return TriangleType.SCALENE