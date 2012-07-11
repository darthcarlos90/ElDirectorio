package directorio.actividades;

import java.io.InputStream;
import java.sql.SQLException;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
