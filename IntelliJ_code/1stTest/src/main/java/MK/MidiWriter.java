package MK;

/**
 *
 * Author:  Karl Brown
 * Quelle: http://www.automatic-pilot.com/midifile.html
 * Angepasst durch: Marina Knabbe
 */

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sound.midi.*; // package for all midi classes

public class MidiWriter{
    private Sequence s;
    private Track t;
    private SysexMessage sm;
    private MidiEvent me;
    private MetaMessage mt;
    private String TrackName;
    private ShortMessage mm;

   /* public static void main(String argv[]) {
      //  createMidiFile();
        new MidiWriter("midifile track");
    }*/

    public MidiWriter(String s){
        TrackName = s;
    }

    public void createMidiFile(ArrayList<Integer> track) {
        long time = 1;
        System.out.println("midifile begin ");
        try {
            s = new Sequence(Sequence.PPQ,12); // Create a new MIDI sequence with 24 ticks per beat
            t = s.createTrack(); // Obtain a MIDI track from the sequence

            //****  General MIDI sysex -- turn on General MIDI sound set  ****
            byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
            sm = new SysexMessage();
            sm.setMessage(b, 6);
            me = new MidiEvent(sm,(long)0);
            t.add(me);

            //****  set tempo (meta event)  ****
            mt = new MetaMessage();
            byte[] bt = {0x02, (byte)0x00, 0x00};
            mt.setMessage(0x51 ,bt, 3);
            me = new MidiEvent(mt,(long)0);
            t.add(me);

            //****  set track name (meta event)  ****
            mt = new MetaMessage();
            mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
            me = new MidiEvent(mt,(long)0);
            t.add(me);

            //****  set omni on  ****
            mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7D, 0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

            //****  set poly on  ****
            mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7F, 0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

            //****  set instrument to Piano  ****
            mm = new ShortMessage();
            mm.setMessage(0xC0, 0x00, 0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

            // int to event and add to track
            byte typeB = 0x00;
            byte noteB = 0x00;
            byte veloB = 0x00;
            for (Integer i : track) {
                int velo = i % 1000;
              //  System.out.println("Velo: " + velo);

                int note = i % 1_000_000 / 1000;
             //   System.out.println("Note: " + note);

                typeB = getType(i);

              //  int channel = i % 1_000_000_000 / 10_000_000;
              //  System.out.println(channel);

                mm = new ShortMessage();
               // mm.setMessage(typeB, 0x3C, 0x60);
                mm.setMessage(typeB, note, velo);
                me = new MidiEvent(mm, time);
                t.add(me);

                if(typeB == 0x80){
                    time += 0;
                }else{
                    time += 60;
                }
            }

//****  note off - middle C - 120 ticks later  ****
         /*   mm = new ShortMessage();
            mm.setMessage(0x80,0x3C,0x40);
            me = new MidiEvent(mm,(long)121);
            t.add(me);
*/

            //****  set end of track (meta event)  ****
            mt = new MetaMessage();
            byte[] bet = {}; // empty array
            mt.setMessage(0x2F, bet, 0);
            me = new MidiEvent(mt, time);
            t.add(me);

            //****  write the MIDI sequence to a MIDI file  ****
            Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
            Date date = new Date();
            String fileName = "midiSample_" + formatter.format(date) + ".mid";
            File f = new File(fileName);
            MidiSystem.write(s, 1, f);
        } //try
        catch(Exception e) {
            System.out.println("Exception caught " + e.toString());
        } //catch
        System.out.println("midifile end ");
    }

    private byte getType(Integer i) {
        byte typeB = 0x00;
        // get type
        int type = i % 10_000_000 / 1_000_000;
        //System.out.println(type);
        if(type == 0){ //off
            typeB = (byte) 0x80;
        }else if(type == 1){ //on
            typeB = (byte) 0x90;
        }else{
            System.out.println("Error: invalid type");
        }
        return typeB;
    }
}