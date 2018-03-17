package CN3;

import java.io.Console;

public class Console1 {

	public static void main(String[] args){
		Console console = System.console();
		String password ;
		password = new String(console.readPassword());
		System.out.println("password="+password);
	}
}