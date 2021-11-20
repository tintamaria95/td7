/**
 *	TP7 JAVA AVANCE 
 */


package nio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

public class DirMonitor {
	Path path;

	public DirMonitor(Path p) throws IllegalArgumentException {
		if (Files.isReadable(p) && Files.isDirectory(p)) {
			System.out.println("Path ok");
			this.path = p;
		} else {
			throw new IllegalArgumentException("Invalid Path");
		}
	}

	public void showFilesAndRepositories() throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			Iterator<Path> iterator = ds.iterator();
			while (iterator.hasNext()) {
				Path p = iterator.next();
				System.out.println(p);
			}
		}
	}

	public long getFileSize() throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			Iterator<Path> iterator = ds.iterator();
			long bytes = 0;
			while (iterator.hasNext()) {
				Path p = iterator.next();

				if (Files.isRegularFile(p)) {
					bytes = bytes + Files.size(p);
				}
			}
			return bytes;
		}
	}

	public String getLastModified() throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			Iterator<Path> iterator = ds.iterator();
			long bytes = 0;
			Path p_lastmodified = iterator.next();
			FileTime lastmodified = Files.getLastModifiedTime(p_lastmodified);
			while (iterator.hasNext()) {
				Path p = iterator.next();
				if (Files.isRegularFile(p)) {
					if (Files.getLastModifiedTime(p).compareTo(lastmodified) > 0) {
						lastmodified = Files.getLastModifiedTime(p);
						p_lastmodified = p;
					}
				}
			}
			return p_lastmodified.toString();
		}
		
	}

	public static void main(String[] args) throws IOException {
		Path p = Paths.get("C:\\Users\\tinta\\Documents\\COURS M1");
		DirMonitor dir = new DirMonitor(p);
		dir.showFilesAndRepositories();
		System.out.println(dir.getFileSize());
	}
}
