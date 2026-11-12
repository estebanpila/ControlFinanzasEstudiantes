package ec.edu.epn.poo.proyecto;

/**
 * Representa al estudiante universitario dueño del presupuesto.
 * Por defecto pertenece a la EPN, pero puede ser de cualquier universidad.
 */
public class Estudiante {
	// Atributos privados: solo esta clase puede leerlos o cambiarlos directamente
	private String nombre;
	private String codigo;       // código único del estudiante (solo dígitos)
	private String carrera;
	private String universidad;

	// Constantes de las reglas de negocio
	public static final int MIN_DIGITOS_CODIGO = 6;
	public static final int MAX_DIGITOS_CODIGO = 10;
	public static final String UNIVERSIDAD_POR_DEFECTO = "EPN";

	// Constructor completo: sirve para estudiantes de cualquier universidad
	public Estudiante(String nombre, String codigo, String carrera, String universidad) {
		// Se reutilizan los setters para que el constructor respete las mismas reglas.
		// Si un dato no cumple la regla, se guarda un valor seguro.
		if (!setNombre(nombre)) {
			this.nombre = "Sin nombre";
		}
		if (!setCodigo(codigo)) {
			this.codigo = "000000";
		}
		if (!setCarrera(carrera)) {
			this.carrera = "Sin carrera";
		}
		if (!setUniversidad(universidad)) {
			this.universidad = UNIVERSIDAD_POR_DEFECTO;
		}
	}

	// Constructor sobrecargado: si no se indica la universidad, se asume EPN
	public Estudiante(String nombre, String codigo, String carrera) {
		if (!setNombre(nombre)) {
			this.nombre = "Sin nombre";
		}
		if (!setCodigo(codigo)) {
			this.codigo = "000000";
		}
		if (!setCarrera(carrera)) {
			this.carrera = "Sin carrera";
		}
		this.universidad = UNIVERSIDAD_POR_DEFECTO;
	}

	// ---------- Getters: consultar ----------
	public String getNombre() {
		return nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCarrera() {
		return carrera;
	}

	public String getUniversidad() {
		return universidad;
	}

	// ---------- Setters con reglas de negocio ----------
	// Regla RN1: el nombre no puede ser null ni estar vacío
	public boolean setNombre(String nombre) {
		if (nombre != null && !nombre.strip().isEmpty()) {
			this.nombre = nombre.strip();
			return true;
		}
		return false;
	}

	// Regla RN2: el código solo tiene dígitos y entre 6 y 10 caracteres
	public boolean setCodigo(String codigo) {
		if (codigo == null) {
			return false;
		}
		codigo = codigo.strip();
		if (codigo.length() < MIN_DIGITOS_CODIGO || codigo.length() > MAX_DIGITOS_CODIGO) {
			return false;
		}
		for (int i = 0; i < codigo.length(); i++) {
			char c = codigo.charAt(i);
			if (c < '0' || c > '9') {
				return false;    // encontró una letra o símbolo
			}
		}
		this.codigo = codigo;
		return true;
	}

	// Regla RN1 también para la carrera
	public boolean setCarrera(String carrera) {
		if (carrera != null && !carrera.strip().isEmpty()) {
			this.carrera = carrera.strip();
			return true;
		}
		return false;
	}

	// Regla RN1 también para la universidad
	public boolean setUniversidad(String universidad) {
		if (universidad != null && !universidad.strip().isEmpty()) {
			this.universidad = universidad.strip();
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return nombre + " (" + codigo + ") - " + carrera + ", " + universidad;
	}
}
