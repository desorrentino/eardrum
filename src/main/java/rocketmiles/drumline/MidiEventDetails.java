package rocketmiles.drumline;

/**
 * Simplified midi event, just note and tick
 */
public class MidiEventDetails implements Comparable<MidiEventDetails> {

	public long tick;

	public String note;

	@Override
	public int compareTo(MidiEventDetails o) {
		if (tick == o.tick) {
			return note.compareTo(o.note);
		}
		return Long.compare(tick, o.tick);
	}

	public String toString() {
		return tick + ": " + note;
	}
}
