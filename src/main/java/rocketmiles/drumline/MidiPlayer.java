package rocketmiles.drumline;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import org.springframework.beans.factory.annotation.Autowired;

public class MidiPlayer {
	private final MidiArchiverService midiArchiverService;

	private Sequence sequence;
	private Sequencer sequencer;

	public MidiPlayer(MidiArchiverService midiArchiverService) {
		super();
		this.midiArchiverService = midiArchiverService;
	}

	public void prepare(File fileToPlay) throws InvalidMidiDataException, IOException, MidiUnavailableException {
		sequence = MidiSystem.getSequence(fileToPlay);
		sequencer = MidiSystem.getSequencer(true);
		sequencer.open();
		sequencer.setSequence(sequence);
	}

	public void play() {
		sequencer.start();
		sequencer.addMetaEventListener(new MetaEventListener() {

			@Override
			public void meta(MetaMessage meta) {
				if (meta.getType() == 47) {
					midiArchiverService.checkForNewDevices();
					Metronome m = new Metronome(sequence);
					m.startWithSequence(sequence, 4);

					long sequenceLengthInSeconds = sequence.getMicrosecondLength() / 1000 / 1000;
					long numberOfTicks = sequence.getTickLength();
					int numberOfBeats = (int) (numberOfTicks / sequence.getResolution());
					int beatsPerMinute = (int) (numberOfBeats / sequenceLengthInSeconds * 60);
					int ctr = 0;
					while (ctr < numberOfBeats) {
						try {
							Thread.sleep(60000 / beatsPerMinute);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							++ctr;
						}
					}
					try {
						midiArchiverService.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	private Instrument loadInstrument(String instrumentName) throws MidiUnavailableException, InvalidMidiDataException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
//		synth.getChannels()[0].programChange(2);
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

		mainChannel.programChange(0);
		program = mainChannel.getProgram();

		boolean supported = synth.isSoundbankSupported(mainInst.getSoundbank());
		if (!supported) {
			throw new MidiUnavailableException("Instrument Soundbank not supported: " + mainInst);
		}
		boolean loaded = synth.loadInstrument(mainInst);
		if (!loaded) {
			throw new MidiUnavailableException("Could not load instrument: " + mainInst);
		}

		return mainInst;
	}
}
