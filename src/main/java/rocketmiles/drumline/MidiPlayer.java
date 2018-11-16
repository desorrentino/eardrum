package rocketmiles.drumline;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class MidiPlayer {	
	private Sequence sequence;
	private Sequencer sequencer;
	
	public MidiPlayer() {
	}
	
	public void play(File fileToPlay) {		
		try {			
			
			String instrumentName = "Standard Kit";
			loadInstrument(instrumentName);
			
			sequence = MidiSystem.getSequence(fileToPlay);
			sequencer = MidiSystem.getSequencer(true);
			sequencer.setSequence(sequence);
			sequencer.open();		
			sequencer.start();
			
		} catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void loadInstrument(String instrumentName) throws MidiUnavailableException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		Instrument mainInst = null;		
		for (Instrument inst : synth.getAvailableInstruments()) {
			String name = inst.getName();
			if (name.equals(instrumentName)) {
				System.out.println("Using instrument: " + name);
				mainInst = inst;
				break;
			}
		}
		synth.loadInstrument(mainInst);
	}
}
