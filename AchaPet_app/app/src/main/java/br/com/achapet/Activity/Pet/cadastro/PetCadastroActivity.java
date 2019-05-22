package br.com.achapet.Activity.Pet.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

import br.com.achapet.Activity.Pet.SlideImage.SlideUriAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_cadastro_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.pet_barra_nova_foto).setOnClickListener(this);
        cadastroViewPage = (ViewPager) findViewById(R.id.cadastro_viewPage);
        cadastroTablayout = (TabLayout) findViewById(R.id.cadastro_tablayout);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);
        petModal = new PetModal("","","");
        slideUriAdapter = new SlideUriAdapter(getBaseContext(), petModal.getFoto());
        criarSlide();
    }

    public void criarSlide(){
        if(!petModal.getFoto().isEmpty()){
            cadastroViewPage.setVisibility(View.VISIBLE);
            cadastroTablayout.setVisibility(View.VISIBLE);
            cadastroTablayout.setupWithViewPager(cadastroViewPage, true);
            cadastroViewPage.setAdapter(slideUriAdapter);
        }else{
            cadastroViewPage.setVisibility(View.GONE);
            cadastroTablayout.setVisibility(View.GONE);
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
        }
    }

    private void usarCamera() {
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imagem = new File(diretorio.getPath() + "/" + System.currentTimeMillis() + ".jpg");
        imageFileUri  = Uri.fromFile(imagem);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(takePictureIntent, 15);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 15) {
            if(resultCode == Activity.RESULT_OK){
                //usuário tirou a foto
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFileUri);
                sendBroadcast(novaIntent);
                imageFileUri.getPath();
                petModal.setFoto(imageFileUri);
                criarSlide();
            }
        }
    }
}
