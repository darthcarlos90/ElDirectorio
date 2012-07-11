package directorio.actividades;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class PruebaMapa extends MapActivity {

	@SuppressLint("ParserError")
	MapView mapa;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mapa);
		
		mapa = (MapView)findViewById(R.id.mapview);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
