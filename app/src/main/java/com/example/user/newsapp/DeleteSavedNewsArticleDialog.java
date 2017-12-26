package com.example.user.newsapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import io.realm.Realm;

/**
 * Created by zahrsino on 22/12/17.
 */

public class DeleteSavedNewsArticleDialog extends DialogFragment{
    Realm realm;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove favorite news").setMessage("Are you sure to remove this news from favorites?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((ArticlesDetailsActivity)getActivity()).removeFavoriteArticleOnDB();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
