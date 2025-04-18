package com.example.fourthhandsonexercise;

import android.content.DialogInterface;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.widget.*;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPrelim, etMidterm, etFinal;
    Button btnComput, btnNew;
    TextView tvStudentName, tvSemGrade, tvEquiv, tvRemark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etPrelim = findViewById(R.id.etPrelim);
        etMidterm = findViewById(R.id.etMidterm);
        etFinal = findViewById(R.id.etFinal);
        btnComput = findViewById(R.id.btnCompute);
        btnNew = findViewById(R.id.btnNew);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvSemGrade = findViewById(R.id.tvSemGrade);
        tvEquiv = findViewById(R.id.tvEquiv);
        tvRemark = findViewById(R.id.tvRemark);

        etName.requestFocus();


        showResult();

    }
    public void showResult() {
        btnComput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning Message");
                builder.setMessage("Are all entries correct?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!checkError()) {
                            String remark = "";
                            double equiv;
                            String name = etName.getText().toString();
                            String prelim = etPrelim.getText().toString();
                            String midterm = etMidterm.getText().toString();
                            String finalGrade = etFinal.getText().toString();
                            double prelimGrade = Double.parseDouble(prelim);
                            double midtermGrade = Double.parseDouble(midterm);
                            double finalGradeGrade = Double.parseDouble(finalGrade);
                            double semGrade = (prelimGrade + midtermGrade + finalGradeGrade) / 3;
                            tvStudentName.setText(name);
                            tvSemGrade.setText(String.valueOf(semGrade));
                            if (semGrade == 100) {
                                equiv = 1.00;
                                remark = "PASSED";
                            } else if (semGrade >= 95 && semGrade <= 99) {
                                equiv = 1.50;
                                remark = "PASSED";
                            } else if (semGrade >= 90 && semGrade <= 94) {
                                equiv = 2.00;
                                remark = "PASSED";
                            } else if (semGrade >= 85 && semGrade <= 89) {
                                equiv = 2.50;
                                remark = "PASSED";
                            } else if (semGrade >= 80 && semGrade <= 84) {
                                equiv = 3.00;
                                remark = "PASSED";
                            } else if (semGrade >= 75 && semGrade <= 79) {
                                equiv = 3.50;
                                remark = "PASSED";
                            } else {
                                equiv = 5.00;
                                remark = "FAILED";
                            }

                            if (remark.equals("PASSED")) {
                                tvRemark.setTextColor(Color.GREEN);
                            } else {
                                tvRemark.setTextColor(Color.RED);
                            }
                            tvEquiv.setText(String.valueOf(equiv));
                            tvRemark.setText(remark);
                            etName.requestFocus();
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Action on No
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning Message");
                builder.setMessage("Are you sure?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        etName.setText("");
                        etPrelim.setText("");
                        etMidterm.setText("");
                        etFinal.setText("");
                        tvStudentName.setText("");
                        tvSemGrade.setText("");
                        tvEquiv.setText("");
                        tvRemark.setText("");
                        etName.requestFocus();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Action on No
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    };

    public boolean checkError(){
        EditText[] etFields = new EditText[] {etName, etPrelim, etMidterm, etFinal};
        EditText[] etNumericFields = new EditText[] {etPrelim, etMidterm, etFinal};
        boolean emptyError = false;
        boolean alphaError = false;
        boolean numericError = false;

        for(EditText etField : etFields){
            if(etField.getText().toString().trim().isEmpty()){
                etField.requestFocus();
                etField.setError("Field Required");
                emptyError = true;
            }
        }

        if(etName.getText().toString().trim().matches("[^a-zA-Z\\s]+")){
            etName.requestFocus();
            etName.setError("Letters and Spaces only");
            alphaError = true;
        }

        for(EditText etNumericField : etNumericFields){
            String fieldInput = etNumericField.getText().toString();
            if(!fieldInput.isEmpty()){
                try{
                    Double.parseDouble(fieldInput);
                } catch (NumberFormatException e) {
                    etNumericField.requestFocus();
                    etNumericField.setError("Enter a valid number");
                    numericError = true;
                }
            }
        }

        return emptyError || alphaError || numericError;
    }
}