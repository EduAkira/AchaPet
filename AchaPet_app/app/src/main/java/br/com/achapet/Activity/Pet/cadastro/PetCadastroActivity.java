package br.com.achapet.Activity.Pet.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import br.com.achapet.R;
import br.com.achapet.Util.PermissaoRecurso;

import static br.com.achapet.Activity.Pet.cadastro.PetCameraActivity.REQUEST_IMAGE_CAPTURE;

public class PetCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private PermissaoRecurso permissaoRecurso;

    private ImageButton petCadastroImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_cadastro_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        petCadastroImage = findViewById(R.id.pet_cadastro_image);
        petCadastroImage.setOnClickListener(this);
        findViewById(R.id.pet_cadastro_button).setOnClickListener(this);

        permissaoRecurso = new PermissaoRecurso(PetCadastroActivity.this);

    }


    @Override
    public void onClick(View v) {
        v.getId();

        permissaoRecurso.pedirPermissaoCamera();
        if(permissaoRecurso.possoUsarCamera()){dispatchTakePictureIntent();}
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            petCadastroImage.setImageBitmap(imageBitmap);
        }
    }
}
