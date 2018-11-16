package rocketmiles.drumline;

import org.springframework.stereotype.Component;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;

/**
 * TODO add docs
 */
@Component
public class GameService {

	private final MessageDecoder messageDecoder;
	private final MidiDrumTrackDiff midiDrumTrackDiff;
	private final MidiPlayer midiPlayer;

	public GameService(MessageDecoder messageDecoder, MidiDrumTrackDiff midiDrumTrackDiff, MidiPlayer midiPlayer) {
		this.messageDecoder = messageDecoder;
		this.midiDrumTrackDiff = midiDrumTrackDiff;
		this.midiPlayer = midiPlayer;
	}

	public File[] getAvailableBeats() {
		return new File("/beats/").listFiles();
	}

	public void runFileAsync(String fileName) throws Exception {
		new Thread(() -> {
			try {
				runFile(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void runFile(String fileName) throws Exception {

//		String fileName = "sample1.mid"; //args.getOptionValues("file").get(0);
		File file = new File(fileName);
		Sequence sequence = MidiSystem.getSequence(file);
		Metronome m = new Metronome(sequence);
		midiPlayer.prepare(file);
		m.startWithSequence(sequence, 4);
		midiPlayer.play();
	}
}
