package br.com.achapet.Activity.Pet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.com.achapet.R;
import br.com.achapet.Util.ListPopupWindowDialogHelper;

public class PetPesquisarBottomFragment extends BottomSheetDialogFragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pet_fragment_pesquisa, container, false);
        mContext = view.getContext();

        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) view.findViewById(R.id.pet_campos_porte), R.menu.pet_porte_textfield, mContext);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) view.findViewById(R.id.pet_campos_sexo),R.menu.pet_sexo_textfield,mContext);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) view.findViewById(R.id.pet_campos_tipo),R.menu.pet_tipo_textfield,mContext);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) view.findViewById(R.id.pet_campos_cor),R.menu.pet_porte_textfield,mContext);

        AutoCompleteTextView petCampoRaca = view.findViewById(R.id.pet_campos_raca);
        String[] countries = getResources().getStringArray(R.array.cachorro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, countries);
        petCampoRaca.setAdapter(adapter);

        return view;
    }
}
