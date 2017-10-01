package debuggers.sewakendaraan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BukaRental extends AppCompatActivity {

    private DatabaseReference mDb;
    private RentalAdapter4P mAdapter;

    private ActionMode actionMode;
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_catalog,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            menu.findItem(R.id.action_edit).setVisible(mAdapter.selectionCount()==1);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_edit:
                    editRental();
                    return true;
                case R.id.action_delete:
                    deleteRental();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            mAdapter.resetSelection();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_rental);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerViewBR);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        mDb = FirebaseDatabase.getInstance().getReference().child("rental_list");
        mAdapter = new RentalAdapter4P(this, mDb, findViewById(R.id.empty_view), new RentalAdapter4P.onClickHandler() {
            @Override
            public void onClick(String rental_id, Rental currentRental) {
                if(actionMode != null) {
                    mAdapter.toggleSelection(rental_id);
                    if (mAdapter.selectionCount() == 0) actionMode.finish();
                    else actionMode.invalidate();
                    return;
                }
                Toast.makeText(BukaRental.this, currentRental.getNm_motor(),Toast.LENGTH_LONG).show();
            }

            @Override
            public boolean onLongClick(String rental_id) {
                if(actionMode != null)return false;
                mAdapter.toggleSelection(rental_id);
                actionMode = BukaRental.this.startSupportActionMode(callback);
                return true;
            }
        });
        rv.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRental();
            }
        });
    }

    private void addRental() {
        //TODO (10) insert dummy data to our real-time database
//        String key = mDb.push().getKey();
//        mDb.child(key).setValue(new Pet("Ratatouille","Tapir"));
        View view = getLayoutInflater().inflate(R.layout.dialog_editor,null, false);
        final EditText name = (EditText)view.findViewById(R.id.edit_rental_name);
        final EditText info = (EditText)view.findViewById(R.id.edit_rental_info);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.editor_activity_title_new_pet)
                .setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String key = mDb.push().getKey();
                        mDb.child(key).setValue(new Rental(
                                name.getText().toString(),
                                info.getText().toString()
                        ));
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();

    }

    private void editRental(){
        View view = getLayoutInflater().inflate(R.layout.dialog_editor,null, false);
        final EditText name = (EditText)view.findViewById(R.id.edit_rental_name);
        final EditText info = (EditText)view.findViewById(R.id.edit_rental_info);

        //copy terus tambahin dibawah ini
        final String currentPetId = mAdapter.getmSelectedId().get(0);
        Rental currentPet = mAdapter.getRental(currentPetId);
        name.setText(currentPet.getNm_motor());
        info.setText(currentPet.getInfo());


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.editor_activity_title_edit_pet)
                .setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //key hapus terus ganti current PetId
                        mDb.child(currentPetId).setValue(new Rental(
                                name.getText().toString(),
                                info.getText().toString()));
                        actionMode.finish(); //tambahin finish


                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        actionMode.finish(); //tambahin finish
                    }
                });
        builder.create().show();
    }

    private void deleteRental(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_dialog_msg)
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(String id:mAdapter.getmSelectedId()){
                            mDb.child(id).removeValue();
                        }
                        actionMode.finish(); //tambahin finish
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        actionMode.finish(); //tambahin finish
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

}
