class Vector():
	
	def __init__(self, from_list = None, as_is = False):
		'''
		Initializes a new Vector
		from_list, allows to create a new vector from a list, by reversing it
		as_is, the vector will use the list `from_list`, as is, it will not be reversed
		'''
		self.__internal_list = []
		self.__size = 0
		if from_list is None:
			from_list = []
		if as_is:
			self.__internal_list.extend(from_list)
			self.__size = len(from_list)
		else:
			for e in from_list:
				self.add(e)
		
	def add(self, e):
		self.__internal_list.insert(0, e)
		self.__size += 1
		
	def __eq__(self, other):
		if other is None:
			return False
		if other.__size != self.__size:
			return False
		for i in range(0, self.__size):
			if self.__internal_list[i] != other.__internal_list[i]:
				return False
		return True
		
	def __len__(self):
		return self.__size
		
	def __getitem__(self, i):
		if i < 0 or i >= self.__size:
			raise IndexError(f"Invalid index {i} [0 - {self.__size - 1}]")
		return self.__internal_list[self.__size - 1 - i]
		
	def __setitem__(self, i, v):
		if i < 0 or i >= self.__size:
			raise IndexError(f"Invalid index {i} [0 - {self.__size - 1}]")
		self.__internal_list[self.__size - 1 - i] = v
		
	def __repr__(self):
		return self.__str__()
		
	def __str__(self):
		rep = "["
		for i in range(0, self.__size):
			v = self.__internal_list[i]
			rep += str(v)
			if i + 1 < self.__size:
				rep += ", "
		rep += "]"
		return rep
