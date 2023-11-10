package com.company.ws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Email_Ac extends AppCompatActivity {


    DBHelper DB;
    EditText ed;
    TextView textView,textid,texwarn;
    Button b1,deleteall,deletefinalbtn;
    String newString,idstring ;

    ScrollView scrollView;
    Context context;


    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);



        scrollView = findViewById(R.id.scrollView2);
        ed = findViewById(R.id.edei);
        textid = findViewById(R.id.idtext);
        deletefinalbtn = findViewById(R.id.deletefinal);
        texwarn = findViewById(R.id.warning);

        DB = new DBHelper(this);
        deleteall = findViewById(R.id.deleteb);

        b1 = findViewById(R.id.b1b1);
        textView = findViewById(R.id.t);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
            idstring = null;
        } else {
            newString = extras.getString("stringineed"); //ΒΑΣΗ
            idstring  = extras.getString("identifier"); // ΤΑΥΤΟΤΗΤΑ SCANNER
        } // δουλευει τσεκ



        textView.setText(newString);
        textid.setText(idstring);





        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletefinalbtn.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                deleteall.setVisibility(View.INVISIBLE);
                b1.setVisibility(View.INVISIBLE);
                deletefinalbtn.setClickable(true);
                ed.setVisibility(View.VISIBLE);
                texwarn.setVisibility(View.VISIBLE);




            }
        });


        deletefinalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed.getText().toString().equals("2023")){
                    Boolean checkinsertdata = DB.deletedata();//EDW

                    if(checkinsertdata)
                    {
                        Toast.makeText(Email_Ac.this, "Αποτυχία ενέργειας.", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(Email_Ac.this, "Τα στοιχεία σβήστηκαν!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Email_Ac.this,MainActivity.class);
                        intent.putExtra("identifier",idstring);
                        startActivity(intent);

                    }
                }else{

                    ed.setError("Λάθος κωδικός");
                    ed.requestFocus();

                }

            }
        });






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idstring = textid.getText().toString();

                try{
                    newString = newString.replaceAll("[^A-Za-z0-9_Α-Ωα-ωό:/‣-]"," ");
                    String tr[] = newString.split("/");

                    root.child(idstring).removeValue();

                    for(int i=0;i<=tr.length-1;i++){
                        root.child(idstring).child(String.valueOf(i)).setValue(tr[i]);

                    }


                    Toast.makeText(Email_Ac.this, "ΕΠΙΤΥΧΗΣ ΑΠΟΘΗΚΕΥΣΗ", Toast.LENGTH_SHORT).show();


                }catch (NullPointerException e){

                    if(idstring==null){
                        Toast.makeText(Email_Ac.this, "empty", Toast.LENGTH_SHORT).show();

                    }

                }





            }
        });


    }

    private boolean isConnected(){

        ConnectivityManager connectivityManager  = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();

    }



}
