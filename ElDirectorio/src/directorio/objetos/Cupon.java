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
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import android.os.Environment;



public class Cupon {
	private int cuponId;
	private String advertiserId;
	private String name;
	private String descripcion;
	private String conditions;
	private String HowToCash;
	private String start;
	private String end;
	private String picUrl;
	private String Negocio;
	
	public String getNegocio() {
		return Negocio;
	}

	public void setNegocio(String negocio) {
		Negocio = negocio;
	}

	private byte[] imgSrc;

	public int getCuponId() {
		return cuponId;
	}

	public void setCuponId(int cuponId) {
		this.cuponId = cuponId;
	}
	public byte[] getImgSrc() {

		return imgSrc;
	}

	public void setImgSrc(String url) {
		byte[] imgSrc = null;

		File RutaImagenes = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/ElDirectorio/");
		RutaImagenes.mkdirs();
		File archivo = new File(RutaImagenes, this.getCuponId() + ".png");

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


	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getHowToCash() {
		return HowToCash;
	}

	public void setHowToCash(String howToCash) {
		HowToCash = howToCash;
	}

	public String getStart() {
		try{
		StringTokenizer st = new StringTokenizer(start);
		String result = st.nextToken();
		return result;
		}catch(NullPointerException npe){
			return "";
		}
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
