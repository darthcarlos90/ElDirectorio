package directorio.actividades;

import java.io.File;
import java.io.IOException;

import directorio.BaseDeDatos.DownManager;
import directorio.DAO.otrosDao;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint({ "ParserError", "ParserError" })
public class Inicio extends Activity {

	File bd;
	Thread timer;
	SQLiteDatabase db;
	otrosDao od;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		String ver = Build.VERSION.RELEASE.substring(0,3);
		float version = Float.parseFloat(ver);
			
		System.out.println("Estoy corriendo en android " + version);
		
		if(version >= 3.0){
			System.out.println("Aplique permisos al thread principal");
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy); 
		}
		
		setContentView(R.layout.splishsplash);
		final ProgressBar descargar = (ProgressBar) findViewById(R.id.progressBar1);
		 final TextView texto = (TextView)findViewById(R.id.textView1);
		 final TextView texto2 = (TextView)findViewById(R.id.textView2);

		timer = new Thread() {
			public void run() {
				try {
					if (!checkForBD()) {
						System.out.println("No existe... descargando...");
						texto.setText("Descargando...");
//						texto.setGravity(Gravity.CENTER_VERTICAL);
						descargar.setIndeterminate(true);
						downloadDataBase();
					} else {
						ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo netInfo = cm.getActiveNetworkInfo();
						if (netInfo != null) {
							System.out.println("Hay conexión a internet");
							sleep(4000);
							// descargar.setIndeterminate(true);
							tryAct();
						} else {
							System.out.println("No hay conexión");
						}
					}
					sleep(4000);
					System.out.println("Existe, No descargue nada...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						// texto.setVisibility(TextView.INVISIBLE);
						// texto2.setVisibility(TextView.INVISIBLE);
						// descargar.setVisibility(ProgressBar.INVISIBLE);
						Class listactivity = Class
								.forName("directorio.actividades.ShowPortalActivity");
						Intent ble = new Intent(Inicio.this, listactivity);
						ble.putExtra("basedatos", bd.getAbsolutePath());

						startActivity(ble);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				}
			}
		};
		timer.start();
	}

	private void downloadDataBase() {
		new DownManager(0);
	}

	private void updateDatabase() {
		int version = 0;
		od = new otrosDao();

		try {
			version = od.getVersion();
		} catch (Exception e) {
			boolean done = bd.delete();
			if (!done) {
				try {
					bd.getCanonicalFile().delete();
					restartFirstActivity();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
		new DownManager(version);
		od.regresaOtraDb().close();
	}

	private void restartFirstActivity() {
		// TODO Auto-generated method stub
		Intent i = getApplicationContext().getPackageManager()
				.getLaunchIntentForPackage(
						getApplicationContext().getPackageName());

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	private boolean checkForBD() {
		bd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/DirLaguna.db");
		boolean bool = bd.exists();
		if (bool == true) {
			return true;
		} else {
			return false;
		}
	}

	private void tryAct() {
		try {
			updateDatabase();
			System.out.println("Descarga Actualización");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
