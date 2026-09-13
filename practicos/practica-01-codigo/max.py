'''
Some functions to calculate the maximum between values
'''

def max_int(a: int, b: int) -> int:
    '''
    Given two int values `a` and `b`, return the
    maximum of the two.
    '''
    if a > b:
        return a
    else:
        return b

def max_int_seq(a: list[int], b: list[int]) -> list[int]:
    '''
    Given two int lists `a` and `b`, return the maximum of
    the two.
    '''
    max_a: int = 0
    max_b: int = 0
    if len(a) > len(b):
        return a
    elif len(a) < len(b):
        return b
    else:
        for i in range(0, len(a)):
            if max_int(a[i], b[i]) == a:
                max_a += 1
            else:
                max_b += 1
        if max_a > max_b:
            return a
        else:
            return b