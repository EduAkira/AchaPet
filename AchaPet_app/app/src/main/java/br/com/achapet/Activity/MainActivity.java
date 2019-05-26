package br.com.achapet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.achapet.Activity.Pet.PetActivity;
import br.com.achapet.Activity.Pessoa.LoginPessoaActivity;
import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PessoaModal pessoaModal;
    static public String VALOR_TAB = "indexTabLayout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.splash_conectar).setOnClickListener(this);
        findViewById(R.id.splash_pet_perdido).setOnClickListener(this);
        findViewById(R.id.splash_pet_achado).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pessoaModal = new PessoaModal();
        if(pessoaModal.temUsuarioConectado()){
            findViewById(R.id.splash_conectar).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.splash_conectar:
                intent = new Intent(this, LoginPessoaActivity.class);
                startActivity(intent);
                break;
            case R.id.splash_pet_perdido:
                intent = new Intent(this, PetActivity.class);
                intent.putExtra(VALOR_TAB, 0);
                startActivity(intent);
                break;
            case R.id.splash_pet_achado:
                intent = new Intent(this, PetActivity.class);
                intent.putExtra(VALOR_TAB, 1);
                startActivity(intent);
                break;
        }
    }
}
