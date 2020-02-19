
public class TopStreamingArtists {

	private Artist first;

	public class Artist implements Comparable<Artist> {

		private String name;
		private int numTracks;
		private Artist next;

		public Artist() {
			name = "";
			numTracks = 0;
			next = null;
		}
		public Artist(String name, int numTracks) {
			this.name = name;
			this.numTracks = numTracks;
		}

		public String getName() {
			return name;
		}
		public int getNumStreams() {
			return numTracks;
		}

		public String displayArtist() {
			return String.format("%-30s[%d]%s", name, numTracks, " tracks");
		}

		// uses String.compareTo, natural order of Artist is alphabetized by name field
		// no need to check for case where 2 artists have same name
		// negative return -> this Artist precedes the argument
		// positive return -> this Artist follows the argument
		public int compareTo(Artist other) {

			if (this.name.compareToIgnoreCase(other.getName()) < 0)
				return -1;

			else
				return 1;
		}
	}

	public TopStreamingArtists() {
		first = null;
	}

	public boolean isEmpty() {
		return (first == null);
	}
	
	public void insertFirst(String name, int numTracks) {

		Artist newArtist = new Artist(name, numTracks);
		newArtist.next = first;
		first = newArtist;
	}
	
	public Artist deleteFirst() {

		if (isEmpty())
			return null;
		else {
			Artist temp = first;
			first = first.next;
			return temp;
		}
	}

	public String display() {

		Artist current = first;
		String str = "";

		while (current != null) {
			str += current.displayArtist() + "\n";
			current = current.next;
		}
		return str;
	}

	// uses natural ordering of Artist objects (alphabetical by name) to sort the list
	public void insertionSort() {

		Artist sortedHead = null;
		Artist current = first;
		Artist next = null;

		while (current != null) {

			next = current.next;
			sortedHead = sortedInsert(current, sortedHead);
			
			current = next;
		}
		first = sortedHead;

	}

	// method to be used by insertionSort method to move each Artist to the proper location
	private Artist sortedInsert(Artist toInsert, Artist sortedHead) {

		if (sortedHead == null || sortedHead.compareTo(toInsert) > 0) {
			toInsert.next = sortedHead;
			sortedHead = toInsert;
		}
		else {
			Artist current = sortedHead;
			
			while (current.next != null && current.next.compareTo(toInsert) < 0) 
				current = current.next;
			
			toInsert.next = current.next;
			current.next = toInsert;
		}
		return sortedHead;
	}
}
