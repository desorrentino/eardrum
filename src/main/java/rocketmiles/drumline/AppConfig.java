package rocketmiles.drumline;

import java.io.File;
import java.util.function.Function;

import javax.sound.midi.MidiDevice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	private String dataDirectoryPath = "data";
	private int stopRecordingDelayMillis = 5 * 1000;

	@Bean
	public MidiPlayer midiPlayer(MidiArchiverService midiArchiveService) {
		return new MidiPlayer(midiArchiveService);
	}

	@Bean
	public MidiArchiverService midiArchiverService(final MidiSystemService midiSystemService) {
		return new MidiArchiverService(midiSystemService, archivingReceiverFactory(midiSystemService));
	}

	@Bean
	public Function<MidiDevice.Info, ArchivingReceiver> archivingReceiverFactory(
			final MidiSystemService midiSystemService) {
		return (MidiDevice.Info deviceInfo) -> {
			SequenceWriter sequenceWriter = new FileSequenceWriter(
					dataDirectoryPath + File.separator + midiSystemService.getDeviceId(deviceInfo));
			return new ArchivingReceiver(deviceInfo, sequenceWriter, stopRecordingDelayMillis);
		};
	}

	@Bean
	public MidiSystemService midiSystemService() {
		return new MidiSystemService();
	}
}
