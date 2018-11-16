package rocketmiles.drumline;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MainApplicationRunner implements ApplicationRunner {

	private final MessageDecoder messageDecoder;
	private final MidiDrumTrackDiff midiDrumTrackDiff;
	private final MidiPlayer midiPlayer;


	public MainApplicationRunner(MessageDecoder messageDecoder, MidiDrumTrackDiff midiDrumTrackDiff, MidiPlayer midiPlayer) {
		super();
		this.messageDecoder = messageDecoder;
		this.midiDrumTrackDiff = midiDrumTrackDiff;
		this.midiPlayer = midiPlayer;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {		
		String fileName = "sample1.mid"; //args.getOptionValues("file").get(0);
		File file = new File(fileName);
		Sequence sequence = MidiSystem.getSequence(file);		
		Metronome m = new Metronome(sequence);
		midiPlayer.prepare(file);
		m.startWithSequence(sequence, 4);			
		midiPlayer.play();
		
//		String file1 = "sample1.mid";
//		String file2 = "sample2.mid";
//		if (file1 != null && file2 != null) {
//			Track track1 = MidiUtils.getLastTrack(file1);
//			Track track2 = MidiUtils.getLastTrack(file2);
//			String diff = midiDrumTrackDiff.getDiff(track1, track2);
//			return;
//		}

		m.startWithSequence(sequence, 4);
	}

}
