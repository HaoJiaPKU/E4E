package temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Random;

import backup.Path;

public class GenerateHour {
	
	public static void main(String [] args){
		String [] job = {};
		String [] grade = {};
		String [][] courseNames = new String [grade.length][job.length];
		
		String fileName = Path.courseNamePath;
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			line = reader.readLine();
			job = line.trim().split("	");
			line = reader.readLine();
			grade = line.trim().split("	");
			courseNames = new String [grade.length][job.length];
			
			int gradeIndex = 0, jobIndex = 0;
			while((line = reader.readLine()) != null){
				courseNames[gradeIndex][jobIndex] = line.trim();
				jobIndex ++;
				if(jobIndex >= job.length){
					jobIndex = 0;
					gradeIndex ++;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileName = Path.courseInfoPath;
		file = new File(fileName);
		HashSet <String> courseName = new HashSet <String> ();
		Random random = new Random();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
			BufferedWriter bw = new BufferedWriter(osw);
			for(int i = 0; i < grade.length; i ++){
				for(int j = 0; j < job.length; j ++){
					if(courseNames[i][j].length() == 0)
						continue;
					String [] temp = courseNames[i][j].trim().split(" ");
					for(int k = 0; k < temp.length; k ++){
						if(!courseName.contains(temp[k])){
							courseName.add(temp[k]);
							bw.write(temp[k] + "	"
									+ ((Math.abs(random.nextInt()) % 20 + 1.0) / 2.0) + "	"
									+ (Math.abs(random.nextInt()) % 10 + 1));
							bw.newLine();
						}
					}
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
