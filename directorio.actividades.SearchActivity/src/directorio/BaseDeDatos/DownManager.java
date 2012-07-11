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
		

	}

	@SuppressLint({ "ParserError", "ParserError" })
	public void DescargaBD() {
		try {
			URL url = new URL(
					"http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=0");

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
			}
			fileOutput.close();
			location = file.getAbsolutePath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				"http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=0");
		URLConnection connection = url.openConnection();
		InputStream response = connection.getInputStream();

		FileOutputStream fos = new FileOutputStream(Environment
				.getExternalStorageDirectory().getPath() + "/DirLaguna.db");
		byte data[] = new byte[1024];

		// IOUtils.copy(response, fos);
		response.close();
	}
	
	public double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) { 
		float pk = (float) (180/3.14169);
		
		float a1 = lat_a / pk;
		float a2 = lng_a / pk;
		float b1 = lat_b / pk;
		float b2 = lng_b / pk;

		float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
		float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
		float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);
		
		return 6366000*tt;
	}

}