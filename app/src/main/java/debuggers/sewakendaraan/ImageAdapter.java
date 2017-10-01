package debuggers.sewakendaraan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by BrosnanUhYeah on 23/09/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
        // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
             imageView.setPadding(8, 8, 8, 8);
            } else {
             imageView = (ImageView) convertView;
            }

         imageView.setImageResource(mThumbIds[position]);
         return imageView;
}

// references to our images
       private Integer[] mThumbIds = {
            R.drawable.mobil_box, R.drawable.mobil_classic,
            R.drawable.mobil_hatchback, R.drawable.mobil_minibus,
            R.drawable.mobil_pickup, R.drawable.mobil_roofless,
            R.drawable.mobil_sedan, R.drawable.mobil_sport,
            R.drawable.mobil_suv, R.drawable.mobil_van,
            R.drawable.mobil_box, R.drawable.mobil_classic,
            R.drawable.mobil_hatchback, R.drawable.mobil_minibus,
            R.drawable.mobil_pickup, R.drawable.mobil_roofless,
            R.drawable.mobil_sedan, R.drawable.mobil_sport,
            R.drawable.mobil_van, R.drawable.mobil_suv,
            R.drawable.mobil_box, R.drawable.mobil_classic
        };
}

