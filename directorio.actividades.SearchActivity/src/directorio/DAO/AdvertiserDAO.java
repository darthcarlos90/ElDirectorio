package directorio.DAO;

import java.io.File;
import java.util.ArrayList;

import directorio.objetos.Advertiser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	//
	public AdvertiserDAO(String path){
		ble = new File(path);
		db = SQLiteDatabase.openOrCreateDatabase(ble, null);
	}
	
	public ArrayList<Advertiser>  findAll(){
		ArrayList<Advertiser> arr = new ArrayList<Advertiser>();
		Cursor holo = db.rawQuery("select * from Advertiser;", null);
		//Segun vi el registro, el total de advertisers son 1297, pero si ponemos el moveToFirst, omite el primer registro, y nos devuelve solo 1296, pero vuelvo, como tu prefieras
//		holo.moveToFirst();
		while(holo.moveToNext()){
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
		return arr;
		
	}
}
