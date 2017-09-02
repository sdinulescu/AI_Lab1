import java.util.*;

public class ProbabilityGenerator<E> {
	ArrayList<E> notesInFile;
	ArrayList<Integer> alphabet = new ArrayList<Integer>(); //contains note names (in midi)
	ArrayList<Integer> noteInstances = new ArrayList<Integer>(); //contains the instances the note is found (in midi file), corresponds to index of alphabet array
	ArrayList<Float> probabilities = new ArrayList<Float>(); //contains probabilities that the specific note occurs (noteInstances/the total)
	ArrayList<Integer> generatedNotes = new ArrayList<Integer>(); //arrayList of generated notes based on probabilities

//Markov Chain ArrayLists
	ArrayList<Integer> seedOptions = new ArrayList<Integer>();
	ArrayList<Integer> seedInstances = new ArrayList<Integer>();
	ArrayList<Float> seedProbabilities = new ArrayList<Float>();
	ArrayList<Integer> generatedMarkov = new ArrayList<Integer>();
	
	
	ProbabilityGenerator() {
		
	}
	
	void train(ArrayList<E> elements) {
		//tally up the notes
		System.out.println(elements);
		for (int i = 0; i < elements.size(); i++) {
			if (alphabet.size() == 0) {
				alphabet.add(0, (int)elements.get(i));
				noteInstances.add(0, 1);
			} else {
				if (alphabet.contains(elements.get(i))) {
					noteInstances.set(alphabet.indexOf(elements.get(i)), noteInstances.get(alphabet.indexOf(elements.get(i))) + 1);
				} else {
					alphabet.add((int)elements.get(i));
					noteInstances.add(1);
				}
			}
		}
		System.out.println("alphabet: " + alphabet);
		System.out.println("noteInstances: " + noteInstances);
		
		//find percentages
		int total = 0; 
		for (int i = 0; i < noteInstances.size(); i++) {
			total = total + noteInstances.get(i);
		}
		for (int i = 0; i < noteInstances.size(); i++) {
			probabilities.add((float)noteInstances.get(i)/total);
		}
		System.out.println("probabilities: " + probabilities);
	}
	
	void generate() {
		double numGen = Math.random(); //generate a random number
		//use to generate a note -- midi value or rhythm value
		if (0 <= numGen && numGen <= 0.19) {
			generatedNotes.add(69);
		} else if (0.19 < numGen && numGen <= 0.36) {
			generatedNotes.add(71);
		} else if (0.36 < numGen && numGen <= 0.46) {
			generatedNotes.add(-2147483648);
		} else if (0.46 < numGen && numGen <= 0.54) {
			generatedNotes.add(72);
		} else if (0.54 < numGen && numGen <= 0.61) { 
			generatedNotes.add(68);
		} else if (0.61 < numGen && numGen <= 0.66) {
			generatedNotes.add(64);
		} else if (0.66 < numGen && numGen <= 0.71) {
			generatedNotes.add(73); 
		} else if (0.71 < numGen && numGen <= 0.75) {
			generatedNotes.add(65);
		} else if (0.75 < numGen && numGen <= 0.78) {
			generatedNotes.add(76);
		} else if (0.78 < numGen && numGen <= 0.81) {
			generatedNotes.add(74);
		} else if (0.81 < numGen && numGen <= 0.84) {
			generatedNotes.add(62);
		} else if (0.84 < numGen && numGen <= 0.87) {
			generatedNotes.add(66);
		} else if (0.87 < numGen && numGen <= 0.90) {
			generatedNotes.add(61);
		} else if (0.90 < numGen && numGen <= 0.93) {
			generatedNotes.add(67);
		} else if (0.93 < numGen && numGen <= 0.95) {
			generatedNotes.add(63);
		} else if (0.95 < numGen && numGen <= 0.97) {
			generatedNotes.add(70);
		} else if (0.97 < numGen && numGen <= 0.98) {
			generatedNotes.add(81);
		} else if (0.98 < numGen && numGen <= 0.99) {
			generatedNotes.add(59);
		} else if (0.99 < numGen && numGen <= 1) {
			generatedNotes.add(78);
		}
	}
	
	void markovCalcs(int seed, ArrayList<E> elements) {
		//tally up notes after seed
		for (int i = 0; i < elements.size(); i++) {
			if ((int)elements.get(i) == seed) {
				if (seedOptions.contains(elements.get(i+1))) {
					seedInstances.set(seedOptions.indexOf(elements.get(i+1)), (seedInstances.get(seedOptions.indexOf(elements.get(i+1))) + 1));
				} else {
					seedOptions.add((int)elements.get(i+1));
					seedInstances.add(1);
				}
			} else {}
		}
		System.out.println("seedOptions: " + seedOptions);
		System.out.println("seedInstances: " + seedInstances);
		//calculate probabilities
		int seedTotal = 0; 
		for (int i = 0; i < seedInstances.size(); i++) {
			seedTotal = seedTotal + seedInstances.get(i);
		}
		for (int i = 0; i < seedInstances.size(); i++) {
			seedProbabilities.add((float)seedInstances.get(i)/seedTotal);
		}
		System.out.println("seedProbabilities: " + seedProbabilities);
	}
	
	void generateMarkov() {
		double numMarkov = Math.random();
		if (0 <= numMarkov && numMarkov <= 0.24) {
			generatedMarkov.add(68); //note 68
		} else if (0.24 < numMarkov && numMarkov <= 0.44) {
			generatedMarkov.add(69); //note 69
		} else if (0.44 < numMarkov && numMarkov <= 0.61) {
			generatedMarkov.add(73); //note 73
		} else if (0.61 < numMarkov && numMarkov <= 0.78) {
			generatedMarkov.add(72); //note 72
		} else if (0.78 < numMarkov && numMarkov <= 0.91) {
			generatedMarkov.add(-2147483648); //note -2147483648
		} else if (0.91 < numMarkov && numMarkov < 0.98) {
			generatedMarkov.add(71); //note 71
		} else if (0.98 <= numMarkov && numMarkov <= 1) {
			generatedMarkov.add(70); //note 70
		}
	}
	
	
	ArrayList<Integer> generatedNotes() {
		//DO I NEED TO MAKE A MIDI FILE TO PLAY THE NOTES?
		return generatedNotes;
	}
	
	ArrayList<Integer> generatedMarkovNotes() {
		return generatedMarkov;
	}
}
