import java.util.*;
//import java.rmi.registry.*;

public class Instance implements java.io.Serializable
{
	
	boolean decided;
	int n_p, n_a;
	String content;
	
	Instance(){
		decided = false;
		n_p = -1;
		n_a = -1;
	}

	public String toString(){
		
		return new String(decided +":"+ n_p + ":" + n_a + ":" + content);
		
	}
	
}