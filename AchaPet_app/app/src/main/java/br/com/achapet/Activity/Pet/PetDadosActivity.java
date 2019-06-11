package br.com.achapet.Activity.Pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

import static br.com.achapet.Activity.MainActivity.VALOR_TAB;
import static br.com.achapet.Activity.Pet.PetDadosFragment.ADICIONAR_TAG;
import static br.com.achapet.Modal.PetModal.COLLECTION_PET_ACHADO;
import static br.com.achapet.Modal.PetModal.COLLECTION_PET_PERDIDO;

public class PetDadosActivity extends AppCompatActivity implements PetSlideFragment.FotoListener, PetDadosFragment.PetDadosListener{

    private Toolbar toolbar;
    private ProgressBar toolbarProgress;

    private PetDadosFragment petDadosFragment;
    private PetSlideFragment petSlideFragment;
    private FragmentManager fragmentManager;

    private FirebaseFirestore firebaseFirestore;
    private DocumentReference petDocumentReference;
    //private DocumentReference pessoaDocumentReference;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String collection;
    private String documentId;

    private List<String> fotos;
    private PetModal petModal;
    private PessoaModal pessoaModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_dados_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarProgress = findViewById(R.id.toolbarprogress);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if(intent.getIntExtra(VALOR_TAB, 0) == 1){
            collection = COLLECTION_PET_ACHADO;
        }else{
            collection = COLLECTION_PET_PERDIDO;
        }

        petModal = new PetModal();
        pessoaModal = new PessoaModal();
        fotos = new ArrayList<>();

        createRef();
        showFrag();
        showSlide();
    }

    private void showSlide() {
        petSlideFragment = PetSlideFragment.newInstance(collection,documentId);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_slide, petSlideFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showFrag(){
        petDadosFragment = PetDadosFragment.newInstance(ADICIONAR_TAG);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_dados, petDadosFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void createRef() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        petDocumentReference = firebaseFirestore.collection(collection).document();
        documentId = petDocumentReference.getId();

        //pessoaDocumentReference = firebaseFirestore.collection("pessoas").document(pessoaModal.getUid());

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        storageReference = storageReference.child(collection+"/"+documentId+"/");
    }

    private void updateImg(Uri file){
        final StorageReference imgStorageReference = storageReference.child(file.getLastPathSegment());
        UploadTask uploadTask = imgStorageReference.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                imgStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                    @Override
                    public void onSuccess(Uri uri) {
                        fotos.add(uri.toString());
                    }
                });
            }
        });
    }

    private void registrar() {
        petDocumentReference.set(petModal);
        //pessoaDocumentReference.set(pessoaModal);
    }

    @Override
    public void FotoListener(Uri foto) {
        updateImg(foto);
    }

    @Override
    public void RemoverListener(int pos) {
        if(fotos.size() > pos){
            fotos.remove(pos);
        }
    }

    @Override
    public void PetDadosListener(PetModal petModal) {
        this.petModal = petModal;
        this.petModal.setFotos(fotos);

        pessoaModal.addPetRegistro(documentId);

        registrar();
        finish();
    }
}
