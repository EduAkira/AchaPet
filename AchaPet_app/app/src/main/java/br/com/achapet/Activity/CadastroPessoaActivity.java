package br.com.achapet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.com.achapet.R;

public class CadastroPessoaActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CadastroPessoaActivity";

    private FirebaseAuth mAuth;

    private String valEmail;
    private String valPassword;
    private String valName;
    private Uri valFoto;

    private EditText editTextEmail;
    private EditText editTextNomeCompleto;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        // Button listeners
        findViewById(R.id.singup_in_button).setOnClickListener(this);

        editTextEmail = findViewById(R.id.cadastro_pessoa_email);
        editTextNomeCompleto = findViewById(R.id.cadastro_pessoa_nome);
        editTextPassword = findViewById(R.id.cadastro_pessoa_senha);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void singUp(){
        mAuth.createUserWithEmailAndPassword(valEmail, valPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(valName)
                            //.setPhotoUri(valFoto)
                            .build();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateUI(user);
                            }
                        }
                    });
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CadastroPessoaActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    public void singin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(CadastroPessoaActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            finish();
            /*String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            Log.e("NOME: ", name);
            Log.e("Email: ", email);
            ///Log.e("photoUrl: ", photoUrl.toString());

            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singup_in_button:
                valName = editTextNomeCompleto.getText().toString();
                valEmail = editTextEmail.getText().toString();
                valPassword = editTextPassword.getText().toString();
                singUp();
                break;
        }
    }
}