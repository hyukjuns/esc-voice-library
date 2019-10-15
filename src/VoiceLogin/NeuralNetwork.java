package VoiceLogin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
public class NeuralNetwork {
	
	private static int inputnum  = 13;
	private static int hiddennum = 10;
	private static int outputnum = 2;
	private static int maxinputnum = 5000;
	private static double ymin = -1;
	
	//sourceDataArray -> getData()에 인수로 사용
	public boolean NN(double[][] sourceDataArray, int loopcnt, int selecmodel) throws IOException {
	double[][] wh = new double[hiddennum][inputnum];
	double[][] wo = new double[outputnum][hiddennum];
	double[] bias1 = new double[hiddennum];
	double[] bias2 = new double[outputnum];
	double[][] data = new double[maxinputnum][inputnum];
	double[] hi = new double[hiddennum];
	double[] xoffset = new double[inputnum];
	double[] gain = new double[inputnum];
	int n_of_e;
	int i;
	int cnt1=0;
	int cnt2=0;
	String str = null;
	switch(selecmodel) {
	case 1: str = "1322039";
			break;
	case 2: str = "1422011";
			break;
	case 3: str = "1422061";
			break;
	case 4: str = "1422007";
			break;
	case 5: str = "1422014";
			break;
	default : System.out.println("selelmoodel error");
			  break;
	
	}
	File whFile = new File(str+"_wh.txt");//히든층 가중치
	File woFile = new File(str+"_wo.txt");//출력층 가중치
	File b1File = new File(str+"_hbias.txt");//히든층 임계치
	File b2File = new File(str+"_obias.txt");//출력층 임계치
	File xoffsetFile = new File(str+"_xoffset.txt");
	File gainFile = new File(str+"_gain.txt");

	initwh(whFile,b1File,wh,bias1);
	initwo(woFile,b2File,wo,bias2);
	//print(wh,wo,bias1,bias2);
	n_of_e = getData(sourceDataArray,xoffsetFile,gainFile,data,xoffset,gain,loopcnt); 
	double[][] o1 = new double[n_of_e][outputnum];
	double[] result = new double[inputnum]; //to bsxfun
	System.out.println("n_of_e :"+" "+n_of_e);
	double sub=0;
	for(i=0; i<n_of_e;i++)
	{
		bsxfun(inputnum,result, data[i], xoffset, gain);
		forward(wh,wo,hi,result,bias1,bias2,o1[i]);
	}
	for(i=0;i<n_of_e;i++)
	{
		//System.out.println(i+" "+o1[i][0]+" "+o1[i][1]);
		//if((o1[i][0]>o1[i][1]) ) cnt1++;
		//else if(o1[i][0]<o1[i][1]) cnt2++;
		sub = (o1[i][0] - o1[i][1]) * (o1[i][0] - o1[i][1]);
		System.out.println("output"+i+" "+o1[i][0]+" "+o1[i][1] +" " + "sub : " + sub);
		if((o1[i][0]>o1[i][1]) && (o1[i][0]>0 && o1[i][1]<0))   cnt1++;
		else if((o1[i][0]<o1[i][1]) && (o1[i][0]<0 && o1[i][1]>0)) cnt2++;
	}
	System.out.println("cnt1: "+cnt1+" cnt2: "+cnt2);
	//double real = (double)cnt1/(double)n_of_e * 100.0;
	//double fake = (double)cnt2/(double)n_of_e * 100.0;
	double real = (double)cnt1/(double)(cnt1+cnt2) * 100.0;
	double fake = (double)cnt2/(double)(cnt1+cnt2) * 100.0;
	System.out.println("정확도 : "+real); //임시 정확도 체크
	if(real>60) {
		System.out.print("real ");
		System.out.printf("%.2f",real);
		System.out.println("%");
		return true;
	}
	else {
		System.out.print("fake ");
		System.out.printf("%.2f",fake);
		System.out.println("%");
		return false;
	}
}
	public static void forward(double[][] wh,double[][] wo, double[] hi, double[] data,double[] bias1,double[] bias2,double[] o)
	{
		int i,j;
		double u,u1; //가중합
		for(i=0; i<hiddennum;i++)
		{	u=0;
			for(j=0; j<inputnum;j++)
			{
				u += data[j]*wh[i][j];
			}
			u += bias1[i];
			hi[i] = sig(u); //중간층 sigmoid사용
		}
		for(i=0; i<outputnum;i++) 
		{	u1=0;
			for(j=0; j<hiddennum; j++)
			{
				u1 += hi[j]*wo[i][j];//가중치 처리
			}
			u1 += bias2[i];
			o[i] = sig(u1);
		}
	}
	public static double sig(double u)
	{
		return (2.0)/(1.0+Math.exp(-2*u))-1;  //tansig
	}
	public static int getData(double[][] sourceData, File xoffsetFile, File gainFile, double[][] data, double[] xoffset, double[] gain,int loopcnt) throws FileNotFoundException
	{ //sourceData -> data
		
		FileReader f3 = new FileReader(xoffsetFile);
		Scanner sc3 = new Scanner(f3);
		FileReader f4 = new FileReader(gainFile);
		Scanner sc4 = new Scanner(f4);
		
		int n_of_e=0;
		for(int i=0; i<loopcnt; i++) /////mfcc배열의 길이 만큼
		{
			for(int j=0; j<inputnum;j++)
			{
				data[i][j] = sourceData[i][j];	//값 대입
			
			}			
			n_of_e++;
		}
			for(int j=0; j<inputnum;j++)
			{
				xoffset[j] = sc3.nextDouble();
				if(sc3.hasNextDouble()==false)	break;
			}			
			for(int j=0; j<inputnum;j++)
			{
				gain[j] = sc4.nextDouble();
				if(sc4.hasNextDouble()==false)	break;
			}
		sc3.close();
		sc4.close();
		return n_of_e;
	}
	
	
	
	/*중간층 가중치 및 임계치 초기화*/
	public static void initwh(File file1,File file2, double[][] wh,double[] bias1) throws FileNotFoundException {
	int i,j;
	FileReader f = new FileReader(file1); //가중치 
	Scanner sc = new Scanner(f); 
	FileReader f2 = new FileReader(file2); //임계치
	Scanner sc2 = new Scanner(f2);
	for(i=0; i<hiddennum; i++) { 
		for(j=0; j<inputnum;j++) {
			wh[i][j] = sc.nextDouble();
			//System.out.print(wh[i][j]+ " ");
			}
		bias1[i] = sc2.nextDouble();
		//System.out.println();
		}
	sc.close();
	sc2.close();
	}
	/*출력층 가중치 및 임계치 초기화*/
	public static void initwo(File file1, File file2, double[][] wo,double[] bias2) throws FileNotFoundException
	{
		int i,j;
		FileReader f = new FileReader(file1); //가중치
		Scanner sc = new Scanner(f); 
		FileReader f2 = new FileReader(file2); //임계치
		Scanner sc2 = new Scanner(f2);
		
		for(i=0;i<outputnum;i++)
		{
			for(j=0;j<hiddennum;j++)
			{	
				wo[i][j] = sc.nextDouble();
			}
			bias2[i]= sc2.nextDouble();
		}
		sc.close();
		sc2.close();
	}
	public static void print(double[][] wh, double[][] wo,double[] bias1,double[] bias2)
	{
		int i,j;
		for(i=0;i<hiddennum;i++)
		{
			for(j=0;j<inputnum;j++)
				System.out.print(wh[i][j]+" ");
			System.out.println();
		}
		System.out.println();
		for(i=0; i<outputnum; i++)
		{
			for(j=0; j<hiddennum; j++)
				System.out.print(wo[i][j]+" ");
			System.out.println();
		}
		System.out.println();
		
			for(j=0; j<hiddennum; j++)
				System.out.print(bias1[j]+" ");
			System.out.println();
			for(j=0; j<outputnum; j++)
				System.out.print(bias2[j]+" ");
			System.out.println();
	}
	public static void bsxfun(int iteration, double[] y,double[] data, double[] xoffset, double[] gain)
	{
		for(int i =0;i<iteration;i++)
		{
			y[i] = data[i]-xoffset[i];
		}
		for(int i=0;i<iteration;i++)
		{
			y[i] = y[i]*gain[i];
		}
		for(int i=0;i<iteration;i++)
		{
			y[i] = y[i] + ymin;
		}
	}
}
