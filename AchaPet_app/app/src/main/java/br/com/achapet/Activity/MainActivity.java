package br.com.achapet.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.achapet.R;
import br.com.achapet.Util.PermissaoRecurso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PermissaoRecurso permissaoRecurso = new PermissaoRecurso(MainActivity.this);

        Button b1 = (Button) findViewById(R.id.camera);
        Button b2 = (Button) findViewById(R.id.arquivo);
        Button b3 = (Button) findViewById(R.id.gps);

        final TextView t1 = (TextView) findViewById(R.id.textCamera);
        final TextView t2 = (TextView) findViewById(R.id.textArquivo);
        final TextView t3 = (TextView) findViewById(R.id.textGPS);

        t1.setText(""+ permissaoRecurso.possoUsarCamera());
        t2.setText(""+ permissaoRecurso.possoUsarArquvo());
        t3.setText(""+ permissaoRecurso.possoUsarGPS());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissaoRecurso.pedirPermissaoCamera();
                t1.setText(""+ permissaoRecurso.possoUsarCamera());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissaoRecurso.pedirPermissaoArquivo();
                t2.setText(""+ permissaoRecurso.possoUsarArquvo());
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissaoRecurso.pedirPermissaoGps();
                t3.setText(""+ permissaoRecurso.possoUsarGPS());
            }
        });

    }
}
