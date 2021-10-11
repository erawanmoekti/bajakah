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

            binding.lblAboutLogoTitle.setText(getResources().getText(R.string.about_logo_title_id));
            binding.lblAboutLogoContent.setText(getResources().getText(R.string.about_logo_content_id));

            binding.lblAboutSloganSandy.setText(getResources().getText(R.string.duta_bahasa_kalimantan_tengah_2021));
            binding.lblAboutSloganWinney.setText(getResources().getText(R.string.duta_bahasa_kalimantan_tengah_2021));

            binding.lblAboutNamaSandy.setText(getResources().getText(R.string.nama_panggilan_sandy_id));
            binding.lblAboutTtlSandy.setText(getResources().getText(R.string.tempat_tanggal_lahir_sandy_id));
            binding.lblAboutEmailSandy.setText(getResources().getText(R.string.pos_el_sandy_id));

            binding.lblAboutNamaWinney.setText(getResources().getText(R.string.nama_panggilan_winney_id));
            binding.lblAboutTtlWinney.setText(getResources().getText(R.string.tempat_tanggal_lahir_winney_id));
            binding.lblAboutEmailWinney.setText(getResources().getText(R.string.pos_el_winney_id));
        });
        binding.imgAboutUsImageEn.setOnClickListener(v -> {
            binding.lblAboutUsTitle.setText(getResources().getText(R.string.about_us_title_en));
            binding.lblAboutUsContent.setText(getResources().getText(R.string.about_us_content_en));

            binding.lblAboutLogoTitle.setText(getResources().getText(R.string.about_logo_title_en));
            binding.lblAboutLogoContent.setText(getResources().getText(R.string.about_logo_content_en));

            binding.lblAboutSloganSandy.setText(getResources().getText(R.string.central_kalimantan_language_ambassador_2021));
            binding.lblAboutSloganWinney.setText(getResources().getText(R.string.central_kalimantan_language_ambassador_2021));

            binding.lblAboutNamaSandy.setText(getResources().getText(R.string.nama_panggilan_sandy_en));
            binding.lblAboutTtlSandy.setText(getResources().getText(R.string.tempat_tanggal_lahir_sandy_en));
            binding.lblAboutEmailSandy.setText(getResources().getText(R.string.pos_el_sandy_en));

            binding.lblAboutNamaWinney.setText(getResources().getText(R.string.nama_panggilan_winney_en));
            binding.lblAboutTtlWinney.setText(getResources().getText(R.string.tempat_tanggal_lahir_winney_en));
            binding.lblAboutEmailWinney.setText(getResources().getText(R.string.pos_el_winney_en));
        });
    }
}