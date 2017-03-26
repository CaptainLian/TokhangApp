package dlsu.wirtec.tokhangapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;

/**
 * Created by lyssa on 21/03/2017.
 */

public class ShopDialogFragment extends DialogFragment {

    public static final String ARGUMENT_GUN_NAME = "GUN_NAME";
    public static final String ARGUMENT_GUN_COST = "GUN_COST";
    public static final String ARGUMENT_GUN_DESCRIPTION = "GUN_DESCRIPTION";


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
                .setPositiveButton(R.string.shop_purchase_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SoundManager soundManager = GameManager.getSoundManager();
                        soundManager.playSound(soundManager.SOUND_SHOP_PURCHASE1);
                    }
                })
                .setNeutralButton(R.string.shop_purchase_neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setNegativeButton(R.string.shop_purchase_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .create();
    }
}
