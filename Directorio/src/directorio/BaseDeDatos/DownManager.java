package directorio.BaseDeDatos;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.xml.sax.InputSource;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.webkit.MimeTypeMap;

/**
 * Clase que descarga la base de datos del link.
 * 
 * @author Juan Carlos
 * 
 */

public class DownManager {

	String location = "";
	private SQLiteDatabase db;
	private File file;

	private static String DB_URL = "http://admin.eldirectorio.mx/dbHandler.axd?SqliteDbVersion=";
	private static String otraParte = "&nv=1&device=12";
	private String finalUrl;

	public DownManager(int version) {
		// dbUrl = "http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=0";
		finalUrl = DB_URL + version + otraParte;
		DescargaBD();
		/*
		 * try { modifyDatabase(); } catch (SQLiteException e) {
		 * System.out.println(e.getMessage()); }
		 */

	}
	
	public void modifyDatabase() {
		db = SQLiteDatabase.openOrCreateDatabase(file, null);
		String query = "CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US')";
		db.execSQL(query);
		query = "INSERT INTO 'android_metadata' VALUES ('en_US')";
		db.execSQL(query);
		db.close();
	}

	@SuppressLint({ "ParserError", "ParserError" })
	public void DescargaBD() {

		try {
			Downloading(finalUrl);
		} catch (Exception e) {
			// Downloading(dbUrl);
			System.out.println(e.getMessage());
		}

	}

	private void updateProgress(int downloadedSize, int totalSize) {
		System.out.println("He descargado " + downloadedSize + " de "
				+ totalSize);
	}

	public String dameBDLocation() {
		return location;

	}

	/**
	 * Checar este m�todo, hace lo mismo que el de arriba pero diferente xD.
	 * No se pudo probar el m�todo porque no se puede importar en IOUtils no
	 * se porqu�. REVISAR PORFAVOR!!!!
	 * 
	 * @throws IOException
	 */
	public void DescargaBD2() throws IOException {
		URL url = new URL(
				"http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=200");
		URLConnection connection = url.openConnection();
		InputStream response = connection.getInputStream();

		FileOutputStream fos = new FileOutputStream(Environment
				.getExternalStorageDirectory().getPath() + "/DirLaguna.db");
		byte data[] = new byte[1024];

		// IOUtils.copy(response, fos);
		response.close();
	}

	public void Downloading(String urlDB) {
		try {
			System.out.println("Descargar de: " +urlDB);
			URL url = new URL(urlDB);
			System.out.println("Estado de la tarjeta es: "
					+ Environment.getExternalStorageState());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			InputStream inputStream = urlConnection.getInputStream();
			int empty = inputStream.available();
			if (empty == 0) {	
				System.out.println("System up to date");
			} else {
				//Para comparar, el .db es un application/octet-stream, y el .gz es un application/x-gzip
				String tipodeArchivo = urlConnection.getContentType();
				System.out.println("El tipo de archivo es: "+tipodeArchivo);
				
				if(tipodeArchivo.equals("application/octet-stream") || tipodeArchivo.equals("application/x-gzip")){
					System.out.println("Entre al comprimido");
					File SDCardRoot = Environment.getExternalStorageDirectory();
					file = new File(SDCardRoot, "DirLaguna.db");
					System.out.println("Tamaño del archivo... " + urlConnection.getContentLength());
					InputStream stream = urlConnection.getInputStream();
					stream = new GZIPInputStream(stream);
					InputSource is = new InputSource(stream);
					InputStream input = new BufferedInputStream(is.getByteStream());
					OutputStream output = new FileOutputStream(file);
					byte data[] = new byte[2097152];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
					total += count;
					output.write(data, 0, count);
					}
					output.flush();
					output.close();
					input.close();
					location = file.getAbsolutePath();
					
				}else{
					System.out.println("Archivo normal...");
					File SDCardRoot = Environment.getExternalStorageDirectory();
					file = new File(SDCardRoot, "DirLaguna.db");

					FileOutputStream fileOutput = new FileOutputStream(file);

					int totalSize = urlConnection.getContentLength();
					
					int downloadedSize = 0;

					byte[] buffer = new byte[1024];
					int bufferLength = 0;
					while ((bufferLength = inputStream.read(buffer)) > 0) {
						fileOutput.write(buffer, 0, bufferLength);
						downloadedSize += bufferLength;

						updateProgress(downloadedSize, totalSize);
					}
					fileOutput.close();
					location = file.getAbsolutePath();
					
					
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFile(){
		return file;
	}
	
}