package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityAboutUsBinding;
import id.erris.bajakah.databinding.ActivityFeedbackBinding;
import id.erris.bajakah.response.RegisterResponse;
import id.erris.bajakah.response.SaveResponse;
import id.erris.bajakah.retrofit.ApiClient;
import id.erris.bajakah.retrofit.ApiInterface;
import id.erris.bajakah.utils.ActivityUtil;
import id.erris.bajakah.utils.PreferenceUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedbackActivity extends AppCompatActivity {

    private ActivityFeedbackBinding binding;
    private Dialog loadingDialog;
    private boolean result;
    private String message;

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
                doSendFeedback(PreferenceUtil.getId(getBaseContext()), nama, email, kritik);
            }
        });
    }

    private void doSendFeedback(String id, String nama, String email, String kritik) {
        loadingDialog = ProgressDialog.show(FeedbackActivity.this, "Mohon tunggu", "Mengirim Saran");
        loadingDialog.setCanceledOnTouchOutside(true);

        ApiInterface apiInterface = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);
        apiInterface.createFeedback(id, nama, email, kritik)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SaveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Send Feedback", "Subscribe Start");
                    }

                    @Override
                    public void onNext(SaveResponse response) {
                        result = response.getResult();
                        message = response.getMessage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        Snackbar.make(binding.getRoot(), "Terjadi kesalahan, silahkan ulangi beberapa saat lagi", Snackbar.LENGTH_SHORT).show();
                        Log.d("SENDFEEDBACK_ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.dismiss();

                        if ( result ) {
                            Snackbar.make(binding.getRoot(), "Terima kasih, kritik & saran anda berhasil dikirim", Snackbar.LENGTH_SHORT).show();
                            binding.txtFeedbackNama.setText("");
                            binding.txtFeedbackEmail.setText("");
                            binding.txtFeedbackKritik.setText("");
                        } else {
                            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateNama(String nama) {
        if ( nama.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom nama tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.txtFeedbackNama);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        if ( email.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom email tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.txtFeedbackEmail);
            return false;
        }

        if ( !isValidEmail(email) ) {
            Snackbar.make(binding.getRoot(), "Format email salah. Silahkan periksa kembali", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.txtFeedbackEmail);
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
            ActivityUtil.requestFocus(this, binding.txtFeedbackKritik);
            return false;
        }

        return true;
    }
}