package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;

import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityKontenMateriBinding;
import id.erris.bajakah.databinding.ActivityMateriBahasaBinding;

public class KontenMateriActivity extends AppCompatActivity {

    private ActivityKontenMateriBinding binding;
    private String url, judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKontenMateriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getExtra();
        initializeToolbar();
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
        setSupportActionBar(binding.tlbKontenMateri);
        Objects.requireNonNull(getSupportActionBar()).setTitle(judul);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getExtra() {
        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        judul = extras.getString("judul");
    }

    private void initializeResources() {
        binding.webKontenMateri.loadUrl(url);
    }
}