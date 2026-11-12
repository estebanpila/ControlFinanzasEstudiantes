# Control de recursos económicos para estudiantes universitarios

Proyecto integrador — Programación Avanzada (ITID433), EPN, 2026-B.
Integrantes: Esteban Pila, Erick Veliz.

Programa en Java que permite a un estudiante registrar sus ingresos y gastos del
semestre, controlar su saldo y recibir una alerta cuando supera su límite de gasto semanal.

## Clases (paquete `ec.edu.epn.poo.proyecto`)
- `Estudiante` — titular del presupuesto (por defecto EPN, sirve para cualquier universidad).
- `Ingreso` — dinero recibido (familia, beca, trabajo, otros).
- `Gasto` — dinero gastado por categoría (alimentación, transporte, materiales, vivienda, otros).
- `Presupuesto` — guarda ingresos y gastos y controla el saldo.
- `AppFinanzas` — menú principal (ejecutar esta clase).
- `PruebasReglas` — pruebas exitosas/fallidas de las reglas de negocio.
