package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import id.erris.bajakah.databinding.ActivityReminderBinding;

public class ReminderActivity extends AppCompatActivity {

    private ActivityReminderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}