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
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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
	
	private static int currentSong =0;
	
	private static MusicBox musicbox = new MusicBox();
	
	private MusicBox(){}
	
	
	public static void play(){
		//System.out.println("playing?");
		try {
			clip = AudioSystem.getClip();
			clip.close();
			clip.stop();
			clip.addLineListener(musicbox);
			//System.out.println("try");
			AudioFileFormat frm = audioFormats.get(currentSong);
			clip.open(frm.getFormat(), audio.get(currentSong), 0, frm.getByteLength());
			//System.out.println("open");
			clip.start();
			//System.out.println("start");
		} catch (LineUnavailableException e) {
			System.out.println("not working");
			e.printStackTrace();
		}
		UI.printMessage("Now Playing: " + audioFiles.get(currentSong).getName().replaceFirst(".wav", "").replace('-', ' '));
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
