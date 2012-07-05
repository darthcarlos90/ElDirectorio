package directorio.actividades;

import java.io.File;
import java.util.ArrayList;

import directorio.BaseDeDatos.DownManager;
import directorio.DAO.AdvertiserDAO;
import directorio.DAO.otrosDao;
import directorio.objetos.Advertiser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
	private DownManager ble;
	private Button test;
	private LocationManager lm;
	private Location loc;
	private double longitude;
	private double latitude;
	private File bd;
	private static final String PRIMERA_VEZ = "Primera Vez";
	private otrosDao others; // para recoger las ciudades de la base de datos

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getLocation();
		downloadDataBase();
		others = new otrosDao();// se inicializa la variable
		// Inicializar los elementos en el View
		setupViews();

	}

	/**
	 * Método encargado de revisarr si existe la base de datos en la memoria, si
	 * no existe, se descarga.
	 */
	private void downloadDataBase() {
		// Aquí implementé el uso de shared preferences, pero aparentemente no
		// funciona :(
		ble = new DownManager();
		// ble.DescargaBD();
		System.out.println("Existe la base de datos");

		checkForBD();
	}

	private boolean checkForBD() {
		// TODO Auto-generated method stub
		// bd = new File("/sdcard/DirLaguna.db");
		// Se hizo modificación para no tener que usar hard code para la
		// localización de la sdCard.
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
	// saco de la base de datos, imprimira el tamaÃ±o del arreglo, la latitud y
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
		switch (item.getItemId()) {
		case R.id.btn_abecedario:
			Intent intent = new Intent(this, MostrarCategoriasActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.btn_favoritos:
			// mostrar la secciï¿½n de favoritos
			return true;
		case R.id.btn_buscar:
			// Cuando se oprima el botón de buscar, se realizará la búsqueda,
			// sin embargo, todavía no funciona.
			AdvertiserDAO add = new AdvertiserDAO(bd.getAbsolutePath());
			ArrayList<Advertiser> didi = add.findAll();
			System.out.println("TamaÃ±o de arreglo: " + didi.size());
			System.out.println("Latitude: " + latitude);
			System.out.println("Longitude: " + longitude);
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