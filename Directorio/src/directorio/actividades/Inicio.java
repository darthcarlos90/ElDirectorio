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
import android.os.Bundle;
import android.os.Environment;
import android.widget.ProgressBar;


@SuppressLint({ "ParserError", "ParserError" })
public class Inicio extends Activity {

	File bd;
	Thread timer;
	SQLiteDatabase db;
	otrosDao od;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!checkForBD()) {
			setContentView(R.layout.splishsplash);
		} else {
			setContentView(R.layout.splishsplashmas);
		}

		timer = new Thread() {
			public void run() {
				try {
					if (!checkForBD()) {
						final ProgressBar descargar = (ProgressBar) findViewById(R.id.progressBar1);
						System.out.println("No existe... descargando...");
						descargar.setIndeterminate(true);
						downloadDataBase();
					} else {
						ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
						    NetworkInfo netInfo = cm.getActiveNetworkInfo();
						    if(netInfo != null){
						    	System.out.println("Hay conexión a internet");
						    	tryAct();
						    }else{
						    	System.out.println("No hay conexión");
						    }
					}
					sleep(4000);
					System.out.println("Existe, No descargue nada...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						Class texto = Class
								.forName("directorio.actividades.SearchActivity");
						Intent ble = new Intent(Inicio.this, texto);
						// Intent intent = new Intent(Inicio.this,
						// ListaIndexada.class);
						// startActivity(intent);
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
		 
		 try{	version = od.getVersion(); 
		 }catch(Exception e){
			boolean done = bd.delete();
			if(!done){ 
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
			System.out.println("Descarga Actualizaciòn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
