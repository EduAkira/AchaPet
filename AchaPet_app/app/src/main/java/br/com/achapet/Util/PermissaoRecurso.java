package br.com.achapet.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissaoRecurso {

    private boolean flagPermissaoCamera = false;
    private boolean flagPermissaoArquivos = false;
    private boolean flagPermissaoGPS = false;

    private Activity mActivity;

    public PermissaoRecurso(Activity activity){
        mActivity = activity;
        checarAsPermissao();
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
        if(flagPermissaoCamera == false){
            if(Build.VERSION.SDK_INT >= 23){
                int MY_PERMISSIONS_REQUEST = 0;
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
                if(MY_PERMISSIONS_REQUEST == PackageManager.PERMISSION_GRANTED)
                    flagPermissaoCamera = true;
            }
        }
    }

    public void pedirPermissaoArquivo(){
        if(flagPermissaoArquivos == false){
            if(Build.VERSION.SDK_INT >= 23){
                int MY_PERMISSIONS_REQUEST = 0;
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
                if(MY_PERMISSIONS_REQUEST == PackageManager.PERMISSION_GRANTED)
                    flagPermissaoArquivos = true;
            }
        }
    }

    public void pedirPermissaoGps(){
        if(flagPermissaoGPS == false){
            if(Build.VERSION.SDK_INT >= 23){
                int MY_PERMISSIONS_REQUEST = 0;
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST);
                if(MY_PERMISSIONS_REQUEST == PackageManager.PERMISSION_GRANTED)
                    flagPermissaoGPS = true;
            }
        }
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
}
