package br.com.fabriciocurvello.mp3playerlifecycle;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btPlayPause;
    private boolean isPlaying = false; // Estado para validar se a música está rodando.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btPlayPause = findViewById(R.id.bt_play_pause);

        // Iniciando o MediaPLayer com um arquivo de áudio local.
        mediaPlayer = MediaPlayer.create(this, R.raw.mymusic);

        btPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mediaPlayer.isPlaying()) {
                    pauseMusic();
                    isPlaying = false; // Indica que o usuário pausou a música
                } else {
                    playMusic();
                    isPlaying = true; // Indica que o usuário iniciou/retomou a música
                }
            }
        });

    } // onCreate

    @Override
    protected void onStart() {
        super.onStart();
        // Se a música estava tocando antes de parar a Activity, retoma a reprodução.
       if(isPlaying) {
            playMusic();
       }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Pausa a música quando a Activity sai de foco.
        if (mediaPlayer.isPlaying()) {
            // Forçar o isPLaying em verdadeiro para que ao retornar a Activity, o onStart() recarregue a música.
            pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // libera o recurso do MediaPLayer quando a Activity é detruída.
        if ( mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            btPlayPause.setText("Pause");
            isPlaying = true;
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btPlayPause.setText("Play");
        }
    }



}