package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.erris.bajakah.adapter.ReminderAdapter;
import id.erris.bajakah.databinding.ActivityReminderBinding;
import id.erris.bajakah.model.Reminder;
import id.erris.bajakah.response.ReminderResponse;
import id.erris.bajakah.retrofit.ApiClient;
import id.erris.bajakah.retrofit.ApiInterface;
import id.erris.bajakah.utils.Constants;
import id.erris.bajakah.utils.PreferenceUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReminderActivity extends AppCompatActivity {

    private ActivityReminderBinding binding;
    private Dialog loadingDialog;
    private List<Reminder> reminderList;
    private ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeToolbar();
        initializeRecycler();
        initializeResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar() {
        setSupportActionBar(binding.tlbReminder);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Pengingat Waktu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeRecycler() {
        binding.rcvReminder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        binding.rcvReminder.setLayoutManager(layoutManager);
    }

    private void loadData() {
        loadingDialog = ProgressDialog.show(ReminderActivity.this, "Please Wait", "Mengambil Data...");
        loadingDialog.setCanceledOnTouchOutside(true);

        reminderList = new ArrayList<>();

        ApiInterface apiInterface = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);
        apiInterface.getReminder(PreferenceUtil.getId(getBaseContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ReminderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("LOAD_REMINDER", "Subscribe Start");
                    }

                    @Override
                    public void onNext(ReminderResponse response) {
                        reminderList = response.getData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        reminderList = PreferenceUtil.getReminder(getBaseContext());
                        adapter = new ReminderAdapter(getBaseContext(), reminderList);
                        binding.rcvReminder.setAdapter(adapter);

                        Log.d("LOAD_REMINDER_ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.dismiss();

                        PreferenceUtil.setReminder(getBaseContext(), reminderList);
                        adapter = new ReminderAdapter(getBaseContext(), reminderList);
                        binding.rcvReminder.setAdapter(adapter);
                    }
                });
    }

    private void initializeResources() {
        binding.fabReminder.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), FormReminderActivity.class);
            intent.putExtra(Constants.REMINDER_STATUS, "rekam");
            startActivity(intent);
        });
    }
}