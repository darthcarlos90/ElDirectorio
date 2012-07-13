package directorio.actividades;

import java.util.ArrayList;
import java.util.List;

import directorio.BaseDeDatos.SearchManager;
import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;

public class adverlistitem extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double latitude = getIntent().getExtras().getDouble("latitude");
        double longitude = getIntent().getExtras().getDouble("longitude");
        double kil = getIntent().getExtras().getDouble("kil");
        String ciudad = getIntent().getExtras().getString("ciudad");
        String filtro = getIntent().getExtras().getString("busqueda");
        
        AdvertiserDAO ble = new AdvertiserDAO();

        ArrayList<Advertiser> adds = SearchManager.negociosenRango(latitude, longitude, kil, ciudad, filtro, ble.getdb());
        ble.getdb().close();

        setListAdapter(new MyCustomAdapter(this, adds));
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
}