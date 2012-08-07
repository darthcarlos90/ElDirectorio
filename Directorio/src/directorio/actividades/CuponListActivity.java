package directorio.actividades;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import directorio.DAO.cuponDAO;
import directorio.objetos.Cupon;
import directorio.objetos.CuponCustomAdapter;

public class CuponListActivity extends MenuActivity {
	private ArrayList<Cupon> cups = null;
	private ListView Lista;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_cupones);
		addButtons();

		String categoria = getIntent().getExtras().getString("categoria");
		System.out.println(categoria);
		cuponDAO ble = new cuponDAO();
		// TodoManagerApplication ama =
		// (TodoManagerApplication)getApplication();
		cups = ble.cuponesPorCategorias(categoria);
		for(int i =0; i < cups.size(); i++){
			System.out.println(cups.get(i).getAdvertiserId());
			System.out.println(cups.get(i).getConditions());
			System.out.println(cups.get(i).getCuponId());
			System.out.println(cups.get(i).getDescripcion());
		//	System.out.println(cups.get(i).getEnd());
			System.out.println(cups.get(i).getName());
		}

		Lista = (ListView) findViewById(R.id.listaCupones);
		Lista.setAdapter(new CuponCustomAdapter(this, cups));
		Lista.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final int idCupon = cups.get(arg2).getCuponId();
				Thread holo = new Thread() {
					public void run() {

						try {
							sleep(100);
							Class texto = Class
									.forName("directorio.actividades.ShowCuponActivity");
							Intent correo = new Intent(CuponListActivity.this,
									texto);
							correo.putExtra("idCupon",idCupon);
							startActivity(correo);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				holo.start();

			}
		});
	}
}
