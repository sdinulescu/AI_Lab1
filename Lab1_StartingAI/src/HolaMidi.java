
/*
 * c2017 Courtney Brown 
 * 
 * Class: HolaElMundoMidi
 * Description: Demonstration of MIDI file manipulations, etc. & 'MelodyPlayer' sequencer
 * 
 */

import processing.core.*;

import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

public class HolaMidi extends PApplet {

	MelodyPlayer player;
	MelodyPlayer generatedNotes;
	MidiFileToNotes midiNotes;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("HolaMidi");  //enclosing main class for processing library

	}

	public void settings() {
		size(300, 300);
	}

	public void setup() {
		fill(120, 50, 240);
		String filePath = getPath("mid/gardel_por.mid"); // returns a url
		//playMidiFile(filePath); //THIS ACTUALLY PLAYS THE FILE

		//gets all the midi notes from existing files
		midiNotes = new MidiFileToNotes(filePath);

		// which line
		midiNotes.setWhichLine(0);
		//midiNotes.processPitchesAsTokens();

		player = new MelodyPlayer(this, 100.0f);

		//player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
		
//probability calculations
//		ProbabilityGenerator<Integer> myGenerator = new ProbabilityGenerator<Integer>();
//		myGenerator.trainPitches(midiNotes.pitches);
//		myGenerator.trainRhythms(midiNotes.rhythms);
//		for (int i = 0; i < midiNotes.pitches.size(); i++) {
//			myGenerator.generateNotes();
//		}
//		for (int i = 0; i < midiNotes.rhythms.size(); i++) {
//			myGenerator.generateRhythms();
//		}
//		System.out.println("generatedNotes by probability: " + myGenerator.generatedNotes);
//		System.out.println("generatedRhythms by probability: " + myGenerator.generatedRhythms);
		
//Markov calculations
		MarkovGenerator<Integer> notesMarkov = new MarkovGenerator<Integer>();
		notesMarkov.markovCalcs(midiNotes.pitches);
		//generate
		for (int i = 0; i < midiNotes.pitches.size(); i++) {
			notesMarkov.generateMarkov(71);
		}
		System.out.println("GeneratedMarkovNotes: " + notesMarkov.generatedMarkov);
		
		MarkovGenerator<Double> rhythmsMarkov = new MarkovGenerator<Double>();
		for (int i = 0; i < midiNotes.rhythms.size(); i++) {
			midiNotes.rhythms.set(i, ((float)Math.round(midiNotes.rhythms.get(i) * 100.0) / 100.0)); //https://www.quora.com/How-can-I-round-a-double-number-to-4-decimal-digits-in-Java
		}
		System.out.println("New Rhythms" + midiNotes.rhythms);
		rhythmsMarkov.markovCalcs(midiNotes.rhythms);
		for (int i = 0; i < midiNotes.pitches.size(); i++) {
			rhythmsMarkov.generateMarkov(0.14);
		}
		System.out.println("GeneratedMarkovRhythms: " + rhythmsMarkov.generatedMarkov);
	}

	public void draw() {
		//ellipse(width / 2, height / 2, second(), second());
		//player.play();
	}

	String getPath(String path) { //accesses resources
		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8"); //file name, decodes url
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath; //returns correct file path
	}

	void playMidiFile(String filename) { //using API
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	public void keyPressed() {
		if (key == ' ') {
			player.reset();
			println("Melody started!");
		}
	}
}
