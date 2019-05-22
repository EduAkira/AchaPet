package br.com.achapet.Modal;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PessoaModal{

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String uid;
    private String nome;
    private String email;

    boolean emailVerificado;

    public PessoaModal() {
        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null)
            mUser = mAuth.getCurrentUser();
        if(temUsuarioConectado())
            informacaoDoUsuario();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public boolean temUsuarioConectado(){
        if(mUser != null){
            return true;
        }
        return false;
    }

    public void informacaoDoUsuario(){
        nome = mUser.getDisplayName();
        email = mUser.getEmail();
        emailVerificado = mUser.isEmailVerified();
        uid = mUser.getUid();
        //uTokn = mUser.getIdToken();
    }

    //trocar de lugar
    public void atualizarPerfil(String nome){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        mUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //atualizado
                    informacaoDoUsuario();
                }
            }
        });
    }

    //trocar isso do lugar
    public void verificacaoEmail(){
        mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //emailenviado
                }
            }
        });
    }

    //trocar isso do lugar
    public void trocarSenha(String newPassword){
        mUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //senha Atualizada
                }
            }
        });
    }

    //trocar isso do lugar
    public void excluirConta(){
        mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //contaExcluida
                }
            }
        });
    }

    //tocar isso do lugar
    public void validar(String erro, EditText email, EditText senha){
        switch (erro) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                //Toast.makeText(SplashScreenActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                //Toast.makeText(SplashScreenActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_CREDENTIAL":
                //Toast.makeText(SplashScreenActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_EMAIL":
                //Toast.makeText(SplashScreenActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                email.setError("Email invalido.");
                email.requestFocus();
                break;
            case "ERROR_WRONG_PASSWORD":
                //Toast.makeText(SplashScreenActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                senha.setError("Senha invalida.");
                senha.requestFocus();
                senha.setText("");
                break;
            case "ERROR_USER_MISMATCH":
                //Toast.makeText(SplashScreenActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                //Toast.makeText(SplashScreenActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                //Toast.makeText(SplashScreenActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                //Toast.makeText(SplashScreenActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                email.setError("The email address is already in use by another account.");
                email.requestFocus();
                break;
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                //Toast.makeText(SplashScreenActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_DISABLED":
                //Toast.makeText(SplashScreenActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
                //Toast.makeText(SplashScreenActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_USER_NOT_FOUND":
                //Toast.makeText(SplashScreenActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_INVALID_USER_TOKEN":
                //Toast.makeText(SplashScreenActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                //Toast.makeText(SplashScreenActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                //Toast.makeText(SplashScreenActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                senha.setError("A senha deve conter mais de 6 caracter.");
                senha.requestFocus();
                break;
        }
    }

    @Override
    public String toString() {
        return "PessoaModal{" +
                "uid='" + uid + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", emailVerificado=" + emailVerificado +
                '}';
    }
}
