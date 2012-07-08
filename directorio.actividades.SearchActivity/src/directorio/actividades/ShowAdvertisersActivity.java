package directorio.actividades;

import java.util.ArrayList;

import directorio.objetos.Advertiser;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowAdvertisersActivity extends Activity {
	private ListView lv;
	private static final String PREFS_NAME = "tipo de busqueda";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_negocios);
		setUpViews();
	}

	private void setUpViews() {
		lv = (ListView) findViewById(R.id.show_advertisers);
		AdvertiserManagerApplication ama = (AdvertiserManagerApplication) getApplication();
		// inserción de prueba
		/*
		 * Advertiser a = new Advertiser(); a.setCiudad("torreon");
		 * a.setNombre("negocio pruebis"); ama.addToFavoritos(a);
		 */
		SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
		String showing = sp.getString(PREFS_NAME, null);
		if (showing.equals("favoritos")) {
			ArrayList<String> favoritos = ama.getFavoritos();
			String[] favs = new String[favoritos.size()];
			for (int i = 0; i < favoritos.size(); i++) {
				favs[i] = favoritos.get(i);
			}
			lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					favs));
		}

	}
}
