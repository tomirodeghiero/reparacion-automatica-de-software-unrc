from abc import ABC, abstractmethod


class Set(ABC):
	"""
	An abstract set, an unordered collection of distinct elements.
	We will be working with only ints
	"""

	@abstractmethod
	def contains(self, e: int) -> bool:
		'''
		Returns True iff this set contains element `e`
		'''
		pass

	@abstractmethod
	def __contains__(self, e: int) -> bool:
		'''
		Returns True iff this set contains element `e`
		'''
		pass

	@abstractmethod
	def __eq__(self, other) -> bool:
		'''
		Returns whether this set is equal to `other`
		'''
		pass

	@abstractmethod
	def __iter__(self):
		'''
		Allows to iterate over elements in this set
		order of the elements is not guaranteed
		'''
		pass

	@abstractmethod
	def add(self, e: int) -> bool:
		'''
		Adds an element `e` to the set
		returns whether the element `e` could be added or not
		'''
		pass

	@abstractmethod
	def remove(self, e: int) -> bool:
		'''
		Removes an element `e` to the set
		returns whether the element `e` could be removed or not
		'''
		pass

	@abstractmethod
	def union(self, other):
		'''
		Returns the union between this set and `other`
		'''
		pass

	@abstractmethod
	def intersection(self, other):
		'''
		Returns the intersection between this set and `other`
		'''
		pass

	@abstractmethod
	def difference(self, other):
		'''
		Returns the difference between this set and `other`
		'''
		pass

	@abstractmethod
	def symmetric_difference(self, other):
		'''
		Returns the symmetric difference between this set and `other`
		'''
		pass

	@abstractmethod
	def extend(self, other) -> int:
		'''
		Extends this set with all the elements of `other`
		returns how many new elements were added
		'''
		pass

	@abstractmethod
	def is_subset(self, other) -> bool:
		'''
		Returns whether or not `other` is a subset of this set
		'''
		pass

	@abstractmethod
	def is_strict_subset(self, other) -> bool:
		'''
		Returns whether or not `other` is a subset of this set
		'''
		pass

	@abstractmethod
	def __len__(self) -> int:
		'''
		Returns the length of this set
		'''
		pass

	@abstractmethod
	def __str__(self) -> str:
		'''
		Returns the string representation of this set
		'''
		pass

	@abstractmethod
	def __repr__(self) -> str:
		'''
		Returns the string representation of this set
		'''
		pass
