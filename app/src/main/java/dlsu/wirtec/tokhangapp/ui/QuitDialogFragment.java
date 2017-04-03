package dlsu.wirtec.tokhangapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.activities.MainActivity;

/**
 * Created by lyssa on 11/03/2017.
 */

public class QuitDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_quit_title)
                .setMessage(R.string.dialog_quit_message)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_quit_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.dialog_quit_negative, null)
                .setNeutralButton(R.string.dialog_quit_neutral, null)
                .create();
    }
}
