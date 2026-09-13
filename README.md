# Reparación Automática de Software — UNRC

Material del curso de posgrado *Reparación Automática de Software* (Universidad Nacional de Río Cuarto): teóricos, papers de referencia, herramientas de mutation testing y prácticos.

## Estructura

```
teoricos/       Slides de las clases teóricas
papers/         Papers de investigación de referencia
herramientas/   Herramientas de mutation testing (Major, MuJP, mutmut)
practicos/      Consignas de prácticos y material de demo
```

## Teóricos

| Clase | Tema | Carpeta |
|-------|------|---------|
| 1 | Introducción | [`teoricos/clase-1-introduccion`](teoricos/clase-1-introduccion) |
| 2 | Test Selection · Mutation Testing | [`teoricos/clase-2-test-selection-mutation-testing`](teoricos/clase-2-test-selection-mutation-testing) |
| 3 | HOMS · Fault Localization | [`teoricos/clase-3-homs-fault-localization`](teoricos/clase-3-homs-fault-localization) |
| 4 | Mutant Subsumption · Minimum Test Set | [`teoricos/clase-4-mutant-subsumption-minimum-test-set`](teoricos/clase-4-mutant-subsumption-minimum-test-set) |

## Papers

| Paper | Tema relacionado |
|-------|------------------|
| `EstablishingTheoreticalMinimalSetsofMutants-ICST2014.pdf` | Minimum test set / mutant subsumption (Clase 4) |
| `high_order_mutation_testing.pdf` | Higher Order Mutants — HOMS (Clase 3) |
| `ask_the_mutants.pdf` | Fault localization con mutantes (Clase 3) |
| `effective_fL_via_mutation_a_selective_mutation_approach.pdf` | Selective mutation para fault localization (Clase 2/3) |
| `flip.pdf` | Fault localization (Clase 3) |

## Herramientas

| Carpeta | Herramienta | Lenguaje |
|---------|-------------|----------|
| [`herramientas/major`](herramientas/major) | [Major](https://mutation-testing.org/) — mutation testing framework | Java |
| [`herramientas/mujp`](herramientas/mujp) | MuJP — proyecto Maven de ejemplo (mutation testing) | Java |
| [`herramientas/mutmut`](herramientas/mutmut) | mutmut — mutation testing (ejemplo `triangle.py`) | Python |

## Prácticos

- [`practicos/practica-01.pdf`](practicos/practica-01.pdf) — Consigna del Práctico 1
- [`practicos/material-teorico-1`](practicos/material-teorico-1) — Material de demo del Teórico 1 (ejemplos de bugs y demos de testing en Java/pytest)
