package directorio.DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import directorio.objetos.Categoria;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class CategoriaDAO {
	private SQLiteDatabase database;
	private ArrayList<String> categorias;
	private ArrayList<Categoria> cats;
	private ArrayList<String> categoriasConCupones;
	private ArrayList<Categoria> catsConCupones;
	private File file;
	private static final String LOCATION_DB = Environment
			.getExternalStorageDirectory().getPath() + "/DirLaguna.db";

	public CategoriaDAO() {
		file = new File(LOCATION_DB);
		database = SQLiteDatabase.openOrCreateDatabase(file, null);

	}

	/**
	 * M�todo que carga las categorias de la base de datos.
	 * 
	 * @return El ArrayList con las categorias de la base de datos.
	 */
	public ArrayList<String> getCategorias() {
		cargaCategorias();
		categorias = new ArrayList<String>();
		for (int i = 0; i < cats.size(); i++) {
			categorias.add(cats.get(i).getNombre());
		}
		database.close();
		return categorias;
	}

	public ArrayList<String> getCategoriasConCupones() {
		cargaCategorias();
		categoriasConCupones = new ArrayList<String>();
		for (int i = 0; i < catsConCupones.size(); i++) {
			categoriasConCupones.add(catsConCupones.get(i).getNombre());
		}
		database.close();
		return categoriasConCupones;
	}

	public ArrayList<Categoria> getCatsConCupones() {
		return catsConCupones;
	}

	public void setCatsConCupones(ArrayList<Categoria> catsConCupones) {
		this.catsConCupones = catsConCupones;
	}

	public ArrayList<Categoria> getCats() {
		return cats;
	}

	public void setCats(ArrayList<Categoria> cats) {
		this.cats = cats;
	}

	public void setCategorias(ArrayList<String> categorias) {
		this.categorias = categorias;
	}

	/**
	 * M�todo que hace el query a la base de datos y guarda las categorias en
	 * un ArrayList.
	 */
	private void cargaCategorias() {
		catsConCupones = new ArrayList<Categoria>();
		cats = new ArrayList<Categoria>();
		Cursor cursor = null;
		try{

			cursor = database.rawQuery("SELECT * FROM Category", null);
		}catch(Exception e){
			try {
				File bd = new File(Environment.getExternalStorageDirectory().getPath() + "/DirLaguna.db");
				bd.getCanonicalFile().delete();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			do {
				try {

					int id = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("CategoryId")));
					String nombre = cursor.getString(cursor
							.getColumnIndex("CatName"));
					char letra = cursor.getString(
							cursor.getColumnIndex("Letter")).charAt(0);
					int cupones = cursor.getInt(3);
					boolean tieneCupones;
					if (cupones > 0) {
						tieneCupones = true;
					} else
						tieneCupones = false;
					Categoria categoria = new Categoria(id, nombre, letra,
							tieneCupones);
					cats.add(categoria);
					if (categoria.isHasCoupons() == true) {
						catsConCupones.add(categoria);
					}
				} catch (NullPointerException e) {
					System.err
							.println("One of the database fields doesn't exist");
				}
			} while (cursor.moveToNext());
		}

		cursor.close();

	}

}
