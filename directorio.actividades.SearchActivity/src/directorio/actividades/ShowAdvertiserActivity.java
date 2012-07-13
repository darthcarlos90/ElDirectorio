package directorio.actividades;

import java.sql.SQLException;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;
import directorio.objetos.ItemizedOverlayDirectorio;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowAdvertiserActivity extends MapActivity {
	Advertiser toShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertiser);
		searchAdvertiser();
		try {
			setupViews();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchAdvertiser() {
		SharedPreferences sp = getSharedPreferences("advertiser", 0);
		String advertiser = sp.getString("advertiser", null);
		AdvertiserDAO ad = new AdvertiserDAO();
		toShow = ad.find(advertiser);
		ad.getdb().close();
	}

	private void setupViews() throws SQLException {
		ImageView iv = (ImageView) findViewById(R.id.negocio_logo);
		try {
			iv.setImageBitmap(BitmapFactory.decodeByteArray(toShow.getImgSrc(),
					0, toShow.getImgSrc().length));
		} catch (NullPointerException npe) {
			iv.setBackgroundColor(Color.WHITE);
		}
		TextView nombreEmpresa = (TextView) findViewById(R.id.nombre_empresa);
		nombreEmpresa.setText(toShow.getNombre());
		TextView descripcionEmpresa = (TextView) findViewById(R.id.descripcion_empresa);
		descripcionEmpresa.setText(toShow.getDescripcion());
		TextView direccionEmpresa = (TextView) findViewById(R.id.direccion_empresa);
		direccionEmpresa.setText(toShow.getDireccion());

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.maps_icon);
		ItemizedOverlayDirectorio itemizedoverlay = new ItemizedOverlayDirectorio(
				drawable, this);
		GeoPoint point = new GeoPoint((int) toShow.getPosx(),
				(int) toShow.getPosy());
		OverlayItem overlayitem = new OverlayItem(point, toShow.getNombre(),
				toShow.getDireccion());
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		final MapController mapController = mapView.getController();
		mapController.animateTo(point,

		new Runnable() {
			public void run() {
				mapController.setZoom(12);
			}
		});

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
