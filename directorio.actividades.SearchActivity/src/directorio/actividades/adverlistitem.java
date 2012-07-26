package directorio.actividades;

import java.util.ArrayList;
import directorio.BaseDeDatos.SearchManager;
import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ParserError")
public class adverlistitem extends Activity {
	
	 private ArrayList<Advertiser> adds = null;
	 private ListView Lista;
	 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadvertiserss);
        
        int estado = getIntent().getExtras().getInt("estado");
        String buss =  getIntent().getExtras().getString("busqueda");
        
        AdvertiserDAO ble = new AdvertiserDAO();
        AdvertiserManagerApplication ama =  (AdvertiserManagerApplication)getApplication();
        
        if(estado == 1){
        double latitude = getIntent().getExtras().getDouble("latitude");
        double longitude = getIntent().getExtras().getDouble("longitude");
        double kil = getIntent().getExtras().getDouble("kil");
        String ciudad = getIntent().getExtras().getString("ciudad");
        String filtro = getIntent().getExtras().getString("busqueda");
        
        if(filtro.length() == 0){
        	Toast.makeText(this, "Por favor escribe una busqueda", Toast.LENGTH_LONG).show();
        	filtro = "nada";
        }
        
        if(ciudad.equals("Todas las ciudades") && kil == 0){
        	adds = SearchManager.dameTodos(ble.getdb(), filtro);
        }else{
        adds = SearchManager.negociosenRango(latitude, longitude, kil, ciudad, filtro, ble.getdb());
        }
        ble.getdb().close();
        }else if(estado == 2){
        	String cat = getIntent().getExtras().getString("categoria");
        	adds = ble.getByCategory(cat);
        	ble.getdb().close();
        }
        else if(estado == 3){
        	adds = ama.getFavoritos();
        }
        
        
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
									Intent correo = new Intent(adverlistitem.this, texto);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		onBackPressed();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
}