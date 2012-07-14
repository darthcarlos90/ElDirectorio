package directorio.actividades;

import java.sql.SQLException;
import java.util.ArrayList;
import directorio.DAO.AdvertiserDAO;
import directorio.DAO.sucursalDAO;
import directorio.objetos.Advertiser;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ShowAdvertiserActivity extends Activity {
	Advertiser toShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertiser);
		searchAdvertiser();
		try {
			setupViews();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void searchAdvertiser() {
	
		String advertiser =getIntent().getExtras().getString("advertiser");
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

		// ListView sucs = (ListView) findViewById(R.id.sucursalesEmpresaList);
		LinearLayout rl = (LinearLayout) findViewById(R.id.linear_layout_si);
		TextView suc = (TextView) findViewById(R.id.sucursales_empresa);
		sucursalDAO sDAO = new sucursalDAO();
		boolean tieneSucursales = sDAO.hasSucursales(toShow.getId());
		if (tieneSucursales == false) {
			suc.setVisibility(View.GONE);
			// sucs.setVisibility(View.GONE);

		} else {
			suc.setVisibility(View.VISIBLE);
			// sucs.setVisibility(View.VISIBLE);
			ArrayList<String> sucursales = sDAO.getStringSucursales(toShow
					.getId());
			ArrayList<TextView> tvs = new ArrayList<TextView>();
			for (int i = 0; i < sucursales.size(); i++) {
				TextView tv = new TextView(this);
				tv.setText(sucursales.get(i));
				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				tvs.add(tv);
			}
			for (int i = 0; i < tvs.size(); i++) {
				rl.addView(tvs.get(i), i);
			}

		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout_contacto);
		for (int i = 0; i < toShow.getTelefono().size(); i++) {
			TextView tv = new TextView(this);
			tv.setText(toShow.getTelefono().get(i));
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			ll.addView(tv);
		}

		for (int i = 0; i < toShow.getEmail().size(); i++) {
			TextView tv = new TextView(this);
			tv.setText(toShow.getEmail().get(i));
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			ll.addView(tv);
		}

		String webPage = toShow.getSitioWeb();
		if(webPage.isEmpty()==false){
			TextView tvweb = new TextView(this);
			tvweb.setText(webPage);
			tvweb.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			ll.addView(tvweb);
		}

		String facebook = toShow.getFacebook();
		if (facebook.isEmpty() == false) {
			TextView tvFacebook = new TextView(this);
			tvFacebook.setText(facebook);
			tvFacebook.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			ll.addView(tvFacebook);
		}

		String twitter = toShow.getTwitter();
		if (twitter.isEmpty() == false) {
			TextView tvTwitter = new TextView(this);
			tvTwitter.setText(twitter);
			tvTwitter.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			ll.addView(tvTwitter);
		}

		Button favs = (Button) findViewById(R.id.agregar_favs);
		final AdvertiserManagerApplication ama = (AdvertiserManagerApplication) getApplication();
		favs.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				ama.addToFavoritos(toShow);

				AlertDialog alertDialog = new AlertDialog.Builder(
						ShowAdvertiserActivity.this).create();
				alertDialog.setTitle("Agregado a Favoritos");
				alertDialog.setMessage("Se agregó " + toShow.getNombre()
						+ " a favoritos");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								ShowAdvertiserActivity.this.finish();
							}
						});
				alertDialog.show();
				System.out.println("Agregado a favoritos");
			}
		});

	}
}