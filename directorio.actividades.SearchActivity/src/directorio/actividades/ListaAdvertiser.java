package directorio.actividades;

import java.util.ArrayList;

import directorio.objetos.Advertiser;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

public class ListaAdvertiser extends Activity{
	
	private ProgressDialog m_ProgressDialog = null; 
    private ArrayList<Advertiser> m_advers = null;
    private MyCustomAdapter m_adapter;
    private Runnable viewOrders;
    private ListView holo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listadvertiserss);
		holo = (ListView)findViewById(R.id.lisst);
		m_advers = new ArrayList<Advertiser>();
		Advertiser ble = new Advertiser();
		ble.setNombre("Prueba 1");
		ble.setDireccion("lugar desconocido");
		m_advers.add(ble);
		Advertiser bla = new Advertiser();
		bla.setNombre("holo");
		bla.setDireccion("wiiii");
		m_advers.add(bla);
		m_adapter = new MyCustomAdapter(this, m_advers);
		holo.setAdapter(m_adapter);
		
	}
	

	

}
