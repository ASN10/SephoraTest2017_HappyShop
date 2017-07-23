package com.sephora.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.sephora.app.Extras;
import com.sephora.app.R;

/**
 * Created by nivedhitha.a on 27-Apr-16.
 */
public class SimpleDialogFragment extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.alert_title));
        builder.setMessage(getArguments().getString(Extras.EXTRAS_ALERT_MESSAGE));

        builder.setPositiveButton(getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });



        return builder.create();

    }




}
