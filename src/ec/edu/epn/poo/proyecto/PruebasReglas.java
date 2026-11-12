package ec.edu.epn.poo.proyecto;

import java.util.Locale;

/**
 * Pruebas de las reglas de negocio: intenta modificar los atributos con
 * valores válidos (prueba exitosa) y con valores inválidos (prueba fallida)
 * y comprueba que el objeto solo acepta los cambios que cumplen las reglas.
 */
public class PruebasReglas {

	private static int total = 0;
	private static int superadas = 0;

	public static void main(String[] args) {
		System.out.println("=========== PRUEBAS DE REGLAS DE NEGOCIO ===========");

		// ---------- Estudiante ----------
		System.out.println("\n[Estudiante]");
		Estudiante e = new Estudiante("Esteban Pila", "202312345", "Tecnologías de la Información");
		verificar("P01", "setNombre(\"Erick Veliz\")", true,
				e.setNombre("Erick Veliz"), "nombre = " + e.getNombre());
		verificar("P02", "setNombre(\"   \")", false,
				e.setNombre("   "), "nombre = " + e.getNombre());
		verificar("P03", "setCodigo(\"202354321\")", true,
				e.setCodigo("202354321"), "código = " + e.getCodigo());
		verificar("P04", "setCodigo(\"20A3\")", false,
				e.setCodigo("20A3"), "código = " + e.getCodigo());
		verificar("P05", "setUniversidad(\"Universidad Central\")", true,
				e.setUniversidad("Universidad Central"), "universidad = " + e.getUniversidad());

		// ---------- Gasto ----------
		System.out.println("\n[Gasto]");
		Gasto g = new Gasto("Almuerzo", "Alimentación", 3.50, 1);
		verificar("P06", "setMonto(4.25)", true,
				g.setMonto(4.25), "monto = " + g.getMonto());
		verificar("P07", "setMonto(-10)", false,
				g.setMonto(-10), "monto = " + g.getMonto());
		verificar("P08", "setCategoria(\"transporte\")", true,
				g.setCategoria("transporte"), "categoría = " + g.getCategoria());
		verificar("P09", "setCategoria(\"Videojuegos\")", false,
				g.setCategoria("Videojuegos"), "categoría = " + g.getCategoria());
		verificar("P10", "setSemana(20)", false,
				g.setSemana(20), "semana = " + g.getSemana());

		// ---------- Ingreso ----------
		System.out.println("\n[Ingreso]");
		Ingreso i = new Ingreso("Mesada", "Familia", 120.00, 1);
		verificar("P11", "setFuente(\"Beca\")", true,
				i.setFuente("Beca"), "fuente = " + i.getFuente());
		verificar("P12", "setMonto(0)", false,
				i.setMonto(0), "monto = " + i.getMonto());

		// ---------- Presupuesto ----------
		System.out.println("\n[Presupuesto]");
		Presupuesto p = new Presupuesto(e, 40.0);
		verificar("P13", "setLimiteSemanal(-5)", false,
				p.setLimiteSemanal(-5), "límite = " + p.getLimiteSemanal());
		verificar("P14", "registrarIngreso(mesada $120)", true,
				p.registrarIngreso(new Ingreso("Mesada", "Familia", 120.00, 1)),
				"saldo = " + p.getSaldo());
		verificar("P15", "registrarGasto(pasajes $15)", true,
				p.registrarGasto(new Gasto("Pasajes", "Transporte", 15.00, 1)),
				"saldo = " + p.getSaldo());
		verificar("P16", "registrarGasto(laptop $800)", false,
				p.registrarGasto(new Gasto("Laptop", "Materiales", 800.00, 2)),
				"saldo = " + p.getSaldo());
		verificar("P17", "registrarGasto(libro $30)", true,
				p.registrarGasto(new Gasto("Libro de Física", "Materiales", 30.00, 1)),
				"saldo = " + p.getSaldo());
		System.out.printf(Locale.US, "    Alerta semana 1: gastó $%.2f, límite $%.2f -> superaLimite = %b%n",
				p.calcularGastoDeSemana(1), p.getLimiteSemanal(), p.superaLimite(1));

		// p.saldo = 1000000;   // NO COMPILA: saldo es private y no tiene setter

		System.out.println("\n----------------------------------------------------");
		System.out.printf(Locale.US, "Pruebas superadas: %d de %d%n", superadas, total);
	}

	// Compara lo que se esperaba (aceptar o rechazar) con lo que hizo el objeto
	private static void verificar(String id, String accion, boolean esperado,
			boolean obtenido, String estado) {
		total++;
		String resultado;
		if (obtenido) {
			resultado = "ACEPTADO";
		} else {
			resultado = "RECHAZADO";
		}
		String marca;
		if (esperado == obtenido) {
			marca = "[OK]";
			superadas++;
		} else {
			marca = "[ERROR]";
		}
		System.out.printf("%s %-38s -> %-9s | %-28s %s%n",
				id, accion, resultado, estado, marca);
	}
}
