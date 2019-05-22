package br.com.achapet.Activity.Pet.Detalhe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.net.URI;
import java.util.List;

import br.com.achapet.Activity.Pet.Detalhe.SlideImage.SlideImagePagerAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class DetalhePetActivity extends AppCompatActivity {

    private SlideImagePagerAdapter slideImagePagerAdapter;
    private ViewPager detalheViewPage;
    private TabLayout detalheTablayout;
    private PetModal petModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detalhe_activity);
        petModal = new PetModal("","","");

        petModal.adicionarFoto(Uri.parse("https://imgcomfort.com/Userfiles/Upload/images/illustration-geiranger.jpg"));
        petModal.adicionarFoto(Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSu2Tb3XfM0shkZGMEOy_VyXaVfKWbluaCWUenH80HTfD44bgnE"));
        petModal.adicionarFoto(Uri.parse("https://www.sticky.digital/wp-content/uploads/2013/11/img-6.jpg"));

        slideImagePagerAdapter = new SlideImagePagerAdapter(getBaseContext(), petModal.getFoto());
        detalheViewPage = (ViewPager) findViewById(R.id.detalhe_viewPage);
        detalheTablayout = (TabLayout) findViewById(R.id.detalhe_tablayout);
        detalheTablayout.setupWithViewPager(detalheViewPage, true);
        detalheViewPage.setAdapter(slideImagePagerAdapter);
    }
}
