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
 * Created by BrosnanUhYeah on 30/09/2017.
 */

public class RentalAdapter4P extends RecyclerView.Adapter<RentalAdapter4P.ViewHolder_> {

    private final Context mContext;
    private final onClickHandler mClickHandler;
    private ArrayList<Rental> mData;

    private final View emptyView;
    private ArrayList<String> mDataId;
    private ArrayList<String> mSelectedId;

    @Override
    public ViewHolder_ onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_ed,parent,false);


        return new ViewHolder_(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_ holder, int position) {
        Rental currentRental = mData.get(position);
        holder.mName.setText(currentRental.getNm_motor());
        holder.mInfo.setText(currentRental.getInfo());
        holder.itemView.setSelected(mSelectedId.contains(mDataId.get(position)));
    }

    private void setEmptyView(){
        if(mData.isEmpty())emptyView.setVisibility(View.VISIBLE);
        else emptyView.setVisibility(View.GONE);
    }

    RentalAdapter4P(Context context, DatabaseReference mDb, View empty, onClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
        mData = new ArrayList<>();
        mDataId = new ArrayList<>();
        mSelectedId = new ArrayList<>();
        emptyView = empty;

        mDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mData.add(dataSnapshot.getValue(Rental.class));
                mDataId.add(dataSnapshot.getKey());
                notifyDataSetChanged();
                setEmptyView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int pos = mDataId.indexOf(dataSnapshot.getKey());
                mData.set(pos, dataSnapshot.getValue(Rental.class));
                notifyDataSetChanged();
                setEmptyView();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int pos = mDataId.indexOf(dataSnapshot.getKey());
                mDataId.remove(pos);
                mData.remove(pos);
                notifyDataSetChanged();
                setEmptyView();
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
        void onClick (String rental_id, Rental currentRental);
        boolean onLongClick (String rental_id);

    }

    class ViewHolder_ extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        final TextView mName;
        final TextView mInfo;

        ViewHolder_ (View view){
            super(view);
            mName = (TextView)view.findViewById(R.id.nm_rental);
            mInfo = (TextView)view.findViewById(R.id.info_rental);
            view.setOnClickListener(this);

            view.setOnLongClickListener(this);
            view.setLongClickable(true);
        }
        @Override
        public void onClick(View view) {
            mClickHandler.onClick(mDataId.get(getAdapterPosition()), mData.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            return mClickHandler.onLongClick(mDataId.get(getAdapterPosition()));
        }
    }

    public void toggleSelection(String id){
        if(mSelectedId.contains(id)) mSelectedId.remove(id);
        else mSelectedId.add(id);
        notifyDataSetChanged();
    }

    public int selectionCount(){
        return mSelectedId.size();
    }

    public  void resetSelection(){
        mSelectedId = new ArrayList<>();
        notifyDataSetChanged();
    }

    public Rental getRental(String rental_id){
        return mData.get(mDataId.indexOf(rental_id));
    }

    public ArrayList<String> getmSelectedId(){
        return mSelectedId;
    }
}
