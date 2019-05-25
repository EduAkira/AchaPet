package br.com.achapet.Util;

import android.content.Context;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;

public final class ListPopupWindowDialogHelper {

    public static void setListPopupWindowDialogHelper(final EditText editTextipo, final int menu, final Context context) {


        editTextipo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextipo.callOnClick();
                }
            }
        });

        editTextipo.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideSoftKeyboard(mContext);
                int inType = editTextipo.getInputType(); // guarda informação do inpu type
                editTextipo.setInputType(InputType.TYPE_NULL); // desabilita o soft input
                editTextipo.onTouchEvent(event); // chama handler nativo
                editTextipo.setInputType(inType); //restaura o input type
                editTextipo.setCursorVisible(false);

                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editTextipo.getWindowToken(), 0);
                return true;
            }
        });

        editTextipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, editTextipo);

                popup.getMenuInflater().inflate(menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        editTextipo.setText(item.getTitle());
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }
}
