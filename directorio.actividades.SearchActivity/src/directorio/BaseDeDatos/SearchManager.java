package directorio.BaseDeDatos;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import directorio.objetos.Advertiser;

public class SearchManager {

	private static Double EARTH_RADIUS = 6371.00;


	public static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2){
		  Double Radius = EARTH_RADIUS; //6371.00;
		  Double dLat = Math.toRadians(lat2-lat1);
		  Double dLon = Math.toRadians(lon2-lon1);            
		  Double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		  Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		  Math.sin(dLon/2) * Math.sin(dLon/2);
		  Double c = (2 * Math.asin(Math.sqrt(a)));
		  return (Radius * c);		
		}

		@SuppressLint("ParserError")
		public static ArrayList<Advertiser> negociosenRango(double latitude, double longitude, double rangoBuscar , String ciudad, String filtroString, SQLiteDatabase db){
			ArrayList<Advertiser> negociosPorNombre = new ArrayList<Advertiser>();

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
			return negociosEnRango;
			}
		}
}