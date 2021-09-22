package com.example.watcher;

import static java.math.BigDecimal.ONE;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileWatcher implements Runnable {
	public int j = 0;
	protected int k = 0;
	private int i = 0;
	
	protected final File folder;
	protected List<FileListener> listeners = new ArrayList<>();

	protected static final List<WatchService> watchServices = new ArrayList<>();
	private static final Kind<?> ENTRY_CREATE = null;
	private static final Kind<?> ENTRY_MODIFY = null;
	private static final Kind<?> ENTRY_DELETE = null;

	public FileWatcher(File folder) {
		this.folder = folder;
	}

	public FileWatcher addListner(FileListener listener) {
		listeners.add(listener);
		return this;
	}

	public FileWatcher getListeners(List<FileListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	public static List<WatchService> getWatchServices() {
		return Collections.unmodifiableList(watchServices);
	}

	public FileWatcher removeListener(FileListener listener) {
		listeners.remove(listener);
		return this;
	}

	public FileWatcher setListener(List<FileListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	public void watch() {
		if (folder.exists()) {
			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		}
	}

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Paths.get(folder.getAbsolutePath());
			path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
			watchServices.add(watchService);
			boolean poll = true;
			while (poll) {
				poll = pollEvents(watchService);
			}
		} catch (InterruptedException | ClosedWatchServiceException | IOException e) {
			Thread.currentThread().interrupt();
		}

	}

	public static List<WatchService> getWatcherServices() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(watchServices);

	}

	protected boolean pollEvents(WatchService watchService)throws InterruptedException {
		WatchKey key = watchService.take();
		Path path = (Path) key.watchable();
		
		for(WatchEvent<?> event : key.pollEvents()) {
			notifyListners(event.kind(),path.resolve((Path)event.context()).toFile();
		}
	}

	protected void notifyListners(Kind<?> kind, File file) {
		// TODO Auto-generated method stub
		FileEvent event = new FileEvent(file);
		if(kind == E)
	}

}
