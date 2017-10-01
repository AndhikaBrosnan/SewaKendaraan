package debuggers.sewakendaraan;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class MotorFragment extends Fragment {


    private DatabaseReference mDb;
    private RentalAdapter3P mAdapter;

    public MotorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.fragment_motor, container, false);

        FloatingActionButton fab_ = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMotor();
            }
        });


        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        //Buat deklarasi pas nambahin ke data ke RecyclerViewnya
        mDb = FirebaseDatabase.getInstance().getReference().child("motor_list");
        mAdapter = new RentalAdapter3P(getContext(), mDb, new RentalAdapter3P.onClickHandler() {
            @Override
            public void onClick(Rental currentRental) {
                Toast.makeText(getContext(),"Data telah ditambah" ,Toast.LENGTH_LONG).show();
            }
        });
        rv.setAdapter(mAdapter);

        return rootView;
    }


    private void addMotor() {
        //TODO (10) insert dummy data to our real-time database
        String key = mDb.push().getKey();
        mDb.child(key).setValue(new Rental("Motor Bu Suk","Tahun 2013","Gambar Coming Soon"));
    }

}
