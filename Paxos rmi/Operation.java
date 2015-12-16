import java.util.*;

public class Operation implements java.io.Serializable
{
	int op_num;
	String content;
	
	Operation(int op_num, String content){
		this.op_num = op_num;
		this.content = content;
	}
	
}