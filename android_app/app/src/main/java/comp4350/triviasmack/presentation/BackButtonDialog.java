package comp4350.triviasmack.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

public class BackButtonDialog {
    public static void buildExitDialog(final Exitable activity){
        AlertDialog.Builder builder;

        if (activity instanceof AppCompatActivity) {
            builder = new AlertDialog.Builder((AppCompatActivity) activity);
        }
        else {
            throw new IllegalArgumentException("Expected activity to be AppCompatActivity");
        }

        builder.setTitle("Exit game?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.exitAction();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
