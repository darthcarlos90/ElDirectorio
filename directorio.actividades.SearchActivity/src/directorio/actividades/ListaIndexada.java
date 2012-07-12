package directorio.actividades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import directorio.DAO.CategoriaDAO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

@SuppressLint("ParserError")
public class ListaIndexada extends Activity {
	private IndexableListView mListView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otralista);
        CategoriaDAO adb = new CategoriaDAO();
        
       final ArrayList<String> categorias = adb.getCategorias();
       
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
				String categoria = categorias.get(arg2);
				SharedPreferences sharedPrefs = getSharedPreferences("tipo de busqueda",0);
				Editor editor = sharedPrefs.edit();
				editor.putString("tipo de busqueda", "advertiser");
				editor.commit();
				SharedPreferences sp = getSharedPreferences("categoria",0);
				editor = sp.edit();
				editor.putString("categoria", categoria);
				editor.commit();
				Thread holo = new Thread(){
					public void run(){
						
						try {
							sleep(100);
							Class texto = Class.forName("directorio.actividades.ShowAdvertisersActivity");
							Intent correo = new Intent(ListaIndexada.this, texto);
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
    
    private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {
    	
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
							if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
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