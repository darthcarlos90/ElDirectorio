package directorio.objetos;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Esta es la clase donde se guardan los datos del advertiser.
 * 
 * @author Carlos Tirado
 * 
 */
public class Advertiser {

	private String id;
	private String nombre;
	private String descripcion;
	private String contacto;
	private String Direccion;
	private String sitioWeb;
	private double posx;
	private double posy;
	private String ciudad;
	private String facebook;
	private String twitter;
	private ArrayList<String> telefono;
	private String[] email;
	private String[] categorias;
	private String[] tags;
	private byte[] imgSrc;
	private String publicityUrl;

	public String getPublicityUrl() {
		return publicityUrl;
	}

	public void setPublicityUrl(String publicityUrl) {
		this.publicityUrl = publicityUrl;
	}

	public byte[] getImgSrc() {
		
		return imgSrc;
	}

	public void setImgSrc(byte[] imgSrc) {
		this.imgSrc = imgSrc;
	}

	public Advertiser(String id, String nombre, String descripcion,
			String contacto, String direccion, String sitioWeb, double posx,
			double posy, String ciudad, String facebook, String twitter,
			ArrayList<String> telefono, String[] email, String[] categorias, String[] tags,
			boolean favorito) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contacto = contacto;
		Direccion = direccion;
		this.sitioWeb = sitioWeb;
		this.posx = posx;
		this.posy = posy;
		this.ciudad = ciudad;
		this.facebook = facebook;
		this.twitter = twitter;
		this.telefono = telefono;
		this.email = email;
		this.categorias = categorias;
		this.tags = tags;
		this.favorito = favorito;
	}

	public Advertiser() {
		// TODO Auto-generated constructor stub
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public ArrayList<String> getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefonos) {
		StringTokenizer st = new StringTokenizer(telefonos);
		while(st.hasMoreElements()){
			String temp = st.nextToken("*|@");
			telefono.add(temp);
		}
		
	}

	public String[] getEmail() {
		return email;
	}

	public void setEmail(String[] email) {
		this.email = email;
	}

	public String[] getCategorias() {
		return categorias;
	}

	public void setCategorias(String[] categorias) {
		this.categorias = categorias;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	private boolean favorito; // en caso de que sea favorito, guardarlo en la
								// clase

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + nombre + descripcion + ciudad + facebook;
	}

}
