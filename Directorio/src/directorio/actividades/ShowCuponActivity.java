package directorio.actividades;

import directorio.DAO.cuponDAO;
import directorio.objetos.Cupon;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowCuponActivity extends MenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cupon_info);
		addButtons();
		setupViews();
	}

	@Override
	public void addButtons() {
		// TODO Auto-generated method stub
		super.addButtons();
		Button b = (Button) findViewById(R.id.Cupones);
		b.setBackgroundResource(R.drawable.cupones_presionado);
	}

	public void setupViews() {
		Intent intent = getIntent();
//		int id = intent.getIntExtra("idCupon", 0);
		int id = intent.getExtras().getInt("idCupon");
		cuponDAO cd = new cuponDAO();
		Cupon cupon = cd.getCupon(id);
		TextView nombreEmpresa = (TextView) findViewById(R.id.nombreEmpresa);
		System.out.println(cupon.getName());
		nombreEmpresa.setText(cupon.getName());
		

		TextView descripcion = (TextView) findViewById(R.id.descCupon);
		descripcion.setText(cupon.getDescripcion());

		ImageView im = (ImageView) findViewById(R.id.imagenCupon);
		if (cupon.getPicUrl() != null) {
			im.setAdjustViewBounds(true);
			im.setMaxHeight(100);
			im.setMaxWidth(100);
			cupon.setImgSrc(cupon.getPicUrl());
			im.setImageBitmap(BitmapFactory.decodeByteArray(cupon.getImgSrc(),
					0, cupon.getImgSrc().length));
		}
		TextView desde = (TextView) findViewById(R.id.desde);
		TextView hasta = (TextView) findViewById(R.id.hasta);
		if (cupon.getStart() == null) {
			desde.setText("Dese: sinFecha");
		} else {

			desde.setText("Desde: " + cupon.getStart());
		}

		if (cupon.getEnd() == null) {
			hasta.setText("Hasta: Sin fecha");
		} else {
			hasta.setText("Hasta: " + cupon.getEnd());
		}
		
		TextView desCupon = (TextView)findViewById(R.id.descCupon);
		desCupon.setText(cupon.getHowToCash());
		
		TextView insCupon = (TextView) findViewById(R.id.insCupon);
		insCupon.setText(cupon.getConditions());

	}
}
