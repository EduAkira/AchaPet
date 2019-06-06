package br.com.achapet.Activity.Pet;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class PetCadastroFragment extends Fragment {

    private PetModal petModal;

    private PetCadastroFragmentListener petCadastroFragmentListener;


    public interface PetCadastroFragmentListener {
        void DadosFragmentPetCadastroListener(PetModal petModal);
    }

    public PetCadastroFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pet_fragment_cadastro, container, false);
    }



}
