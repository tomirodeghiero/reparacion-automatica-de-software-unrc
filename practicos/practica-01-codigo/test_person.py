"""
Tests para el modulo person.py  (Practica 1 - Ejercicio 4).

Ejecutar con:  pytest test_person.py -v

Convencion:
  - [PASA]  pasa contra el codigo dado.
  - [FALLA] falla contra el codigo dado y evidencia el defecto
            documentado en el informe LaTeX. Pasa con la correccion.

Las fechas se calculan relativas a hoy para que los tests sean
deterministas y no dependan del dia en que se corran.
"""

from datetime import date
from person import Person, Studies

HOY = date.today()

def hace_anios(n, mes=1, dia=1):
    return date(HOY.year - n, mes, dia)


# =========================== __init__ ===========================

def test_init_guarda_atributos():        # [PASA]
    p = Person(date(2000, 1, 1), "David", Studies.BACHELOR)
    assert p.name == "David"
    assert p.birthdate == date(2000, 1, 1)
    assert p.studies == Studies.BACHELOR

def test_init_estudios_por_defecto():    # [PASA]
    p = Person(date(2000, 1, 1), "David")
    assert p.studies == Studies.NONE

def test_init_no_valida_fecha_futura():  # [FALLA] (segun spec "no estados invalidos")
    # El constructor acepta una fecha de nacimiento en el futuro sin
    # rechazarla; asumiendo que eso es un estado invalido, el test falla.
    futuro = date(HOY.year + 5, 1, 1)
    p = Person(futuro, "David")
    assert p.birthdate <= HOY   # deberia haberse rechazado / no ocurre


# ============================= age =============================

def test_age_cumpleanios_ya_paso():      # [PASA]
    p = Person(hace_anios(30, 1, 1), "David")   # nacio 1-ene
    assert p.age == 30

def test_age_cumpleanios_no_llego():     # [FALLA] evidencia el defecto (off-by-one)
    # Nacio un 31-dic: hasta diciembre todavia tiene 29, no 30.
    p = Person(hace_anios(30, 12, 31), "David")
    esperado = 29 if (HOY.month, HOY.day) < (12, 31) else 30
    assert p.age == esperado


# ========================= studies.setter =========================

def test_studies_setter_actualiza():     # [PASA]
    p = Person(date(2000, 1, 1), "David", Studies.NONE)
    p.studies = Studies.MASTERS
    assert p.studies == Studies.MASTERS

def test_studies_setter_valida_tipo():   # [FALLA] evidencia el defecto (sin validacion)
    # El setter acepta cualquier cosa, rompiendo la invariante de tipo.
    p = Person(date(2000, 1, 1), "David", Studies.MASTERS)
    p.studies = "PhD"                     # valor invalido, no es Studies
    assert isinstance(p.studies, Studies) # deberia mantener la invariante

def test_studies_setter_no_regresa():    # [FALLA] (segun spec: estudios no bajan)
    p = Person(date(2000, 1, 1), "David", Studies.MASTERS)
    p.studies = Studies.PRIMARY           # "des-estudiar" no deberia permitirse
    assert p.studies == Studies.MASTERS


# ============================= __eq__ =============================

def test_eq_mismos_datos_nombre_interno():   # [PASA] (coincidencia por interning)
    a = Person(date(1990, 5, 5), "David", Studies.NONE)
    b = Person(date(1990, 5, 5), "David", Studies.NONE)
    assert a == b

def test_eq_nombre_no_interno():             # [FALLA] evidencia el defecto (`is not`)
    n1 = "".join(["D", "a", "v", "i", "d"])            # mismo valor, distinto objeto
    n2 = "".join(["D", "a", "v", "i", "d"])
    a = Person(date(1990, 5, 5), n1, Studies.NONE)
    b = Person(date(1990, 5, 5), n2, Studies.NONE)
    assert a == b                             # deberian ser iguales

def test_eq_distinta_fecha_no_iguales():     # [FALLA] evidencia el defecto (compara age, no fecha)
    # Mismo nombre, misma edad hoy, pero distinta fecha de nacimiento:
    # el codigo dado los considera iguales porque compara age en vez de birthdate.
    a = Person(hace_anios(40, 1, 1), "Jacob", Studies.NONE)
    b = Person(hace_anios(40, 3, 3), "Jacob", Studies.NONE)
    assert a != b

def test_person_es_hasheable():              # [FALLA] evidencia el defecto (falta __hash__)
    a = Person(date(1990, 5, 5), "David", Studies.NONE)
    assert hash(a) is not None                # TypeError: unhashable type


# ============================== max ==============================
# La funcion max(a, b) esta sin implementar (raise NotImplementedError).
# Estos tests definen el comportamiento esperado bajo el criterio
# propuesto en el informe (mayor nivel de estudios; a igualdad, mayor edad).

def test_max_por_estudios():                 # [FALLA] hasta implementar
    a = Person(hace_anios(30), "David", Studies.DOCTORATE)
    b = Person(hace_anios(30), "Isaac", Studies.BACHELOR)
    from person import max as person_max
    assert person_max(a, b) is a

def test_max_desempata_por_edad():           # [FALLA] hasta implementar
    a = Person(hace_anios(50), "David", Studies.MASTERS)
    b = Person(hace_anios(20), "Isaac", Studies.MASTERS)
    from person import max as person_max
    assert person_max(a, b) is a
