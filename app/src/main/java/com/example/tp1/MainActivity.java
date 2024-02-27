package com.example.tp1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameED = findViewById(R.id.editTextName);
        EditText surnameED = findViewById(R.id.editTextSurname);
        EditText ageED = findViewById(R.id.editTextAge);
        Spinner skillED = findViewById(R.id.spinnerSkillDomain);
        EditText numED = findViewById(R.id.editTextPhoneNumber);

        btn1 = (Button) findViewById(R.id.button_send);

        btn1.setOnClickListener(view -> {
            String nameText = nameED.getText().toString();
            String surnameText = surnameED.getText().toString();
            String ageText = ageED.getText().toString();
            String skillText = skillED.getSelectedItem().toString();
            String numText = numED.getText().toString();

            if(checkEmptyText(nameText) && checkEmptyText(surnameText) && checkEmptyText(ageText) && checkEmptySpinner(skillED, skillText) && checkEmptyText(numText)) {
                showAlterDialogInterract("Are you sure ?", getResources().getString(R.string.text_confirmation), getResources().getString(R.string.text_confirmation_yes), getResources().getString(R.string.text_confirmation_no));
            } else {
                showAlterDialog(getResources().getString(R.string.text_missing_item), getResources().getString(R.string.text_missing_item));
            }
        });
    }

    private boolean checkEmptyText(String text) {
        if(text.isEmpty()) {
            showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }

    private boolean checkEmptySpinner(Spinner spinner, String text) {
        if(text.equals(spinner.getAdapter().getItem(0).toString())) {
            showDialog(R.string.text_missing_item);
            return false;
        }
        return true;
    }

    private void showAlterDialogInterract(String title, String message, String msg_yes, String msg_no) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(msg_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Nice Job", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(msg_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void showAlterDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}