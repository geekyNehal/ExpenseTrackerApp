package com.project.geekynehal.expensetrackerapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private TextView totalsumResult;

    private String type;
    private int amount;
    private String note;
    private String post_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expense List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        totalsumResult=findViewById(R.id.total_amount);

        mAuth=FirebaseAuth.getInstance();
        //getting user detail
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Expense Tracker").child(uId);
        mDatabase.keepSynced(true);

        recyclerView=findViewById(R.id.recycler_home);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        //Reversing item traversal and layout order.(first layout laid out at the end.
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        //avoiding unnecessary layout passes.
        recyclerView.setLayoutManager(layoutManager);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_amount=0;
                    for(DataSnapshot snap:dataSnapshot.getChildren())
                    {
                        Data data=snap.getValue(Data.class);
                        total_amount=data.getAmount();
                        String amount =String.valueOf(total_amount+".00");
                        totalsumResult.setText(amount);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void customDialog()
    {
        AlertDialog.Builder myDialog=new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myView=inflater.inflate(R.layout.input_data,null);
        AlertDialog dialog=myDialog.create();
        dialog.setView(myView);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private class MyViewHolder extends RecyclerView.ViewHolder
    {
        View myView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            myView=itemView;
        }
        public void setType(String type)
        {
            TextView mType=myView.findViewById(R.id.type);
            mType.setText(type);
        }
        public void setNote(String note)
        {
            TextView mNote=myView.findViewById(R.id.note);
            mNote.setText(note);
        }
        public void setDate(String date)
        {
            TextView  mDate=myView.findViewById(R.id.date);
            mDate.setText(date);
        }
        public void setAmount(int ammount)
        {
            TextView mAmount=myView.findViewById(R.id.amount);
            String stam=String.valueOf(ammount);
            mAmount.setText(stam);
        }
    }
}
