package com.tenebris.engine.engine.audio;

import com.tenebris.engine.engine.core.GameEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.ArrayList;

public class AudioClip {

    private static ArrayList<AudioClip> clips = new ArrayList<>();

    private String name;
    private Clip clip;
    private AudioInputStream inputStream;
    private String path;

    public AudioClip(String name, String path) {
        this.name = name;
        this.path = path;

        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    GameEngine.class.getResourceAsStream(path));
            clip.open(inputStream);
            setVolume(0.0F);
            clip.setMicrosecondPosition(clip.getMicrosecondLength());
            clip.start();
            setVolume(1.0F);
        } catch (Exception ignored) { ignored.printStackTrace(); }
    }

    public static void addSound(String name, String path) {
        clips.add(new AudioClip(name, path));
    }

    public static void addSound(String name, String path, float volume) {
        clips.add(new AudioClip(name, path));
        getSound(name).setVolume(volume);
    }

    public static AudioClip getSound(String name) {
        for (AudioClip audioClip : clips) {
            if (audioClip.getName().equals(name)) {
                return audioClip;
            }
        }

        return null;
    }

    public synchronized void play() {
        new Thread(() -> {
            try {
                if(!clip.isRunning()) {
                    clip.setFramePosition(0);
                    clip.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
