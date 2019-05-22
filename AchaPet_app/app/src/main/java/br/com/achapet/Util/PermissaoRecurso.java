package br.com.achapet.Util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissaoRecurso extends AppCompatActivity {

    private boolean flagPermissaoCamera = false;
    private boolean flagPermissaoArquivos = false;
    private boolean flagPermissaoGPS = false;

    private String[] mensagemAlerta = new String[]{" ", " "};

    private Activity mActivity;

    public PermissaoRecurso(Activity activity){
        mActivity = activity;
        checarAsPermissao();
    }

    public void checarAsPermissao(){
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            flagPermissaoCamera = true;

        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            flagPermissaoArquivos = true;

        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            flagPermissaoGPS = true;
    }

    public boolean possoUsarCamera(){
        return flagPermissaoCamera;
    }
    public boolean possoUsarArquvo(){
        return flagPermissaoArquivos;
    }
    public boolean possoUsarGPS(){
        return flagPermissaoGPS;
    }


    public void pedirPermissaoCamera(){

        mensagemAlerta[0] = "pedirPermissaoCamera";
        mensagemAlerta[1] = "USO DA CAMERA";

        if(flagPermissaoCamera == false)
            pedirPermissao(new String[]{Manifest.permission.CAMERA});
    }
    public void pedirPermissaoArquivo(){

        mensagemAlerta[0] = "pedirPermissaoArquivo";
        mensagemAlerta[1] = "USO DE ARQUIVO";

        if(flagPermissaoArquivos == false)
            pedirPermissao(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }
    public void pedirPermissaoGps(){

        mensagemAlerta[0] = "pedirPermissaoGps";
        mensagemAlerta[1] = "USO DE GPS";

        if(flagPermissaoGPS == false)
            pedirPermissao(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public void pedirPermissao(final String[] permissao){
        final int MY_PERMISSIONS_REQUEST = 0;
        if(Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissao[0])){
                new AlertDialog.Builder(mActivity).setTitle(mensagemAlerta[0])
                        .setMessage(mensagemAlerta[1])
                        .setPositiveButton("Autorizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(mActivity, permissao, MY_PERMISSIONS_REQUEST);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }else{
                ActivityCompat.requestPermissions(mActivity, permissao, MY_PERMISSIONS_REQUEST);
            }
        }
        checarAsPermissao();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        checarAsPermissao();
    }
}
