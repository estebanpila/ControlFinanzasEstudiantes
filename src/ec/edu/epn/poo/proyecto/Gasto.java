package ec.edu.epn.poo.proyecto;

import java.util.Locale;

/**
 * Dinero que gasta el estudiante en una categoría y una semana del semestre.
 */
public class Gasto {
	private String descripcion;
	private String categoria;
	private double monto;
	private int semana;

	public static final String[] CATEGORIAS =
		{"Alimentación", "Transporte", "Materiales", "Vivienda", "Otros"};
	public static final double MONTO_MAXIMO = 5000.0;

	public Gasto(String descripcion, String categoria, double monto, int semana) {
		if (!setDescripcion(descripcion)) {
			this.descripcion = "Sin descripción";
		}
		if (!setCategoria(categoria)) {
			this.categoria = "Otros";
		}
		if (!setMonto(monto)) {
			this.monto = 0.0;      // un gasto de 0 no se acepta en el presupuesto
		}
		if (!setSemana(semana)) {
			this.semana = 1;
		}
	}

	// ---------- Getters ----------
	public String getDescripcion() {
		return descripcion;
	}

	public String getCategoria() {
		return categoria;
	}

	public double getMonto() {
		return monto;
	}

	public int getSemana() {
		return semana;
	}

	// ---------- Setters con reglas de negocio ----------
	public boolean setDescripcion(String descripcion) {
		if (descripcion != null && !descripcion.strip().isEmpty()) {
			this.descripcion = descripcion.strip();
			return true;
		}
		return false;
	}

	// Regla RN4: la categoría debe ser una de la lista CATEGORIAS
	public boolean setCategoria(String categoria) {
		if (!esCategoriaValida(categoria)) {
			return false;
		}
		for (String c : CATEGORIAS) {
			if (c.equalsIgnoreCase(categoria.strip())) {
				this.categoria = c;    // se guarda con el formato oficial de la lista
			}
		}
		return true;
	}

	// Método static: se puede usar sin crear un Gasto (Gasto.esCategoriaValida("x"))
	public static boolean esCategoriaValida(String categoria) {
		if (categoria == null) {
			return false;
		}
		for (String c : CATEGORIAS) {
			if (c.equalsIgnoreCase(categoria.strip())) {
				return true;
			}
		}
		return false;
	}

	// Regla RN3: el monto debe ser mayor que 0 y no superar el máximo
	public boolean setMonto(double monto) {
		if (monto > 0 && monto <= MONTO_MAXIMO) {
			this.monto = monto;
			return true;
		}
		return false;
	}

	// Regla RN5: la semana va de 1 a 16 (se reutiliza la constante de Ingreso)
	public boolean setSemana(int semana) {
		if (semana >= 1 && semana <= Ingreso.SEMANAS_SEMESTRE) {
			this.semana = semana;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "Sem %2d  %-12s %-20s -%8.2f",
				semana, categoria, descripcion, monto);
	}
}
