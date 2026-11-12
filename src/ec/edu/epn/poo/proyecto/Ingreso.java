package ec.edu.epn.poo.proyecto;

import java.util.Locale;

/**
 * Dinero que recibe el estudiante: mesada, beca, trabajo, etc.
 */
public class Ingreso {
	private String descripcion;
	private String fuente;      // de dónde viene el dinero
	private double monto;
	private int semana;         // semana del semestre en la que llegó

	public static final String[] FUENTES = {"Familia", "Beca", "Trabajo", "Otros"};
	public static final double MONTO_MAXIMO = 5000.0;
	public static final int SEMANAS_SEMESTRE = 16;

	public Ingreso(String descripcion, String fuente, double monto, int semana) {
		if (!setDescripcion(descripcion)) {
			this.descripcion = "Sin descripción";
		}
		if (!setFuente(fuente)) {
			this.fuente = "Otros";
		}
		if (!setMonto(monto)) {
			this.monto = 0.0;      // un ingreso de 0 no se acepta en el presupuesto
		}
		if (!setSemana(semana)) {
			this.semana = 1;
		}
	}

	// ---------- Getters ----------
	public String getDescripcion() {
		return descripcion;
	}

	public String getFuente() {
		return fuente;
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

	// Regla RN4: la fuente debe ser una de la lista FUENTES
	public boolean setFuente(String fuente) {
		if (!esFuenteValida(fuente)) {
			return false;
		}
		for (String f : FUENTES) {
			if (f.equalsIgnoreCase(fuente.strip())) {
				this.fuente = f;     // se guarda con el formato oficial de la lista
			}
		}
		return true;
	}

	// Método static: se puede usar sin crear un Ingreso (Ingreso.esFuenteValida("x"))
	public static boolean esFuenteValida(String fuente) {
		if (fuente == null) {
			return false;
		}
		for (String f : FUENTES) {
			if (f.equalsIgnoreCase(fuente.strip())) {
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

	// Regla RN5: la semana va de 1 a 16
	public boolean setSemana(int semana) {
		if (semana >= 1 && semana <= SEMANAS_SEMESTRE) {
			this.semana = semana;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "Sem %2d  %-9s %-22s +%8.2f",
				semana, fuente, descripcion, monto);
	}
}
