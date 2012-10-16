package ch.zhaw.inf3.emulator;
	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;
	
	
public class FileParser {
	
		public List<String> load(File file) throws IOException, FileNotFoundException{
			 List<String> list = new ArrayList<String>();
			 BufferedReader in = null;
			 try{
				in = new BufferedReader(new FileReader(file));
				
				String line = in.readLine();
				while (line != null){
					list.add(line);
					line = in.readLine();
				}
			 return list;
			 }finally{
					 if (in != null) {
						 in.close();
				 }
			 }
		}
}
