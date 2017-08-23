
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

public class HolaElMundoMidi extends PApplet {

	MelodyPlayer player;
	MidiFileToNotes midiNotes;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("HolaElMundoMidi");

	}

	public void settings() {
		size(300, 300);

	}

	public void setup() {
		fill(120, 50, 240);

		// returns a url
		String filePath = getPath("mid/gardel_por.mid");
		// playMidiFile(filePath);

		midiNotes = new MidiFileToNotes(filePath);

		// which line
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);

		player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	}

	public void draw() {
		ellipse(width / 2, height / 2, second(), second());
		player.play();

	}

	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	void playMidiFile(String filename) {
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
