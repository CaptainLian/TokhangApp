package dlsu.wirtec.tokhangapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;

/**
 * Created by lyssa on 21/03/2017.
 */

public class ShopDialogFragment extends DialogFragment {

    public static final String ARGUMENT_GUN_NAME = "GUN_NAME";
    public static final String ARGUMENT_GUN_COST = "GUN_COST";
    public static final String ARGUMENT_GUN_DESCRIPTION = "GUN_DESCRIPTION";

    public static final String DIALOG_TAG_SHOP = "DIALOG_TAG_SHOP";

    private DialogInterface.OnClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();

        String gunName = b.getString(ARGUMENT_GUN_NAME, "");
        String gunDescription = b.getString(ARGUMENT_GUN_DESCRIPTION, "");
        int gunCost = b.getInt(ARGUMENT_GUN_COST, 0);

        Resources r = getResources();
        String purchase = r.getString(R.string.shop_purchase)+ " ";
        String cost = r.getString(R.string.shop_cost);
        return new AlertDialog.Builder(getContext())
                .setTitle(purchase + gunName + "?")
                .setMessage(gunDescription + "\n\n" + cost + ": " + gunCost)
                .setPositiveButton(R.string.shop_purchase_positive, listener)
                .setNegativeButton(R.string.no, listener)
                .create();
    }

    public DialogInterface.OnClickListener getDialogListener() {
        return this.listener;
    }

    public void setDialogListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

}
