package directorio.actividades;

import java.io.File;
import java.util.ArrayList;

import directorio.BaseDeDatos.DownManager;
import directorio.BaseDeDatos.SearchManager;
import directorio.DAO.AdvertiserDAO;
import directorio.DAO.otrosDao;
import directorio.objetos.Advertiser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends Activity {
	private TextView textoBarra;
	private Spinner spinner;
	private SeekBar barra;
	private Button info;
	private LocationManager lm;
	private Location loc;
	private double longitude;
	private double latitude;
	private File bd;
	private double kil;
	private otrosDao others; // para recoger las ciudades de la base de datos
	private static final String PREFS_NAME= "tipo de busqueda";

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getLocation();
		// Checa ausencia de Base de datos, si no existe la descarga
		if (!checkForBD()) {
			System.out.println("No existe, descargando...");
			downloadDataBase();
		}
		//Imprimira si si existe...
		System.out.println("Existe, No descargue nada...");
		//Si existe, inicializara los otros DAO
		others = new otrosDao();
		// Inicializar los elementos en el View
		setupViews();

	}

	/**
	 * M�todo encargado de revisarr si existe la base de datos en la memoria, si
	 * no existe, se descarga.
	 */
	private void downloadDataBase() {
		new DownManager();
	}

	private boolean checkForBD() {
		// TODO Auto-generated method stub
		// bd = new File("/sdcard/DirLaguna.db");
		// Se hizo modificaci�n para no tener que usar hard code para la
		// localizaci�n de la sdCard.
		bd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/DirLaguna.db");
		boolean bool = bd.exists();
		if (bool == true) {
			return true;
		} else {
			return false;
		}
	}

	// El resultado lo imprimira en el logcat, imprimira todos los objetos que
	// saco de la base de datos, imprimira el tamaño del arreglo, la latitud y
	// longitud
	private void getLocation() {
		// TODO Auto-generated method stub
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (loc != null) {
			longitude = loc.getLongitude();
			latitude = loc.getLatitude();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {
		case R.id.btn_abecedario:
			intent = new Intent(this, MostrarCategoriasActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.btn_favoritos:
			SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME,0);
			Editor editor = sharedPrefs.edit();
			editor.putString(PREFS_NAME, "favoritos");
			editor.commit();
			intent = new Intent(this, ShowAdvertisersActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.btn_buscar:
			// Cuando se oprima el bot�n de buscar, se realizar� la b�squeda,
			// sin embargo, todav�a no funciona.
//			AdvertiserDAO add = new AdvertiserDAO(bd.getAbsolutePath());
//			ArrayList<Advertiser> didi = add.findAll();
//			System.out.println("Tamaño de arreglo: " + didi.size());
//			System.out.println("Latitude: " + latitude);
//			System.out.println("Longitude: " + longitude);
			
			//Algoritmo de Busqueda de Lugares Version 1
			AdvertiserDAO add = new AdvertiserDAO(bd.getAbsolutePath());
			ArrayList<Advertiser> didi = add.findAll();
			ArrayList<Advertiser> enRango = new ArrayList<Advertiser>();
			//Clase que contiene el metodo para obtener la distancia entre 2 puntos
			SearchManager ble = new SearchManager();
			//Obtiene la distancia en metros entre 2 puntos, entre el del usuario, y el uno de los negocios del arreglo,
			for(int i = 0; i < didi.size();i++){
				//Obtiene la distancia en metros entre 2 puntos, entre el del usuario, y el uno de los negocios del arreglo,
				double metros = ble.gps2m((float)latitude, (float)longitude,(float) didi.get(i).getPosx(),(float)didi.get(i).getPosy());
				//Lo convertimos a kilometros
				double kilometrosDistancia = metros * 0.001;
				//Si la distancia en kilometros, es menos a la distancia que solicito el usuario...
				if(kilometrosDistancia < kil){
					System.out.println("En rango: "+ didi.get(i).getNombre());
					//lo agrega a la lista temporal de los necogios que estan en rango
					enRango.add(didi.get(i));
				}else{
					//Si no lo esta, imprime que no esta en rango
					System.out.println("No en rango");
				}
			}
			//Al final, imprimimos los negocios que estan en rango
			for(int i1 = 0; i1 < enRango.size();i1++){
				System.out.println("Esta en rango: " +  enRango.get(i1).getNombre());
			}
			//Imprimimos el tamaño del arreglo que esta en rango
			System.out.println("El tamaño del rango es: " + enRango.size());
			//Algoritmo de busqueda de lugares Version 1
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupViews() {

		spinner = (Spinner) findViewById(R.id.spinner_localidades);
		/*
		 * ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		 * this, R.array.ciudades_array, android.R.layout.simple_spinner_item);
		 * 
		 * adapter.setDropDownViewResource(android.R.layout.
		 * simple_spinner_dropdown_item);
		 */

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Se obtiene la lista de ciudades de la base de datos. Sin embargo, la
		// base de datos sigue sin funcionar.
		ArrayList<String> datos = others.getCiudades();
		for (int i = 0; i < datos.size(); i++) {
			adapter.add(datos.get(i));
		}

		spinner.setAdapter(adapter);

		textoBarra = (TextView) findViewById(R.id.mostrar_metros);

		barra = (SeekBar) findViewById(R.id.radioALaRedonda);
		barra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress == 0) {
					textoBarra.setText("--");
				} else {
					kil = (double)progress;
					textoBarra.setText(progress + "km");
				}

			}
		});

		info = (Button) findViewById(R.id.info_button);
		info.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(SearchActivity.this,
						AcercaDeActivity.class);
				SearchActivity.this.startActivity(myIntent);

			}
		});

	}

}