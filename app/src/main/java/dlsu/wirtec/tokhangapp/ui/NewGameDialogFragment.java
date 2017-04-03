package dlsu.wirtec.tokhangapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import dlsu.wirtec.tokhangapp.R;

/**
 * Created by lyssa on 03/04/2017.
 */

public class NewGameDialogFragment extends DialogFragment {

    public static final String DIALOG_TAG_OVERWRITE_SAVE = "DIALOG_TAG_OVERWRITE_SAVE";
    private DialogInterface.OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Resources r = getActivity().getResources();
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(r.getString(R.string.yes), listener)
                .setNegativeButton(r.getString(R.string.no), listener)
                .setTitle("Do you wish to overwrite?")
                .setMessage("An existing save game exists. Do you wish to overwrite the existing save?")
                .setCancelable(true)
                .create();
    }

    public DialogInterface.OnClickListener getDialogListener() {
        return this.listener;
    }

    public void setDialogListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }
}