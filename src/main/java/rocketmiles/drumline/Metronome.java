package rocketmiles.drumline;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class Metronome implements Runnable {
	private boolean running = true;
	private final Sequence sequence;
	
	public Metronome(Sequence sequence) {
		super();
		this.sequence = sequence;
	}

	public void startWithSequence(Sequence sequence, int beats) {
	    try {
	    	long sequenceLengthInSeconds = sequence.getMicrosecondLength() / 1000 / 1000;
	    	long numberOfTicks = sequence.getTickLength();	    	
	    	int numberOfBeats = (int)(numberOfTicks / sequence.getResolution());
	    	int beatsPerMinute = (int)(numberOfBeats / sequenceLengthInSeconds * 60);
	    	Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			int ctr = 0;
			while (ctr < beats) {
				Thread.sleep(60000 / beatsPerMinute);
				ShortMessage myMsg = new ShortMessage();
				myMsg.setMessage(ShortMessage.NOTE_ON, 4, 72, 93);
				MidiChannel channel0 = synth.getChannels()[4];
				channel0.programChange(26);
			    Receiver synthRcvr = synth.getReceiver();
			    synthRcvr.send(myMsg, -1); 
			    myMsg.setMessage(ShortMessage.NOTE_OFF, 4, 72, 93);	    
			    synthRcvr.send(myMsg, -1);	
			    ctr++;	
			}
			Thread.sleep(60000 / beatsPerMinute);
		} catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//	    String instrumentName = "Standard Kit";
//		Instrument[] instruments = synth.getAvailableInstruments();
//		Instrument mainInst = null;
//		for (Instrument inst : instruments) {
//			String name = inst.getName();
//			if (name.equals(instrumentName)) {
//				mainInst = inst;
//				break;
//			}
//		}
//		boolean loaded = synth.loadInstrument(mainInst);
//		if (!loaded) {
//			throw new Exception("Could not load instrument: " + mainInst);
//		}		
		
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		startWithSequence(sequence, 4);
	}
}
