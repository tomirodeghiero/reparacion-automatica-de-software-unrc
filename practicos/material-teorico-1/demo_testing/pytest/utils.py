from vector import Vector

def bin_to_int(b: Vector) -> int:
	'''
	Given a binary number defined as a list of int
	return the int representation
	'''
	r = 0;
	n = len(b)
	for i in range(0, len(b)):
		exponent = i
		r = r + (b[i] * pow(2, exponent))
	return r
	
def int_to_bin(i: int, bits: int = 0) -> Vector:
	'''
	Given a positive integer number
	return the binary representation as a list of int
	the `bits` argument will make sure to use at least an amount of `bits` for the binary representation
	'''
	b = Vector()
	finished = False
	while not finished:
		b.add(i % 2)
		i = i // 2
		finished = i == 0
	if len(b) < bits:
		for zero in [0] * (bits - len(b)):
			b.add(0) 
	return b
	
def characteristic_vector(t, n) -> Vector:
	'''
	Returns the characteristic vector of a subset from
	a set S = {1, ..., n}
	'''
	v = Vector([0] * n)
	for i in range(0, n):
		e = n - i
		if e in t:
			v[i] = 1
	return v
