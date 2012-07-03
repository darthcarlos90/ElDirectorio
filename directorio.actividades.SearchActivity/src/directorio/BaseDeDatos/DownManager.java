package directorio.BaseDeDatos;

import org.apache.http.client.HttpClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
 
import org.apache.http.util.ByteArrayBuffer;
 
import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

/**
 * Clase que descarga la base de datos del link.
 * @author Juan Carlos
 *
 */
 
public class DownManager {
	
		String location = "";
 
        @SuppressLint({ "ParserError", "ParserError" })
		public void DescargaBD() {  
        	try {
                URL url = new URL("http://71.6.150.179:8079/dbHandler.axd?SqliteDbVersion=0");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                File SDCardRoot = Environment.getExternalStorageDirectory();
                File file = new File(SDCardRoot,"DirLaguna.db");

                FileOutputStream fileOutput = new FileOutputStream(file);

                InputStream inputStream = urlConnection.getInputStream();

                int totalSize = urlConnection.getContentLength();
                int downloadedSize = 0;

                byte[] buffer = new byte[1024];
                int bufferLength = 0; 
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
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
        
        public String dameBDLocation(){
			return location;
        	
        }
}