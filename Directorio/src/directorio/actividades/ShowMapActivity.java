package directorio.actividades;

import java.util.List;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import directorio.objetos.ItemizedOverlayDirectorio;

public class ShowMapActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.show_map);
		setupViews();
	}

	private void setupViews() {
		Button button = (Button) findViewById(R.id.buttonBack);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		List<Overlay> mapOverlays = mapView.getOverlays();
		//CAMBIOS EN EL ICONO QUE SE MUESTRA, AHORA ES AZUL :D
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.local_icon);
		ItemizedOverlayDirectorio itemizedoverlay = new ItemizedOverlayDirectorio(
				drawable, this);
		SharedPreferences msp = getSharedPreferences("point x", 0);
		SharedPreferences msp2 = getSharedPreferences("point y", 0);
		float latitude = msp.getFloat("point x", (float) 0.0);
		float longitude = msp2.getFloat("point y", (float) 0.0);
		GeoPoint point = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));
		SharedPreferences sp = getSharedPreferences("nEmpresa", 0);
		String nombre = sp.getString("nombre", null);
		SharedPreferences sp2 = getSharedPreferences("dEmpresa", 0);
		String direccion = sp2.getString("dir", null);
		OverlayItem overlayitem = new OverlayItem(point, nombre, direccion);
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		final MapController mapController = mapView.getController();
		mapController.animateTo(point,

		new Runnable() {
			public void run() {
				mapController.setZoom(30);
			}
		});

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
