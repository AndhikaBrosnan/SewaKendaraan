package debuggers.sewakendaraan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by BrosnanUhYeah on 21/09/2017.
 */

public class RentalAdapter3P extends RecyclerView.Adapter<RentalAdapter3P.ViewHolder_>{

    private final Context mContext;
    private final onClickHandler mClickHandler;
    private ArrayList<Rental> mData;

    @Override
    public ViewHolder_ onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item,parent,false);


        return new ViewHolder_(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_ holder, int position) {
        Rental currentRental = mData.get(position);
        holder.mName.setText(currentRental.getNm_motor());
        holder.mInfo.setText(currentRental.getInfo());
        holder.mGambar.setText(currentRental.getGambar());
    }

    RentalAdapter3P(Context context, DatabaseReference mDb, onClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
        mData = new ArrayList<>();
        mDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mData.add(dataSnapshot.getValue(Rental.class));
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    interface onClickHandler{
        void onClick (Rental currentRental);
    }

    class ViewHolder_ extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView mName;
        final TextView mInfo;
        final TextView mGambar;

        ViewHolder_ (View view){
            super(view);
            mName = (TextView)view.findViewById(R.id.nm_motor);
            mInfo = (TextView)view.findViewById(R.id.info);
            mGambar =(TextView)view.findViewById(R.id.gambar);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mClickHandler.onClick(mData.get(getAdapterPosition()));
        }
    }
}
