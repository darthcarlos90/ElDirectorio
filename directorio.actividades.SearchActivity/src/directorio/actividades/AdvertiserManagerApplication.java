package directorio.actividades;

import java.util.ArrayList;

import directorio.BaseDeDatos.FavoritosDatabaseHelper;
import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdvertiserManagerApplication extends Application {
	private SQLiteDatabase db;
	private ArrayList<Advertiser> favoritos;
	private FavoritosDatabaseHelper helper;

	@Override
	public void onCreate() {
		// Initialization of variables.
		super.onCreate();
		openDatabase();
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
		assert (null != adv);
		if (db.isOpen() == false) {
			openDatabase();
		}
		String query = "insert into Favoritos values('" + adv.getId() + "', '"
				+ adv.getNombre() + "');";
		db.rawQuery(query, null);
		db.close();
		favoritos.add(adv);
	}

	private void openDatabase() {
		helper = new FavoritosDatabaseHelper(this);
		db = helper.getWritableDatabase();
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
			Advertiser adv = getAdvertiser(favs.get(i));
			adv.setFavorito(true);
			favoritos.add(adv);
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
