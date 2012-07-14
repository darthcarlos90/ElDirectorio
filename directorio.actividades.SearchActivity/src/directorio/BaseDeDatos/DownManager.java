package directorio.BaseDeDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.FloatMath;

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

	public DownManager() {
		DescargaBD();
		try{
		modifyDatabase();
		}catch(SQLiteException e){
			System.out.println(e.getMessage());
		}

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
			URL url = new URL("http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=0");

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			File SDCardRoot = Environment.getExternalStorageDirectory();
			file = new File(SDCardRoot, "DirLaguna.db");

			FileOutputStream fileOutput = new FileOutputStream(file);

			InputStream inputStream = urlConnection.getInputStream();

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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateProgress(int downloadedSize, int totalSize) {
		// TODO Auto-generated method stub
		System.out.println("He descargado " + downloadedSize + " de " + totalSize);
	}

	public String dameBDLocation() {
		return location;

	}

	/**
	 * Checar este m�todo, hace lo mismo que el de arriba pero diferente xD. No
	 * se pudo probar el m�todo porque no se puede importar en IOUtils no se
	 * porqu�. REVISAR PORFAVOR!!!!
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
	
}