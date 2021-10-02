package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityGameBinding;
import id.erris.bajakah.databinding.ActivityMateriBahasaBinding;
import id.erris.bajakah.model.ContentList;

public class MateriBahasaActivity extends AppCompatActivity {

    private ActivityMateriBahasaBinding binding;
    private List<ContentList> contentListst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMateriBahasaBinding.inflate(getLayoutInflater());
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
        setSupportActionBar(binding.tlbMateriBahasa);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Materi Bahasa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        contentListst = new ArrayList<>();

        contentListst.add(new ContentList("1", "Alfabet, Angka & Warna", "https://erawan.azurewebsites.net/public/materi/alfabet_angka_warna.htm"));
        contentListst.add(new ContentList("2", "Perkenalan", "https://erawan.azurewebsites.net/public/materi/perkenalan.htm"));
        contentListst.add(new ContentList("3", "Pertanyaan, Sapa, Respons", "https://erawan.azurewebsites.net/public/materi/pertanyaan_sapa_respons.htm"));
        contentListst.add(new ContentList("4", "Keluarga dan karakter tubuh", "https://erawan.azurewebsites.net/public/materi/keluarga_dan_karakter_tubuh.htm"));
        contentListst.add(new ContentList("5", "Waktu hari tanggal bulan", "https://erawan.azurewebsites.net/public/materi/waktu_hari_tanggal_bulan.htm"));
        contentListst.add(new ContentList("6", "Aktivitas sehari-hari", "https://erawan.azurewebsites.net/public/materi/aktivitas_sehari_hari.htm"));
    }

    private void initializeResources() {
        ArrayAdapter<ContentList> adapter = new ArrayAdapter<>(MateriBahasaActivity.this, android.R.layout.simple_list_item_1, contentListst);
        binding.lvMateriBahasa.setAdapter(adapter);

        binding.lvMateriBahasa.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getBaseContext(), KontenMateriActivity.class);
            intent.putExtra("url", String.valueOf(contentListst.get(position).getUrl()));
            intent.putExtra("judul", String.valueOf(contentListst.get(position).getName()));
            startActivity(intent);
        });
    }
}