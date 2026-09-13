from enum import Enum
from datetime import date

class Studies(Enum):
    '''
    Defines the studies a person can have
    '''
    NONE = ("No studies", 0)
    EARLY_CHILDHOOD = ("Early Childhood Education (ISCED 0)", 1)
    PRIMARY = ("Primary Education (ISCED 1)", 2)
    LOWER_SECONDARY_VOCATIONAL = ("Lower Secondary – Vocational Track (ISCED 2)", 3)
    LOWER_SECONDARY_GENERAL = ("Lower Secondary – General Track (ISCED 2)", 3)
    UPPER_SECONDARY = ("Upper Secondary Education (ISCED 3)", 4)
    BACHELOR = ("Bachelor’s Degree (ISCED 6)", 5)
    POSTGRADUATE_SPECIALIZATION = ("Postgraduate Specialization (ISCED 7 – Short Cycle)", 6)
    MASTERS = ("Master’s Degree (ISCED 7)", 7)
    DOCTORATE = ("Doctoral Degree / PhD (ISCED 8)", 8)
    POSTDOCTORAL = ("Postdoctoral Studies / Research", 9)


    def __level(self) -> int:
        return self.value[1]

    def primary_studies(self) -> bool:
        '''
        Returns `True` iff a studie is Primary or greater
        '''
        return self.__level() >= 1

    def lower_secondary_studies(self) -> bool:
            '''
            Returns `True` iff a studie is Lower Secondary or greater
            '''
            return self.__level() >= 2

    def upper_secondary_studies(self) -> bool:
         '''
         Returns `True` iff a studie is Upper Secondary or greater
         '''
         return self.__level() >= 4

    def university_studies(self) -> bool:
        '''
        Returns `True` iff a studie is University or greater
        '''
        return self.__level() >= 5

    def masters_studies(self) -> bool:
        '''
        Returns `True` iff a studie is Masters or greater
        '''
        return self.__level() >= 7

    def phd_studies(self) -> bool:
        '''
        Returns `True` iff a studie is Doctorate/Phd or greater
        '''
        return self.__level() >= 8

    def __str__(self) -> str:
         '''
         Returns the string representation for a `Studies` value
         '''
         return self.value[0]
        

class Person():
    '''
    This class defines a Person with date of birth, name, and studies
    '''

    def __init__(self, birth: date, name: str, studies: Studies = Studies.NONE):
        '''
        Creates a new Person with a given birth date, name, and studies
        by default a person will have no studies whatsoever.
        '''
        #TODO: Question: Is this implementation correct?
        #      provide at least one test that passes with the current implementation
        #      provide at least one test that fails with the current implementation
        #      explain what is the defect, the error, and the failure for the failing test
        self._birthdate = birth
        self._name = name
        self._studies = studies

    #TODO: Question: Would it be correct to allow the modification of birth date and name?

    @property
    def name(self) -> str:
        '''
        '''
        return self._name

    @property
    def birthdate(self) -> date:
        '''
        Returns the birth date of this person
        '''
        return self._birthdate

    @property
    def age(self) -> int:
        '''
        Returns the age of this person in years
        '''
        #TODO: Question: Is this implementation correct?
        #      provide at least one test that passes with the current implementation
        #      provide at least one test that fails with the current implementation
        #      explain what is the defect, the error, and the failure for the failing test
        today = date.today()
        years = today.year - self._birthdate.year

        return years

    @property
    def studies(self) -> Studies:
        '''
        Returns the studies of this person
        '''
        return self._studies

    @studies.setter
    def studies(self, new_studies: Studies) -> None:
        '''
        Updates the studies of this person
        '''
        #TODO: Question: Is this implementation correct?
        #      provide at least one test that passes with the current implementation
        #      provide at least one test that fails with the current implementation
        #      explain what is the defect, the error, and the failure for the failing test
        self._studies = new_studies

    def __str__(self) -> str:
        '''
        Returns a string representation for this person
        '''
        return f"{self._name} born in {self._birthdate}, has {self.age} years, and has done up to {self._studies}"

    def __eq__(self, other) -> bool:
        '''
        Returns `True` iff this person is equal to `other`
        '''
        #TODO: Question: Is this implementation correct?
        #      provide at least one test that passes with the current implementation
        #      provide at least one test that fails with the current implementation
        #      explain what is the defect, the error, and the failure for the failing test
        if other is None:
             return False
        if not isinstance(other, Person):
             return False
        if self._name is not other._name:
            return False
        if self.age != other.age:
            return False
        if self._studies != other._studies:
            return False
        return True

def max(a: Person, b: Person) -> Person:
    '''
    Given two persons `a` and `b` returns the maximum person
    '''
    #TODO: Question: Implement, and provide tests
    raise NotImplementedError("Not implemented yer")