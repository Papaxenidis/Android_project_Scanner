package com.company.ws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DBHelper DB;

    EditText name,contact,dob;
    Button insert,update,delete,view,deleteall;
    String idstring = "";

    TextView textid;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            idstring = null;
        } else {
            idstring = extras.getString("identifier");
        } // δουλευει τσεκ


        image = findViewById(R.id.exodos);
        textid = findViewById(R.id.idtext);
        textid.setText(idstring);

        //ΕDITTEXT
        name = findViewById(R.id.idname);
        contact = findViewById(R.id.idcontact);
        dob = findViewById(R.id.iddate);


        //BUTTONS
        insert = findViewById(R.id.idinsert);
        delete = findViewById(R.id.iddelete);
        update = findViewById(R.id.idupdate);
        view = findViewById(R.id.idview);
       // emailbtn = findViewById(R.id.button2);

        DB = new DBHelper(this);

       /* db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Userdetails(name TEXT,contact TEXT,dob TEXT)");
        */



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Launch_act.class);
                startActivity(intent);
            }
        });
        contact.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    String nameTXT =  name.getText().toString().trim();
                    String contactTXT = contact.getText().toString().trim();

                    if(nameTXT.isEmpty())
                    {
                        name.setError("Το πεδίο είναι υποχρεωτικό");
                        name.requestFocus();

                    }else if(contactTXT.isEmpty())
                    {
                        contact.setError("To πεδίο είναι υποχρεωτικό");
                        contact.requestFocus();

                    }else{



                        Boolean checkinsertdata = DB.insertuserdata(nameTXT,contactTXT);//EDW

                        if(checkinsertdata)
                        {
                            Toast.makeText(MainActivity.this, "Επιτυχής εισαγωγή", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            contact.setText("");
                            name.post(new Runnable() {
                                @Override
                                public void run() {
                                    name.requestFocus();
                                }
                            });

                        }else
                        {
                            Toast.makeText(MainActivity.this, "Η καταχώρηση απέτυχε", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            contact.setText("");
                            name.post(new Runnable() {
                                @Override
                                public void run() {
                                    name.requestFocus();
                                }
                            });
                        }



                    }





                    return true;
                }
                return false;
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deiktis = 0;
                String x;
                Cursor res = DB.getdata(name.getText().toString());
                if(res.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "Η βάση είναι κενή.", Toast.LENGTH_SHORT).show();
                    return;


                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append(" --------------------------------\n");
                    buffer.append(" ‣Barcode :" + res.getString( 0)+" "+"\n");
                    buffer.append(" ‣Ποσότητα :" + res.getString(1)+""+"\n");
                    buffer.append(" ‣ΙD: " + String.valueOf(deiktis)+"/"+"\n\n");
                    deiktis++;

                }

                x = buffer.toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Καταχωρήσεις");
                builder.setMessage(buffer.toString());

                Intent intent = new Intent(MainActivity.this,Email_Ac.class);
                intent.putExtra("stringineed",x);
                intent.putExtra("identifier",idstring);
                startActivity(intent);
            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTXT =  name.getText().toString();
                String contactTXT = contact.getText().toString();
                //String dobTXT = dob.getText().toString();
                int posotita = conv(contactTXT);

                if(nameTXT.isEmpty())
                {
                    name.setError("Το πεδίο είναι υποχρεωτικό");
                    name.requestFocus();

                }else if(contactTXT.isEmpty())
                {
                    contact.setError("To πεδίο είναι υποχρεωτικό");
                    contact.requestFocus();
                }else if(posotita>5000){
                    contact.setText("");
                    name.setText("");
                    contact.setError("Η ποσότητα δεν μπορεί να υπερβαίνει τον αριθμό : 5.000");
                    name.requestFocus();
                }else{

                    Boolean checkinsertdata = DB.insertuserdata(nameTXT,contactTXT);//EDW

                    if(checkinsertdata)
                    {
                        Toast.makeText(MainActivity.this, "Επιτυχής καταχώρηση", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();

                    }else
                    {
                        Toast.makeText(MainActivity.this, "Η καταχώρηση απέτυχε", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();
                    }

                    name.setText("");
                    contact.setText("");
                    name.requestFocus();

                }




            }
        });



        //UPDATE
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT =  name.getText().toString();
                String contactTXT = contact.getText().toString();
                //String dobTXT = dob.getText().toString();


                if(nameTXT.isEmpty())
                {
                    name.setError("Το πεδίο είναι υποχρεωτικό");
                    name.requestFocus();

                }else{

                    Boolean checkupdatedata = DB.updateuserdata(nameTXT,contactTXT);

                    if(checkupdatedata == true)
                    {
                        Toast.makeText(MainActivity.this, "Η βάση ανανεώθηκε", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();

                    }else
                    {
                        Toast.makeText(MainActivity.this, "Η ανανέωση απέτυχε", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();
                    }
                    name.setText("");
                    contact.setText("");
                    name.requestFocus();


                }





            }
        });

        //DELETE
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT =  name.getText().toString();

                if(nameTXT.isEmpty())
                {
                    name.setError("Το πεδίο είναι υποχρεωτικό");
                    name.requestFocus();


                }else{

                    Boolean checkdeletedata = DB.deletedata(nameTXT);

                    if(checkdeletedata == true)
                    {
                        Toast.makeText(MainActivity.this, "Επιτυχής διαγραφή", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();

                    }else
                    {
                        Toast.makeText(MainActivity.this, "Αποτυχία διαγραφής", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        contact.setText("");
                        name.requestFocus();
                    }
                    name.setText("");
                    contact.setText("");
                    name.requestFocus();


                }



            }
        });




    }

    private int conv(String contactTXT) {
        if(contactTXT.length()>5){
            int nex;
            String x = contactTXT.substring(0,5);
            nex = Integer.parseInt(x);
            return nex;
        }else{
            return Integer.parseInt(contactTXT);
        }

    }

    @Override
    public void onBackPressed() {
        if (1>3) {
            super.onBackPressed();
        } else {
        }
    }


}