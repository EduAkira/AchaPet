package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;

import br.com.achapet.Activity.Pet.TabLayout.AchadoPetFragment;
import br.com.achapet.Activity.Pet.TabLayout.PerdidoPetFragment;
import br.com.achapet.Activity.Pet.TabLayout.PetTabLayoutAdapter;
import br.com.achapet.R;

public class PetActivity extends AppCompatActivity {

    Intent intent;

    ViewPager viewPager;
    TabLayout tabLayout;
    PetTabLayoutAdapter petTabLayoutAdapter;
    int tabLayoutAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fresco.initialize(this);

        intent = getIntent();
        tabLayoutAtivo = intent.getIntExtra("indexTabLayout", 0);

        viewPager = (ViewPager) findViewById(R.id.lista_view);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        petTabLayoutAdapter = new PetTabLayoutAdapter( getSupportFragmentManager() );
        petTabLayoutAdapter.adicionar( new PerdidoPetFragment() , "Pet Perdidos");
        petTabLayoutAdapter.adicionar( new AchadoPetFragment(), "Pet Achado");
        viewPager.setAdapter(petTabLayoutAdapter);
        viewPager.setCurrentItem(tabLayoutAtivo,true);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pet_menu, menu);
        return true;
    }
}
