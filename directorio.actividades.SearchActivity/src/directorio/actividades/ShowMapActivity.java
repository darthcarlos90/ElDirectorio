package directorio.actividades;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import directorio.objetos.ItemizedOverlayDirectorio;

public class ShowMapActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setupViews();
	}

	private void setupViews() {
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.maps_icon);
		ItemizedOverlayDirectorio itemizedoverlay = new ItemizedOverlayDirectorio(
				drawable, this);
		/*
		 * GeoPoint point = new GeoPoint((int) toShow.getPosx(), (int)
		 * toShow.getPosy()); OverlayItem overlayitem = new OverlayItem(point,
		 * toShow.getNombre(), toShow.getDireccion());
		 * itemizedoverlay.addOverlay(overlayitem);
		 * mapOverlays.add(itemizedoverlay); final MapController mapController =
		 * mapView.getController(); mapController.animateTo(point,
		 * 
		 * new Runnable() { public void run() { mapController.setZoom(12); } });
		 */

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
