package br.com.achapet.Util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissaoRecurso extends AppCompatActivity {

    private boolean flagPermissaoCamera = false;
    private boolean flagPermissaoArquivos = false;
    private boolean flagPermissaoGPS = false;

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
        if(flagPermissaoCamera == false)
            pedirPermissao(new String[]{Manifest.permission.CAMERA});
    }
    public void pedirPermissaoArquivo(){
        if(flagPermissaoArquivos == false)
            pedirPermissao(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }
    public void pedirPermissaoGps(){
        if(flagPermissaoGPS == false)
            pedirPermissao(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public void pedirPermissao(String[] permissao){
        int MY_PERMISSIONS_REQUEST = 0;
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(mActivity, permissao, MY_PERMISSIONS_REQUEST);

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissao[0])){
                //Log.e("PermissaoRecurso", "pedirPermissao com explicação");
            }else{
                //Log.e("PermissaoRecurso", "pedirPermissao sem explicação");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        checarAsPermissao();
    }

}
