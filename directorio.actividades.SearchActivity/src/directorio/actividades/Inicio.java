package directorio.actividades;

import java.io.File;

import directorio.BaseDeDatos.DownManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

;

@SuppressLint("ParserError")
public class Inicio extends Activity {

	File bd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		
		if (!checkForBD()) {
			setContentView(R.layout.splishsplash);
		}else{
			setContentView(R.layout.splishsplashmas);
		}
		
		Thread timer = new Thread(){
    		public void run(){
    			try{
    				if (!checkForBD()) {
    					final ProgressBar descargar = (ProgressBar)findViewById(R.id.progressBar1);
    					System.out.println("No existe... descargando...");
    					descargar.setIndeterminate(true);
    					downloadDataBase();
    				}
    				sleep(4000);
    				System.out.println("Existe, No descargue nada...");
    			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
    				try {
						Class texto = Class.forName("directorio.actividades.SearchActivity");
						Intent ble = new Intent(Inicio.this,texto);
						ble.putExtra("basedatos", bd.getAbsolutePath());
	    				startActivity(ble);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
    			}
    		}

			private void downloadDataBase() {
				// TODO Auto-generated method stub
				new DownManager();
			}

			private boolean checkForBD() {
				// TODO Auto-generated method stub
				bd = new File(Environment.getExternalStorageDirectory().getPath()
						+ "/DirLaguna.db");
				boolean bool = bd.exists();
				if (bool == true) {
					return true;
				} else {
					return false;
				}
			}
    	};
    	timer.start();	
		
	}

	private boolean checkForBD() {
		// TODO Auto-generated method stub
		bd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/DirLaguna.db");
		boolean bool = bd.exists();
		if (bool == true) {
			return true;
		} else {
			return false;
		}
	}
	}

