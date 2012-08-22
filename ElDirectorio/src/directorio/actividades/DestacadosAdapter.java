package directorio.actividades;

import java.util.ArrayList;

import directorio.objetos.Advertiser;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DestacadosAdapter extends BaseAdapter {
	private LayoutInflater li;
	private ArrayList<Advertiser> adds = new ArrayList<Advertiser>();
	
	public DestacadosAdapter(Context context, ArrayList<Advertiser> addss)
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
				v = li.inflate(R.layout.dest_item, null);
			}
			//reference to the ImageView component
			ImageView iconin = (ImageView)v.findViewById(R.id.icon);
			
			
			//set 'name' in line 1.
			final TextView nameTv = (TextView) v.findViewById(R.id.linr1);
			nameTv.setText(adver.getNombre());
			//set 'gender' in line 2.
			final TextView genderTv = (TextView) v.findViewById(R.id.linr2);
			genderTv.setText(adver.getDireccion());
		
		//if View exists, then reuse... else create a new object.

		return v;
	}
	
}
