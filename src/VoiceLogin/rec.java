package VoiceLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;

public class rec {
	private static JFrame frame;

	//private static final int BUFFER_SIZE = 4096;
	private ByteArrayOutputStream recordBytes;
	private TargetDataLine audioLine;
	private AudioFormat format;
	private boolean isRunning;
	File wavFile = new File("C:\\Users\\Admin\\Desktop\\qual3.wav");
	private AudioFormat getAudioFormat() {
	    float sampleRate = 8000;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = true;
	    return new AudioFormat(sampleRate, 
	      sampleSizeInBits, channels, signed, bigEndian);
	}
	public rec() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton start = new JButton("start");
		JButton stop = new JButton("stop");
		
		start.setBounds(157, 64, 97, 23);
		frame.getContentPane().add(start);
		ActionListener startListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					recording();
					start.setEnabled(false);
					stop.setEnabled(true);
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		};
		start.addActionListener(startListener);
		
		stop.setBounds(157, 121, 97, 23);
		frame.getContentPane().add(stop);
		ActionListener stopListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					  isRunning = false;
				try {
					save(wavFile);
					start.setEnabled(true);
					stop.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		stop.addActionListener(stopListener);
		frame.setVisible(true);
	}
	public void recording() throws LineUnavailableException {
		format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
		
		  // checks if system supports the data line
		  if (!AudioSystem.isLineSupported(info)) {
	            throw new LineUnavailableException(
	                    "The system does not support the specified format.");
	        }
		audioLine = AudioSystem.getTargetDataLine(format);
		
		audioLine.open(format);
		audioLine.start();
		
		Thread recordThread = new Thread(new Runnable() {
		//byte[] buffer = new byte[BUFFER_SIZE];
		//int bytesRead = 0;
			int bufferSize = (int)format.getSampleRate() * format.getFrameSize();
			byte[] buffer = new byte[bufferSize];
			int bytesRead = 0;
		
		public void run() {
		recordBytes = new ByteArrayOutputStream();
		isRunning = true;
		
		while (isRunning) {
			bytesRead = audioLine.read(buffer, 0, buffer.length);
			recordBytes.write(buffer, 0, bytesRead);
			}
		}
		});
		recordThread.start();
	}
	 public void save(File wavFile) throws IOException {
		 byte[] audioData = recordBytes.toByteArray();
		 ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
		 AudioInputStream audioInputStream = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
		AudioSystem.write(audioInputStream,  AudioFileFormat.Type.WAVE, wavFile);
		audioInputStream.close();
		recordBytes.close();
	 }
  public static void main(String[] args) {
	  new rec();
  }
}