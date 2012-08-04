package directorio.galeria;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import directorio.DAO.AdvertiserDAO;
import directorio.actividades.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.Toast;

public class GalleryView extends Activity {
	// Integer[] pics = { };
	LinearLayout imageView;
	String GalleryId;
	ArrayList<Drawable> images = new ArrayList<Drawable>();
	ArrayList<Drawable> thumbs = new ArrayList<Drawable>();
	ArrayList<String> urls;
	AdvertiserDAO aDao = new AdvertiserDAO();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria);
		setGallery();
		setThumbs();

		try {
			// InputStream in = (new URL("www.google.com").openStream());
		} catch (Exception e) {
			e.getMessage();
		}
		Button back = (Button) findViewById(R.id.Back);
		back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				
			}
		});
		
		Gallery ga = (Gallery) findViewById(R.id.Gallery01);
		ga.setAdapter(new ImageAdapter(this));

		imageView = (LinearLayout) findViewById(R.id.ImageView01);
		ga.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*
				 * Toast.makeText( getBaseContext(),
				 * "You have selected picture " + (arg2 + 1) + " of Antartica",
				 * Toast.LENGTH_SHORT).show();
				 */
				try {
					imageView.removeAllViews();
				} catch (Exception e) {
					e.getMessage();
				}
				TouchImageView touchImageView = new TouchImageView(
						GalleryView.this);
				touchImageView.setImageDrawable(images.get(arg2));
				// touchImageView.setImageResource(pics[arg2]);
				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				imageView.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL);
				touchImageView.setLayoutParams(lp);
				imageView.addView(touchImageView);
			}

		});
		
		

	}

	private void setGallery() {
		SharedPreferences sp = getSharedPreferences("galeriaId", 0);
		GalleryId = sp.getString("galeriaId", null);
		urls = aDao.getUrls(GalleryId);
		for (int i = 0; i < urls.size(); i++) {
			Drawable d = ImageOperations(this, urls.get(i), "image" + i
					+ ".jpg");
			images.add(d);
		}

	}

	private void setThumbs() {
		SharedPreferences sp = getSharedPreferences("galeriaId", 0);
		GalleryId = sp.getString("galeriaId", null);
		urls = aDao.getThumbs(GalleryId);
		for (int i = 0; i < urls.size(); i++) {
			Drawable d = ImageOperations(this, urls.get(i), "image" + i
					+ ".jpg");
			thumbs.add(d);
		}
	}

	private Drawable ImageOperations(Context ctx, String url,
			String saveFilename) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,
			IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}

	public class ImageAdapter extends BaseAdapter {

		private Context ctx;
		int imageBackground;

		public ImageAdapter(Context c) {
			ctx = c;
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		public int getCount() {

			return images.size();
		}

		public Object getItem(int arg0) {

			return arg0;
		}

		public long getItemId(int arg0) {

			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ImageView iv = new ImageView(ctx);
			iv.setImageDrawable(thumbs.get(arg0));
			// iv.setImageResource(pics[arg0]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
			iv.setBackgroundResource(imageBackground);
			return iv;
		}

	}
}