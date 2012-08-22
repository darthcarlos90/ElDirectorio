package directorio.actividades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import directorio.DAO.AdvertiserDAO;
import directorio.DAO.sucursalDAO;
import directorio.galeria.GalleryView;
import directorio.objetos.Advertiser;
import directorio.objetos.Sucursal;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ShowAdvertiserActivity extends MenuActivity {
	private Advertiser toShow;
	private AdvertiserDAO ad;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertiser);
		ad = new AdvertiserDAO();
		searchAdvertiser();
		try {
			setupViews();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	private void searchAdvertiser() {

		String advertiser = getIntent().getExtras().getString("advertiser");
		toShow = ad.find(advertiser);

	}

	public void sendInfo(double posx, double posy, String nombre,
			String Direccion) {
		SharedPreferences mySharedPreference = getSharedPreferences("point x",
				0);
		Editor editor = mySharedPreference.edit();
		editor.putFloat("point x", (float) posx);
		editor.commit();
		SharedPreferences mySharedPreference2 = getSharedPreferences("point y",
				0);
		Editor editor2 = mySharedPreference2.edit();
		editor2.putFloat("point y", (float) posy);
		editor2.commit();
		SharedPreferences sp = getSharedPreferences("nEmpresa", 0);
		Editor e = sp.edit();
		e.putString("nombre", nombre);
		e.commit();
		SharedPreferences sp2 = getSharedPreferences("dEmpresa", 0);
		Editor ed = sp2.edit();
		ed.putString("dir", Direccion);
		ed.commit();

		intent = new Intent(ShowAdvertiserActivity.this, ShowMapActivity.class);
		ShowAdvertiserActivity.this.startActivity(intent);
	}

	private final String extractNumber(String nameNumber) {
		StringTokenizer st = new StringTokenizer(nameNumber, ": ");
		st.nextToken();
		return st.nextToken();
	}

	private final void makeCall(String nameNumber) {

		String number = extractNumber(nameNumber);
		System.out.println(number);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + number));
		startActivity(callIntent);

	}

	private final void openSite(String url) {
		Uri uri;
		try {
			uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	private void setupViews() throws SQLException {

		// CAMBIOS EN TODO EL METODO PARA PONER LOS ICONOS DEL MAPA Y ASI
		int position = 0;
		addButtons();// (RelativeLayout)findViewById(R.id.buttons));
		TextView nombretv = (TextView) findViewById(R.id.banner);
		String showing = toShow.getNombre();
		String nombre = "";
		if (showing.length() > 40) {

			for (int i = 0; i < 41; i++) {
				nombre += showing.charAt(i);
			}
			for (int i = 0; i < 3; i++) {
				nombre += ".";
			}
			nombretv.setText(nombre);
		} else {
			nombretv.setText(showing);
		}

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
		direccionEmpresa.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				sendInfo(toShow.getPosx(), toShow.getPosy(),
						toShow.getNombre(), toShow.getDireccion());

			}
		});

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
			final ArrayList<String> sucursales = sDAO
					.getStringSucursales(toShow.getId());
			// ArrayList<TextView> tvs = new ArrayList<TextView>();
			final ArrayList<Sucursal> arraySucursales = sDAO
					.getSucursales(toShow.getId());
			for (int i = 0; i < sucursales.size(); i++) {
				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				// ll.setLayoutParams(params);
				ImageView iv1 = new ImageView(this);
				iv1.setImageResource(R.drawable.local_icon);
				ll.addView(iv1);
				TextView tv = new TextView(this);
				tv.setTextSize(18);
				tv.setText(sucursales.get(i));
				tv.setTextColor(Color.BLACK);
				final int pos = i;
				tv.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						sendInfo(arraySucursales.get(pos).getPointX(),
								arraySucursales.get(pos).getPointY(), toShow
										.getNombre(), arraySucursales.get(pos)
										.getAddress());

					}
				});
				tv.setLayoutParams(new LayoutParams(400,
						LayoutParams.WRAP_CONTENT));
				// tvs.add(tv);
				ll.addView(tv);
				ImageView iv2 = new ImageView(this);
				iv2.setImageResource(R.drawable.next_icon);
				ll.addView(iv2);
				rl.addView(ll, i);
				// rl.addView(tv, i);
			}
			/*
			 * for (int i = 0; i < tvs.size(); i++) { rl.addView(tvs.get(i), i);
			 * }
			 */

		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout_contacto);
		for (int i = 0; i < toShow.getTelefono().size(); i++) {
			LinearLayout tll = new LinearLayout(this);
			tll.setOrientation(LinearLayout.HORIZONTAL);
			// ll.setLayoutParams(params);
			ImageView iv1 = new ImageView(this);
			iv1.setImageResource(R.drawable.phone_icon);
			tll.addView(iv1);
			TextView tv = new TextView(this);
			tv.setTextSize(18);
			tv.setText(toShow.getTelefono().get(i));
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			tv.setTextColor(Color.BLACK);

			final int index = i;
			tv.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					makeCall(toShow.getTelefono().get(index));

				}
			});
			tv.setLayoutParams(new LayoutParams(400, LayoutParams.WRAP_CONTENT));
			tll.addView(tv);
			ImageView iv2 = new ImageView(this);
			iv2.setImageResource(R.drawable.next_icon);
			tll.addView(iv2);
			ll.addView(tll, i);
			// ll.addView(tv);
		}

		for (int i = 0; i < toShow.getEmail().size(); i++) {
			LinearLayout tll = new LinearLayout(this);
			tll.setOrientation(LinearLayout.HORIZONTAL);
			ImageView iv1 = new ImageView(this);
			iv1.setImageResource(R.drawable.mail_icon);
			tll.addView(iv1);
			TextView tv = new TextView(this);
			tv.setTextSize(18);
			tv.setText(toShow.getEmail().get(i));
			tv.setLayoutParams(new LayoutParams(400, LayoutParams.WRAP_CONTENT));
			tv.setTextColor(Color.BLACK);
			final int index = i;
			tv.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[] {
							toShow.getEmail().get(index), "" });
					email.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
					email.putExtra(Intent.EXTRA_TEXT, "message");
					email.setType("message/rfc822");
					startActivity(Intent.createChooser(email,
							"Choose an Email client :"));
				}
			});
			tll.addView(tv);
			ImageView iv2 = new ImageView(this);
			iv2.setImageResource(R.drawable.next_icon);
			tll.addView(iv2);
			ll.addView(tll, i);
			// ll.addView(tv);
			position = i + 1;
		}

		final String webPage = toShow.getSitioWeb();
		if (webPage.equals("") == false) {
			LinearLayout tll = new LinearLayout(this);
			tll.setOrientation(LinearLayout.HORIZONTAL);
			ImageView iv1 = new ImageView(this);
			iv1.setImageResource(R.drawable.browser_icon);
			tll.addView(iv1);
			TextView tvweb = new TextView(this);
			tvweb.setTextSize(18);
			tvweb.setText(webPage);
			tvweb.setLayoutParams(new LayoutParams(400,
					LayoutParams.WRAP_CONTENT));
			tvweb.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					openSite(webPage);
				}
			});
			// ll.addView(tvweb);
			tll.addView(tvweb);
			ImageView iv2 = new ImageView(this);
			iv2.setImageResource(R.drawable.next_icon);
			tll.addView(iv2);
			ll.addView(tll, position);
			position++;
		}

		final String facebook = toShow.getFacebook();

		if (facebook.equals("") == false) {
			LinearLayout tll = new LinearLayout(this);
			tll.setOrientation(LinearLayout.HORIZONTAL);
			ImageView iv1 = new ImageView(this);
			iv1.setImageResource(R.drawable.facebook_icon);
			tll.addView(iv1);
			TextView tvFacebook = new TextView(this);
			tvFacebook.setTextSize(18);
			tvFacebook.setText(facebook);
			tvFacebook.setLayoutParams(new LayoutParams(400,
					LayoutParams.WRAP_CONTENT));
			tvFacebook.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					openSite(facebook);

				}
			});
			tll.addView(tvFacebook);
			ImageView iv2 = new ImageView(this);
			iv2.setImageResource(R.drawable.next_icon);
			tll.addView(iv2);
			ll.addView(tll, position);
			// ll.addView(tvFacebook);
			position++;
		}

		final String twitter = toShow.getTwitter();
		if (twitter.equals("") == false) {
			LinearLayout tll = new LinearLayout(this);
			tll.setOrientation(LinearLayout.HORIZONTAL);
			ImageView iv1 = new ImageView(this);
			iv1.setImageResource(R.drawable.twitter_icon);
			tll.addView(iv1);
			TextView tvTwitter = new TextView(this);
			tvTwitter.setTextSize(18);
			tvTwitter.setText(twitter);
			tvTwitter.setLayoutParams(new LayoutParams(400,
					LayoutParams.WRAP_CONTENT));
			tvTwitter.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					openSite(twitter);
				}
			});
			tll.addView(tvTwitter);
			ImageView iv2 = new ImageView(this);
			iv2.setImageResource(R.drawable.next_icon);
			tll.addView(iv2);
			ll.addView(tll, position);
			// ll.addView(tvTwitter);

		}

		boolean tieneGalerias = ad.hasGallery(toShow.getId());
		TextView gal = (TextView) findViewById(R.id.galerias);
		TextView galName = (TextView) findViewById(R.id.nombre_galeria);
		if (tieneGalerias == true) {
			gal.setVisibility(View.VISIBLE);
			galName.setVisibility(View.VISIBLE);
			galName.setTextSize(23);
			galName.setText("Ver Galer�a: "
					+ ad.getGalleryName(toShow.getId()));
			galName.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					SharedPreferences mySharedPreference = getSharedPreferences(
							"galeriaId", 0);
					Editor editor = mySharedPreference.edit();
					editor.putString("galeriaId",
							ad.getGalleryId(toShow.getId()));
					editor.commit();
					intent = new Intent(ShowAdvertiserActivity.this,
							GalleryView.class);
					ShowAdvertiserActivity.this.startActivity(intent);

				}
			});
		} else {
			gal.setVisibility(View.GONE);
			galName.setVisibility(View.GONE);

		}

		Button favs = (Button) findViewById(R.id.agregar_favs);
		final TodoManagerApplication ama = (TodoManagerApplication) getApplication();
		boolean isInFavs = ama.isInFavoritos(toShow.getNombre());
		if (isInFavs == true) {
			// favs.setText("Quitar de Favoritos");
			favs.setBackgroundResource(R.drawable.favorito);
			favs.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					ama.removeFromFavoritos(toShow.getNombre());
					AlertDialog alertDialog = new AlertDialog.Builder(
							ShowAdvertiserActivity.this).create();
					alertDialog.setTitle("Se elimino de favoritos");
					alertDialog.setMessage("Se eliminó " + toShow.getNombre()
							+ " de favoritos");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ShowAdvertiserActivity.this.finish();
								}
							});
					alertDialog.show();
					System.out.println("Eliminado de favoritos");

				}
			});
		} else {
			favs.setBackgroundResource(R.drawable.no_favorito);
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

		Button addContacts = (Button) findViewById(R.id.agregar_contectos);
		addContacts.setBackgroundResource(R.drawable.contacto);
		addContacts.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
				int rawContactInsertIndex = ops.size();

				ops.add(ContentProviderOperation
						.newInsert(RawContacts.CONTENT_URI)
						.withValue(RawContacts.ACCOUNT_TYPE, null)
						.withValue(RawContacts.ACCOUNT_NAME, null).build());
				ops.add(ContentProviderOperation
						.newInsert(Data.CONTENT_URI)
						.withValueBackReference(Data.RAW_CONTACT_ID,
								rawContactInsertIndex)
						.withValue(Data.MIMETYPE,
								StructuredName.CONTENT_ITEM_TYPE)
						.withValue(StructuredName.DISPLAY_NAME,
								toShow.getNombre()) // Name of the person
						.build());
				ops.add(ContentProviderOperation
						.newInsert(Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID,
								rawContactInsertIndex)
						.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
						.withValue(Phone.NUMBER,
								extractNumber(toShow.getTelefono().get(0))) // Number
																			// of
																			// the
						// person
						.withValue(Phone.TYPE, Phone.TYPE_COMPANY_MAIN).build()); // Type
				// of
				// mobile
				// number
				AlertDialog alertDialog = new AlertDialog.Builder(
						ShowAdvertiserActivity.this).create();
				alertDialog.setTitle("Agregado a Contactos");
				alertDialog.setMessage("Se agregó " + toShow.getNombre()
						+ " a contactos");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								ShowAdvertiserActivity.this.finish();
							}
						});
				alertDialog.show();
				try {
					ContentProviderResult[] res = getContentResolver()
							.applyBatch(ContactsContract.AUTHORITY, ops);
				} catch (RemoteException e) {
					System.out.println("Error RemoteException");
					System.out.println(e.getStackTrace());
				} catch (OperationApplicationException e) {
					System.out.println("Error! OperationApplicationException");
					System.out.println(e.getStackTrace());
				}

			}
		});
	}

}