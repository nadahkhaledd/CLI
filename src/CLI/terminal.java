package CLI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

public class terminal {
	
	static String defaultPath="C:\\";
	
	public String help()
	{
		String data = "clear: Clear the terminal\n"
				+ "cd: Change current directory\n"
				+ "ls: List all contents of current directory\n"
				+ "cp: Copy file\n"
				+ "mv: Moves file\n"
				+ "rm: Deletes file\n"
				+ "mkdir: Create new directory\n"
				+ "rmdir: Delete directory\n"
				+ "cat: Displays contents of a file and concatenates files and display output\n"
				+ "more: Let us display and scroll down the output in one direction only\n"
				+ "pwd: Display the absolute path of current directory\n"
				+ "args: List all command arguments\n"
				+ "date: Display current date and time\n"
				+ "exit: stop all";
		return data;

	}
	
	public String args(String cmd)
	{
		switch(cmd)
		{
		case "clear" : return "No arguments\n";
		case "cd" : return "cd sourcePath: direct to sourcePath\ncd .. : go to previous directory\ncd : head to default";
		case "ls" : return "No arguments\n";
		case "cp" : return "arg1: sourcePath, arg2: destinationPath\n";
		case "mv" : return "arg1: sourcePath, arg2: destinationPath\n";
		case "rm" : return "arg1: fileName\n";
		case "mkdir" : return "arg1: directoryName\n";
		case "rmdir" : return "arg1: directoryName\n";
		case "cat" : return "arg1: filePath, arg2: filePath ,....\n";
		case "more" : return "No arguments\n";
		case "pwd" : return "No arguments";
		case "args" : return "arg1: commandName";
		case "date" : return "No arguments";
		case "exit" : return "No arguments";
		}
		return "";
	}
	
	public void clear()
	{ 
		for (int i = 0; i < 60; i++) {
			System.out.print("\n");
		}
	}
	
	public String pwd()
	{
		return defaultPath;
	}
	
	public void rm(String name)
	{
		if(name.contains(":"))
		{
			File path = new File(name);
			path.delete();
		}
		else 
		{
			File path = new File(defaultPath +"\\" +  name);
			path.delete();
		}
		
	}
	
	public boolean mkdir(String path)
	{
		File mkfile = new File(defaultPath + "\\" + path);
		return mkfile.mkdir();
	}

	public void rmdir(String dir) throws IOException
	{
		dir = defaultPath + "\\" +  dir;
		File path = new File(dir);
		if (path.list().length == 0)
		{
			path.delete();
		}
		if(path.exists() == false)
		{
			System.out.println("Can't remove nonexistent file");
		}
	}
	
	public void cd(String arg)
	{
		Path p = Paths.get(defaultPath);
		if (arg.equals(".."))
		{
			p = p.getParent();
			defaultPath = String.valueOf(p.toAbsolutePath());
		}
		else if (arg.contains(":"))
		{
			File test = new File(arg);
			if(!test.exists())
			{
				System.out.println("Not Found");
				return;
			}
			else 
			{
				p = Paths.get(arg);
				defaultPath = String.valueOf(p.toAbsolutePath());
			}
			
		}
		else 
		{		
			File test = new File(defaultPath + "\\" + arg);
			if(!test.exists())
			{
				System.out.println("Not Found");
				return;
			}
			else 
			{
				defaultPath = String.valueOf(p.toAbsolutePath() + "\\" + arg);
			}
			
		}
		
	}
	
	public String ls()
	{
		File l = new File(defaultPath);
		String data = "";
		for(int i=0; i<l.list().length; i++)
		{
			data += l.list()[i] + "\n";
		}
		return data;
	}
	
	public void cp(String sourcePath, String destPath) throws IOException
	{
		FileOutputStream dest ;
		if(!sourcePath.contains("\\"))
		{
			sourcePath = defaultPath + "\\" +  sourcePath;
		}
		FileInputStream path = new FileInputStream(sourcePath);
		
		File size = new File(sourcePath);
		String name = size.getName();
		if(!destPath.contains("\\"))
		{
			 dest = new FileOutputStream(defaultPath+"\\" + destPath);
		}
		else 
		{
			 dest = new FileOutputStream(destPath+"\\" + name);
		}
		byte[] data = new byte[(int) size.length()];
		int size2;
		while ((size2 = path.read(data)) > 0)
		{
			dest.write(data, 0, size2);
		}
		path.close();
		dest.close();
	}
	
	public void mv(String sourcePath, String destPath) throws IOException
	{
		cp(sourcePath, destPath);
		rm(sourcePath);
	}
		
	public String date()
	{
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");  	    
	    String afterformat = now.format(format);
	    return afterformat;
	}
	
	public String cat(String[] paths) throws IOException {
		String data = "";
		for(int i = 1; i < paths.length; i++) {
			File f = null;
			if(paths[i].contains(":"))
			{
				f = new File(paths[i]);
			}
			else 
			{
				f = new File(defaultPath + "\\" + paths[i]);
			}
			
			if(f.exists()) {
				Scanner myReader = new Scanner(f);
				
				while (myReader.hasNextLine())
					data += myReader.nextLine() + "\n";
				
				myReader.close();
			}
		}
		
		File file = new File(defaultPath + "\\" + paths[1]);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(data);
		fileWriter.close();
		return data.trim();
	}
	
	public void more(String filePath) throws FileNotFoundException {
		Vector<String> vec = new Vector<>();
		File f = null;
		if(filePath.contains(":"))
		{
			f = new File(filePath);
		}
		else 
		{
			f = new File(defaultPath + "\\" + filePath);
		}
    	
    	if(f.exists()){
    		Scanner myReader = new Scanner(f);
    		
        	while (myReader.hasNextLine())
        		vec.add(myReader.nextLine());

        	myReader.close();
    		Scanner scanner = new Scanner(System.in);
    	    String readString = scanner.nextLine();
    	    int b = 0;
    	    
    	    while(readString!=null) {
    	    	
    	        if (readString.isEmpty()) {
    	            for (int i = 0; b < vec.size() && i < 10; i++,b++) {
    	            	System.out.println(vec.get(b));
    				}
    	            if(b == vec.size())
    	            	break;
    	        }

    	        if (scanner.hasNextLine()) {
    	            readString = scanner.nextLine();
    	        } else {
    	            readString = null;
    	        }
			}

		}
		
    	else 
    	{
    		System.out.println("Wrong file");
    	}
	    
	    
	}
	
	public void exit()
	{
		System.exit(0);
	}
}
