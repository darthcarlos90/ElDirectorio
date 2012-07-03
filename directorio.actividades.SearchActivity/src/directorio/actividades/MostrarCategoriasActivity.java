package directorio.actividades;

import java.util.ArrayList;

import directorio.objetos.Categoria;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarCategoriasActivity extends Activity {

	ListView lv;
	ArrayList <Categoria> datosTentativos;
	String [] datosTentativos2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
		setupViews();
	}

	public void setupViews() {
		lv = (ListView) findViewById(R.id.mostrar_categorias);
		
		
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item, datosTentativos2));
	}
}
