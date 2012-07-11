package directorio.actividades;

import java.util.ArrayList;
import java.util.Collections;
import directorio.DAO.CategoriaDAO;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarCategoriasActivity extends ListActivity {

	private CategoriaDAO catDao;
	private ArrayList<String>lista;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
		updateCategories();
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String category = lista.get(position);
		SharedPreferences sharedPrefs = getSharedPreferences("tipo de busqueda",0);
		Editor editor = sharedPrefs.edit();
		editor.putString("tipo de busqueda", "advertiser");
		editor.commit();
		SharedPreferences sp = getSharedPreferences("categoria",0);
		editor = sp.edit();
		editor.putString("categoria", category);
		editor.commit();
		Intent intent = new Intent(this, ShowAdvertisersActivity.class);
		this.startActivity(intent);
	}

	public void updateCategories() {
		catDao = new CategoriaDAO();
		lista = catDao.getCategorias();
		Collections.sort(lista, String.CASE_INSENSITIVE_ORDER);
		ArrayAdapter<String> favoritos = new ArrayAdapter<String>(this,
				R.layout.list_item, lista);
		setListAdapter(favoritos);
	}

	
}
