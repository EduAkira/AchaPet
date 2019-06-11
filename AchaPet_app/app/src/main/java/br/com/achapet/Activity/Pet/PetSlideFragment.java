package br.com.achapet.Activity.Pet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.achapet.Activity.Pet.slideImage.SlideUriAdapter;
import br.com.achapet.R;

public class PetSlideFragment extends Fragment implements View.OnClickListener {

    static final String EXTRA_FOTO = "PETMODAL";
    static final String EXTRA_ID = "ID";
    static final String EXTRA_COLLECTION = "COLLECTION";

    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_GALERIA = 2;

    private View viewFlame;
    private ViewPager cadastroViewPage;
    private TabLayout cadastroTablayout;

    private SlideUriAdapter slideUriAdapter;
    private Context context;

    private List<String> fotos;
    private Uri photoURI;

    private String documentId;
    private String collection;

    private FotoListener fotoListener;

    public interface FotoListener{
        void FotoListener(Uri foto);
        void RemoverListener(int pos);
    }

    public static PetSlideFragment newInstance(ArrayList<String> fotos, String collection, String id) {
        PetSlideFragment dialog = new PetSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FOTO, id);
        bundle.putString(EXTRA_COLLECTION, collection);
        bundle.putStringArrayList(EXTRA_FOTO, fotos);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static PetSlideFragment newInstance(String collection, String id) {
        PetSlideFragment dialog = new PetSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FOTO, id);
        bundle.putString(EXTRA_COLLECTION, collection);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static PetSlideFragment newInstance(ArrayList<String> fotos) {
        PetSlideFragment dialog = new PetSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA_FOTO, fotos);
        dialog.setArguments(bundle);
        return dialog;
    }


    public PetSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFlame = inflater.inflate(R.layout.pet_slide_fragment, container, false);

        final Bundle bundle = getArguments();

        collection = bundle.getString(EXTRA_COLLECTION);
        documentId = bundle.getString(EXTRA_ID);
        if(bundle.getStringArrayList(EXTRA_FOTO) != null){
            fotos = bundle.getStringArrayList(EXTRA_FOTO);
        }else{
            fotos = new ArrayList<>();
        }

        viewFlame.findViewById(R.id.pet_barra_remover_foto).setOnClickListener(this);
        viewFlame.findViewById(R.id.pet_barra_nova_foto).setOnClickListener(this);
        viewFlame.findViewById(R.id.pet_barra_nova_galeria).setOnClickListener(this);

        cadastroViewPage = viewFlame.findViewById(R.id.slide_viewPage);
        cadastroTablayout = viewFlame.findViewById(R.id.slide_tablayout);

        slideUriAdapter = new SlideUriAdapter(context, fotos);
        cadastroTablayout.setupWithViewPager(cadastroViewPage, true);
        cadastroViewPage.setAdapter(slideUriAdapter);
        //cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);

        return viewFlame;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.pet_barra_remover_foto:
                if(!fotos.isEmpty()){
                    int index = cadastroTablayout.getSelectedTabPosition();
                    fotos.remove(index);
                    slideUriAdapter.notifyDataSetChanged();
                    cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);

                    fotoListener.RemoverListener(index);
                }
                break;
            case R.id.pet_barra_nova_foto:
                camera();
                break;
            case R.id.pet_barra_nova_galeria:
                galeria();
                break;
        }
    }

    private void galeria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagem"), REQUEST_GALERIA);
    }

    private void camera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(context, "br.com.achapet", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                fotos.add(String.valueOf(photoURI));
                fotoListener.FotoListener(photoURI);
            }else if(requestCode == REQUEST_GALERIA){
                fotos.add(String.valueOf(data.getData()));
                fotoListener.FotoListener(data.getData());
            }

            slideUriAdapter.notifyDataSetChanged();
            if(!fotos.isEmpty())
                cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        fotoListener = (FotoListener) context;
    }
}
