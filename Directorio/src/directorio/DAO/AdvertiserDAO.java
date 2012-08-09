package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import directorio.objetos.Advertiser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Clase para accesar a la tabla de Advertiser y otros datos que tengan que ver
 * con advertiser.
 * 
 * @author Carlos Tirado
 * 
 */
public class AdvertiserDAO {

	File ble;
	SQLiteDatabase db;
	boolean finished;

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	private static final String LOCATION_DB = Environment
			.getExternalStorageDirectory().getPath() + "/DirLaguna.db";

	//
	public AdvertiserDAO() {
		ble = new File(LOCATION_DB);
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public AdvertiserDAO(String path) {
		ble = new File(path);
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public void openDB() {
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public ArrayList<Advertiser> findAll() {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<Advertiser> arr = new ArrayList<Advertiser>();
		Cursor holo = db.rawQuery("select * from Advertiser;", null);
		// Segun vi el registro, el total de advertisers son 1297, pero si
		// ponemos el moveToFirst, omite el primer registro, y nos devuelve solo
		// 1296, pero vuelvo, como tu prefieras
		// holo.moveToFirst();
		while (holo.moveToNext()) {
			Advertiser adver = new Advertiser();
			adver.setId(holo.getString(0));
			adver.setNombre(holo.getString(1));
			adver.setDescripcion(holo.getString(2));
			adver.setDireccion(holo.getString(3));
			adver.setContacto(holo.getString(4));
			adver.setSitioWeb(holo.getString(5));
			adver.setFacebook(holo.getString(6));
			adver.setTwitter(holo.getString(7));
			adver.setPosx(holo.getDouble(8));
			adver.setPosy(holo.getDouble(9));
			adver.setCiudad(holo.getString(10));
			arr.add(adver);
		}
		holo.close();
		db.close();
		return arr;

	}

	public ArrayList<Advertiser> getByCategory(String category) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<Advertiser> resultados = new ArrayList<Advertiser>();
		Cursor cats = db.rawQuery(
				"Select CategoryId from Category where CatName = '" + category
						+ "';", null);
		cats.moveToPosition(0);
		String catId = "*|@" + cats.getString(0) + "|";
		cats.close();
		Cursor c = db.rawQuery(
				"Select * from Advertiser where Categories like '%" + catId
						+ "%';", null);
		Advertiser temp;
		c.moveToPosition(0);
		if (!c.isAfterLast()) {
			do {
				temp = new Advertiser();
				temp.setId(c.getString(0));
				temp.setNombre(c.getString(1));
				temp.setDescripcion(c.getString(2));
				temp.setDireccion(c.getString(3));
				temp.setContacto(c.getString(4));
				temp.setSitioWeb(c.getString(5));
				temp.setFacebook(c.getString(6));
				temp.setTwitter(c.getString(7));
				temp.setPosx(c.getDouble(8));
				temp.setPosy(c.getDouble(9));
				temp.setCiudad(c.getString(11));
				temp.setImgSrc(c.getString(17));
				resultados.add(temp);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return resultados;
	}

	public ArrayList<Advertiser> getByCategory2(String category) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<Advertiser> resultados = new ArrayList<Advertiser>();
		Cursor cats = db.rawQuery(
				"Select CategoryId from Category where CatName = '" + category
						+ "';", null);
		cats.moveToPosition(0);
		String catId = "*|@" + cats.getString(0) + "|";
		cats.close();
		Cursor c = db.rawQuery(
				"Select * from Advertiser where Categories like '%" + catId
						+ "%';", null);
		Advertiser temp;
		c.moveToPosition(0);
		if (!c.isAfterLast()) {
			do {
				temp = new Advertiser();
				temp.setId(c.getString(0));
				temp.setNombre(c.getString(1));
				temp.setDescripcion(c.getString(2));
				temp.setDireccion(c.getString(3));
				temp.setContacto(c.getString(4));
				temp.setSitioWeb(c.getString(5));
				temp.setFacebook(c.getString(6));
				temp.setTwitter(c.getString(7));
				temp.setPosx(c.getDouble(8));
				temp.setPosy(c.getDouble(9));
				temp.setCiudad(c.getString(11));
				resultados.add(temp);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return resultados;
	}

	
	public Advertiser find(String nombre) {
		if (db.isOpen() == false) {
			openDB();
		}
		Advertiser resultado = new Advertiser();
		Cursor c = db.query("Advertiser", null, "AdvName = ?",
				new String[] { nombre }, null, null, null);
		/*
		 * String query = "Select * from Advertiser where AdvName= '" + nombre +
		 * "';";
		 */
		// db.rawQuery(query, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			do {
				try {
					resultado.setId(c.getString(0));
					resultado.setNombre(c.getString(1));
					resultado.setDescripcion(c.getString(2));
					resultado.setDireccion(c.getString(3));
					resultado.setContacto(c.getString(4));
					resultado.setSitioWeb(c.getString(5));
					resultado.setFacebook(c.getString(6));
					resultado.setTwitter(c.getString(7));
					resultado.setPosx(c.getDouble(8));
					resultado.setPosy(c.getDouble(9));
					resultado.setCiudad(c.getString(11));
					resultado.setTelefono(c.getString(12));
					resultado.setEmail(c.getString(13));
					resultado.setImgSrc(c.getString(17));

				} catch (NullPointerException e) {
					System.out.println("No existe uno de los cursores");
				}
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return resultado;
	}
	
	public ArrayList<Advertiser> getByCategoryAndId(String category, String id) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<Advertiser> resultados = new ArrayList<Advertiser>();
		Cursor cats = db.rawQuery(
				"Select CategoryId from Category where CatName = '" + category
						+ "';", null);
		cats.moveToPosition(0);
		String catId = "*|@" + cats.getString(0) + "|";
		cats.close();
		Cursor c = db.rawQuery(
				"Select * from Advertiser where Categories like '%" + catId
						+ "%' AND AdvertiserId = " + id + ";", null);
		Advertiser temp;
		c.moveToPosition(0);
		if (!c.isAfterLast()) {
			do {
				temp = new Advertiser();
				temp.setId(c.getString(0));
				temp.setNombre(c.getString(1));
				temp.setDescripcion(c.getString(2));
				temp.setDireccion(c.getString(3));
				temp.setContacto(c.getString(4));
				temp.setSitioWeb(c.getString(5));
				temp.setFacebook(c.getString(6));
				temp.setTwitter(c.getString(7));
				temp.setPosx(c.getDouble(8));
				temp.setPosy(c.getDouble(9));
				temp.setCiudad(c.getString(11));
				temp.setImgSrc(c.getString(17));
				resultados.add(temp);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return resultados;
	}

	public boolean hasGallery(String id) {
		boolean resultado;
		if (db.isOpen() == false) {
			openDB();
		}
		Cursor c = db.rawQuery("Select * from Gallery where AdvertiserId = '"
				+ id + "';", null);
		resultado = c.moveToFirst();
		c.close();
		db.close();
		return resultado;
	}

	public String getGalleryName(String id) {
		String resultado = "";
		if (db.isOpen() == false) {
			openDB();
		}
		Cursor c = db.rawQuery("Select * from Gallery where AdvertiserId = '"
				+ id + "';", null);
		if (c.moveToFirst() == true) {
			resultado = c.getString(2);
		}
		c.close();
		db.close();

		return resultado;
	}

	public String getGalleryId(String id) {
		String resultado = "";
		if (db.isOpen() == false) {
			openDB();
		}
		Cursor c = db.rawQuery("Select * from Gallery where AdvertiserId = '"
				+ id + "';", null);
		if (c.moveToFirst() == true) {
			resultado = c.getString(0);
		}
		c.close();
		db.close();
		return resultado;
	}

	public ArrayList<String> getUrls(String galleryId) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<String> resultados = new ArrayList<String>();
		Cursor c = db.rawQuery("Select * from Picture where GalleryId = '"
				+ galleryId + "';", null);
		c.moveToFirst();
		while(c.moveToNext()){
			resultados.add(c.getString(3));
		}
		c.close();
		db.close();
		
		return resultados;

	}
	
	public ArrayList<String> getThumbs(String galleryId){
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<String> resultados = new ArrayList<String>();
		Cursor c = db.rawQuery("Select * from Picture where GalleryId = '"
				+ galleryId + "';", null);
		c.moveToFirst();
		while(c.moveToNext()){
			resultados.add(c.getString(4));
		}
		c.close();
		db.close();
		
		return resultados;
	}

	public SQLiteDatabase getdb() {
		return db;
	}
}