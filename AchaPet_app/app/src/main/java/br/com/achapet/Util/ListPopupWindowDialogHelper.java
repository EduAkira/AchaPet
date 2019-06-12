package br.com.achapet.Util;

import android.content.Context;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.PopupMenu;

import br.com.achapet.R;

public final class ListPopupWindowDialogHelper {

    public static void setListPopupWindowDialogHelper(final AutoCompleteTextView autoCompleteTextView, final String[] value, final Context context) {


        autoCompleteTextView.setAdapter(new ArrayAdapter(context,android.R.layout.simple_dropdown_item_1line, value));

        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideSoftKeyboard(mContext);
                int inType = autoCompleteTextView.getInputType(); // guarda informação do inpu type
                autoCompleteTextView.setInputType(InputType.TYPE_NULL); // desabilita o soft input
                autoCompleteTextView.onTouchEvent(event); // chama handler nativo
                autoCompleteTextView.setInputType(inType); //restaura o input type
                autoCompleteTextView.setCursorVisible(false);
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                autoCompleteTextView.showDropDown();
                return true;
            }
        });

    }
}
