from sets import Set

class CheatingSet(Set):

    def __init__(self, from_list = []):
        self.__internal_list = []
        for e in from_list:
            self.add(e)
        
    def contains(self, e: int) -> bool:
        '''
        Returns True iff this set contains element `e`
        '''
        return e in self.__internal_list
    
    def __contains__(self, e: int) -> bool:
        '''
        Returns True iff this set contains element `e`
        '''
        return self.contains(e)
    
    def __iter__(self):
        '''
        Allows to iterate over elements in this set
        order of the elements is not guaranteed
        '''
        for e in self.__internal_list:
            yield e

    def __eq__(self, other) -> bool:
        '''
        Returns whether this set is equal to `other`
        '''
        eq = True
        for e in self:
            if e not in other:
                eq = False
        for v in other:
            if v not in self:
                eq = False
        return eq
        

    def add(self, e: int) -> bool:
        '''
        Adds an element `e` to the set
        returns whether the element `e` could be added or not
        '''
        if e not in self.__internal_list:
            self.__internal_list.append(e)

    def remove(self, e: int) -> bool:
        '''
        Removes an element `e` to the set
        returns whether the element `e` could be removed or not
        '''
        if e in self.__internal_list:
            self.__internal_list.remove(e)

    def union(self, other):
        '''
        Returns the union between this set and `other`
        '''
        u = CheatingSet()
        for e in self.__internal_list:
            u.add(e)
        for e in other:
            u.add(e)
        return u

    def intersection(self, other):
        '''
        Returns the intersection between this set and `other`
        '''
        n = CheatingSet()
        for e in self.__internal_list:
            if e in other:
                n.add(e)
        return n


    def difference(self, other):
        '''
        Returns the difference between this set and `other`
        '''
        diff = CheatingSet()
        for e in self.__internal_list:
            if e not in other:
                diff.add(e)
        return diff

    def symmetric_difference(self, other):
        '''
        Returns the symmetric difference between this set and `other`
        '''
        return self.union(other).difference(self.intersection(other))

    def extend(self, other) -> int:
        '''
        Extends this set with all the elements of `other`
        returns how many new elements were added
        '''
        for e in other:
            self.add(e)

    def is_subset(self, other) -> bool:
        '''
        Returns whether or not `other` is a subset of this set
        '''
        res = True
        for e in other:
            if e not in self:
                res = False
        return res


    def is_strict_subset(self, other) -> bool:
        '''
        Returns whether or not `other` is a subset of this set
        '''
        return self.is_subset(other) and not (self == other)

    def __len__(self) -> int:
        '''
        Returns the length of this set
        '''
        return len(self.__internal_list)
    
    def __str__(self) -> str:
        res = "{"
        for i in range(0, len(self)):
            res += str(self.__internal_list[i])
            if (i + 1) < len(self):
                res += ", "
        res += "}"
        return res
    
    def __repr__(self) -> str:
        return str(self)