"""
Tests para el modulo max.py  (Practica 1 - Ejercicio 3).

Ejecutar con:  pytest test_max.py -v

Convencion:
  - Los tests marcados como  [PASA]  pasan contra el codigo dado.
  - Los tests marcados como  [FALLA] fallan contra el codigo dado y
    evidencian el defecto (ver informe LaTeX). Pasan con la correccion.
"""

from max import max_int, max_int_seq


# ----------------------------- max_int -----------------------------
# max_int es correcta: todos estos tests pasan.

def test_max_int_a_mayor():          # [PASA]
    assert max_int(5, 3) == 5

def test_max_int_b_mayor():          # [PASA]
    assert max_int(3, 5) == 5

def test_max_int_iguales():          # [PASA]
    assert max_int(4, 4) == 4

def test_max_int_negativos():        # [PASA]
    assert max_int(-2, -7) == -2


# --------------------------- max_int_seq ---------------------------
# Especificacion asumida (segun el codigo): si las listas tienen
# distinta longitud gana la mas larga; si tienen igual longitud gana
# la que tiene mas maximos elemento-a-elemento.

def test_seq_distinta_longitud():    # [PASA]
    # gana la lista mas larga
    assert max_int_seq([1], [2, 3]) == [2, 3]

def test_seq_igual_longitud_gana_b():   # [PASA] (correccion "por coincidencia")
    # b gana legitimamente y el bug tambien devuelve b -> pasa por casualidad
    assert max_int_seq([1, 2], [3, 4]) == [3, 4]

def test_seq_igual_longitud_gana_a():   # [FALLA] evidencia el defecto
    # a deberia ganar (2 maximos vs 0) pero el codigo dado devuelve b
    assert max_int_seq([9, 9], [1, 1]) == [9, 9]

def test_seq_igual_longitud_gana_a_2():  # [FALLA] evidencia el defecto
    assert max_int_seq([3, 5], [1, 2]) == [3, 5]
