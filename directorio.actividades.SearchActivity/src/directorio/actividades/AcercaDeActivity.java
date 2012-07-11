package directorio.actividades;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AcercaDeActivity extends Activity {

	private ImageView img1;
	private ImageView img2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acerca_de);

		// Inicializar los elementos en el View
		setupViews();
	}

	/**
	 * Método para crear los views de la interfaz, y para darle comportamientos.
	 */
	private void setupViews() {
		TextView email = (TextView) findViewById(R.id.info5);
		email.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] {
						"darth_carlos_90@hotmail.com", "" });
				email.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
				email.putExtra(Intent.EXTRA_TEXT, "message");
				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));
			}
		});

		TextView numero1 = (TextView) findViewById(R.id.info6_1);
		numero1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:8711831706"));
					startActivity(callIntent);
				} catch (ActivityNotFoundException activityException) {
					// Log.e("helloandroid dialing example", "Call failed", e);
				}

			}
		});

		TextView numero2 = (TextView) findViewById(R.id.info7_1);
		numero2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:8711831706"));
					startActivity(callIntent);
				} catch (ActivityNotFoundException activityException) {
					// Log.e("helloandroid dialing example", "Call failed", e);
				}

			}
		});

		Button regresar = (Button) findViewById(R.id.backtosearch);
		regresar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		img1 = (ImageView) findViewById(R.id.directorio_img);
		img1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Open a view to view the wikipedia info for the artist.
				Uri uri;
				try {
					uri = Uri.parse("http://eldirectorio.mx");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}
		});

		img2 = (ImageView) findViewById(R.id.publi_img);
		img2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Uri uri;
				try {
					uri = Uri.parse("http://www.publysorpresas.com.mx");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}
		});

	}
}
