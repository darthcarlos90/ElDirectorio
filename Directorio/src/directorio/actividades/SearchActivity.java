package directorio.actividades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import directorio.BaseDeDatos.SearchManager;
import directorio.DAO.otrosDao;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends MenuActivity /*
												 * implements
												 * View.OnClickListener
												 */{
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cargando.setVisibility(ProgressBar.INVISIBLE);
		cargando.setIndeterminate(false);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.out.println("Holo");
	}

	private TextView textoBarra;
	private Spinner spinner;
	private SeekBar barra;
	private Button info;
	private LocationManager lm;
	private double longitude;
	private double latitude;
	private EditText Busqueda;
	private File bd;
	private double kil;
	private otrosDao others; // para recoger las ciudades de la base de datos
	private Button abc;
	private Button busqueda;
	private Button favoritos;
	private Intent intent;
	private ProgressBar cargando;
	private TextView buscando;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getLocation();

		if (longitude == 0.0 && latitude == 0.0) {
			Toast.makeText(
					this,
					"No pudimos obtener tu localización, intentalo mas tarde...",
					Toast.LENGTH_SHORT).show();
		}

		System.out.println("Longitud: " + longitude);
		System.out.println("Latitude: " + latitude);

		bd = new File(Environment.getExternalStorageDirectory().getPath() + "/DirLaguna.db");
		try {
			setupViews();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * M�todo encargado de revisarr si existe la base de datos en la memoria,
	 * si no existe, se descarga.
	 */
	/*
	 * private void downloadDataBase() { new DownManager(); }
	 */

	// El resultado lo imprimira en el logcat, imprimira todos los objetos que
	// saco de la base de datos, imprimira el tamaño del arreglo, la latitud y
	// longitud
	private void getLocation() {
		// TODO Auto-generated method stub

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location loc1 = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (loc1 != null) {
			System.out.println("Lo obtuve por WIFI");
			latitude = loc1.getLatitude();
			longitude = loc1.getLongitude();
		} else {
			System.out.println("Lo obtuve por GPS");
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			sendBroadcast(intent);
			lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			try {
				latitude = loc.getLatitude();
				longitude = loc.getLongitude();
			} catch (NullPointerException npe) {
				latitude = 0.0;
				longitude = 0.0;
			}
			Intent intento = new Intent("android.location.GPS_ENABLED_CHANGE");
			intento.putExtra("enabled", false);
			sendBroadcast(intento);
		}
	}
	
	@Override
	public void addButtons() {
		super.addButtons();
		try {
			others.regresaOtraDb().close();
		} catch (Exception e) {

		}
	}

	private void setupViews() throws IOException {
		findViewById(R.id.buttons);
		this.addButtons();

		buscando = (TextView)findViewById(R.id.textoBusqueda);
		buscando.setVisibility(TextView.INVISIBLE);
		cargando = (ProgressBar)findViewById(R.id.progresoBusqueda);
		cargando.setVisibility(ProgressBar.INVISIBLE);
		
		boolean haycarga = cargando.isShown();
		
		if(!haycarga){
			cargando.setVisibility(ProgressBar.INVISIBLE);
			cargando.setIndeterminate(false);
		}
		
		// MenuUtilities mu = new MenuUtilities("Search");
		// mu.addButtons(rl, SearchActivity.this,this);
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
		try {
			ArrayList<String> datos = others.getCiudades();
			adapter.add("Todas las ciudades");
			for (int i = 0; i < datos.size(); i++) {
				adapter.add(datos.get(i));
			}
			others.regresaOtraDb().close();
		} catch (Exception e) {
			Toast.makeText(this, "Error de bd, reintentando...",
					Toast.LENGTH_LONG).show();
			bd.getCanonicalFile().delete();
			restartFirstActivity();
		}

		spinner.setAdapter(adapter);
		Busqueda = (EditText) findViewById(R.id.busqueda);
		textoBarra = (TextView) findViewById(R.id.mostrar_metros);
		abc = (Button) findViewById(R.id.ABC);
		busqueda = (Button) findViewById(R.id.search);
		busqueda.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// SegundoAlgoritmo de Busqueda
				
				
				if (Busqueda.getText().toString().equals("")) {
					Toast.makeText(
							SearchActivity.this,
							"No insertaste valores para la búsqueda, intenta de nuevo.",
							Toast.LENGTH_SHORT).show();
				} else {

					Thread timer = new Thread() {
						public void run() {
							
							if(spinner.getSelectedItem().toString().equals("Todas las ciudades") && kil == 0.0){
								others = new otrosDao();
								SearchManager.dameTodos(others.regresaOtraDb(), Busqueda.getText().toString());
								others.regresaOtraDb().close();
							}else{
								others = new otrosDao();
								SearchManager.negociosenRango(latitude, longitude, kil, spinner.getSelectedItem().toString(), Busqueda.getText().toString(), others.regresaOtraDb());
								others.regresaOtraDb().close();
							}
							
							intent = new Intent(SearchActivity.this,
									adverlistitem.class);
							intent.putExtra("estado", 1);
							intent.putExtra("latitude", latitude);
							intent.putExtra("longitude", longitude);
							intent.putExtra("kil", kil);
							intent.putExtra("ciudad", spinner.getSelectedItem()
									.toString());
							intent.putExtra("busqueda", Busqueda.getText()
									.toString());
							SearchActivity.this.startActivity(intent);
						}
					};
					cargando.setVisibility(ProgressBar.VISIBLE);
					cargando.setIndeterminate(true);
					timer.start();

				}
				
			
			}
		});
		favoritos = (Button) findViewById(R.id.favoritos);
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
					kil = 0.0;
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

	private void restartFirstActivity() {
		Intent i = getApplicationContext().getPackageManager()
				.getLaunchIntentForPackage(
						getApplicationContext().getPackageName());

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

}
