package com.example.android.themusicgenreator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A quick custom class to allow me to create the search_db dialog across all activities without
 * duplicating a lot of code.
 */

class SearchDialogCreator {

    static Dialog createSearchDialog(Context context1, int titleBarColorResId) {
        final Context context = context1;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.search_db_dialog);
        //Setting the colour of the dialog title
        String str = context.getResources().getString(R.string.search_db);
        SpannableString dialogTitle = new SpannableString(str);
        dialogTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,
                R.color.browse_buttons_text_color)), 0, str.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dialog.setTitle(dialogTitle);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource
                    (titleBarColorResId);

        }
        //Second use an EditText to allow the user to search the DB
        EditText searcher = (EditText) dialog.findViewById(R.id.search_db);
        searcher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent i = new Intent(context, SearchResultsActivity.class);
                    i.putExtra("SEARCH_TERM", v.getText().toString());
                    Log.d("SEARCH_TERM", v.getText().toString());
                    v.setText("");
                    context.startActivity(i);
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }
}
