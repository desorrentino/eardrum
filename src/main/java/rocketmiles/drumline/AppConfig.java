package rocketmiles.drumline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Bean
	public MidiPlayer midiPlayer() {
		return new MidiPlayer();
	}
}