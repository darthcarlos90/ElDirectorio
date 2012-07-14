package directorio.actividades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import directorio.BaseDeDatos.DownManager;
import directorio.BaseDeDatos.SearchManager;
import directorio.DAO.AdvertiserDAO;
import directorio.DAO.otrosDao;
import directorio.objetos.Advertiser;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private TextView textoBarra;
	private Spinner spinner;
	private SeekBar barra;
	private Button info;
	private LocationManager lm;
	private Location loc;
	private double longitude;
	private double latitude;
	private EditText Busqueda;
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

		System.out.println("Longitud: " + longitude);
		System.out.println("Latitude: " + latitude);
		
		bd = new File(getIntent().getExtras().getString("basedatos"));
		try {
			setupViews();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	@SuppressLint({ "ParserError", "ParserError", "ParserError" })
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {
		case R.id.btn_abecedario:
			others.regresaOtraDb().close();
			intent = new Intent(this, ListaIndexada.class);
			this.startActivity(intent);
			return true;
		case R.id.btn_favoritos:
//			SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME,0);
//			Editor editor = sharedPrefs.edit();
//			editor.putString(PREFS_NAME, "favoritos");
//			editor.commit();
			intent = new Intent(this, adverlistitem.class);
			intent.putExtra("estado", 3);
			this.startActivity(intent);
			return true;
		case R.id.btn_buscar:
			//SegundoAlgoritmo de Busqueda
			AdvertiserDAO add = new AdvertiserDAO(bd.getAbsolutePath());
			 ArrayList<Advertiser> negociosenRango = SearchManager.negociosenRango(latitude, longitude, kil, spinner.getSelectedItem().toString(), Busqueda.getText().toString(), add.getdb());			
			for(int i = 0; i < negociosenRango.size();i++){
				System.out.println("En rango: " + negociosenRango.get(i).getNombre());
			}
			System.out.println("Tamaño del arreglo: " + negociosenRango.size());
			add.getdb().close();
			//Algoritmo de busqueda de lugares Version 2
		
			intent = new Intent(this, adverlistitem.class);
			intent.putExtra("estado", 1);
			intent.putExtra("latitude", latitude);
			intent.putExtra("longitude", longitude);
			intent.putExtra("kil", kil);	
			intent.putExtra("ciudad", spinner.getSelectedItem().toString());
			intent.putExtra("busqueda", Busqueda.getText().toString());
			this.startActivity(intent);
			
			 return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupViews() throws IOException {

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
		others = new otrosDao();
		try{
		ArrayList<String> datos = others.getCiudades();
		adapter.add("Todas las ciudades");
		for (int i = 0; i < datos.size(); i++) {
			adapter.add(datos.get(i));
		}
		others.regresaOtraDb().close();
		}catch(Exception e){
			Toast.makeText(this, "Error de bd, reintentando...", Toast.LENGTH_LONG).show();
			File file = new File(getIntent().getExtras(	).getString("basedatos"));
			file.getCanonicalFile().delete();
			restartFirstActivity();
		}
		
		
		spinner.setAdapter(adapter);
		Busqueda = (EditText)findViewById(R.id.busqueda);
		textoBarra = (TextView) findViewById(R.id.mostrar_metros);
		
		barra = (SeekBar) findViewById(R.id.radioALaRedonda);
		barra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@SuppressLint({ "ParserError", "ParserError" })
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress == 0) {
					textoBarra.setText("--");
				} else {
					kil = (double) progress;
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
	private void restartFirstActivity()
	 {
	 Intent i = getApplicationContext().getPackageManager()
	 .getLaunchIntentForPackage(getApplicationContext().getPackageName() );

	 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
	 startActivity(i);
	 }

}
