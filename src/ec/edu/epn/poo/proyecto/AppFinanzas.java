package ec.edu.epn.poo.proyecto;

import java.util.Locale;
import java.util.Scanner;

/**
 * Programa principal: menú para que el estudiante registre ingresos y gastos
 * y revise cómo va su presupuesto durante el semestre.
 */
public class AppFinanzas {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.US);

		System.out.println("=== CONTROL DE RECURSOS ECONÓMICOS PARA ESTUDIANTES ===");
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Código de estudiante: ");
		String codigo = sc.nextLine();
		System.out.print("Carrera: ");
		String carrera = sc.nextLine();
		System.out.print("Universidad (Enter = EPN): ");
		String universidad = sc.nextLine();

		Estudiante estudiante;
		if (universidad.strip().isEmpty()) {
			estudiante = new Estudiante(nombre, codigo, carrera);
		} else {
			estudiante = new Estudiante(nombre, codigo, carrera, universidad);
		}
		double limite = leerDouble(sc, "Límite de gasto semanal ($): ");
		Presupuesto presupuesto = new Presupuesto(estudiante, limite);
		System.out.println("Bienvenido/a: " + estudiante);

		int opcion;
		do {
			System.out.println();
			System.out.println("========== MENÚ ==========");
			System.out.printf(Locale.US, "Saldo actual: $%.2f%n", presupuesto.getSaldo());
			System.out.println("1. Registrar ingreso");
			System.out.println("2. Registrar gasto");
			System.out.println("3. Ver movimientos");
			System.out.println("4. Ver resumen");
			System.out.println("5. Revisar gasto de una semana");
			System.out.println("6. Cambiar límite semanal");
			System.out.println("0. Salir");
			opcion = leerEntero(sc, "Opción: ");

			switch (opcion) {
				case 1 -> {
					String desc = leerTexto(sc, "Descripción: ");
					String fuente;
					do {
						System.out.print("Fuente (");
						for (String f : Ingreso.FUENTES) {
							System.out.print(f + " ");
						}
						fuente = leerTexto(sc, "): ");
					} while (!Ingreso.esFuenteValida(fuente));
					double monto = leerDouble(sc, "Monto: ");
					int semana = leerSemana(sc);

					Ingreso ingreso = new Ingreso(desc, fuente, monto, semana);
					if (presupuesto.registrarIngreso(ingreso)) {
						System.out.println("Ingreso registrado: " + ingreso);
					} else {
						System.out.println("Ingreso rechazado: el monto debe ser mayor que 0 y hasta "
								+ Ingreso.MONTO_MAXIMO);
					}
				}
				case 2 -> {
					String desc = leerTexto(sc, "Descripción: ");
					String categoria;
					do {
						System.out.print("Categoría (");
						for (String c : Gasto.CATEGORIAS) {
							System.out.print(c + " ");
						}
						categoria = leerTexto(sc, "): ");
					} while (!Gasto.esCategoriaValida(categoria));
					double monto = leerDouble(sc, "Monto: ");
					int semana = leerSemana(sc);

					Gasto gasto = new Gasto(desc, categoria, monto, semana);
					if (presupuesto.registrarGasto(gasto)) {
						System.out.println("Gasto registrado: " + gasto);
						if (presupuesto.superaLimite(gasto.getSemana())) {
							System.out.printf(Locale.US,
									"¡Alerta! En la semana %d ya gastó $%.2f (límite $%.2f)%n",
									gasto.getSemana(),
									presupuesto.calcularGastoDeSemana(gasto.getSemana()),
									presupuesto.getLimiteSemanal());
						}
					} else {
						System.out.printf(Locale.US,
								"Gasto rechazado: monto inválido o saldo insuficiente (saldo: $%.2f)%n",
								presupuesto.getSaldo());
					}
				}
				case 3 -> presupuesto.mostrarMovimientos();
				case 4 -> presupuesto.mostrarResumen();
				case 5 -> {
					int semana = leerSemana(sc);
					System.out.printf(Locale.US, "Gasto de la semana %d: $%.2f de $%.2f%n",
							semana, presupuesto.calcularGastoDeSemana(semana),
							presupuesto.getLimiteSemanal());
					if (presupuesto.superaLimite(semana)) {
						System.out.println("Superó el límite semanal.");
					} else {
						System.out.println("Está dentro del límite.");
					}
				}
				case 6 -> {
					double nuevo = leerDouble(sc, "Nuevo límite semanal: ");
					if (presupuesto.setLimiteSemanal(nuevo)) {
						System.out.println("Límite actualizado.");
					} else {
						System.out.println("Límite rechazado: debe ser mayor que 0.");
					}
				}
				case 0 -> System.out.println("Hasta pronto, " + estudiante.getNombre() + ".");
				default -> System.out.println("Opción inválida.");
			}
		} while (opcion != 0);
		sc.close();
	}

	// ---------- Métodos auxiliares de lectura (validan el tipo de dato) ----------
	private static int leerEntero(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextInt()) {
			System.out.print("Debe ser un número entero. " + mensaje);
			sc.next();
		}
		int valor = sc.nextInt();
		sc.nextLine();                 // consume el Enter pendiente
		return valor;
	}

	private static double leerDouble(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextDouble()) {
			System.out.print("Debe ser un número (use punto decimal). " + mensaje);
			sc.next();
		}
		double valor = sc.nextDouble();
		sc.nextLine();                 // consume el Enter pendiente
		return valor;
	}

	// Pide la semana hasta que esté entre 1 y 16
	private static int leerSemana(Scanner sc) {
		int semana;
		do {
			semana = leerEntero(sc, "Semana (1-" + Ingreso.SEMANAS_SEMESTRE + "): ");
		} while (semana < 1 || semana > Ingreso.SEMANAS_SEMESTRE);
		return semana;
	}

	private static String leerTexto(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		return sc.nextLine();
	}
}
