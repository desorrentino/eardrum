package rocketmiles.drumline;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.springframework.stereotype.Component;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diff for simple drum tracks. looks only at note_on events.
 */
@Component
public class MidiDrumTrackDiff {

	private final static int NOTE_ON = 0x90;

	private MessageDecoder messageDecoder;

	public MidiDrumTrackDiff(MessageDecoder messageDecoder) {
		this.messageDecoder = messageDecoder;
	}

	/**
	 * Return a difference between two midi tracks
	 * @param track1 - the base track
	 * @param track2
	 * @return
	 */
	String getDiff(Track track1, Track track2) throws Exception {
		Long round = 100L;
		List<MidiEventDetails> track1Details = getMidiEventDetails(track1, round);
		List<MidiEventDetails> track2Details = getMidiEventDetails(track2, round);
		Collections.sort(track1Details);
		Collections.sort(track2Details);
		String track1String = track1Details.stream().map(Object::toString).collect(Collectors.joining("\n"));
		String track2String = track2Details.stream().map(Object::toString).collect(Collectors.joining("\n"));
		System.out.println(track1String);
		System.out.println(track2String);

		List<String> track1Strings = track1Details.stream().map(Object::toString).collect(Collectors.toList());
		List<String> track2Strings = track2Details.stream().map(Object::toString).collect(Collectors.toList());
		Patch<String> patch = DiffUtils.diff(track1Strings, track2Strings);
		patch.getDeltas().size();
		DiffRowGenerator generator = DiffRowGenerator.create()
				.showInlineDiffs(true)
				.inlineDiffByWord(true)
				.oldTag(f -> "~")
				.newTag(f -> "**")
				.build();

		List<DiffRow> diffRows = generator.generateDiffRows(track1Strings, track2Strings);
		System.out.println("================================");
		System.out.println("================================");
		System.out.println("================================");

		for (DiffRow row: diffRows) {
			System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
		}

		return patch.toString();
	}

	List<MidiEventDetails> getMidiEventDetails(Track track, Long round) {
		List<MidiEventDetails> eventDetails = new ArrayList<>();
		for (int i = 0; i < track.size(); i++) {
			MidiEvent event = track.get(i);
			MidiMessage message = event.getMessage();
			if (message instanceof ShortMessage) {
				ShortMessage shortMessage = (ShortMessage) message;
				if (shortMessage.getCommand() == NOTE_ON) {
					MidiEventDetails midiEventDetails = new MidiEventDetails();
					midiEventDetails.tick = roundTick(event.getTick(), round);
					midiEventDetails.note = MessageDecoder.getKeyName(shortMessage.getData1());
					eventDetails.add(midiEventDetails);
				}
			}
		}
		return eventDetails;
	}

	Long roundTick(Long tick, Long round) {
		return tick - (tick % round);
	}


}
