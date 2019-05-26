package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import br.com.achapet.Activity.Pet.slideImage.SlideUriAdapter;
import br.com.achapet.Modal.PetModal;
import br.com.achapet.R;

public class PetDetalheActivity extends AppCompatActivity {

    private SlideUriAdapter slideImagePagerAdapter;
    private ViewPager detalheViewPage;
    private TabLayout detalheTablayout;
    private PetModal petModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detalhe_activity);
        petModal = new PetModal();

        slideImagePagerAdapter = new SlideUriAdapter(getBaseContext(), petModal.getFoto());
        detalheViewPage = (ViewPager) findViewById(R.id.slide_viewPage);
        detalheTablayout = (TabLayout) findViewById(R.id.slide_tablayout);
        detalheTablayout.setupWithViewPager(detalheViewPage, true);
        detalheViewPage.setAdapter(slideImagePagerAdapter);
    }
}
