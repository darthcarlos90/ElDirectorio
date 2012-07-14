package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import directorio.objetos.Sucursal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class sucursalDAO {
	File ble;
	SQLiteDatabase db;
	private static final String LOCATION_DB = Environment
			.getExternalStorageDirectory().getPath() + "/DirLaguna.db";

	public sucursalDAO() {
		ble = new File(LOCATION_DB);
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public void openDB() {
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public ArrayList<String> getStringSucursales(String id) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<String> resultados = new ArrayList<String>();
		String query = "Select * from Office where AdvertiserId = '" + id
				+ "';";
		Cursor c = db.rawQuery(query, null);


		c.moveToFirst();
		while (c.moveToNext()) {
			String result = "";
			result += "Sucursal " + c.getString(3);
			result += ", " + c.getString(4);
			result += ", " + c.getString(5);
			resultados.add(result);
		}
		c.close();
		db.close();
		return resultados;
	}

	public ArrayList<Sucursal> getSucursales(String id) {
		if (db.isOpen() == false) {
			openDB();
		}
		ArrayList<Sucursal> sucursales = new ArrayList<Sucursal>();
		String query = "Select * from Office where AdvertiserId = '" + id
				+ "';";
		Cursor c = db.rawQuery(query, null);

		c.moveToFirst();
		while (c.moveToNext()) {
			Sucursal sucursal = new Sucursal();
			sucursal.setId(c.getInt(0));
			sucursal.setAdvertiserID(c.getInt(1));
			sucursal.setName(c.getString(3));
			sucursal.setAddress(c.getString(4));
			sucursal.setCityName(c.getString(5));
			sucursal.setPointX(c.getDouble(6));
			sucursal.setPointY(c.getDouble(7));
			sucursales.add(sucursal);
		}
		c.close();
		db.close();
		return sucursales;
	}

	public boolean hasSucursales(String id) {
		if (db.isOpen() == false) {
			openDB();
		}
		String query = "Select * from Office where AdvertiserId = '" + id
				+ "';";
		Cursor c = db.rawQuery(query, null);
		int cantidad = c.getCount();
		c.close();
		db.close();
		if (cantidad > 1) {
			return true;
		} else
			return false;
	}

}