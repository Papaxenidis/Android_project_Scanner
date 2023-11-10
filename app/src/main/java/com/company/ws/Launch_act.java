package com.company.ws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Launch_act extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String text = "";
    Button buttoneis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        buttoneis = findViewById(R.id.butteisodos);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.number,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        buttoneis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.isEmpty()){

                    AlertDialog alertDialog = new AlertDialog.Builder(Launch_act.this).create();
                    alertDialog.setTitle("ΣΗΜΑΝΤΙΚΟ");
                    alertDialog.setMessage("ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΣΥΣΚΕΥΗ ΧΡΗΣΗΣ.");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    alertDialog.show();

                }else{
                    Intent intent = new Intent(Launch_act.this,MainActivity.class);
                    intent.putExtra("identifier",text);
                    startActivity(intent);
                }
            }






        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}