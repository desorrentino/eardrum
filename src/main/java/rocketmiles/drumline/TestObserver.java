package rocketmiles.drumline;

import java.util.Observable;
import java.util.Observer;

public class TestObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("done: " + arg);		
	}

}
