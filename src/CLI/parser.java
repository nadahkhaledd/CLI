package CLI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;




public class parser {
	
	String[] args=new String[3];
	boolean flag = false;
	terminal terminall = new terminal();
	String cmd;
	String [] cmds= {"clear","cd","ls","cp","mv","rm","mkdir","rmdir","cat","more","pwd","exit","help","date", "args"};
	BufferedWriter bw = null;
	FileWriter fw = null;
	String dt = "";
	
	public boolean pipe(String input) throws IOException
	{
		String _1st = "", _2nd = "";
		boolean f = false;
		for(int i=0; i<input.length(); i++)
		{
			if(input.charAt(i) == '|') {
				f  = true;
				continue;
			}
				
			if(f)
				_2nd += input.charAt(i);
			else
				_1st += input.charAt(i);
		}
		if(!f)
			return false;
		
		if(_2nd.trim().equalsIgnoreCase("more"))
			flag = true;

		if(!parse(operator(_1st.trim())))
			System.out.println("Wrong Command (try help)");
		if(!parse(operator(_2nd.trim())))
			System.out.println("Wrong Command (try help)");
		return true;
	}
	
	public String operator(String command) throws IOException {
		String[] data;
		
		if(command.contains(" > ")) {
			data = command.split(" > ");
			File file;
			Path p = Paths.get(terminal.defaultPath + "\\" + data[1].trim());
			if(data[1].contains(":"))
				file = new File(data[1].trim());
			else
				file = new File(String.valueOf(p.toAbsolutePath()));

		    if (!file.exists()) {
		        file.createNewFile();
		    }

		    fw = new FileWriter(file.getAbsoluteFile());
		    bw = new BufferedWriter(fw);
		   
		} else if(command.contains(" >> ")) {
			data = command.split(" >> ");
			
			Path p = Paths.get(terminal.defaultPath + "\\" + data[1].trim());
			
			File file;
			if(data[1].contains(":"))
				file = new File(data[1].trim());
			else
				file = new File(String.valueOf(p.toAbsolutePath()));
		    if (!(file.exists())) {
		        file.createNewFile();
		    }

		    fw = new FileWriter(file.getAbsoluteFile(),true);
		    bw = new BufferedWriter(fw);
		    
		} else {
			return command;
		}
		return data[0].trim();
	}

	public void printData(String data) throws IOException {
		if(bw == null && (!flag)) {
			System.out.println(data);	
		} else if(dt == "" && flag){
			File file = new File(terminal.defaultPath+"\\" +"zxcv.txt");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(data);
			fileWriter.close();
			dt = terminal.defaultPath+"\\" +"zxcv.txt";
		}else if(bw != null) {
			bw.write(data);
			bw.close();
			bw = null;
			fw = null;
		}
	}
	
	public boolean parse(String input) throws IOException
	{
		String[] str = input.split(" ");
		
		for (int i=0; i<15; i++)
		{
			if(str[0].equals(cmds[i]))
			{
				cmd=str[0];
				if(i==0)
				{
					if(str.length == 1)
					terminall.clear();
					else 
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
				}
				else if(i==1)
				{
					
					if(str.length==2)
					{
						args[0] = str[1];
						terminall.cd(args[0]);
							
					}
					else if(str.length == 1)
					{
						terminall.cd("C:\\");
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==2)
				{
					if(str.length==1)
					{
						printData(terminall.ls());
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==3)
				{
				    if(str.length==3)
					{
						args[0] = str[1];
						args[1] = str[2];
						terminall.cp(args[0],args[1]); 
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==4)
				{
				    if(str.length==3)
					{
						args[0] = str[1];
						args[1] = str[2];
						terminall.mv(args[0], args[1]); 
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==5)
				{
				    if(str.length==2)
					{
						args[0] = str[1];
						terminall.rm(args[0]); 
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==6)
				{
				    if(str.length==2)
					{
						args[0] = str[1];
						terminall.mkdir(args[0]); 
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==7)
				{
				    if(str.length==2)
					{
						args[0] = str[1];
						terminall.rmdir(args[0]); 
					}
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==8)
				{
				    if(str.length==3)
					{
						args[0] = str[1];
						args[1] = str[2];
						dt = str[1];
						terminall.cat(str); 
					}
				    else if(str.length==2)
				    {
				    	args[0] = str[1];
				    	dt = str[1];
				    	printData(terminall.cat(str)); 
				    }
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==9)
				{
				    if(str.length==2)
				    {
				    	args[0] = str[1];
				    	
						terminall.more(str[1]); 
						flag = false;
						dt = "";
				    }
				    else if(str.length==1 && dt != "" && flag) 
				    {
				    	terminall.more(dt);
				    	if(dt.equalsIgnoreCase(terminal.defaultPath+"\\" +"zxcv.txt"))
				    		   new File(dt).delete();
				    	flag = false;
				    	dt = "";
				    }
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
				}
				else if (i==10)
				{
					if(str.length == 1)				
						printData(terminall.pwd());
					else 
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
				}
				else if (i==11)
				{
					if(str.length == 1)
						terminall.exit();
					else 
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
				}
				else if (i==12)
				{
					if(str.length == 1)
						printData(terminall.help());
					else 
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
				}
				else if (i==13)
				{
					if(str.length == 1)
						printData(terminall.date());
					else 
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
				}
				else if (i==14)
				{
					if(str.length==2)
				    {
				    	args[0] = str[1];
				    	printData(terminall.args(args[0]));
				    }
					else
					{
						System.out.println("Wrong arguments (try 'args commandName' to know the proper arguments)");
					}
					
				}
				if(bw != null)
					bw.close();
				return true;
			}
			
		}
		if(bw != null)
			bw.close();
		return false;
	}
	
	public String getCmd()
	{
		return cmd;
	}
	
	public String[] getArguments()
	{
		return args;
		
	}

}
