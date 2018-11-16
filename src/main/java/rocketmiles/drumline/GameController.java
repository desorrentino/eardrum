package rocketmiles.drumline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.Map;

@Controller
public class GameController {

	private GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("beats", gameService.getAvailableBeats());
		return "game";
	}

	@RequestMapping("/play")
	public String play(Map<String, Object> model, @WebParam(name="file") String file) throws Exception {
		System.out.println("PLAYING!");
		gameService.runFileAsync(file);
		model.put("beats", gameService.getAvailableBeats());
		model.put("message", "Listen to the beat, and then try to match it");
		return "game";
	}

}