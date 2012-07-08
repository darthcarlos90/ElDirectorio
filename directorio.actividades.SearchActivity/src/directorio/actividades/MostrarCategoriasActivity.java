package directorio.actividades;

import java.util.ArrayList;
import java.util.Collections;

import directorio.DAO.CategoriaDAO;
import directorio.objetos.Categoria;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarCategoriasActivity extends Activity {

	private ListView lv;
	private Categoria categoria;
	private CategoriaDAO catDao;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
		setupViews();
	}

	public void setupViews() {
		lv = (ListView) findViewById(R.id.mostrar_categorias);
		catDao = new CategoriaDAO();
		ArrayList<String> lista = catDao.getCategorias();
		Collections.sort(lista, String.CASE_INSENSITIVE_ORDER);
		String[] datosTentativos2 = new String[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			datosTentativos2[i] = lista.get(i);
		}
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				datosTentativos2));
	}
}
