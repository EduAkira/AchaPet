package br.com.achapet.Activity.Pet;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

import br.com.achapet.Activity.Pet.slideImage.SlideUriAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;
import br.com.achapet.Util.ListPopupWindowDialogHelper;
import br.com.achapet.Util.PermissaoRecurso;

public class PetCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private PermissaoRecurso permissaoRecurso;
    private SlideUriAdapter slideUriAdapter;
    private ViewPager cadastroViewPage;
    private TabLayout cadastroTablayout;
    private PetModal petModal;
    private Uri imageFileUri;

    //private ImageButton petBarraEditarFoto;
    private ImageButton petBarraRemoverFoto;
    private TextView petCadastroMensagemFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_cadastro_activity);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //slide
        cadastroViewPage = findViewById(R.id.slide_viewPage);
        cadastroTablayout = findViewById(R.id.slide_tablayout);

        //barra de foto
        petBarraRemoverFoto =  findViewById(R.id.pet_barra_remover_foto);
        petCadastroMensagemFoto = findViewById(R.id.pet_cadastro_mensagem_foto);
        findViewById(R.id.pet_barra_nova_foto).setOnClickListener(this);
        findViewById(R.id.pet_barra_nova_galeria).setOnClickListener(this);
        petBarraRemoverFoto.setOnClickListener(this);


        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) findViewById(R.id.pet_campos_porte), R.menu.pet_porte_textfield,getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) findViewById(R.id.pet_campos_sexo),R.menu.pet_sexo_textfield,getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) findViewById(R.id.pet_campos_tipo),R.menu.pet_tipo_textfield,getApplicationContext());
        ListPopupWindowDialogHelper.setListPopupWindowDialogHelper((EditText) findViewById(R.id.pet_campos_cor),R.menu.pet_porte_textfield,getApplicationContext());

        AutoCompleteTextView petCampoRaca = findViewById(R.id.pet_campos_raca);
        String[] countries = getResources().getStringArray(R.array.cachorro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, countries);
        petCampoRaca.setAdapter(adapter);


        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);
        petModal = new PetModal("","","");
        slideUriAdapter = new SlideUriAdapter(getBaseContext(), petModal.getFoto());
        criarSlide();
    }

    public void criarSlide(){
        if(!petModal.getFoto().isEmpty()){
            cadastroViewPage.setVisibility(View.VISIBLE);
            cadastroTablayout.setVisibility(View.VISIBLE);
            petBarraRemoverFoto.setVisibility(View.VISIBLE);
            petCadastroMensagemFoto.setVisibility(View.GONE);

            cadastroTablayout.setupWithViewPager(cadastroViewPage, true);
            cadastroViewPage.setAdapter(slideUriAdapter);
            cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);

        }else{
            cadastroViewPage.setVisibility(View.GONE);
            cadastroTablayout.setVisibility(View.GONE);
            petCadastroMensagemFoto.setVisibility(View.VISIBLE);
            petBarraRemoverFoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        v.getId();
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
                petModal.removerFoto(cadastroTablayout.getSelectedTabPosition());
                criarSlide();
            break;
            case R.id.pet_barra_editar_foto:
                criarSlide();
                break;
        }
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
        imageFileUri  = Uri.fromFile(imagem);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(takePictureIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFileUri);
                sendBroadcast(novaIntent);
                imageFileUri.getPath();
                petModal.setFoto(imageFileUri);
            }
            if (requestCode == 0) {
                petModal.setFoto(data.getData());
            }
            criarSlide();
        }
    }
}
