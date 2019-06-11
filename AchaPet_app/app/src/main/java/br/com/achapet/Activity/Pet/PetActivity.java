package br.com.achapet.Activity.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import br.com.achapet.Activity.Pessoa.LoginPessoaActivity;
import br.com.achapet.Activity.Pet.tabLayout.AchadoPetFragment;
import br.com.achapet.Activity.Pet.tabLayout.PerdidoPetFragment;
import br.com.achapet.Activity.Pet.tabLayout.PetTabLayoutAdapter;
import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

import static br.com.achapet.Activity.MainActivity.VALOR_TAB;
import static br.com.achapet.Activity.Pet.PetDadosFragment.ADICIONAR_TAG;

public class PetActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Intent intent;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PetTabLayoutAdapter petTabLayoutAdapter;
    private PetPesquisarBottomFragment petPesquisarBottomSheetDialogFragment;
    private PessoaModal pessoaModal;
    private int tabLayoutPosicaoAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity);

        Fresco.initialize(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        pessoaModal = new PessoaModal();

        intent = getIntent();
        tabLayoutPosicaoAtivo = intent.getIntExtra(VALOR_TAB, 0);

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
                intent = new Intent(this, PetDadosActivity.class);
                intent.putExtra(VALOR_TAB, tabLayoutPosicaoAtivo);
                startActivity(intent);
            break;
            case R.id.pet_menu_pesquisar:
                petPesquisarBottomSheetDialogFragment = new PetPesquisarBottomFragment();
                petPesquisarBottomSheetDialogFragment.show(getSupportFragmentManager(),"Pesquisa");
            break;
            case R.id.pet_menu_login:
                intent = new Intent(this, LoginPessoaActivity.class);
                startActivity(intent);
            break;
            case R.id.pet_menu_sair:
                FirebaseAuth.getInstance().signOut();
             break;
        }
        return false;
    }
}
