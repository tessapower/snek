package tengine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioClip {
    private AudioInputStream audio;
    private final AudioFormat format;
    private final byte[] data;
    private final long length;
    private Clip loopClip;

    public AudioClip(InputStream inputStream) {
        InputStream bufferedIn = new BufferedInputStream(inputStream);

        try {
            audio = AudioSystem.getAudioInputStream(bufferedIn);
        } catch (Exception e) {
            System.err.println("Error: cannot read input stream: " + bufferedIn);
        }

        assert audio != null;

        format = audio.getFormat();
        length = audio.getFrameLength() * format.getFrameSize();
        data = new byte[(int) length];

        // TODO: maybe rework this? It seems like a bit of a hack from Massey Engine authors
        try {
            audio.read(data);
        } catch (Exception exception) {
            System.err.println("Error: reading audio file");

            System.exit(1);
        }

        loopClip = null;
    }

    public Clip getLoopClip() {
        return loopClip;
    }

    public void setLoopClip(Clip clip) {
        loopClip = clip;
    }

    public AudioFormat getAudioFormat() {
        return format;
    }

    public byte[] getData() {
        return data;
    }

    public long getBufferSize() {
        return length;
    }

    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(format, data, 0, (int) length);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: playing audio clip");
        }
    }

    // TODO: Debug this, passing a volume doesn't seem to work
    public void play(float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(format, data, 0, (int) length);

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: could not play audio clip");
        }
    }

    // Start playing an AudioClip on loop
    public void playOnLoop() {
        Clip clip = loopClip;

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(format, data, 0, (int) length);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                this.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // TODO: Debug this, passing a volume doesn't seem to work
    // Start playing an AudioClip on loop with a volume in decibels
    public void playOnLoop(float volume) {
        Clip clip = loopClip;

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(format, data, 0, (int) length);

                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(volume);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                this.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    public void stopPlayingLoop() {
        Clip clip = loopClip;

        if (clip != null) {
            clip.stop();
        }
    }
}
