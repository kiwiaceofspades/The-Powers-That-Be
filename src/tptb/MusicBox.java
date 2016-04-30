package tptb;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import ecs100.UI;
import static javax.sound.sampled.LineEvent.Type.*;

public class MusicBox implements LineListener {
	
	private static List<AudioFileFormat> audioFormats;
	private static List<AudioInputStream> audios;
	private static List<byte[]> audio;
	private static List<File> audioFiles;
	
	private static Clip clip;
	private static Control[] controls;
	private static FloatControl volCtrl;
	private static BooleanControl muteCtrl;
	
	private static int currentSong =0;
	
	private static MusicBox musicbox = new MusicBox();
	
	private MusicBox(){}
	
	static {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void play(){
		//System.out.println("playing?");
		try {
//			clip = AudioSystem.getClip();
			clip.close();
			clip.stop();
			clip.addLineListener(musicbox);
			// we have audio system, now load the music
			AudioFileFormat frm = audioFormats.get(currentSong);
			clip.open(frm.getFormat(), audio.get(currentSong), 0, frm.getByteLength());
			// now start playing
			clip.start();
			setControls();
			
//			DataLine.Info info = new DataLine.Info(Clip.class, 
//				    frm.getFormat());
//			clip = (Clip) AudioSystem.getLine(info);
//			clip.open(frm.getFormat(), audio.get(currentSong), 0, frm.getByteLength());
//			clip.start();
			
		} catch (LineUnavailableException e) {
			System.out.println("not working");
			e.printStackTrace();
		}
		System.out.println("Now Playing: " + audioFiles.get(currentSong).getName().replaceFirst(".wav", "").replace('-', ' '));
		UI.printMessage("Now Playing: " + audioFiles.get(currentSong).getName().replaceFirst(".wav", "").replace('-', ' '));
	}
	
	private static void setControls(){
		volCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		muteCtrl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
	}
	
	public static boolean toggleMute(){
		muteCtrl.setValue(!muteCtrl.getValue());
		return muteCtrl.getValue();
	}
	
	public static void volUp(){
		volChange(1);
	}
	
	public static void volDown(){
		volChange(-1);
	}
	
	private static void volChange(float delta){
		float max = volCtrl.getMaximum();
		float min = volCtrl.getMinimum();
		float curr = volCtrl.getValue();
		float change = curr + delta;
//		System.out.printf("min%f max%f curr%f \ncha%f max%f min%f\n", max, min, curr, change, Math.max(min, change), Math.min(max, change));
		change = Math.max(min, Math.min(max, change));
		
		volCtrl.shift(curr, change, 50);
	}
	
	public static void next(){
//		currentSong++;
//		if (currentSong==audio.size()) { currentSong =0; }
		currentSong = (int) Math.ceil(Math.random()*(7))-1;
		
		//System.out.println("New Song: " + currentSong);
	}
	
	public static void stop(){
		clip.close();
		clip.stop();
	}
	
	public static void LoadMusic(){
		
		File d = new File("music");
		File[] files = d.listFiles( (dir, name) ->  name.toLowerCase().endsWith(".wav"));
		audioFormats = new ArrayList<AudioFileFormat>(files.length);
		audios = new ArrayList<AudioInputStream>(files.length);
		audioFiles = new ArrayList<File>(files.length);
		audio = new ArrayList<byte[]>(files.length);
		
		for (int i=0; i<files.length; ++i){
			try {
				
				audioFormats.add(AudioSystem.getAudioFileFormat(files[i]));
				audios.add(AudioSystem.getAudioInputStream(files[i]));
				audioFiles.add(files[i]);
				AudioFileFormat frm = AudioSystem.getAudioFileFormat(files[i]); 
				AudioInputStream aud = AudioSystem.getAudioInputStream(files[i]);
				
				byte[] b = new byte[frm.getByteLength()];
				aud.read(b, 0, frm.getByteLength());
				audio.add(b);
				
				
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		if (event.getType() == CLOSE){
			//System.out.println("CLOSE");
		}
		if (event.getType() == OPEN){
			//System.out.println("OPEN");
		}
		if (event.getType() == START){
			//System.out.println("START");
		}
		if (event.getType() == STOP){
			next();
			play();
		}
		
	}
}
