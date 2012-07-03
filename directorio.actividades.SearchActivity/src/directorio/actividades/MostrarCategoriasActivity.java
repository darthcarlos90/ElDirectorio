package directorio.actividades;

import java.util.ArrayList;

import directorio.objetos.Categoria;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MostrarCategoriasActivity extends Activity {

	ListView lv;
	ArrayList <Categoria> datosTentativos = new ArrayList<Categoria>();
	String [] datosTentativos2;
	Categoria cat1;
	Categoria cat2;
	Categoria cat3;
	Categoria cat4;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
		setupViews();
	}

	public void setupViews() {
		lv = (ListView) findViewById(R.id.mostrar_categorias);
		cat1 = new Categoria(1,"categoria 1", 'A');
		cat2 = new Categoria(2, "categoria 2", 'B');
		cat3 = new Categoria (3, "categoria 3", 'C');
		cat4 = new Categoria (4,"Pinche error te la mamaste cabron", 'D');
		datosTentativos.add(cat1);
		datosTentativos.add(cat2);
		datosTentativos.add(cat3);
		datosTentativos.add(cat4);
		datosTentativos2 = new String [datosTentativos.size()];
		for(int i = 0; i < datosTentativos.size(); i++){
			datosTentativos2[i] = datosTentativos.get(i).getNombre();
		}
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item, datosTentativos2));
	}
}
