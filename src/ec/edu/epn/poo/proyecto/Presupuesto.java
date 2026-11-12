package ec.edu.epn.poo.proyecto;

import java.util.Locale;

/**
 * Presupuesto del semestre de un estudiante: guarda sus ingresos y gastos
 * y controla el saldo disponible.
 */
public class Presupuesto {
	private Estudiante titular;
	private double saldo;             // NO tiene setter: solo cambia con ingresos y gastos
	private double limiteSemanal;     // cuánto planea gastar como máximo por semana
	private Ingreso[] ingresos;
	private Gasto[] gastos;
	private int numIngresos;          // cuántas posiciones del arreglo están ocupadas
	private int numGastos;

	public static final int MAX_MOVIMIENTOS = 100;
	private static int presupuestosCreados = 0;

	public Presupuesto(Estudiante titular, double limiteSemanal) {
		this.titular = titular;
		if (!setLimiteSemanal(limiteSemanal)) {
			this.limiteSemanal = 50.0;     // valor sugerido si el dato no es válido
		}
		this.saldo = 0.0;                  // todo presupuesto empieza en cero
		this.ingresos = new Ingreso[MAX_MOVIMIENTOS];
		this.gastos = new Gasto[MAX_MOVIMIENTOS];
		this.numIngresos = 0;
		this.numGastos = 0;
		presupuestosCreados++;
	}

	// ---------- Getters ----------
	public Estudiante getTitular() {
		return titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public double getLimiteSemanal() {
		return limiteSemanal;
	}

	public int getNumIngresos() {
		return numIngresos;
	}

	public int getNumGastos() {
		return numGastos;
	}

	public static int getPresupuestosCreados() {
		return presupuestosCreados;
	}

	// ---------- Setter con regla de negocio ----------
	// Regla RN6: el límite semanal debe ser mayor que 0
	public boolean setLimiteSemanal(double limiteSemanal) {
		if (limiteSemanal > 0) {
			this.limiteSemanal = limiteSemanal;
			return true;
		}
		return false;
	}

	// ---------- Operaciones del negocio ----------
	// Regla RN7: un ingreso válido aumenta el saldo
	public boolean registrarIngreso(Ingreso ingreso) {
		if (ingreso == null || ingreso.getMonto() <= 0) {
			return false;
		}
		if (numIngresos >= MAX_MOVIMIENTOS) {
			return false;                  // el arreglo está lleno
		}
		ingresos[numIngresos] = ingreso;
		numIngresos++;
		saldo += ingreso.getMonto();
		return true;
	}

	// Regla RN8: no se puede gastar más dinero del que se tiene (saldo nunca negativo)
	public boolean registrarGasto(Gasto gasto) {
		if (gasto == null || gasto.getMonto() <= 0) {
			return false;
		}
		if (gasto.getMonto() > saldo) {
			return false;                  // saldo insuficiente
		}
		if (numGastos >= MAX_MOVIMIENTOS) {
			return false;
		}
		gastos[numGastos] = gasto;
		numGastos++;
		saldo -= gasto.getMonto();
		return true;
	}

	// ---------- Consultas y reportes ----------
	public double calcularTotalIngresos() {
		double total = 0;
		for (int i = 0; i < numIngresos; i++) {
			total += ingresos[i].getMonto();
		}
		return total;
	}

	public double calcularTotalGastos() {
		double total = 0;
		for (int i = 0; i < numGastos; i++) {
			total += gastos[i].getMonto();
		}
		return total;
	}

	public double calcularGastoPorCategoria(String categoria) {
		double total = 0;
		for (int i = 0; i < numGastos; i++) {
			if (gastos[i].getCategoria().equalsIgnoreCase(categoria)) {
				total += gastos[i].getMonto();
			}
		}
		return total;
	}

	public double calcularGastoDeSemana(int semana) {
		double total = 0;
		for (int i = 0; i < numGastos; i++) {
			if (gastos[i].getSemana() == semana) {
				total += gastos[i].getMonto();
			}
		}
		return total;
	}

	// Regla RN9: avisar cuando lo gastado en una semana supera el límite
	public boolean superaLimite(int semana) {
		return calcularGastoDeSemana(semana) > limiteSemanal;
	}

	// Porcentaje de los ingresos que ya se gastó
	public double calcularPorcentajeGastado() {
		double ingresosTotales = calcularTotalIngresos();
		if (ingresosTotales == 0) {
			return 0;                      // evita dividir para cero
		}
		return calcularTotalGastos() / ingresosTotales * 100;
	}

	public void mostrarMovimientos() {
		System.out.println("--- Ingresos ---");
		for (int i = 0; i < numIngresos; i++) {
			System.out.println(ingresos[i]);
		}
		System.out.println("--- Gastos ---");
		for (int i = 0; i < numGastos; i++) {
			System.out.println(gastos[i]);
		}
	}

	public void mostrarResumen() {
		System.out.println("Estudiante: " + titular);
		System.out.printf(Locale.US, "Total ingresos:   %10.2f%n", calcularTotalIngresos());
		System.out.printf(Locale.US, "Total gastos:     %10.2f%n", calcularTotalGastos());
		System.out.printf(Locale.US, "Saldo disponible: %10.2f%n", saldo);
		System.out.printf(Locale.US, "Gastado:          %9.1f %%%n", calcularPorcentajeGastado());
		System.out.println("Gasto por categoría:");
		for (String c : Gasto.CATEGORIAS) {
			System.out.printf(Locale.US, "  %-13s %8.2f%n", c, calcularGastoPorCategoria(c));
		}
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "Presupuesto de %s: saldo $%.2f, límite semanal $%.2f",
				titular.getNombre(), saldo, limiteSemanal);
	}
}
