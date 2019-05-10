package br.com.achapet.Modal;

import android.net.Uri;

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
    private Uri fotoUrl;

    boolean emailVerificado;

    public PessoaModal() {
        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null)
            mUser = mAuth.getCurrentUser();
        if(mUser != null)
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
        fotoUrl = mUser.getPhotoUrl();
        emailVerificado = mUser.isEmailVerified();
        uid = mUser.getUid();
    }

    public void atualizarPerfil(String nome, Uri foto){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .setPhotoUri(foto)
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

    public void esqueciSenha(String emailAddress){

        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //email Enviado
                }
            }
        });
    }

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


    @Override
    public String toString() {
        return "PessoaModal{" +
                "uid='" + uid + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", fotoUrl=" + fotoUrl +
                ", emailVerificado=" + emailVerificado +
                '}';
    }
}
