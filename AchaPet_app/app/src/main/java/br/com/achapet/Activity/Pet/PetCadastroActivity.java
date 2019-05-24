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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

import br.com.achapet.Activity.Pet.slideImage.SlideUriAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;
import br.com.achapet.Util.PermissaoRecurso;

public class PetCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private PermissaoRecurso permissaoRecurso;
    private SlideUriAdapter slideUriAdapter;
    private ViewPager cadastroViewPage;
    private TabLayout cadastroTablayout;
    private PetModal petModal;
    private Uri imageFileUri;

    private ImageButton petBarraEditarFoto;
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

        cadastroViewPage = findViewById(R.id.slide_viewPage);
        cadastroTablayout = findViewById(R.id.slide_tablayout);
        petBarraEditarFoto =  findViewById(R.id.pet_barra_editar_foto);
        petBarraRemoverFoto =  findViewById(R.id.pet_barra_remover_foto);
        petCadastroMensagemFoto = findViewById(R.id.pet_cadastro_mensagem_foto);

        findViewById(R.id.pet_barra_nova_foto).setOnClickListener(this);
        findViewById(R.id.pet_barra_nova_galeria).setOnClickListener(this);
        petBarraEditarFoto.setOnClickListener(this);
        petBarraRemoverFoto.setOnClickListener(this);

        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);
        petModal = new PetModal("","","");
        slideUriAdapter = new SlideUriAdapter(getBaseContext(), petModal.getFoto());
        criarSlide();
    }

    public void criarSlide(){
        if(!petModal.getFoto().isEmpty()){
            cadastroViewPage.setVisibility(View.VISIBLE);
            cadastroTablayout.setVisibility(View.VISIBLE);
            petBarraEditarFoto.setVisibility(View.VISIBLE);
            petBarraRemoverFoto.setVisibility(View.VISIBLE);
            petCadastroMensagemFoto.setVisibility(View.GONE);

            cadastroTablayout.setupWithViewPager(cadastroViewPage, true);
            cadastroViewPage.setAdapter(slideUriAdapter);
            cadastroViewPage.setCurrentItem(cadastroTablayout.getTabCount()-1,true);

        }else{
            cadastroViewPage.setVisibility(View.GONE);
            cadastroTablayout.setVisibility(View.GONE);
            petCadastroMensagemFoto.setVisibility(View.VISIBLE);

            petBarraEditarFoto.setVisibility(View.GONE);
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
        }
    }
    private void usarGalleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagens"), 0);
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
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFileUri);
                sendBroadcast(novaIntent);
                imageFileUri.getPath();
                petModal.setFoto(imageFileUri);
            }
        }
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                petModal.setFoto(data.getData());
            }
        }
        criarSlide();
    }
}
