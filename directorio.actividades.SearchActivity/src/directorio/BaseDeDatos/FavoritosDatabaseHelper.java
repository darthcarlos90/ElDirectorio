package directorio.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritosDatabaseHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DB_NAME = "favoritos.sqlite";
	private static final String TABLA_FAVS = "Favoritos";
	private static final String FAVORITOS_NOMBRE = "nombreFavoritos";
	private static final String ID = "id";

	public FavoritosDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
		db.rawQuery("insert into " + TABLA_FAVS
				+ " values (1, 'Prueba creacion bd');", null);

	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL("create table " + TABLA_FAVS + " (" + ID
				+ " integer primary key autoincrement not null,"
				+ FAVORITOS_NOMBRE + " text" + ");");
		System.out.println("Base de datos 2 creada c:");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Este método no será necesario.

	}

}
