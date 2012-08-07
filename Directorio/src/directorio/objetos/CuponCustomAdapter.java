package directorio.objetos;

import java.util.ArrayList;

import directorio.actividades.R;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CuponCustomAdapter extends BaseAdapter {
	private LayoutInflater li;
	private ArrayList<Cupon> cups = new ArrayList<Cupon>();

	public CuponCustomAdapter(Context context, ArrayList<Cupon> cupones) {
		li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (cupones != null)
			cups = cupones;
	}

	public int getCount() {
		return cups.size();
	}

	public Object getItem(int position) {
		return cups.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// current view object
		View v = convertView;

		final Cupon cupon = cups.get(position);

		// if View exists, then reuse... else create a new object.
		if (v == null) {
			v = li.inflate(R.layout.cupon_item, null);
		}

		// reference to the ImageView component
		final ImageView mLogo = (ImageView) v.findViewById(R.id.cupon_icon);
		// if gender is male, then use 'male' icon. Otherwise, female.
		if (cupon.getPicUrl() != null) {
			 mLogo.setAdjustViewBounds(true);
			 mLogo.setMaxHeight(380);
			 mLogo.setMaxWidth(195);
			cupon.setImgSrc(cupon.getPicUrl());
			mLogo.setImageBitmap(BitmapFactory.decodeByteArray(
					cupon.getImgSrc(), 0, cupon.getImgSrc().length));
		}

		// set 'name' in line 1.
		final TextView nameTv = (TextView) v.findViewById(R.id.tituloCupon);
		nameTv.setText(cupon.getName());

		// set 'gender' in line 2.
		final TextView fecha = (TextView) v.findViewById(R.id.fechaExpiracion);
		fecha.setText(cupon.getStart());

		final TextView descripcion = (TextView) v
				.findViewById(R.id.descripcion);
		descripcion.setText(cupon.getDescripcion());

		return v;
	}

}
