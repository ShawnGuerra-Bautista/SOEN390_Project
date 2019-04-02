package com.fsck.k9.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.R;

import org.jetbrains.annotations.Nullable;

public class AudioChallenge extends K9Activity {

    private String answer = "";
    private char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3', '4', '5', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                                '6', '7', '8', '9', '0' };


    private EditText answerInput;
    private Button button_play_sound_again;
    private Button button_sound_ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_challenge);

        for (int i = 0; i < 7; i++) {
            int random = (int)(Math.random()*chars.length);

            answer += chars[random];
        }

        button_sound_ok = findViewById(R.id.button_sound_ok);
        button_sound_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerInput = findViewById(R.id.audio_challenge_input);

                if (answer.equalsIgnoreCase(answerInput.getText().toString())) {
                    MediaPlayer winner = MediaPlayer.create(getApplicationContext(), R.raw.win_sound);
                    winner.start();
                    finish();
                }
                else {
                    MediaPlayer loser = MediaPlayer.create(getApplicationContext(), R.raw.lose_sound);
                    loser.start();
                    Intent i = new Intent(getApplicationContext(), FolderList.class);
                    startActivity(i);
                }

            }
        });

        button_play_sound_again = findViewById(R.id.button_play_sound_again);
        button_play_sound_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });

        playSound();
    }

    private void playSound() {

        final String answer = this.answer;

        final MediaPlayer[] mediaPlayers = new MediaPlayer[7];

        for (int i = 0; i < 7; i++) {
            mediaPlayers[i] = getMediaPlayer(answer.charAt(i));
        }

        mediaPlayers[0].start();
        mediaPlayers[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[1].start();
            }
        });

        mediaPlayers[1].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[2].start();
            }
        });

        mediaPlayers[2].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[3].start();
            }
        });

        mediaPlayers[3].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[4].start();
            }
        });

        mediaPlayers[4].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[5].start();
            }
        });

        mediaPlayers[5].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayers[6].start();
            }
        });


    }

    @Nullable
    private MediaPlayer getMediaPlayer(char ch) {
        switch (ch) {
            case '0': return MediaPlayer.create(this, R.raw.n0);
            case '1': return MediaPlayer.create(this, R.raw.n1);
            case '2': return MediaPlayer.create(this, R.raw.n2);
            case '3': return MediaPlayer.create(this, R.raw.n3);
            case '4': return MediaPlayer.create(this, R.raw.n4);
            case '5': return MediaPlayer.create(this, R.raw.n5);
            case '6': return MediaPlayer.create(this, R.raw.n6);
            case '7': return MediaPlayer.create(this, R.raw.n7);
            case '8': return MediaPlayer.create(this, R.raw.n8);
            case '9': return MediaPlayer.create(this, R.raw.n9);

            case 'a': return MediaPlayer.create(this, R.raw.a);
            case 'b': return MediaPlayer.create(this, R.raw.b);
            case 'c': return MediaPlayer.create(this, R.raw.c);
            case 'd': return MediaPlayer.create(this, R.raw.d);
            case 'e': return MediaPlayer.create(this, R.raw.e);
            case 'f': return MediaPlayer.create(this, R.raw.f);
            case 'g': return MediaPlayer.create(this, R.raw.g);
            case 'h': return MediaPlayer.create(this, R.raw.h);
            case 'i': return MediaPlayer.create(this, R.raw.i);
            case 'j': return MediaPlayer.create(this, R.raw.j);
            case 'k': return MediaPlayer.create(this, R.raw.k);
            case 'l': return MediaPlayer.create(this, R.raw.l);
            case 'm': return MediaPlayer.create(this, R.raw.m);
            case 'n': return MediaPlayer.create(this, R.raw.n);
            case 'o': return MediaPlayer.create(this, R.raw.o);
            case 'p': return MediaPlayer.create(this, R.raw.p);
            case 'q': return MediaPlayer.create(this, R.raw.q);
            case 'r': return MediaPlayer.create(this, R.raw.r);
            case 's': return MediaPlayer.create(this, R.raw.s);
            case 't': return MediaPlayer.create(this, R.raw.t);
            case 'u': return MediaPlayer.create(this, R.raw.u);
            case 'v': return MediaPlayer.create(this, R.raw.v);
            case 'w': return MediaPlayer.create(this, R.raw.w);
            case 'x': return MediaPlayer.create(this, R.raw.x);
            case 'y': return MediaPlayer.create(this, R.raw.y);
            case 'z': return MediaPlayer.create(this, R.raw.z);
        }

        return null;
    }
}
