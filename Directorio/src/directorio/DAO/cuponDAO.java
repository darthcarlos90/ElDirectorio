package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import directorio.objetos.Advertiser;
import directorio.objetos.Cupon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class cuponDAO {
	private File ble;
	private SQLiteDatabase db;
	private ArrayList<Cupon> todos;

	private static final String LOCATION_DB = Environment
			.getExternalStorageDirectory().getPath() + "/DirLaguna.db";

	public cuponDAO() {
		ble = new File(LOCATION_DB);
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public void openDB() {
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}

	public ArrayList<Cupon> cuponesPorCategorias(String categoryName) {
		
		getAll();
		if (db.isOpen() == false) {
			openDB();
		}
		Cursor bus = db.rawQuery("select * from Coupon where AdvertiserId in (select AdvertiserId from Advertiser where Categories like '%" +categoryName+ "%');", null);
		ArrayList<Cupon> cupones = new ArrayList<Cupon>();
		while(bus.moveToNext()){
			Cupon cuponcosi = new Cupon();
			cuponcosi.setCuponId(bus.getInt(0));
			cuponcosi.setAdvertiserId(bus.getString(1));
			cuponcosi.setName(bus.getString(2));
			cuponcosi.setDescripcion(bus.getString(3));
			cuponcosi.setConditions(bus.getString(4));
			cuponcosi.setHowToCash(bus.getString(5));
			cuponcosi.setStart(bus.getString(6));
			cuponcosi.setStart(bus.getString(7));
			cuponcosi.setImgSrc(bus.getString(8));
			cupones.add(cuponcosi);
		}
		bus.close();
		db.close();
		return cupones;
	}

	public Cupon getCupon(int id) {
		if (db.isOpen() == false) {
			openDB();
		}
		Cupon resultado = new Cupon();
		Cursor c = db.rawQuery("Select * from Coupon where CouponId = " + id,null);
		c.moveToFirst();
		resultado.setCuponId(id);
		resultado.setAdvertiserId(c.getString(1));
		resultado.setName(c.getString(2));
		resultado.setDescripcion(c.getString(3));
		resultado.setConditions(c.getString(4));
		resultado.setHowToCash(c.getString(5));
		resultado.setStart(c.getString(6));
		resultado.setEnd(c.getString(7));
		resultado.setImgSrc(c.getString(8));
		resultado.setPicUrl(c.getString(8));
		Cursor d = db.rawQuery("select AdvName from Advertiser where AdvertiserId = " + resultado.getAdvertiserId(), null);
		d.moveToFirst();
		resultado.setNegocio(d.getString(0));
		c.close();
		d.close();
		db.close();
		return resultado;
	}

	public void getAll() {
		if (db.isOpen() == false) {
			openDB();
		}
		todos = new ArrayList<Cupon>();
		Cursor c = db.rawQuery("select * from Coupon;", null);
		while (c.moveToNext()) {
			Cupon cupon = new Cupon();
			cupon.setCuponId(c.getInt(0));
			cupon.setAdvertiserId(c.getString(1));
			cupon.setName(c.getString(2));
			cupon.setDescripcion(c.getString(3));
			cupon.setConditions(c.getString(4));
			cupon.setHowToCash(c.getString(5));
			cupon.setStart(c.getString(6));
			cupon.setEnd(c.getString(7));
			cupon.setPicUrl(c.getString(8));
			todos.add(cupon);
		}

		c.close();
		db.close();
	}
}
