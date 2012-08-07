package directorio.actividades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import directorio.DAO.CategoriaDAO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;

public class CatsConCupones extends Activity {

	private ArrayList<String> categorias;
	private IndexableListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cats_con_cupones);
		setupViews();
		CategoriaDAO adb = new CategoriaDAO();

		categorias = adb.getCategoriasConCupones();
		Collections.sort(categorias);
		ContentAdapter adapter = new ContentAdapter(this,
				android.R.layout.simple_list_item_1, categorias);
		mListView = (IndexableListView) findViewById(R.id.listview);
		mListView.setAdapter(adapter);
		mListView.setFastScrollEnabled(true);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final String categoria = categorias.get(arg2);
				Thread holo = new Thread() {
					public void run() {
						try {
							sleep(100);
							Class texto = Class
									.forName("directorio.actividades.CuponListActivity");
							Intent correo = new Intent(CatsConCupones.this,
									texto);
							correo.putExtra("categoria", categoria);
							startActivity(correo);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				holo.start();

			}
		});

	}

	private void setupViews() {
		Button abc = (Button) findViewById(R.id.ABC1);
		abc.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				System.out.println("BOTON ABC");
				Intent intent = new Intent(CatsConCupones.this,
						ListaIndexada.class);
				startActivity(intent);
			}
		});

		Button favs = (Button) findViewById(R.id.favoritos1);
		favs.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				System.out.println("Button FAVORITOS!!!!");
				Intent intent = new Intent(CatsConCupones.this,
						adverlistitem.class);
				intent.putExtra("estado", 3);
				startActivity(intent);

			}
		});

		Button buscar = (Button) findViewById(R.id.buscar1);
		buscar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CatsConCupones.this,
						SearchActivity.class);
				intent.putExtra("basedatos", Environment
						.getExternalStorageDirectory().getPath()
						+ "/DirLaguna.db");
				startActivity(intent);

			}
		});

		/*Button destacados = (Button) findViewById(R.id.destacados1);
		destacados.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(CatsConCupones.this,
						DestacadosActivity.class);
				startActivity(intent);
			}
		});*/
		Button cupon = (Button) findViewById(R.id.Cupones1);
		cupon.setBackgroundResource(R.drawable.cupones_presionado);

	}

	private class ContentAdapter extends ArrayAdapter<String> implements
			SectionIndexer {

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public ContentAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		public int getPositionForSection(int section) {
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(
									String.valueOf(getItem(j).charAt(0)),
									String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(
								String.valueOf(getItem(j).charAt(0)),
								String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		public int getSectionForPosition(int position) {
			return 0;
		}

		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}

	}

}
