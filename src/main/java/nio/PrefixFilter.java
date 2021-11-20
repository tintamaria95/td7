package nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class PrefixFilter implements DirectoryStream.Filter<Path> {

	@Override
	public boolean accept(Path entry) throws IOException {
		try {
			DirMonitor d = new DirMonitor(entry);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

}
