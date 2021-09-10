package CLI;

import java.io.IOException;
import java.util.Scanner;

public class main {
	static boolean flag = true;
	static parser parserr = new parser();


	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		
		while(flag)
		{
			System.out.print( "\n~" + terminal.defaultPath +  "$ ");
			String command = input.nextLine();
			command = command.trim();
			parserr.dt = "";
			parserr.flag = false;
			if(command.isEmpty()) 
			{
				continue;
			} 
			else if(parserr.pipe(command))
		    {

		    }
			else	
			{
				if(!parserr.parse(parserr.operator(command)))
			    {
			    	 System.out.println("Wrong Command (try help)");
			    }
			}

		}
		
	}

}
