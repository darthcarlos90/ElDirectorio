package directorio.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarCategoriasActivity extends Activity {

	ListView lv;
	String[] datosTentativos = { "datos1", "datos2", "holi :3" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
		setupViews();
	}

	public void setupViews() {
		lv = (ListView) findViewById(R.id.mostrar_categorias);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item, datosTentativos));
	}
}
