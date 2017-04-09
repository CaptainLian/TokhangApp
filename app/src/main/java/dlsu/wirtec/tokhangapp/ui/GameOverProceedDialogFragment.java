package dlsu.wirtec.tokhangapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import dlsu.wirtec.tokhangapp.R;

/**
 * Created by lyssa on 09/04/2017.
 */

public class GameOverProceedDialogFragment extends DialogFragment {

    private DialogInterface.OnClickListener clickListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(R.string.dialog_proceed_game_over)
                .setPositiveButton(R.string.yes, clickListener)
                .setNegativeButton(R.string.no, clickListener)
                .create();
    }

    public void setClickListener(DialogInterface.OnClickListener clickListener){
        this.clickListener = clickListener;
    }
}
