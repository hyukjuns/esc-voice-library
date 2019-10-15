package VoiceLogin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class SecondPage {
	static private JFrame frame;
	
	private static int maxDataNum = 512;
    final static int W = 256; //frame duration(32ms) 104 frame shift(13ms) no zero-padding
    
    public double[][] sourceData = new double[maxDataNum][13];
    public int dataNum;
    
    public boolean who=false; //분류결과 저장
    ThirdPage page3 = new ThirdPage();//세번째 페이지 인스턴스 생성
    //아래는 등록된 각 학생의 뉴럴네트워크 모델들의 인스턴스 생성
    public NeuralNetwork neuralNetwork = new NeuralNetwork(); //�߰� 1

    
    //AudioFormat spec
	private ByteArrayOutputStream recordBytes;
	private TargetDataLine audioLine;
	private AudioFormat format;
	private boolean isRunning;
	File wavFile = new File("C:\\Users\\MAIN\\Desktop\\sibal1.wav");
    private AudioFormat getAudioFormat() {
	    float sampleRate = 8000;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = true;
	    return new AudioFormat(sampleRate, 
	      sampleSizeInBits, channels, signed, bigEndian);
	}
    //Layout
	public void Action(int selectmodel) {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			JLabel lblNewLabel = new JLabel("학번을 말해주세요.");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setForeground(UIManager.getColor("ProgressBar.selectionBackground"));
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 22));
			lblNewLabel.setBounds(108, 10, 217, 80);
			frame.getContentPane().add(lblNewLabel);
			
			JButton startBtn = new JButton("Start Recording");
			JButton stopBtn = new JButton("Stop Recording");
			JButton loginBtn = new JButton("Login");
			
			startBtn.setBounds(152, 100, 127, 23);
			frame.getContentPane().add(startBtn);
			startBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						recording();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
					startBtn.setEnabled(false);
					stopBtn.setEnabled(true);
				}
			});
			
			stopBtn.setBounds(152, 133, 127, 23);
			frame.getContentPane().add(stopBtn);
			stopBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
							isRunning = false;
					try {
						save(wavFile);
						startBtn.setEnabled(true);
						stopBtn.setEnabled(false);
						extract();
						JOptionPane.showMessageDialog(null, "mfcc특징 추출완료");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			loginBtn.setBounds(152, 166, 127, 23);
			frame.getContentPane().add(loginBtn);
			loginBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						who = neuralNetwork.NN(sourceData,dataNum,selectmodel);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if(who) {
						JOptionPane.showMessageDialog(null, "확인되었습니다.");
						page3.third();//세번째 페이지 실행
					}
					else
						JOptionPane.showMessageDialog(null, "거부되었습니다, 다시 시도하세요.");
				}
			});
			frame.setVisible(true);
		}
	//MFCC order : 13 extract
	public void extract() throws IOException {
    	double[] arr = new double[W*2];
    	double[] realarr = new double[W];
    	double[] imagarr = new double[W];
    	
        File AudioFile = wavFile;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in;
        byte[] data = new byte[512];
        System.out.println("data길이: "+data.length);
        double[] inbuf = new double[W];
        double[] fftbuf = new double[W];
        
        try {
            in = new BufferedInputStream(new FileInputStream(AudioFile));
            System.out.println("in.available: "+in.available());;
            int read;
            while ((read = in.read(data)) > 0) { //in(AudioFile에서 데이터를 읽어서 data라는 버퍼에 넣음
                out.write(data, 0, read); //data 배열에 0(시작)부터 read길이 만큼 읽는다
            }
            out.flush(); //버퍼에 쌓인 데이터 방출
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     int num=0;
     data = out.toByteArray();
     System.out.println("bytedata: "+data.length);
     System.out.println("inbuflength: "+inbuf.length);
     MFCC m = new MFCC(W, 13, 20,8000);/////////////////////
     for(int j=0; j<(data.length)/2-W; j+=104) //start loop 104 frame shift
     {   	
     		decode(data, inbuf, j);
    	 	arr = fft(inbuf, fftbuf);
    		for (int i = 0; i < arr.length; i++) {
    			if((i%2)==0)
    			{
    				realarr[i/2] = arr[i];
    			}
    			else {
    				imagarr[(i-1)/2] = arr[i];
    			}	
    		}
       			double[] cb = m.cepstrum(realarr, imagarr);
       			for(int w=0; w<cb.length;w++ ) {
       				sourceData[num][w] = cb[w];
       			}
       			num++;
       			System.out.println("num: "+num);
       }//end loop
       dataNum = num;
 }
	// 녹음구현
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
	//녹음파일저장
	public void save(File wavFile) throws IOException {
		 byte[] audioData = recordBytes.toByteArray();
		 ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
		 AudioInputStream audioInputStream = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());
		AudioSystem.write(audioInputStream,  AudioFileFormat.Type.WAVE, wavFile);
		audioInputStream.close();
		recordBytes.close();
	 }
    public static void decode(byte[] input, double[] output,int shift) {
        assert input.length == 2 * output.length;
        for (int i = 0; i < output.length; i++) {
            output[i] = (short) (((0xFF & input[2 * (i+shift) + 1]) << 8) | (0xFF & input[2 * (i+shift)]));
            output[i] /= Short.MAX_VALUE;
        }
    }

    public static double[] fft(final double[] inputReal, double[] inputImag) {
        assert inputReal.length == 2 * inputImag.length;
        int n = inputReal.length;
        double ld = Math.log(n) / Math.log(2.0);
 
        if (((int) ld) - ld != 0) {
            System.out.println("The number of elements is not a power of 2.");
        }
        int nu = (int) ld;
        int n2 = n / 2;
        int nu1 = nu - 1;
        double[] xReal = new double[n];
        double[] xImag = new double[n];
        double tReal, tImag, p, arg, c, s;
 
        double constant;
        if (true){
            constant = -2 * Math.PI;
        }
         
        for (int i = 0; i < n; i++) {
            xReal[i] = inputReal[i];
            xImag[i] = inputImag[i];
        }
 
        int k = 0;
        for (int l = 1; l <= nu; l++) {
            while (k < n) {
                for (int i = 1; i <= n2; i++) {
                    p = bitreverseReference(k >> nu1, nu);
                    arg = constant * p / n;
                    c = Math.cos(arg);
                    s = Math.sin(arg);
                    tReal = xReal[k + n2] * c + xImag[k + n2] * s;
                    tImag = xImag[k + n2] * c - xReal[k + n2] * s;
                    xReal[k + n2] = xReal[k] - tReal;
                    xImag[k + n2] = xImag[k] - tImag;
                    xReal[k] += tReal;
                    xImag[k] += tImag;
                    k++;
                }
                k += n2;
            }
            k = 0;
            nu1--;
            n2 /= 2;
        }
 
        k = 0;
        int r;
        while (k < n) {
            r = bitreverseReference(k, nu);
            if (r > k) {
                tReal = xReal[k];
                tImag = xImag[k];
                xReal[k] = xReal[r];
                xImag[k] = xImag[r];
                xReal[r] = tReal;
                xImag[r] = tImag;
            }
            k++;
        }
 
        double[] newArray = new double[xReal.length * 2];
        double radice = 1 / Math.sqrt(n);
        for (int i = 0; i < newArray.length; i += 2) {
            int i2 = i / 2;
            newArray[i] = xReal[i2] * radice;
            newArray[i + 1] = xImag[i2] * radice;
        }
 
    /*   for (int i = 0; i < newArray.length; i++) {
            System.out.println("Array: " + newArray[i]+"count: "+i);
        }*/
        return newArray;
        
    } 
    private static int bitreverseReference(int j, int nu) {
        int j2;
        int j1 = j;
        int k = 0;
        for (int i = 1; i <= nu; i++) {
            j2 = j1 / 2;
            k = 2 * k + j1 - 2 * j2;
            j1 = j2;
        }
        return k;
    }
}
