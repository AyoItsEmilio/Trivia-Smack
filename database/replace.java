package comp4350;

import java.io.*;

public class replace {
	
	public static void main(String[] args) throws Exception {
		String thisLine = null;
		String subStr = null;
		String line = null;
		//StringBuilder newLine;
		try {
			FileReader fr = new FileReader("c:/Users/xzl/Desktop/db_questions.txt");
			PrintWriter wr = new PrintWriter("new_db_question.txt", "UTF8");
			BufferedReader br = new BufferedReader(fr);
			
			thisLine = br.readLine();
			while ((thisLine) != null) {
				if (thisLine.length() > 2)
				{
					subStr = thisLine.substring(5,13);
					
					if ( subStr.compareTo("question") == 0 )
					{	
						line = thisLine;
						int count = line.length() - line.replace("\"", "").length();
						if (count > 4){
							line = line.replaceAll("\"", "'");
							
							if(line.charAt(line.length()-1) == ','){
								line = line.substring(0, 4)+"\""+line.substring(5,13)+"\""+line.substring(14,15)+"\""+line.substring(16,line.length()-2)+"\""+line.substring(line.length()-1);
							}else{
								System.out.println("Errot: last char is not ,");
							}
							thisLine = line;
						}
					}
				}
				wr.println(thisLine);
				System.out.println(thisLine);
				thisLine = br.readLine();
			}
			wr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}