package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;

import br.com.achapet.Activity.Pet.tabLayout.AchadoPetFragment;
import br.com.achapet.Activity.Pet.tabLayout.PerdidoPetFragment;
import br.com.achapet.Activity.Pet.tabLayout.PetTabLayoutAdapter;
import br.com.achapet.R;

public class PetActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Intent intent;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PetTabLayoutAdapter petTabLayoutAdapter;
    private PetPesquisarBottomSheetDialogFragment petPesquisarBottomSheetDialogFragment;

    private int tabLayoutPosicaoAtivo;

    static public String VALOR_TAB = "VALOR_TAB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity);

        Fresco.initialize(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        intent = getIntent();
        tabLayoutPosicaoAtivo = intent.getIntExtra("indexTabLayout", 0);

        viewPager = (ViewPager) findViewById(R.id.lista_view);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        petTabLayoutAdapter = new PetTabLayoutAdapter(getSupportFragmentManager());
        petTabLayoutAdapter.adicionar(new PerdidoPetFragment(),"Pet Perdidos");
        petTabLayoutAdapter.adicionar(new AchadoPetFragment(),"Pet Achado");
        viewPager.setAdapter(petTabLayoutAdapter);
        viewPager.setCurrentItem(tabLayoutPosicaoAtivo,true);
        tabLayout.setupWithViewPager(viewPager);
        petTabLayoutAdapter.getCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pet_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        tabLayoutPosicaoAtivo = tabLayout.getSelectedTabPosition();
        switch (item.getItemId()){
            case R.id.pet_menu_adicionar:
                intent = new Intent(this, PetCadastroActivity.class);
                intent.putExtra(VALOR_TAB, tabLayoutPosicaoAtivo);
                startActivity(intent);
            break;
            case R.id.pet_menu_pesquisar:
                petPesquisarBottomSheetDialogFragment = new PetPesquisarBottomSheetDialogFragment();
                petPesquisarBottomSheetDialogFragment.show(getSupportFragmentManager(),"Pesquisa");
            break;
        }
        return false;
    }
}
