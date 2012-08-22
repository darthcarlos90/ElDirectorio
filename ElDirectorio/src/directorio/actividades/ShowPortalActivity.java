package directorio.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowPortalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_portal);
		String url = "http://m.eldirectorio.mx/portada.aspx?pa=1";
		WebView webview = (WebView) findViewById(R.id.vistaPortal);
		// WebSettings webSettings = webview.getSettings();
		// webSettings.setJavaScriptEnabled(true);
		webview.loadUrl(url);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				try {
					newActivity();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 8000);

	}

	void newActivity() throws InterruptedException, ClassNotFoundException {
		// Thread.sleep(3000);
		// Intent intent = new Intent(ShowPortalActivity.this, Inicio.class);
		// startActivity(intent);
		Class listactivity = Class
				.forName("directorio.actividades.ListaIndexada");
		Intent ble = new Intent(ShowPortalActivity.this, listactivity);

		String base = getIntent().getStringExtra("basedatos");
		ble.putExtra("basedatos", base);
		startActivity(ble);

	}

}
