package directorio.objetos;

/**
 * Clase donde se guardan los datos de cada categoría.
 * 
 * @author Carlos
 * 
 */
public class Categoria {

	private int id;
	private String nombre;
	private char letra;
	private boolean hasCoupons;

	public boolean isHasCoupons() {
		return hasCoupons;
	}

	public void setHasCoupons(boolean hasCoupons) {
		this.hasCoupons = hasCoupons;
	}

	public Categoria(int id, String nombre, char letra, boolean hasCoupons) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.letra = letra;
		this.hasCoupons = hasCoupons;
	}

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
