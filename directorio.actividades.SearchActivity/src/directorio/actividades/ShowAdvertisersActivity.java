package directorio.actividades;

import java.util.ArrayList;

import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowAdvertisersActivity extends ListActivity {

	private static final String PREFS_NAME = "tipo de busqueda";
	private AdvertiserManagerApplication ama;
	private SharedPreferences sp;
	private AdvertiserDAO aDao;
	private ArrayList<Advertiser> advs;
	private ArrayList<String> elementos;
	private ArrayAdapter<String> advertisers;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_negocios);
		setUpViews();
	}

	private void showFavs() {
		advs = ama.getFavoritos();
		elementos = new ArrayList<String>();
		for (int i = 0; i < advs.size(); i++) {
			elementos.add(advs.get(i).getNombre());
		}

		advertisers = new ArrayAdapter<String>(this, R.layout.list_item,
				elementos);
		setListAdapter(advertisers);

	}

	private void showCatElements() {
		SharedPreferences sp2 = getSharedPreferences("categoria", 0);
		String categoria = sp2.getString("categoria", null);
		advs = aDao.getByCategory(categoria);
		elementos = new ArrayList<String>();
		for (int i = 0; i < advs.size(); i++) {
			elementos.add(advs.get(i).getNombre());
		}

		advertisers = new ArrayAdapter<String>(this, R.layout.list_item,
				elementos);
		setListAdapter(advertisers);

	}

	private void setUpViews() {
		ama = (AdvertiserManagerApplication) getApplication();
		sp = getSharedPreferences(PREFS_NAME, 0);
		aDao = new AdvertiserDAO();
		String showing = sp.getString(PREFS_NAME, null);
		if (showing.equals("favoritos")) {
			showFavs();
		} else if (showing.equals("advertiser")) {
			showCatElements();
		}

	}
}