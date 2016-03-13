package nz.common.creditcalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Николай on 12.03.2016.
 */
public class DialogsBuilder {
    public static void buildAlertTextDialog(final Context context, String title, String message, DialogInterface.OnClickListener okListener, EditText input)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(input == null) {
            input = new EditText(context);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setTitle(title);
        builder.setMessage(message);

        builder
                .setCancelable(false)
                .setPositiveButton(context.getString(android.R.string.ok),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // get user input and set it to result
//                                // edit text
//                                //result.setText(userInput.getText());
//                                Toast.makeText(context, "done", Toast.LENGTH_SHORT);
//                            }
//                        }
                        okListener)
                .setNegativeButton(context.getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }
}
