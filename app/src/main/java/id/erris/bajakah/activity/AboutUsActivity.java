package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        setSupportActionBar(binding.tlbAboutUs);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tentang Kami");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeResources() {
        binding.imgAboutUsImageId.setOnClickListener(v -> {
            binding.lblAboutUsTitle.setText(getResources().getText(R.string.about_us_title_id));
            binding.lblAboutUsContent.setText(getResources().getText(R.string.about_us_content_id));
        });
        binding.imgAboutUsImageEn.setOnClickListener(v -> {
            binding.lblAboutUsTitle.setText(getResources().getText(R.string.about_us_title_en));
            binding.lblAboutUsContent.setText(getResources().getText(R.string.about_us_content_en));
        });
    }
}