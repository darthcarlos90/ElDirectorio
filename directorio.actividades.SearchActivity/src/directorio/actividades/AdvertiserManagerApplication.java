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
	private ArrayList<Advertiser> favoritos;
	

	@Override
	public void onCreate() {
		// Initialization of variables.
		super.onCreate();
		FavoritosDatabaseHelper helper = new FavoritosDatabaseHelper(this);
		db = helper.getWritableDatabase();
		if (null == favoritos) {
			loadFavoritos();
		}
	}

	@Override
	public void onTerminate() {
		db.close();
		super.onTerminate();
	}

	public void addToFavoritos(Advertiser adv) {
		// Method that adds the data of a task into the database.
		assert (null != adv);
		String nombreAdvertiser = adv.getNombre();
		ContentValues cv = new ContentValues();
		// cv.put("id", "");
		cv.put("nombreFavoritos", nombreAdvertiser);
		db.insert("Favoritos", "id", cv);
		db.close();
		favoritos.add(adv);
	}

	private void loadFavoritos() {
		favoritos = new ArrayList<Advertiser>();
		ArrayList<String> favs = new ArrayList<String>();
		Cursor c = db.rawQuery("SELECT * FROM " + "Favoritos;", null);
		while (c.moveToNext()) {
			favs.add(c.getString(1));
		}
		c.close();
		for (int i = 0; i < favs.size(); i++) {
			favoritos.add(getAdvertiser(favs.get(i)));
		}
		
		
	}

	public ArrayList<Advertiser> getFavoritos() {
		if (null == favoritos) {
			loadFavoritos();
		}
		return favoritos;
	}

	public Advertiser getAdvertiser(String nombre) {
		AdvertiserDAO aDao = new AdvertiserDAO();
		Advertiser resultado = aDao.find(nombre);
		return resultado;

	}

}
