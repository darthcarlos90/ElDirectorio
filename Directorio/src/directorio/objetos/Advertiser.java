package directorio.objetos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.os.Environment;

/**
 * Esta es la clase donde se guardan los datos del advertiser.
 * 
 * @author Carlos Tirado
 * 
 */
@SuppressLint("NewApi")
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
	private ArrayList<String> email;
	private String[] categorias;
	private String[] tags;
	private byte[] imgSrc;
	private String publicityUrl;
	private ArrayList<String> sucursales;
	private int featured;

	public int getFeatured() {
		return featured;
	}

	public void setFeatured(int featured) {
		this.featured = featured;
	}

	public ArrayList<String> getSucursales() {
		return sucursales;
	}

	public void setSucursales(String sucursal) {
		sucursales.add(sucursal);
	}

	public String getPublicityUrl() {
		return publicityUrl;
	}

	public void setPublicityUrl(String publicityUrl) {
		this.publicityUrl = publicityUrl;
	}

	public byte[] getImgSrc() {

		return imgSrc;
	}

	public void setImgSrc(String url) {
		byte[] imgSrc = null;

		File RutaImagenes = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/ElDirectorio/");
		RutaImagenes.mkdirs();
		File archivo = new File(RutaImagenes, this.getId() + ".png");

		if (archivo.exists()) {
			try {
				InputStream is = new FileInputStream(archivo);
				this.imgSrc = IOUtils.toByteArray(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			URL uri;
			try {

				if (url == null) {
					this.imgSrc = null;
				} else {
					uri = new URL(url);
					System.out.println("Estado de la tarjeta es: "
							+ Environment.getExternalStorageState());
					HttpURLConnection urlConnection = (HttpURLConnection) uri
							.openConnection();

					urlConnection.setRequestMethod("GET");
					urlConnection.setDoOutput(true);
					urlConnection.connect();

					InputStream inputStream = urlConnection.getInputStream();
					FileOutputStream fileOutput = new FileOutputStream(archivo);

					int totalSize = urlConnection.getContentLength();
					if (totalSize == 0) {
						this.imgSrc = null;
					} else {
						int downloadedSize = 0;

						byte[] buffer = new byte[1024];
						int bufferLength = 0;
						while ((bufferLength = inputStream.read(buffer)) > 0) {
							fileOutput.write(buffer, 0, bufferLength);
							downloadedSize += bufferLength;
						}
						fileOutput.close();
						InputStream is = new FileInputStream(archivo);
						imgSrc = IOUtils.toByteArray(is);
						this.imgSrc = imgSrc;
					}
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Advertiser(String id, String nombre, String descripcion,
			String contacto, String direccion, String sitioWeb, double posx,
			double posy, String ciudad, String facebook, String twitter,
			ArrayList<String> telefono, ArrayList<String> email,
			String[] categorias, String[] tags, boolean favorito) {
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
		telefono = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(telefonos);
		while (st.hasMoreElements()) {
			String temp = st.nextToken("*|@") + ": " + st.nextToken("*|@");
			telefono.add(temp);
		}

	}

	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(String emails) {
		email = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(emails);
		while (st.hasMoreElements()) {
			String temp = st.nextToken("*|");
			String resultado = "";
			for (int j = 1; j < temp.length(); j++) {
				resultado += temp.charAt(j);
			}
			email.add(resultado);
		}
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