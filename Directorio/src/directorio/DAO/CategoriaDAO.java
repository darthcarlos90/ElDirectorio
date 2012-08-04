package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import directorio.objetos.Categoria;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class CategoriaDAO {
	private SQLiteDatabase database;
	private ArrayList<String> categorias;
	private ArrayList<Categoria> cats;
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
	 * M�todo que hace el query a la base de datos y guarda las categorias en un
	 * ArrayList.
	 */
	private void cargaCategorias() {

		cats = new ArrayList<Categoria>();
		Cursor cursor = database.rawQuery("SELECT * FROM Category", null);
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
					Categoria categoria = new Categoria(id, nombre, letra);
					cats.add(categoria);
				} catch (NullPointerException e) {
					System.err
							.println("One of the database fields doesn't exist");
				}
			} while (cursor.moveToNext());
		}

		cursor.close();

	}
}
