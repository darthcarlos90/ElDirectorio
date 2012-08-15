package directorio.actividades;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import directorio.DAO.LoginDAO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginCupones extends MenuActivity {

	public static final String PREFS_NAME = "MyPrefsFile";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		
		
	      	
		  SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		  final Editor guadaSesion = settings.edit();
		  
		  String estado = settings.getString("estado", "No Logueado"); 
		  
	       System.out.println("Estado: " + estado);
	       
	       
	       if(estado.equals("Logueado")){
	    	   Intent intent = new Intent(LoginCupones.this,CatsConCupones.class);
				startActivity(intent);
	       }
	       else{
	    	   setContentView(R.layout.login);
	    	   super.addButtons();
	    	   final EditText username = (EditText)findViewById(R.id.username);
	    	   final EditText password = (EditText)findViewById(R.id.password);
	    	   final ProgressBar carga = (ProgressBar)findViewById(R.id.progressBar1);
				carga.setVisibility(ProgressBar.INVISIBLE);
	    	   Button login = (Button)findViewById(R.id.logini);
	    	   
	    	   login.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					carga.setVisibility(ProgressBar.VISIBLE);
					carga.setIndeterminate(true);
					
					String resultado =loguear(username.getText().toString(), toMd5(password.getText().toString()));
					
					if(resultado == "No Logueado"){
						Toast.makeText(LoginCupones.this, "Datos Incorrectos", Toast.LENGTH_LONG).show();
						carga.setVisibility(ProgressBar.INVISIBLE);
						carga.setIndeterminate(true);
					}else{
						guadaSesion.putString("estado", resultado);
						guadaSesion.commit();

						Intent intent = new Intent(LoginCupones.this,CatsConCupones.class);
						startActivity(intent);
					}	
				}
			});
	    	   
	       }
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	private String toMd5(String pass){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

           
            StringBuffer hexString = new StringBuffer();
            for(int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            Log.w("Pass en MD5: ", hexString.toString());
            return hexString.toString();
        }catch(NoSuchAlgorithmException ex){
            Log.w("NoSuchAlgorithmException", ex.toString());
            return null;
        }
    }
	
	private String loguear(String usuario, String contramd5){
		
		 // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://m.eldirectorio.mx/loginHandler.axd");
	    String log = "No logueado";
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", usuario));
	        nameValuePairs.add(new BasicNameValuePair("md5", contramd5));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        int comprobacion = response.getStatusLine().getStatusCode();
	        System.out.println("Resulto en: " +comprobacion);
	        if(comprobacion == 200){
	        	log = "Logueado";
	        }else{
	        	log = "No logueado";
	        }
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		
		return log;
		
	}
	
}
