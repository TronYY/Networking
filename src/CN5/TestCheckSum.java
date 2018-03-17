package CN5;

import java.io.*;
import java.util.zip.*;

public class TestCheckSum {

	
	public static void main(String argv[]) throws IOException {
		FileInputStream in = new FileInputStream("E:/Computer/Java/Project/Networking/src/CN5/HappyNewYear1.txt");
		CheckedInputStream checked = new CheckedInputStream(in, new Adler32());
		byte[] b = new byte[4096];
		while ((checked.read(b)) != -1) {
		}
		in.close();
		checked.close();
		System.out.println("checksum: " + checked.getChecksum().getValue());
	}

}
