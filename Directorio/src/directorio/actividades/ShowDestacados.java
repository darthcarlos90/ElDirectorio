package directorio.actividades;

import java.util.ArrayList;

import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowDestacados extends MenuActivity{

	 private ArrayList<Advertiser> adds;
	private ListView Lista;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.out.println("Holo");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listadvertiserss);
        addButtons();
        AdvertiserDAO ble = new AdvertiserDAO();
        TextView tv = (TextView) findViewById(R.id.bannerSeh);
        adds =  ble.dameDestacados();
        tv.setText("Destacados");
        ble.getdb().close();
        
        Lista = (ListView)findViewById(R.id.lisst);
        Lista.setAdapter(new MyCustomAdapter(this, adds));
        Lista.setOnItemClickListener(
                new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						final String nombrenegocio = adds.get(arg2).getNombre();
						Thread holo = new Thread(){
							public void run(){
								
								try {
									sleep(100);
									Class texto = Class.forName("directorio.actividades.ShowAdvertiserActivity");
									Intent correo = new Intent(ShowDestacados.this, texto);
									correo.putExtra("advertiser", nombrenegocio);
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
