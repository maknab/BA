package MK;

/**
 *
 * @author Marina Knabbe
 */

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.util.ArrayList;

public class App {
   // public static ArrayList<Integer> track1 = new ArrayList<Integer>();
    public static ArrayList<Integer> track2 = new ArrayList<Integer>();

    public static void main(String[] args)throws Exception{
        //  Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\Wolfgang\\Documents\\Marina\\Studium\\BA\\IntelliJ_code\\1stTest\\src\\main\\java\\MK\\furelise.mid"));
        // Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\Wolfgang\\Documents\\Marina\\Studium\\BA\\IntelliJ_code\\1stTest\\src\\main\\java\\MK\\bumbleb.mid"));
        Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\Wolfgang\\Documents\\Marina\\Studium\\BA\\IntelliJ_code\\1stTest\\src\\main\\java\\MK\\Yiruma_-_.mid"));

        MidiReader midiReader = new MidiReader(sequence);
        track2 = midiReader.readMidi2List();

        System.out.println("Number of events: " + track2.size());
        System.out.print(track2);

        MidiWriter midiWriter = new MidiWriter("midifile track");
        midiWriter.createMidiFile(track2);
    }

}