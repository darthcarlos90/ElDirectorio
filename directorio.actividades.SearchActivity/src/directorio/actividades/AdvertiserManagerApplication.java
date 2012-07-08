package directorio.actividades;

import java.util.ArrayList;

import directorio.BaseDeDatos.FavoritosDatabaseHelper;
import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdvertiserManagerApplication extends Application {
	private SQLiteDatabase db;
	private ArrayList<String> favoritos;

	public void onCreate() {
		// Initialization of variables.
		super.onCreate();
		FavoritosDatabaseHelper helper = new FavoritosDatabaseHelper(this);
		db = helper.getWritableDatabase();
		if (null == favoritos) {
			loadFavoritos();
		}
	}

	public void onTerminate() {
		db.close();
		super.onTerminate();
	}

	public void addToFavoritos(String adv) {
		// Method that adds the data of a task into the database.
		assert (null != adv);
		// String nombreAdvertiser = adv;
		ContentValues cv = new ContentValues();
		// cv.put("id", "");
		cv.put("nombreFavoritos", adv);
		db.insert("Favoritos", "id", cv);
		db.close();
		favoritos.add(adv);

	}

	private void loadFavoritos() {
		favoritos = new ArrayList<String>();

		Cursor c = db.rawQuery("SELECT * FROM " + "Favoritos;", null);
		while (c.moveToNext()) {
			favoritos.add(c.getString(1));
		}
		c.close();

	}

	public ArrayList<String> getFavoritos() {
		return favoritos;
	}

}
