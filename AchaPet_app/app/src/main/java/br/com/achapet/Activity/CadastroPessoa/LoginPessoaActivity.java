package br.com.achapet.Activity.CadastroPessoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class LoginPessoaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private PessoaModal pessoaModal;

    private EditText edLogin_email;
    private EditText edLogin_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pessoa);

        // Button listeners
        findViewById(R.id.login_google).setOnClickListener(this);
        findViewById(R.id.login_conectar).setOnClickListener(this);
        findViewById(R.id.login_esqueci_senha).setOnClickListener(this);
        findViewById(R.id.login_registrar).setOnClickListener(this);

        edLogin_email = findViewById(R.id.login_email);
        edLogin_senha = findViewById(R.id.login_senha);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pessoaModal = new PessoaModal();
        if(pessoaModal.temUsuarioConectado()){
            finish();
        }
    }

    private void signIn(String email, String senha) {
        if(!email.isEmpty() && !senha.isEmpty()){
            pessoaModal.getmAuth().signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        pessoaModal = new PessoaModal();
                        finish();
                        //Log.e("Login Provedor", pessoaModal.toString());
                    } else {
                        // If sign in fails, display a message to the user.
                        String erro = ((FirebaseAuthException) task.getException()).getErrorCode();
                        pessoaModal.validar(erro, edLogin_email, edLogin_senha);
                        Log.e("login google", "email ou senha invalida");
                    }
                }
            });
        }else{
            edLogin_email.setError("Campo Obrigatorio.");
            edLogin_senha.setError("Campo Obrigatorio.");
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e("Login Google", e.toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        pessoaModal.getmAuth().signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //login Google Success
                    pessoaModal = new PessoaModal();
                    //Log.e("Login Google", pessoaModal.toString());
                    finish();
                } else {
                    //erro ao se conectar
                    Log.e("Login Google", task.getException().toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_google:
                signIn();
                break;
            case R.id.login_registrar:
                intent = new Intent(this, CadastroPessoaActivity.class);
                startActivity(intent);
                break;
            case R.id.login_conectar:
                signIn(edLogin_email.getText().toString(), edLogin_senha.getText().toString());
                break;
            case R.id.login_esqueci_senha:
                intent = new Intent(this, RecuperarSenhaPessoaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
