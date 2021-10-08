package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityMateriBahasaBinding;
import id.erris.bajakah.databinding.ActivityMateriBudayaBinding;
import id.erris.bajakah.model.ContentList;
import id.erris.bajakah.utils.Constants;

public class MateriBudayaActivity extends AppCompatActivity {

    private ActivityMateriBudayaBinding binding;
    private List<ContentList> contentListst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMateriBudayaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeToolbar();
        loadData();
        initializeResources();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar() {
        setSupportActionBar(binding.tlbMateriBudaya);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Materi Budaya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        String url = getString(R.string.ip_address_materi);
        contentListst = new ArrayList<>();

        contentListst.add(new ContentList("1", "Suku dan tarian tradisional", url + "suku_dan_tarian_tradisional.pdf"));
        contentListst.add(new ContentList("2", "Wisata", url + "wisata.pdf"));
        contentListst.add(new ContentList("3", "Cerita Rakyat", url + "legenda.pdf"));
        contentListst.add(new ContentList("4", "Makanan", url + "makanan.pdf"));
    }

    private void initializeResources() {
        ArrayAdapter<ContentList> adapter = new ArrayAdapter<>(MateriBudayaActivity.this, android.R.layout.simple_list_item_1, contentListst);
        binding.lvMateriBudaya.setAdapter(adapter);

        binding.lvMateriBudaya.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getBaseContext(), KontenMateriActivity.class);
            intent.putExtra(Constants.MATERI_URL, String.valueOf(contentListst.get(position).getUrl()));
            intent.putExtra(Constants.MATERI_JUDUL, String.valueOf(contentListst.get(position).getName()));
            startActivity(intent);
        });
    }
}