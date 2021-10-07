package id.erris.bajakah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import id.erris.bajakah.R;
import id.erris.bajakah.databinding.ActivityKontenMateriBinding;
import id.erris.bajakah.databinding.ActivityMateriBahasaBinding;
import id.erris.bajakah.utils.FileUtil;

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
        downloadMateri();
    }

    private void downloadMateri() {
        binding.pbKontenMateri.setVisibility(View.VISIBLE);
        String dirPath = FileUtil.getRootDirPath(getBaseContext());
        String fileName = UUID.randomUUID().toString() + ".pdf";

        PRDownloader.download(url, dirPath, fileName)
                    .build()
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            binding.pbKontenMateri.setVisibility(View.GONE);
                            File downloadedFile = new File(dirPath, fileName);
                            showPdfFromFile(downloadedFile);
                        }

                        @Override
                        public void onError(Error error) {

                        }
                    });

    }

    private void showPdfFromFile(File downloadedFile) {
        binding.pdfKontenMateri
                .fromFile(downloadedFile)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .linkHandler(event -> {
                    String uri = event.getLink().getUri();

                    if (uri.contains(".mp3")) {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(uri);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            Log.d("PLAY_ALPHABET", e.getMessage());
                        }
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                    }
                })
                .load();
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
        PRDownloader.initialize(getApplicationContext());
    }
}