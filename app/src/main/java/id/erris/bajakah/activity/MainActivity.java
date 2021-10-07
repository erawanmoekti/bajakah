package id.erris.bajakah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import id.erris.bajakah.databinding.ActivityMainBinding;
import id.erris.bajakah.model.Reminder;
import id.erris.bajakah.response.RegisterResponse;
import id.erris.bajakah.retrofit.ApiClient;
import id.erris.bajakah.retrofit.ApiInterface;
import id.erris.bajakah.service.ReminderBroadcast;
import id.erris.bajakah.service.ReminderService;
import id.erris.bajakah.utils.PreferenceUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Dialog loadingDialog;
    private boolean result;
    private String message;
    private List<Reminder> data;
    private ReminderService reminderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reminderService = new ReminderService(getBaseContext());

        initializeResources();
        initializeUser();
    }

    private void initializeUser() {
        if (PreferenceUtil.getId(getBaseContext()).equals("")) {
            doRegister();
        } else {
            List<Reminder> reminderList = PreferenceUtil.getReminder(getBaseContext());
            for(Reminder reminder : reminderList)
                reminderService.createReminder(reminder);

            binding.btnMainLogin.setVisibility(View.VISIBLE);
        }
    }

    private void doRegister() {
        String id = UUID.randomUUID().toString();
        data = new ArrayList<>();

        loadingDialog = ProgressDialog.show(MainActivity.this, "Mohon tunggu", "Inisialisasi Data");
        loadingDialog.setCanceledOnTouchOutside(true);

        ApiInterface apiInterface = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);
        apiInterface.register(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Register User", "Subscribe Start");
                    }

                    @Override
                    public void onNext(RegisterResponse response) {
                        result = response.getResult();
                        message = response.getMessage();

                        if ( result ) {
                            data = response.getData();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadingDialog.dismiss();
                        Snackbar.make(binding.getRoot(), "Terjadi kesalahan, silahkan ulangi beberapa saat lagi", Snackbar.LENGTH_SHORT).show();
                        Log.d("REGISTER_ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.dismiss();

                        if ( result ) {
                            PreferenceUtil.setId(getBaseContext(), id);
                            PreferenceUtil.setReminder(getBaseContext(), data);

                            for (Reminder reminder : data) {
                                reminderService.createReminder(reminder);
                            }
                            binding.btnMainLogin.setVisibility(View.VISIBLE);
                        } else {
                            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeResources() {
        binding.btnMainLogin.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), MenuActivity.class));
            finish();
        });
    }
}