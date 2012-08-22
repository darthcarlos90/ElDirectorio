package directorio.actividades;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AcercaDeActivity extends Activity {

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.out.println("Disabled");
	}

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
		// CAMBIOS EN TODO EL METODO
		TextView email = (TextView) findViewById(R.id.mail1);
		final String correo = (String) email.getText();
		email.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] {
						correo, "" });
				email.putExtra(Intent.EXTRA_SUBJECT, "Ventas");
				email.putExtra(Intent.EXTRA_TEXT, "message");
				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));
			}
		});


		TextView mail2 = (TextView) findViewById(R.id.mail2);
		final String correo2 = (String) mail2.getText();
		mail2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] {
						correo2, "" });
				email.putExtra(Intent.EXTRA_SUBJECT, "Info");
				email.putExtra(Intent.EXTRA_TEXT, "message");
				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));

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
