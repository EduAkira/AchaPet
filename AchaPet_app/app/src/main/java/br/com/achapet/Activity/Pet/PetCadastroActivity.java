package br.com.achapet.Activity.Pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.achapet.Activity.Pet.slideImage.SlideUriAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;
import br.com.achapet.Util.ListPopupWindowDialogHelper;
import br.com.achapet.Util.PermissaoRecurso;

import static br.com.achapet.Activity.MainActivity.VALOR_TAB;
import static br.com.achapet.Modal.PetModal.COLLECTION_PET_ACHADO;
import static br.com.achapet.Modal.PetModal.COLLECTION_PET_PERDIDO;

public class PetCadastroActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private Toolbar toolbar;
    private ProgressBar toolbarProgress;
    private PermissaoRecurso permissaoRecurso;
    private SlideUriAdapter slideUriAdapter;
    private ViewPager cadastroViewPage;
    private TabLayout cadastroTablayout;
    private Button petCadastroButton;

    private Intent intent;

    private ImageButton petBarraRemoverFoto;
    private TextView petCadastroMensagemFoto;

    private EditText petComposPorteEditText;
    private EditText petComposSexoEditText;
    private EditText petComposTipoEditText;
    private EditText petComposCorEditText;
    private EditText petComposCor1EditText;
    private AutoCompleteTextView petCampoRacaEditText;

    private PetModal petModal;
    private List<String> fotos;
    private List<String> cors;
    private Uri imageFile;

    private FirebaseFirestore db;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_cadastro_activity);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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

        //slide
        cadastroViewPage = findViewById(R.id.slide_viewPage);
        cadastroTablayout = findViewById(R.id.slide_tablayout);

        //barra de foto
        petBarraRemoverFoto =  findViewById(R.id.pet_barra_remover_foto);
        petCadastroMensagemFoto = findViewById(R.id.pet_cadastro_mensagem_foto);
        petBarraRemoverFoto.setOnClickListener(this);
        findViewById(R.id.pet_barra_nova_foto).setOnClickListener(this);
        findViewById(R.id.pet_barra_nova_galeria).setOnClickListener(this);

        petComposPorteEditText = findViewById(R.id.pet_campos_porte);
        petComposSexoEditText = findViewById(R.id.pet_campos_sexo);
        petComposTipoEditText = findViewById(R.id.pet_campos_tipo);
        petCampoRacaEditText = findViewById(R.id.pet_campos_raca);
        petCampoRacaEditText.setOnFocusChangeListener(this);
        petComposCorEditText = findViewById(R.id.pet_campos_cor);
        petComposCor1EditText = findViewById(R.id.pet_campos_cor1);
        findViewById(R.id.pet_campos_nova_cor).setOnClickListener(this);

        petCadastroButton = findViewById(R.id.pet_cadastro_button);
        petCadastroButton.setOnClickListener(this);

        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposPorteEditText, R.menu.pet_porte_textfield, getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposSexoEditText, R.menu.pet_sexo_textfield, getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposTipoEditText, R.menu.pet_tipo_textfield, getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCorEditText, R.menu.pet_cor_textfield, getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper(petComposCor1EditText, R.menu.pet_cor_textfield, getApplicationContext());

        fotos = new ArrayList<>();
        cors = new ArrayList<>();


        firebaseStorage = FirebaseStorage.getInstance();

        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);
        petModal = new PetModal();
        slideUriAdapter = new SlideUriAdapter(getBaseContext(), fotos);
        criarSlide();
    }

    public void criarSlide(){
        if(!fotos.isEmpty()){
            cadastroViewPage.setVisibility(View.VISIBLE);
            cadastroTablayout.setVisibility(View.VISIBLE);
            petBarraRemoverFoto.setVisibility(View.VISIBLE);
            petCadastroMensagemFoto.setVisibility(View.GONE);
            toolbarProgress.setVisibility(View.GONE);

            cadastroTablayout.setupWithViewPager(cadastroViewPage, true);
            cadastroViewPage.setAdapter(slideUriAdapter);
            cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);

        }else{
            cadastroViewPage.setVisibility(View.GONE);
            cadastroTablayout.setVisibility(View.GONE);
            petCadastroMensagemFoto.setVisibility(View.VISIBLE);
            petBarraRemoverFoto.setVisibility(View.GONE);
            toolbarProgress.setVisibility(View.GONE);
        }
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

                ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, racas);
                petCampoRacaEditText.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pet_barra_nova_foto:
                permissaoRecurso.pedirPermissaoArquivo();
                permissaoRecurso.pedirPermissaoCamera();
                if(permissaoRecurso.possoUsarCamera() && permissaoRecurso.possoUsarArquvo())
                    usarCamera();
            break;
            case R.id.pet_barra_nova_galeria:
                permissaoRecurso.pedirPermissaoArquivo();
                permissaoRecurso.pedirPermissaoCamera();
                if(permissaoRecurso.possoUsarCamera() && permissaoRecurso.possoUsarArquvo())
                    usarGalleria();
            break;
            case R.id.pet_barra_remover_foto:
                fotos.remove(cadastroTablayout.getSelectedTabPosition()) ;
                criarSlide();
            break;
            case R.id.pet_barra_editar_foto:
                criarSlide();
            break;
            case R.id.pet_campos_nova_cor:
                findViewById(R.id.pet_campos_nova_cor).setVisibility(View.GONE);
                findViewById(R.id.pet_campos_cor1_input).setVisibility(View.VISIBLE);
            break;
            case R.id.pet_cadastro_button:
                validarCampos();
            break;
        }
    }

    private void validarCampos(){
        Boolean add = true;
        toolbarProgress.setVisibility(View.VISIBLE);
        petCadastroButton.setEnabled(false);

        if(intent.getIntExtra(VALOR_TAB, 0) == 1){
            petModal.setCollectionPet(COLLECTION_PET_ACHADO);
        }else{
            petModal.setCollectionPet(COLLECTION_PET_PERDIDO);
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

            petModal.setCor(cors);
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
        /*if(!fotos.isEmpty()){
            updateFoto();
        }else{
            add = false;
        }*/

        if(add){
            cadastrarPet();
        }else{
            toolbarProgress.setVisibility(View.GONE);
            petCadastroButton.setEnabled(true);
        }
    }

    private void updateFoto(){
        toolbarProgress.setVisibility(View.VISIBLE);
        for(final String foto:fotos){
            storageReference = firebaseStorage.getReference(petModal.pethFoto()+fotos.indexOf(foto));
            storageReference.putFile(Uri.parse(foto)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                        @Override
                        public void onSuccess(Uri uri) {
                            petModal.addFoto(uri.toString());
                            if(fotos.get(fotos.size()-1) == foto){
                                db.collection(petModal.getCollectionPet()).document(petModal.getId()).update("foto", petModal.getFoto());
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
        db.collection(petModal.getCollectionPet()).add(petModal).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                petModal.setId(documentReference.getId());
                updateFoto();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                petCadastroButton.setEnabled(true);
                toolbarProgress.setVisibility(View.GONE);
                //Log.w("TAG", "Error adding document", e);
            }
        });
    }

    private void usarGalleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagem"), 0);
    }

    private void usarCamera() {
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagem = new File(diretorio.getPath() + "/" + System.currentTimeMillis() + ".jpg");
        imageFile  = Uri.fromFile(imagem);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFile);
        startActivityForResult(takePictureIntent, 15);
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
                imageFile = data.getData();
            }
            fotos.add(String.valueOf(imageFile));
            criarSlide();
        }
    }
}
