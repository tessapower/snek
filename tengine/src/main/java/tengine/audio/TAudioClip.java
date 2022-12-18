package tengine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An audio clip that can be played once, or on repeat. Supports common file types such as WAVE,
 * AU, AIFF, but does not support MP3.
 *
 * @author Tessa Power
 */
public class TAudioClip {
    private AudioInputStream audio;
    private final AudioFormat format;
    private final byte[] data;
    private final long length;
    private Clip loopClip;

    /**
     * Constructs a new <code>TAudioClip</code> from the given <code>InputStream</code>.
     */
    public TAudioClip(InputStream inputStream) {
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

        try {
            int numBytesRead = audio.read(data);
            if (numBytesRead <= 0) {
                System.err.println("Error: could not read audio");
            }
        } catch (IOException e) {
            System.err.println("Error: could not read audio file");
            e.printStackTrace();
        }

        loopClip = null;
    }

    /**
     * Plays this <code>TAudioClip</code> once.
     */
    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(format, data, 0, (int) length);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: playing audio clip");
        }
    }

    /**
     *  Plays this <code>TAudioClip</code> on an infinite loop.
     *
     * @see TAudioClip#stopPlayingLoop()
     */
    public void playOnLoop() {
        Clip clip = loopClip;

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(format, data, 0, (int) length);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                this.loopClip = clip;
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    /**
     * Stops playing this <code>TAudioClip</code> on an infinite loop.
     *
     * @see TAudioClip#playOnLoop()
     */
    public void stopPlayingLoop() {
        Clip clip = loopClip;

        if (clip != null) {
            clip.stop();
        }
    }
}
