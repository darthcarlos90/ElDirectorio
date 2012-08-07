package directorio.actividades;

import java.io.File;
import java.util.ArrayList;

import directorio.BaseDeDatos.FavoritosDatabaseHelper;
import directorio.DAO.AdvertiserDAO;
import directorio.objetos.Advertiser;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;

public class TodoManagerApplication extends Application {
	private SQLiteDatabase db;
	private ArrayList<Advertiser> favoritos;
	private FavoritosDatabaseHelper helper;

	@Override
	public void onCreate() {
		// Initialization of variables.
		super.onCreate();
		openDatabase();
		if (null == favoritos) {
			try{
			loadFavoritos();
			}catch(SQLiteException o){
				Intent i = getApplicationContext().getPackageManager()
						.getLaunchIntentForPackage(
								getApplicationContext().getPackageName());

				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
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
		ContentValues cv = new ContentValues();
		cv.put("id", adv.getId());
		cv.put("nombreFavoritos", adv.getNombre());
		/*
		 * String query = "insert into Favoritos values('" + adv.getId() +
		 * "', '" + adv.getNombre() + "');"; db.rawQuery(query, null);
		 */
		db.insert("Favoritos", null, cv);
		db.close();
		favoritos.add(adv);
	}
	
	public void addToLogin(String user, String Estado){
		assert (null != user && Estado != null);
		if (db.isOpen() == false) {
			openDatabase();
		}
		ContentValues cv = new ContentValues();
		cv.put("Id", user);
		cv.put("Estado", Estado);
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
	
	public ArrayList<String> dameLogeado(){
		if (db.isOpen() == false) {
			openDatabase();
		}
		ArrayList<String> resultado = new ArrayList<String>();
		Cursor c = db.rawQuery("select  * from Login", null);
		while(c.moveToNext()){
			resultado.add(c.getString(0));
			resultado.add(c.getString(1));
		}
		c.close();
		return resultado;
	}
	
	public void desloguear(){
		if (db.isOpen() == false) {
			openDatabase();
		}
		Cursor c = db.rawQuery("delete from Login", null);
		c.close();
	}

	public ArrayList<Advertiser> getFavoritos() {
		if (null == favoritos) {
			loadFavoritos();
		}
		return favoritos;
	}

	public Advertiser getAdvertiser(String nombre) {
		File bd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/DirLaguna.db");
		
		AdvertiserDAO aDao = new AdvertiserDAO();
		
		Advertiser resultado = aDao.find(nombre);
		aDao.getdb().close();
		return resultado;

	}

	public boolean isInFavoritos(String name) {
		if (db.isOpen() == false) {
			openDatabase();
		}
		loadFavoritos();
		int existe = 0;
		for (int i = 0; i < favoritos.size(); i++) {
			if (favoritos.get(i).getNombre().equals(name)) {
				existe++;
			}
		}

		if (existe > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void removeFromFavoritos(String name) {
		SQLiteStatement stmt = db
				.compileStatement("DELETE FROM Favoritos WHERE nombreFavoritos = ?");
		stmt.bindString(1, name);
		stmt.execute();
		loadFavoritos();
	}

}