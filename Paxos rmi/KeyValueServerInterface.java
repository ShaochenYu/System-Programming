import java.rmi.*;
import java.util.*;


public interface  KeyValueServerInterface extends Remote
{	
	
	public ArrayList<KeyValueServerInterface> getServersList() throws RemoteException;
	public String getServerName() throws RemoteException;
	public void dump() throws RemoteException;
	public Instance prepare(int ins_num, int income_seq) throws RemoteException;
	public int accept(int ins_num, int income_seq, String content ) throws RemoteException;
	public void decide(int income_seq ,Operation op) throws RemoteException;
	public int propose( int seq, Operation op) throws RemoteException;
	public boolean sendAllAccept(int ins_num, int income_seq, String content, ArrayList<KeyValueServerInterface> list) throws RemoteException;
	public HashMap<String, String> getMap() throws RemoteException;
	public HashMap<Integer, Instance> getInstances() throws RemoteException;
	public int getNumOfIns() throws RemoteException;
	public void setWork(boolean work) throws RemoteException;
}

