package com.example.tp1.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Context;

import com.example.tp1.ProcessActivity;
import com.example.tp1.R;

import java.util.ArrayList;

public class Dialog {
    public static void showInterractDialog(Button button, Context context, String title, String message, String msg_yes, String msg_no, String msg_popup, String name, String surname, String age, String skDomain, String phNum) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(msg_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ProcessActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("surname", surname);
                        intent.putExtra("age", age);
                        intent.putExtra("skillDomain", skDomain);
                        intent.putExtra("phoneNumber", phNum);
                        context.startActivity(intent);

                        /*Toast.makeText(context, msg_popup, Toast.LENGTH_SHORT).show();
                        button.setBackgroundColor(Color.GREEN);
                         */
                    }
                })
                .setNegativeButton(msg_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        button.setBackgroundColor(Color.RED);
                        //dialog.dismiss();
                    }
                })
                .show();

    }

    public static void showErrorDialog(Context context, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
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
