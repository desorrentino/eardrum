package rocketmiles.drumline;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class MidiPlayer {
	private Sequence sequence;
	private Sequencer sequencer;

	public MidiPlayer() {
	}

	public void play(File fileToPlay) {
		try {
			sequence = MidiSystem.getSequence(fileToPlay);
			sequencer = MidiSystem.getSequencer(true);
			sequencer.open();
			sequencer.setSequence(sequence);
			String instrumentName = "Standard Kit";
			loadInstrument(instrumentName);
			sequencer.start();

		} catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Instrument loadInstrument(String instrumentName) throws MidiUnavailableException, InvalidMidiDataException {
		Synthesizer synth = MidiSystem.getSynthesizer();

		synth.getChannels()[0].programChange(2);
		Instrument[] instruments = synth.getAvailableInstruments();
		Instrument mainInst = null;
		for (Instrument inst : instruments) {
			String name = inst.getName();
			if (name.equals(instrumentName)) {
				mainInst = inst;
				break;
			}
		}

		MidiChannel mainChannel = synth.getChannels()[0]; 
		int program = mainChannel.getProgram();

		
		if (mainInst == null) {
			throw new MidiUnavailableException("Could not find instrument: " + mainInst);
		}
		
		mainChannel.programChange(2);
		program = mainChannel.getProgram();
		
//		boolean supported = synth.isSoundbankSupported(mainInst.getSoundbank());
//		if (!supported) {
//			throw new MidiUnavailableException("Instrument Soundbank not supported: " + mainInst);
//		}
//		boolean loaded = synth.loadInstrument(mainInst);
//		if (!loaded) {
//			throw new MidiUnavailableException("Could not load instrument: " + mainInst);
//		}
		
		return mainInst;
	}
}
