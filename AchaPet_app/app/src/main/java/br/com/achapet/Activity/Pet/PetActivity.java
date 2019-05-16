package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import br.com.achapet.R;

public class PetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_lista_activity);

        PetTabLayoutAdapter adapter = new PetTabLayoutAdapter( getSupportFragmentManager() );
        adapter.adicionar( new PerdidoPetFragment() , "Pet Perdidos");
        adapter.adicionar( new AchadoPetFragment(), "Pet Achado");

        ViewPager viewPager = (ViewPager) findViewById(R.id.lista_view);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
