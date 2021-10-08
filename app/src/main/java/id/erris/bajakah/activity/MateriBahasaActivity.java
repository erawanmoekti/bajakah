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
import id.erris.bajakah.utils.Constants;

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
        String url = getString(R.string.ip_address_materi);
        contentListst = new ArrayList<>();

        contentListst.add(new ContentList("1", "Standard Kompetensi", url + "standar_kompetensi.pdf"));
        contentListst.add(new ContentList("2", "Alfabet, Angka & Warna", url + "alfabet_angka_warna.pdf"));
        contentListst.add(new ContentList("3", "Perkenalan", url + "perkenalan.pdf"));
        contentListst.add(new ContentList("4", "Pertanyaan, Sapa, Respons", url + "pertanyaan_sapa_respons.pdf"));
        contentListst.add(new ContentList("5", "Keluarga dan karakter tubuh", url + "keluarga_dan_karakter_tubuh.pdf"));
        contentListst.add(new ContentList("6", "Waktu hari tanggal bulan", url + "waktu_hari_tanggal_bulan.pdf"));
        contentListst.add(new ContentList("7", "Aktivitas sehari-hari", url + "aktivitas_sehari_hari.pdf"));
    }

    private void initializeResources() {
        ArrayAdapter<ContentList> adapter = new ArrayAdapter<>(MateriBahasaActivity.this, android.R.layout.simple_list_item_1, contentListst);
        binding.lvMateriBahasa.setAdapter(adapter);

        binding.lvMateriBahasa.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getBaseContext(), KontenMateriActivity.class);
            intent.putExtra(Constants.MATERI_URL, String.valueOf(contentListst.get(position).getUrl()));
            intent.putExtra(Constants.MATERI_JUDUL, String.valueOf(contentListst.get(position).getName()));
            startActivity(intent);
        });
    }
}