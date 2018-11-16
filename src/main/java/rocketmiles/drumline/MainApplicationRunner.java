package rocketmiles.drumline;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sound.midi.Track;
import java.io.File;

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
		String fileName = "basic.mid"; //args.getOptionValues("file").get(0);
		File file = new File(fileName);
		midiPlayer.play(file);

//		for (String arg: args.getSourceArgs()) {
//			System.out.println(arg);
//		}
//		DiffMatchPatch dmp = new DiffMatchPatch();
//		List diffs = dmp.diffMain("abcd", "abcde");
//		System.out.println(diffs.toString());
//		if (true) return;

//		String file1 = args.getOptionValues("file1").get(0);
//		String file2 = args.getOptionValues("file2").get(0);
		String file1 = "sample1.mid";
		String file2 = "sample2.mid";
		if (file1 != null && file2 != null) {
			Track track1 = MidiUtils.getLastTrack(file1);
			Track track2 = MidiUtils.getLastTrack(file2);
			String diff = midiDrumTrackDiff.getDiff(track1, track2);
			return;
		}

//		String fileName = args.getOptionValues("file").get(0);
//		File file = new File(fileName);
//		Sequence sequence = MidiSystem.getSequence(file);
//		for (Track track : sequence.getTracks()) {
//			for (int i = 0; i < track.size(); i++) {
//				MidiEvent event = track.get(i);
//				System.out.println(event.getTick());
//				MidiMessage message = event.getMessage();
//				if (message instanceof ShortMessage) {
//					String msg = messageDecoder.decodeMessage((ShortMessage) message);
//					System.out.println(msg);
//				}
//			}
//		}
	}

}
