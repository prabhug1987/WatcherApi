package com.example.watcher;

import java.util.EventObject;
import java.io.File;

public class FileEvent extends EventObject{

	public FileEvent(File file) {
		super(file);
	}
	
	public File getFile() {
		return getFile();
	}
}
