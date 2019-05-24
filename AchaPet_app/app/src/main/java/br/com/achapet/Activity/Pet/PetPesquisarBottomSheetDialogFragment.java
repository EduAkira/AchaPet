package br.com.achapet.Activity.Pet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import br.com.achapet.R;

public class PetPesquisarBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pet_fragment_pesquisa, container, false);
        mContext = view.getContext();
        return view;
    }

}
