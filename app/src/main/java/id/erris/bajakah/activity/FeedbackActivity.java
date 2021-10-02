package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityAboutUsBinding;
import id.erris.bajakah.databinding.ActivityFeedbackBinding;

public class FeedbackActivity extends AppCompatActivity {

    private ActivityFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
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
        setSupportActionBar(binding.tlbFeedback);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kritik & Saran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeResources() {
        binding.btnFeedbackKirim.setOnClickListener(v -> {
            String nama = binding.txtFeedbackNama.getText().toString();
            String email = binding.txtFeedbackEmail.getText().toString();
            String kritik = binding.txtFeedbackKritik.getText().toString();

            if ( validateNama(nama) && validateEmail(email) && validateKritik(kritik) ) {
                Snackbar.make(binding.getRoot(), "Kritik & saran berhasil dikirim", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean validateNama(String nama) {
        if ( nama.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom nama tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            requestFocus(binding.txtFeedbackNama);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        if ( email.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom email tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            requestFocus(binding.txtFeedbackEmail);
            return false;
        }

        if ( !isValidEmail(email) ) {
            Snackbar.make(binding.getRoot(), "Format email salah. Silahkan periksa kembali", Snackbar.LENGTH_SHORT).show();
            requestFocus(binding.txtFeedbackEmail);
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateKritik(String kritik) {
        if ( kritik.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom kritik & saran tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            requestFocus(binding.txtFeedbackKritik);
            return false;
        }

        return true;
    }

    private void requestFocus(View view) {
        if ( view.requestFocus() ) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}