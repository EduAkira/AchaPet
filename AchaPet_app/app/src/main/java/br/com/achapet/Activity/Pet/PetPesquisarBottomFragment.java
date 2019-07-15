package br.com.achapet.Activity.Pet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.com.achapet.R;
import br.com.achapet.Util.ListPopupWindowDialogHelper;

public class PetPesquisarBottomFragment extends BottomSheetDialogFragment implements View.OnFocusChangeListener, View.OnClickListener {

    private Context mContext;

    private EditText petComposPorteEditText;
    private EditText petComposSexoEditText;
    private EditText petComposTipoEditText;
    private EditText petComposCorEditText;
    private EditText petComposCor1EditText;
    private AutoCompleteTextView petCampoRacaEditText;
    private View viewLayout;

    private String[] racas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.pet_fragment_pesquisa, container, false);
        mContext = viewLayout.getContext();

        petComposPorteEditText = viewLayout.findViewById(R.id.pet_campos_porte);
        petComposSexoEditText = viewLayout.findViewById(R.id.pet_campos_sexo);
        petComposTipoEditText = viewLayout.findViewById(R.id.pet_campos_tipo);
        petCampoRacaEditText = viewLayout.findViewById(R.id.pet_campos_raca);

        petComposCorEditText = viewLayout.findViewById(R.id.pet_campos_cor);
        petComposCor1EditText = viewLayout.findViewById(R.id.pet_campos_cor1);

        viewLayout.findViewById(R.id.pet_campos_nova_cor).setOnClickListener(this);

        //ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposPorteEditText, R.menu.pet_porte_textfield, mContext);
        //ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposSexoEditText, R.menu.pet_sexo_textfield, mContext);
        //ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposTipoEditText, R.menu.pet_tipo_textfield, mContext);
        //ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCorEditText, R.menu.pet_cor_textfield, mContext);
        //ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCor1EditText, R.menu.pet_cor_textfield, mContext);

        petCampoRacaEditText.setOnFocusChangeListener(this);
        return viewLayout;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.pet_campos_raca:
                Log.e("NOME: ", petComposTipoEditText.getText().toString());
                if(petComposTipoEditText.getText().toString().equals("Cachorro"))
                    racas = getResources().getStringArray(R.array.cachorros);
                else if(petComposTipoEditText.getText().toString().equals("Gato"))
                    racas = getResources().getStringArray(R.array.gatos);
                else
                    racas = getResources().getStringArray(R.array.passaros);

                ArrayAdapter<String> adapter = new ArrayAdapter(mContext, android.R.layout.simple_dropdown_item_1line, racas);
                petCampoRacaEditText.setAdapter(adapter);
            break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pet_campos_nova_cor:
                viewLayout.findViewById(R.id.pet_campos_nova_cor).setVisibility(View.GONE);
                viewLayout.findViewById(R.id.pet_campos_cor1_input).setVisibility(View.VISIBLE);
            break;
        }
    }

}
