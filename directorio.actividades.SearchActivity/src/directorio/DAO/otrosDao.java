package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Clase para hacer búsquedas en la base de datos de otros objetos.
 * 
 * @author Carlos
 * 
 */
public class otrosDao {
	private SQLiteDatabase database;
	private ArrayList<String> ciudades;
	private File file;
	private static final String LOCATION_DB = Environment
			.getExternalStorageDirectory().getPath() + "/DirLaguna.db";

	public otrosDao() {
		file = new File(LOCATION_DB);
		database = SQLiteDatabase.openOrCreateDatabase(file, null);

	}

	/**
	 * Método que manda llamar al método cargaCiudades (metodception ._.) para
	 * que realize el query, y después lo guarde en el "field" ciudades.
	 * 
	 * @return El ArrayList con las ciudades de la base de datos.
	 */
	public ArrayList<String> getCiudades() {
		cargaCiudades();
		return ciudades;
	}

	/**
	 * Método que hace el query a la base de datos y guarda las ciudades en un
	 * ArrayList.
	 */
	private void cargaCiudades() {
		ciudades = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT * FROM City", null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			do {
				try {

					String ciudad = cursor.getString(cursor
							.getColumnIndex("CityName"));
					ciudades.add(ciudad);
				} catch (NullPointerException e) {
					System.err
							.println("One of the database fields doesn't exist");
				}
			} while (cursor.moveToNext());
		}

		cursor.close();

	}
}
