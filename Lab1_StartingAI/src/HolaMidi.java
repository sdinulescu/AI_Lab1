
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

		player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
		
		ProbabilityGenerator<Integer> myGenerator = new ProbabilityGenerator<Integer>();
		
		//probability calculations
		myGenerator.train(midiNotes.pitches);
		for (int i = 0; i < midiNotes.pitches.size(); i++) {
			myGenerator.generate();
		}
		System.out.println("generatedNotes by probability: " + myGenerator.generatedNotes);
		
		//markov calculations
		myGenerator.markovCalcs(71, midiNotes.pitches);
		for (int i = 0; i < midiNotes.pitches.size(); i++) {
			myGenerator.generateMarkov();
		}
		System.out.println("generatedNotes by Markov: " + myGenerator.generatedMarkov);
	}

	public void draw() {
		ellipse(width / 2, height / 2, second(), second());
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
