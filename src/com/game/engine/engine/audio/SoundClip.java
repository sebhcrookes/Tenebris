package com.game.engine.engine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundClip {

    private Clip clip;
    private FloatControl gainControl;

    @Deprecated
    public SoundClip(String path) {
        try {
            InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);


        } catch (Exception e) {
        }
    }

    public void play() {
        new Thread(() -> {
            try {
                if (clip == null) {
                    return;
                }
                stop();
                clip.setFramePosition(0);
                while (!clip.isRunning()) {
                    clip.start();
                }
            } catch (Exception e) {
            }
        }).start();

    }

    public void stop() {
        try {
            if (clip.isRunning()) {
                clip.stop();
            }
        } catch (Exception e) {
        }
    }

    public void close() {
        try {
            stop();
            clip.drain();
            clip.close();
        } catch (Exception e) {
        }
    }

    public void loop() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            play();
        } catch (Exception e) {
        }
    }

    public boolean isRunning() {
        try {
            return clip.isRunning();
        } catch (Exception e) {
        }
        return false;
    }


}
