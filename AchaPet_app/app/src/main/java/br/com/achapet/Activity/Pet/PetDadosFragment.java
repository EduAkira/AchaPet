package br.com.achapet.Activity.Pet;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;
import br.com.achapet.Util.ListPopupWindowDialogHelper;

public class PetDadosFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    static final String EXTRA_TITULO = "TITULO";
    static final String EXTRA_PETMODAL = "PETMODAL";
    static final String EDITAR_TAG = "EDITAR";
    static final String ADICIONAR_TAG = "ADICIONAR";

    private View viewFlame;

    private EditText petComposPorteEditText;
    private EditText petComposSexoEditText;
    private EditText petComposTipoEditText;
    private EditText petComposCorEditText;
    private EditText petComposCor1EditText;
    private EditText petComposComentarioEditText;
    private AutoCompleteTextView petCampoRacaEditText;


    private PetDadosListener petDadosListener;

    private Context context;
    private PetModal petModal;

    public PetDadosFragment() {

    }

    public static PetDadosFragment newInstance(String titulo, PetModal petModal) {
        PetDadosFragment dialog = new PetDadosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITULO, titulo);
        bundle.putSerializable(EXTRA_PETMODAL, petModal);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static PetDadosFragment newInstance(String title) {
        return newInstance(title,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFlame = inflater.inflate(R.layout.pet_dados_fragment, container, false);
        final Bundle bundle = getArguments();

        if(bundle.getString(EXTRA_TITULO).equals(EDITAR_TAG)){
            petModal = (PetModal) bundle.getSerializable(EXTRA_PETMODAL);
        }else{
            petModal = new PetModal();
        }

        Button button = viewFlame.findViewById(R.id.pet_fragment_button);
        button.setText(bundle.getString(EXTRA_TITULO));
        button.setOnClickListener(this);

        petComposPorteEditText = viewFlame.findViewById(R.id.pet_campos_porte);
        petComposSexoEditText = viewFlame.findViewById(R.id.pet_campos_sexo);
        petComposTipoEditText = viewFlame.findViewById(R.id.pet_campos_tipo);
        petCampoRacaEditText = viewFlame.findViewById(R.id.pet_campos_raca);
        petComposCorEditText = viewFlame.findViewById(R.id.pet_campos_cor);
        petComposCor1EditText = viewFlame.findViewById(R.id.pet_campos_cor1);
        petComposComentarioEditText = viewFlame.findViewById(R.id.pet_campos_comentario);

        petCampoRacaEditText.setOnFocusChangeListener(this);

        viewFlame.findViewById(R.id.pet_campos_nova_cor).setOnClickListener(this);
        viewFlame.findViewById(R.id.pet_fragment_button).setOnClickListener(this);

        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposPorteEditText, R.menu.pet_porte_textfield, context);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposSexoEditText, R.menu.pet_sexo_textfield, context);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposTipoEditText, R.menu.pet_tipo_textfield, context);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCorEditText, R.menu.pet_cor_textfield, context);
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCor1EditText, R.menu.pet_cor_textfield, context);

        return viewFlame;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String[] racas;
        switch (v.getId()) {
            case R.id.pet_campos_raca:
                if(petComposTipoEditText.getText().toString().equals("Cachorro"))
                    racas = getResources().getStringArray(R.array.cachorros);
                else if(petComposTipoEditText.getText().toString().equals("Gato"))
                    racas = getResources().getStringArray(R.array.gatos);
                else
                    racas = getResources().getStringArray(R.array.passaros);

                ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, racas);
                petCampoRacaEditText.setAdapter(adapter);
                break;
        }
    }

    private void concluir(){
        petModal.setTipo(petComposTipoEditText.getText().toString());
        petModal.setRaca(petCampoRacaEditText.getText().toString());
        petModal.setPorte(petComposPorteEditText.getText().toString());
        petModal.setSexo(petComposSexoEditText.getText().toString());
        petModal.addCor(petComposCorEditText.getText().toString());
        if(!petComposCor1EditText.getText().toString().isEmpty())
            petModal.addCor(petComposCor1EditText.getText().toString());
        petModal.setComentario(petComposComentarioEditText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pet_fragment_button:
                concluir();
                petDadosListener.PetDadosListener(petModal);
                break;
            case R.id.pet_campos_nova_cor:
                viewFlame.findViewById(R.id.pet_campos_nova_cor).setVisibility(View.GONE);
                viewFlame.findViewById(R.id.pet_campos_cor1_input).setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface PetDadosListener {
        void PetDadosListener(PetModal petModal);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        petDadosListener = (PetDadosListener) context;
    }
}
