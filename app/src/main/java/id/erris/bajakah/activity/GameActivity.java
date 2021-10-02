package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityFeedbackBinding;
import id.erris.bajakah.databinding.ActivityGameBinding;
import id.erris.bajakah.model.ContentList;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private List<ContentList> contentListst;
//    private String[] content = {"Kuis 1: Hewan", "Kuis 2: Buah-buahan", "Kuis 3: Alat Tulis Kantor", "Kuis 4: Alat Transportasi", "Kuis 5: Anggota-anggota Tubuh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
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
        setSupportActionBar(binding.tlbGame);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Permainan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        contentListst = new ArrayList<>();

        contentListst.add(new ContentList("1", "Kuis 1: Hewan", "https://bit.ly/TTS-Bajakah-Hewan"));
        contentListst.add(new ContentList("2", "Kuis 2: Buah-buahan", "https://bit.ly/TTS-Bajakah-Buah"));
        contentListst.add(new ContentList("3", "Kuis 3: Alat Tulis Kantor", "https://bit.ly/TTS-Bajakah-ATK"));
        contentListst.add(new ContentList("4", "Kuis 4: Alat Transportasi", "https://bit.ly/TTS-Bajakah-Transportasi"));
        contentListst.add(new ContentList("5", "Kuis 5: Anggota-anggota Tubuh", "https://bit.ly/TTS-Bajakah_BagianTubuh"));
    }

    private void initializeResources() {
        ArrayAdapter<ContentList> adapter = new ArrayAdapter<>(GameActivity.this, android.R.layout.simple_list_item_1, contentListst);
        binding.lvGame.setAdapter(adapter);

        binding.lvGame.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(String.valueOf(contentListst.get(position).getUrl())));

            startActivity(intent);
        });
    }
}