package rocketmiles.drumline;

import java.io.File;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MainApplicationRunner implements ApplicationRunner {
	private final MessageDecoder messageDecoder;
	
	public MainApplicationRunner(MessageDecoder messageDecoder) {
		super();
		this.messageDecoder = messageDecoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		String fileName = args.getOptionValues("file").get(0);
		File file = new File(fileName);
		Sequence sequence = MidiSystem.getSequence(file);		
		for (Track track : sequence.getTracks()) {
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				System.out.println(event.getTick());
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					String msg = messageDecoder.decodeMessage((ShortMessage) message);
					System.out.println(msg);	
				}
			}
		}
	}

}
