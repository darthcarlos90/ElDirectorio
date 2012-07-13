package directorio.BaseDeDatos;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import directorio.objetos.Advertiser;

@SuppressLint({ "ParserError", "ParserError" })
public class SearchManager {
	
	private static Double EARTH_RADIUS = 6371.00;
	
	
	public static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2){
		double nRadius = 6371; // Earth's radius in Kilometers
	    // Get the difference between our two points
	    // then convert the difference into radians

	    double nDLat = Math.toRadians(lat2 - lat1);
	    double nDLon = Math.toRadians(lon2 - lon1);

	    // Here is the new line
	    lat1 =  Math.toRadians(lat1);
	    lat2 =  Math.toRadians(lat2);

	    double nA = Math.pow( Math.sin(nDLat/2), 2 ) + Math.cos(lat1) * Math.cos(lat2) *Math.pow( Math.sin(nDLon/2), 2 );

	    double nC = 2 * Math.atan2( Math.sqrt(nA), Math.sqrt( 1 - nA ));
	    double nD = nRadius * nC;

	    return nD; // Return our calculated distance	
		}
	
		@SuppressLint("ParserError")
		public static ArrayList<Advertiser> negociosenRango(double latitude, double longitude, double rangoBuscar , String ciudad, String filtroString, SQLiteDatabase db){
			ArrayList<Advertiser> negociosPorNombre = new ArrayList<Advertiser>();
			String ble = " ";
			char bli = ble.charAt(0);
			int last = filtroString.length() - 1;
			
			if(filtroString.charAt(last) == bli){
				filtroString = filtroString.substring(0, filtroString.length() - 1);
			}
			
			if(rangoBuscar == 0){		
				Cursor tablaNegocios = db.rawQuery("select * from Advertiser where AdvName like '%" + filtroString + "%' or Tags like '%"+filtroString+"%'",null);
				while(tablaNegocios.moveToNext()){
					Advertiser adver = new Advertiser();
					adver.setId(tablaNegocios.getString(0));
					adver.setNombre(tablaNegocios.getString(1));
					adver.setDescripcion(tablaNegocios.getString(2));
					adver.setDireccion(tablaNegocios.getString(3));
					adver.setContacto(tablaNegocios.getString(4));
					adver.setSitioWeb(tablaNegocios.getString(5));
					adver.setFacebook(tablaNegocios.getString(6));
					adver.setTwitter(tablaNegocios.getString(7));
					adver.setPosx(tablaNegocios.getDouble(8));
					adver.setPosy(tablaNegocios.getDouble(9));
					adver.setCiudad(tablaNegocios.getString(11));
					adver.setImgSrc(tablaNegocios.getBlob(16));
					negociosPorNombre.add(adver);
				}
				tablaNegocios.close();
				System.out.println(ciudad + " /" + ciudad.length());
				ArrayList<Advertiser> negociosEnRango = new ArrayList<Advertiser>();	
				for(int i = 0; i < negociosPorNombre.size();i++){
					String ciudadcomparar =  negociosPorNombre.get(i).getCiudad();
					if(ciudad.equals(ciudadcomparar)){
						negociosEnRango.add(negociosPorNombre.get(i));
					}
				}
				db.close();
				return  negociosEnRango;
			}
			else{
			Cursor tablaNegocios = db.rawQuery("select * from Advertiser where AdvName like '%" + filtroString + "%' or Tags like '%"+filtroString+"%'",null);
			while(tablaNegocios.moveToNext()){
				Advertiser adver = new Advertiser();
				adver.setId(tablaNegocios.getString(0));
				adver.setNombre(tablaNegocios.getString(1));
				adver.setDescripcion(tablaNegocios.getString(2));
				adver.setDireccion(tablaNegocios.getString(3));
				adver.setContacto(tablaNegocios.getString(4));
				adver.setSitioWeb(tablaNegocios.getString(5));
				adver.setFacebook(tablaNegocios.getString(6));
				adver.setTwitter(tablaNegocios.getString(7));
				adver.setPosx(tablaNegocios.getDouble(8));
				adver.setPosy(tablaNegocios.getDouble(9));
				adver.setCiudad(tablaNegocios.getString(10));
				adver.setImgSrc(tablaNegocios.getBlob(16));
				negociosPorNombre.add(adver);
			}
			tablaNegocios.close();
			ArrayList<Advertiser> negociosEnRango = new ArrayList<Advertiser>();
			for(int i = 0; i < negociosPorNombre.size();i++){
			double kilometrosDistancia = calculateDistance(latitude, longitude, negociosPorNombre.get(i).getPosx(),negociosPorNombre.get(i).getPosy());
			if(kilometrosDistancia < rangoBuscar){
				negociosEnRango.add(negociosPorNombre.get(i));
				}
			}
			db.close();
			return negociosEnRango;
			}
		}
}
