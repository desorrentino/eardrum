package rocketmiles.drumline;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.io.File;
import java.io.IOException;

/**
 * TODO add docs
 */
public class MidiUtils {

	static Sequence getSequence(String fileName) throws InvalidMidiDataException, IOException {
		return getSequence(new File(fileName));
	}

	static Sequence getSequence(File file) throws InvalidMidiDataException, IOException {
		return MidiSystem.getSequence(file);
	}

	static Track extractSingleTrack(String fileName) throws InvalidMidiDataException, IOException {
		return extractSingleTrack(getSequence(fileName));
	}

	static Track extractSingleTrack(File file) throws InvalidMidiDataException, IOException {
		return extractSingleTrack(getSequence(file));
	}

	static Track extractSingleTrack(Sequence sequence) {
		if (sequence.getTracks().length != 1) {
			throw new IllegalStateException("Expected 1 track, sequence had " + sequence.getTracks().length);
		}
		return sequence.getTracks()[0];
	}

	static Track getFirstTrack(String fileName) throws InvalidMidiDataException, IOException {
		return getFirstTrack(getSequence(fileName));
	}

	static Track getFirstTrack(Sequence sequence) {
		return sequence.getTracks()[0];
	}

	static Track getLastTrack(String fileName) throws InvalidMidiDataException, IOException {
		return getLastTrack(getSequence(fileName));
	}

	static Track getLastTrack(Sequence sequence) {
		return sequence.getTracks()[sequence.getTracks().length - 1];
	}

}
