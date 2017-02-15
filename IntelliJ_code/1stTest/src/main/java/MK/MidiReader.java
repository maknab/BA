package MK;

/**
 * Author: Sami Koivu
 * Quelle: http://stackoverflow.com/questions/3850688/reading-midi-files-in-java
 * Angepasst durch: Marina Knabbe
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.sound.midi.*;

public class MidiReader {
    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private Sequence sequence;

    public MidiReader(Sequence sequence){
        this.sequence = sequence;
    }

    public ArrayList readMidi2List() throws InvalidMidiDataException, IOException {
        ArrayList<Integer> track2 = new ArrayList<Integer>();
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int type = 1; // Event type: on
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        addEvent2List(trackNumber, sm, type, key, velocity, track2);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int type = 0; // Event type: off
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        addEvent2List(trackNumber, sm, type, key, velocity, track2);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }
        System.out.println(track2.size());
        System.out.print(track2);
        return track2;

    }

    private static void addEvent2List(int trackNumber, ShortMessage sm, int type, int key, int velocity, ArrayList<Integer> track2) {
        // add to lists
        if(trackNumber == 1){
            int eventAsInt = getEventAsInt(sm, type, key, velocity);
           // track1.add(eventAsInt);
            //track2.add(eventAsInt);
        }else if(trackNumber == 2){
            int eventAsInt = getEventAsInt(sm, type, key, velocity);
            track2.add(eventAsInt);
        }else{
          //  System.out.println("Mehr als zwei Tracks.");
        }
    }

    private static int getEventAsInt(ShortMessage sm, int type, int key, int velocity) {
        String eventString;
        // Channel
        if(sm.getChannel() < 10){
            eventString = "0" + sm.getChannel();
        }else {
            eventString = "" + sm.getChannel();
        }
        // Track on/off
        eventString = eventString + type;
        // Key
        if(key < 10){
            eventString = eventString + "00" + key;
        }else if(key < 100){
            eventString = eventString + "0" + key;
        }else {
            eventString = eventString + "" + key;
        }
        // Velocity
        if(velocity < 10){
            eventString = eventString + "00" + velocity;
        }else if(key < 100){
            eventString = eventString + "0" + velocity;
        }else {
            eventString = eventString + "" + velocity;
        }
        return Integer.parseInt(eventString);
    }
}