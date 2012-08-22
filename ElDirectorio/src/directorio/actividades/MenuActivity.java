package directorio.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public abstract class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menubar);

	}

	public void addButtons() {
		/*
		 * Button b = new Button(this); b.setText("PROBANDO BOTON");
		 * rl.addView(b);
		 */
		Button button = (Button) findViewById(R.id.ABC);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				System.out.println("BOTON ABC");
				Intent intent = new Intent(MenuActivity.this,
						ListaIndexada.class);
				startActivity(intent);
			}
		});

		Button button2 = (Button) findViewById(R.id.favoritos);
		button2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				System.out.println("Button FAVORITOS!!!!");
				Intent intent = new Intent(MenuActivity.this,
						adverlistitem.class);
				intent.putExtra("estado", 3);
				startActivity(intent);
			}
		});

		Button buscar = (Button) findViewById(R.id.buscar);
		buscar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						SearchActivity.class);
				intent.putExtra("basedatos", Environment
						.getExternalStorageDirectory().getPath()
						+ "/DirLaguna.db");
				startActivity(intent);

			}
		});

		Button cupon = (Button) findViewById(R.id.Cupones);

		cupon.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						LoginCupones.class);	
				startActivity(intent);

			}
		});
		
		Button dest = (Button)findViewById(R.id.destacados);
		dest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						ShowDestacados.class);	
				startActivity(intent);

			}
		});
	}

}
