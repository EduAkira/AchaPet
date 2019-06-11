package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;
import br.com.achapet.Util.PermissaoRecurso;

public class PetCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ProgressBar toolbarProgress;

    private PermissaoRecurso permissaoRecurso;

    private Button petCadastroButton;

    private Intent intent;

    private ImageButton petBarraRemoverFoto;
    private TextView petCadastroMensagemFoto;

    private PetModal petModal;
    private List<String> fotos;
    private List<String> cors;
    private Uri imageFile;

    private FirebaseFirestore db;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String collection;
    private String documentId;
    private String pethFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_cadastro_activity);

        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());

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

        intent = getIntent();

        fotos = new ArrayList<>();
        cors = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();

        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);
        petModal = new PetModal();
        //criarSlide();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.pet_barra_nova_foto:
                permissaoRecurso.pedirPermissaoArquivo();
                permissaoRecurso.pedirPermissaoCamera();
                if(permissaoRecurso.possoUsarCamera() && permissaoRecurso.possoUsarArquvo())
                    usarCamera();
            break;*/
            /*case R.id.pet_barra_nova_galeria:
                permissaoRecurso.pedirPermissaoArquivo();
                permissaoRecurso.pedirPermissaoCamera();
                if(permissaoRecurso.possoUsarCamera() && permissaoRecurso.possoUsarArquvo())
                    usarGalleria();
            break;*/
            /*case R.id.pet_campos_nova_cor:
                findViewById(R.id.pet_campos_nova_cor).setVisibility(View.GONE);
                findViewById(R.id.pet_campos_cor1_input).setVisibility(View.VISIBLE);
            break;*/
            case R.id.pet_cadastro_button:
                //validarCampos();
            break;
        }
    }

    /*private void validarCampos(){
        Boolean add = true;
        toolbarProgress.setVisibility(View.VISIBLE);
        petCadastroButton.setEnabled(false);

        if(intent.getIntExtra(VALOR_TAB, 0) == 1){
            collection = COLLECTION_PET_ACHADO;
        }else{
            collection = COLLECTION_PET_PERDIDO;
        }

        if(!petComposPorteEditText.getText().toString().isEmpty()){
            petModal.setPorte(petComposPorteEditText.getText().toString());
        }else{
            petComposPorteEditText.setError("campo obrigatorio");
            add = false;
        }
        if(!petComposSexoEditText.getText().toString().isEmpty()){
            petModal.setSexo(petComposSexoEditText.getText().toString());
        }else{
            petComposSexoEditText.setError("campo obrigatorio");
            add = false;
        }
        if(!petComposTipoEditText.getText().toString().isEmpty()){
            petModal.setTipo(petComposTipoEditText.getText().toString());
        }else{
            petComposTipoEditText.setError("campo obrigatorio");
            add = false;
        }
        if(!petComposCorEditText.getText().toString().isEmpty()){
            cors.add(petComposCorEditText.getText().toString());
            if(!petComposCor1EditText.getText().toString().isEmpty())
                cors.add(petComposCor1EditText.getText().toString());

            petModal.setCores(cors);
        }else{
            petComposCorEditText.setError("campo obrigatorio");
            petComposCor1EditText.setError("campo obrigatorio");
            add = false;
        }
        if(!petCampoRacaEditText.getText().toString().isEmpty()){
            petModal.setRaca(petCampoRacaEditText.getText().toString());
        }else{
            petCampoRacaEditText.setError("campo obrigatorio");
            add = false;
        }
        if(!fotos.isEmpty()){
            updateFoto();
        }else{
            add = false;
        }

        if(add){
            cadastrarPet();
        }else{
            toolbarProgress.setVisibility(View.GONE);
            petCadastroButton.setEnabled(true);
        }
    }*/

    private void updateFoto(){
        toolbarProgress.setVisibility(View.VISIBLE);
        pethFoto = collection + "/" + documentId + "/";
        for(final String foto:fotos){
            storageReference = firebaseStorage.getReference(pethFoto + fotos.indexOf(foto));
            storageReference.putFile(Uri.parse(foto)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                        @Override
                        public void onSuccess(Uri uri) {
                            petModal.addFoto(uri.toString());
                            if(fotos.get(fotos.size()-1) == foto){
                                db.collection(collection).document(documentId).update("foto", petModal.getFotos());
                                finish();
                            }
                        }
                    });
                }
            });
        }

    }

    private void cadastrarPet(){
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection(collection).document();
        documentId = ref.getId();
        ref.set(petModal);

        /*db.collection(collection).add(petModal).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                updateFoto();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                petCadastroButton.setEnabled(true);
                toolbarProgress.setVisibility(View.GONE);
                //Log.w("TAG", "Error adding document", e);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == 15) {
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFile);
                sendBroadcast(novaIntent);
            }
            if (requestCode == 0) {

            }
            fotos.add(String.valueOf(imageFile));
            //criarSlide();
        }
    }
}
