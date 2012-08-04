package directorio.objetos;

/**
 * Clase donde se guardan los datos de cada categoría.
 * @author Carlos
 *
 */
public class Categoria {
	public Categoria(int id, String nombre, char letra) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.letra = letra;
	}

	private int id;
	private String nombre;
	private char letra;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getLetra() {
		return letra;
	}

	public void setLetra(char letra) {
		this.letra = letra;
	}
}
