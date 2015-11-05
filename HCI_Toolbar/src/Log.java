
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	String fileName = System.currentTimeMillis() + "";
	FileWriter fw;
	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	
	public Log(){
		try {
			fw = new FileWriter(fileName + ".txt");
			fw.write("Log started.  Date: " + df.format(date) + "\n");
			fw.flush();
		} catch (IOException e) {
			System.out.println("LOGGING ERROR");
			e.printStackTrace();
		}
		
	}
	
	public Log(String name){
		try {
			fw = new FileWriter(fileName + name + ".txt");
			fw.write("Log started.  Date: " + df.format(date) + "\n");
			fw.flush();
		} catch (IOException e) {
			System.out.println("LOGGING ERROR");
			e.printStackTrace();
		}
		
	}
	
	public void out(String output){
		try {
			System.out.print(output);
			fw.write(output);
			fw.flush();
		} catch (IOException e) {
			System.out.print("LOGGING ERROR");
			e.printStackTrace();
		}
	}

	public void nout() {
		out("\n");
	}
	
	public void hout(String output){
		try {
			fw.write(output + "\n");
			fw.flush();
		} catch (IOException e) {
			System.out.println("LOGGING ERROR");
			e.printStackTrace();
		}		
	}
	
	public void nout(String output){
		out(output + "\n");	
	}
	
	public void EndLog(){
		try {
			fw.close();
			System.out.println("File saved to " + fileName + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
