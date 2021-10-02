package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeResources();
    }

    private void initializeResources() {
        binding.layMenu1.setOnClickListener(new ClickListener());
        binding.layMenu2.setOnClickListener(new ClickListener());
        binding.layMenu3.setOnClickListener(new ClickListener());
        binding.layMenu4.setOnClickListener(new ClickListener());
        binding.layMenu5.setOnClickListener(new ClickListener());
        binding.layMenu6.setOnClickListener(new ClickListener());
        binding.layMenu7.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layMenu1:
                    startActivity(new Intent(getBaseContext(), MateriBahasaActivity.class));
                    break;
                case R.id.layMenu2:
                    startActivity(new Intent(getBaseContext(), MateriBahasaActivity.class));
                    break;
                case R.id.layMenu3:
                    startActivity(new Intent(getBaseContext(), GameActivity.class));
                    break;
                case R.id.layMenu4:
                    startActivity(new Intent(getBaseContext(), ReminderActivity.class));
                    break;
                case R.id.layMenu5:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://ukbi.kemdikbud.go.id/"));

                    startActivity(intent);
                    break;
                case R.id.layMenu6:
                    startActivity(new Intent(getBaseContext(), FeedbackActivity.class));
                    break;
                case R.id.layMenu7:
                    startActivity(new Intent(getBaseContext(), AboutUsActivity.class));
                    break;
            }
        }
    }
}