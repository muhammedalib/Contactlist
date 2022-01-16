package com.muhammed.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton BtnRegister;
    private RecyclerView RcValues;

    DataBaseHelper dataBaseHelper;
    String OrderByNew = DataBase.Constant_Upload + " DESC ";
    String OrderByOld = DataBase.Constant_Upload + " ASC ";
    String OrderByA_Z = DataBase.Constant_Name + " ASC ";
    String OrderByZ_A = DataBase.Constant_Name + " DESC ";



    ActionBar actionBar ;

    String PresentOrder = OrderByNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RcValues = findViewById(R.id.Rv_Registers);
        dataBaseHelper = new DataBaseHelper(this);
        BtnRegister = findViewById(R.id.btn_register);
        actionBar = getSupportActionBar();
        actionBar.setTitle("All Contacts");


        LaunchValues(OrderByNew);






        // Register Button Activity.
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddRegister = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(AddRegister);
            }
        });
    }

    private void LaunchValues(String order) {
        PresentOrder=order;
        RegisterAdaptor registerAdaptor = new RegisterAdaptor(MainActivity.this,dataBaseHelper.TakeAllRegisters(order));
        RcValues.setAdapter(registerAdaptor);
        actionBar.setSubtitle("Number Of Person:"+dataBaseHelper.Counter());
    }

    @Override
    protected void onResume() {

        LaunchValues(PresentOrder);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id == R.id.menu_delete)
        {
            dataBaseHelper.DeleteAll();
            onResume();
        }
        if(id == R.id.menu_Sort)
        {
            SortDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SortDialog() {
        String [] SortItems = {"Launch Date:New To OLD","Launch Date:Old To New","Alphabet:A to Z","Aplhabet:Z To A"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order");
        builder.setItems(SortItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0)
                {
                    LaunchValues(OrderByNew);
                }
                if(i==1)
                {
                    LaunchValues(OrderByOld);
                }
                if(i==2)
                {
                    LaunchValues(OrderByA_Z);
                }
                if(i==3)
                {
                    LaunchValues(OrderByZ_A);
                }
            }
        });
        builder.create().show();
    }
}


