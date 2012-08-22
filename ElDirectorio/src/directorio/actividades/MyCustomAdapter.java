package directorio.actividades;

import java.util.ArrayList;
import directorio.objetos.Advertiser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomAdapter extends BaseAdapter {

	private LayoutInflater li;
	private ArrayList<Advertiser> adds = new ArrayList<Advertiser>();
	
	public MyCustomAdapter(Context context, ArrayList<Advertiser> addss)
	{
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(addss != null)
			adds = addss;
	}
	
	public int getCount() {
		return adds.size();
	}

	public Object getItem(int position) {
		return adds.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	@SuppressLint({ "ParserError", "ParserError", "ParserError" })
	public View getView(int position, View convertView, ViewGroup parent) {
		//current view object
		View v = convertView;

		final Advertiser adver = adds.get(position);

			if (v == null) {
				v = li.inflate(R.layout.destacados_item, null);
			}
			//reference to the ImageView component
			ImageView iconin = (ImageView)v.findViewById(R.id.iconito);
			final ImageView mLogo = (ImageView)v.findViewById(R.id.imagendest);
			if(adver.getFeatured() == 0){
				iconin.setVisibility(ImageView.INVISIBLE);
			
				if(adver.getImgSrc() != null){
					mLogo.setAdjustViewBounds(true);
					mLogo.setMaxHeight(50);
					mLogo.setMaxWidth(50);
					mLogo.setImageBitmap(BitmapFactory.decodeByteArray(adver.getImgSrc(),
							0, adver.getImgSrc().length));
				}
			}else {
				mLogo.setVisibility(ImageView.INVISIBLE);
			}
			
			
			//set 'name' in line 1.
			final TextView nameTv = (TextView) v.findViewById(R.id.linea1);
			nameTv.setText(adver.getNombre());
			//set 'gender' in line 2.
			final TextView genderTv = (TextView) v.findViewById(R.id.linea2);
			genderTv.setText(adver.getDireccion());
		
		//if View exists, then reuse... else create a new object.

		return v;
	}
	
}