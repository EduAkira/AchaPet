package br.com.achapet.Activity;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private PessoaModal pessoaModal;

    private EditText edLogin_email;
    private EditText edLogin_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        //pessoaModal.esqueciSenha("nilo.danilo.fernandes@gmail.com");
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
                    Log.e("Login Google", pessoaModal.toString());
                } else {
                    //erro ao se conectar
                    Log.e("Login Google", task.getException().toString());
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signIn(String email, String senha) {
        if(!email.isEmpty() && !senha.isEmpty()){
            pessoaModal.getmAuth().signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        pessoaModal = new PessoaModal();
                        Log.e("Login Provedor", pessoaModal.toString());
                    } else {
                        // If sign in fails, display a message to the user.
                        //String erro = ((FirebaseAuthException) task.getException()).getErrorCode();
                        //validar(erro);

                        Log.e("login google", "email ou senha invalida");
                    }
                }
            });
        }
    }
    public void validar(String erro){
        switch (erro) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                //Toast.makeText(MainActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                //Toast.makeText(MainActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_CREDENTIAL":
                //Toast.makeText(MainActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_EMAIL":
                //Toast.makeText(MainActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                edLogin_email.setError("Email invalido.");
                edLogin_email.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                //Toast.makeText(MainActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                edLogin_senha.setError("Senha invalida.");
                edLogin_senha.requestFocus();
                edLogin_senha.setText("");
                break;
            case "ERROR_USER_MISMATCH":
                //Toast.makeText(MainActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                //Toast.makeText(MainActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                //Toast.makeText(MainActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                break;
            /*case "ERROR_EMAIL_ALREADY_IN_USE":
                //Toast.makeText(MainActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                edLogin_email.setError("The email address is already in use by another account.");
                edLogin_email.requestFocus();
                break;*/
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                //Toast.makeText(MainActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_DISABLED":
                //Toast.makeText(MainActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                //Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_NOT_FOUND":
                //Toast.makeText(MainActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_USER_TOKEN":
                //Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                //Toast.makeText(MainActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                //Toast.makeText(MainActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                edLogin_senha.setError("The password is invalid it must 6 characters at least");
                edLogin_senha.requestFocus();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_google:
                signIn();
                break;
            case R.id.login_registrar:
                Intent intent = new Intent(this, CadastroPessoaActivity.class);
                startActivity(intent);
                break;
            case R.id.login_conectar:
                signIn(edLogin_email.getText().toString(), edLogin_senha.getText().toString());
                break;
            case R.id.login_esqueci_senha:

                break;
        }
    }
}
