package jdk.compatible.java.nio.file;

import java.io.File;

public class Path {
	public File file;
	public String toString(){
		return file.getPath();
	}
	
	public File toFile(){
		return file;
	}
}
