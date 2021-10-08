package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.UUID;

import id.erris.bajakah.databinding.ActivityFormReminderBinding;
import id.erris.bajakah.model.Reminder;
import id.erris.bajakah.response.SaveResponse;
import id.erris.bajakah.retrofit.ApiClient;
import id.erris.bajakah.retrofit.ApiInterface;
import id.erris.bajakah.utils.ActivityUtil;
import id.erris.bajakah.utils.Constants;
import id.erris.bajakah.utils.PreferenceUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FormReminderActivity extends AppCompatActivity {

    private ActivityFormReminderBinding binding;
    private UUID id = UUID.randomUUID();
    private String pesanId = "";
    private String pesanEn = "";
    private String jam = "";
    private String status = "";
    private Boolean sunday = false;
    private Boolean monday = false;
    private Boolean tuesday = false;
    private Boolean wednesday = false;
    private Boolean thursday = false;
    private Boolean friday = false;
    private Boolean saturday = false;
    private Dialog loadingDialog;
    private boolean result;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeToolbar();
        getExtras();
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
        setSupportActionBar(binding.tlbFormReminder);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Pengingat Waktu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();

        status = extras.getString(Constants.REMINDER_STATUS);
        if (status.equals("ubah")) {
            id = UUID.fromString(extras.getString(Constants.REMINDER_ID));
            pesanId = extras.getString(Constants.REMINDER_KETERANGAN_ID);
            pesanEn = extras.getString(Constants.REMINDER_KETERANGAN_EN);
            jam = extras.getString(Constants.REMINDER_WAKTU);
            sunday = extras.getBoolean(Constants.REMINDER_SUNDAY);
            monday = extras.getBoolean(Constants.REMINDER_MONDAY);
            tuesday = extras.getBoolean(Constants.REMINDER_TUESDAY);
            wednesday = extras.getBoolean(Constants.REMINDER_WEDNESDAY);
            thursday = extras.getBoolean(Constants.REMINDER_THURSDAY);
            friday = extras.getBoolean(Constants.REMINDER_FRIDAY);
            saturday = extras.getBoolean(Constants.REMINDER_SATURDAY);
        }
    }

    private void loadData() {
        binding.txtFormReminderPesanId.setText(pesanId);
        binding.txtFormReminderPesanEn.setText(pesanEn);
        if (!jam.equals(""))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tmpFormReminderJam.setHour(Integer.parseInt(jam.substring(0, 2)));
                binding.tmpFormReminderJam.setMinute(Integer.parseInt(jam.substring(3, 5)));
            } else {
                binding.tmpFormReminderJam.setCurrentHour(Integer.parseInt(jam.substring(0, 2)));
                binding.tmpFormReminderJam.setCurrentMinute(Integer.parseInt(jam.substring(3, 5)));
            }
        }
        binding.chkFormReminderSunday.setChecked(sunday);
        binding.chkFormReminderMonday.setChecked(monday);
        binding.chkFormReminderTuesday.setChecked(tuesday);
        binding.chkFormReminderWednesday.setChecked(wednesday);
        binding.chkFormReminderThursday.setChecked(thursday);
        binding.chkFormReminderFriday.setChecked(friday);
        binding.chkFormReminderSaturday.setChecked(saturday);
    }

    private void initializeResources() {
        binding.tmpFormReminderJam.setOnTimeChangedListener((view, hourOfDay, minute) -> jam = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":00");

        binding.btnFormReminderSimpan.setOnClickListener(v -> {
            setValue();

            if ( validatePesan() && validateJam() ) {
                Reminder reminder = new Reminder(id, pesanId, pesanEn, jam, sunday,
                        monday, tuesday, wednesday, thursday, friday, saturday);

                loadingDialog = ProgressDialog.show(FormReminderActivity.this, "Please Wait", "Mengambil Data...");
                loadingDialog.setCanceledOnTouchOutside(true);
                ApiInterface apiInterface = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);

                if (status.equals("rekam"))
                    doCreate(apiInterface, reminder);
                else
                    doUpdate(apiInterface, reminder);
            }
        });
    }

    private void setValue() {
        pesanId = binding.txtFormReminderPesanId.getText().toString();
        pesanEn = binding.txtFormReminderPesanEn.getText().toString();
        sunday = binding.chkFormReminderSunday.isChecked();
        monday = binding.chkFormReminderMonday.isChecked();
        tuesday = binding.chkFormReminderTuesday.isChecked();
        wednesday = binding.chkFormReminderWednesday.isChecked();
        thursday = binding.chkFormReminderThursday.isChecked();
        friday = binding.chkFormReminderFriday.isChecked();
        saturday = binding.chkFormReminderSaturday.isChecked();
    }

    private boolean validateJam() {
        if ( jam.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom jam tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.tmpFormReminderJam);
            return false;
        }

        return true;
    }

    private boolean validatePesan() {
        if ( pesanId.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom isi pesan (ID) tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.txtFormReminderPesanId);
            return false;
        }

        if ( pesanEn.isEmpty() ) {
            Snackbar.make(binding.getRoot(), "Kolom isi pesan (EN) tidak boleh kosong", Snackbar.LENGTH_SHORT).show();
            ActivityUtil.requestFocus(this, binding.txtFormReminderPesanEn);
            return false;
        }

        return true;
    }

    private void doCreate(ApiInterface apiInterface, Reminder reminder) {
        apiInterface.createReminder(PreferenceUtil.getId(getBaseContext()), reminder.keterangan_id,
                reminder.keterangan_en, reminder.jam, reminder.sunday, reminder.monday,
                reminder.tuesday, reminder.wednesday, reminder.thursday, reminder.friday,
                reminder.saturday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SaveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("SAVE_REMINDER", "Subscribe Start");
                    }

                    @Override
                    public void onNext(SaveResponse response) {
                        setOnNext(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setOnError("SAVE_REMINDER_ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        setOnComplete("SAVE_REMINDER_ERROR");
                    }
                });
    }

    private void doUpdate(ApiInterface apiInterface, Reminder reminder) {
        apiInterface.updateReminder(reminder.getId().toString(), reminder.getKeteranganId(),
                reminder.getKeteranganEn(), reminder.getJam(), reminder.isSunday(),
                reminder.isMonday(), reminder.isTuesday(), reminder.isWednesday(),
                reminder.isThursday(), reminder.isFriday(), reminder.isSaturday())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SaveResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("SAVE_REMINDER", "Subscribe Start");
                    }

                    @Override
                    public void onNext(SaveResponse response) {
                        setOnNext(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setOnError("UPDATE_REMINDER_ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        setOnComplete("UPDATE_REMINDER_ERROR");
                    }
                });
    }

    private void setOnNext(SaveResponse response) {
        result = response.getResult();
        message = response.getMessage();
    }

    private void setOnError(String error, String error_message) {
        loadingDialog.dismiss();
        Snackbar.make(binding.getRoot(), "Terjadi kesalahan, silahkan ulangi lagi", Snackbar.LENGTH_SHORT).show();

        Log.d(error, error_message);
    }

    private void setOnComplete(String error) {
        loadingDialog.dismiss();
        if (result) {
            Toast.makeText(getApplicationContext(), "Pengingat waktu berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Snackbar.make(binding.getRoot(), "Terjadi kesalahan, silahkan ulangi lagi", Snackbar.LENGTH_SHORT).show();
            Log.d(error, message);
        }
    }
}