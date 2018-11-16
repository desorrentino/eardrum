package rocketmiles.drumline;

import java.io.File;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MainApplicationRunner implements ApplicationRunner {
	private final MessageDecoder messageDecoder;
	private final MidiPlayer midiPlayer;
	
	
	public MainApplicationRunner(MessageDecoder messageDecoder, MidiPlayer midiPlayer) {
		super();
		this.messageDecoder = messageDecoder;
		this.midiPlayer = midiPlayer;
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		String fileName = "basic.mid"; //args.getOptionValues("file").get(0);
		File file = new File(fileName);
		midiPlayer.play(file);
	}

}
