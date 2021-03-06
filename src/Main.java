/* Class: CISC 3130
 * Section: MY9
 * EmplId: 23865698
 * Name: David Schykerynec
 */

// this program displays data taken from https://spotifycharts.com/regional week of 2/13/2020
// data file lists top 200 streamed tracks each with 5 data fields

import java.io.*;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader in = new BufferedReader(new FileReader("../data/regional-us-weekly-latest.csv"));

		// 200 tracks, each track may be by a different artist
		int NUMBER_TRACKS = 200; 
		// only relevant data fields are artist name, highest position, and # of times on list
		int RELEVANT_DATA_FIELDS = 2;
		// 2-D array to temporarily hold relevant data for each artist
		// myList[i][0] = artist's name
		// myList[i][1] = respective artist's total number of tracks appearing on list
		String[][] myList = new String[NUMBER_TRACKS][RELEVANT_DATA_FIELDS];
		

		int firstEmptyRow = readFile(in, myList);
		in.close();

		// creates linked list of Artist objects from data in myList
		TopStreamingArtists top = createLinkedList(myList, firstEmptyRow);
		
		TopStreamingArtists.Artist topArtist = top.new Artist(myList[0][0], Integer.parseInt(myList[0][1]));
		System.out.println(topArtist.getName() + " had the highest streaming song");
		
		// myList is no longer needed
		myList = null;
		
		System.out.println("INITIAL LIST");
		System.out.println("-----------------------------------------");
		System.out.print(top.display());
		System.out.println("-----------------------------------------\n\n");
		
		top.insertionSort();
		
		System.out.println("SORTED LIST");
		System.out.println("-----------------------------------------");
		System.out.print(top.display());
		System.out.print("-----------------------------------------");
	}

	// returns index of first empty row to be used in other methods
	public static int readFile(BufferedReader in, String[][] myList) throws NumberFormatException, IOException {

		// variables to be used in processing each track
		String[] track = new String[5];
		String artistName = "";
		int timesAppeared = 0;
		boolean artistFound = false;
		int firstEmptyRow = 0;

		try {
			// clear notes and column titles from buffer
			in.readLine();
			in.readLine();
		}
		catch(IOException ex) {
			System.out.println("END OF FILE");
		}

		// loop to process each track. Saves relevant data; omits track name, streams, and url
		while (in.ready()) {
			try {

				artistFound = false; // sets to false in case previous track set it to true

				String str = in.readLine();
				track = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				track[2] = track[2].replace("\"", ""); // remove "" from artist name
				artistName = track[2];

				for (int i=0; i < firstEmptyRow; i++) {

					if (myList[i][0].contentEquals(artistName)) {

						artistFound = true;
						timesAppeared = Integer.parseInt(myList[i][1]);
						timesAppeared += 1;
						myList[i][1] = String.valueOf(timesAppeared);

						break; // break out of for loop if artist is found
					}
				}
				// executes if artistName was not already in myList; adds artist to myList
				if (artistFound == false) {
					myList[firstEmptyRow][0] = artistName;
					myList[firstEmptyRow][1] = String.valueOf(1);

					firstEmptyRow += 1;
				}
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return firstEmptyRow;
	}

	public static TopStreamingArtists createLinkedList(String[][] myList, int firstEmptyRow) {

		TopStreamingArtists top = new TopStreamingArtists();

		for (int i=0; i < firstEmptyRow; i++) {
			top.insertFirst(myList[i][0], Integer.parseInt(myList[i][1]));
		}
		
		return top;
	}
}
