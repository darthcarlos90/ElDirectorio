package directorio.actividades;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

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

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Inicializar los elementos en el View
		setupViews();
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
			// mostrar la sección de favoritos
			return true;
		case R.id.btn_buscar:
			// No hacer nada, ya estamos aquí.
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupViews() {
		spinner = (Spinner) findViewById(R.id.spinner_localidades);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.ciudades_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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