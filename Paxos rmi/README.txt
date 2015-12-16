***********************************

	HOW TO RUN THE PROJECT 

***********************************

0. In terminal A, enter Paxos rmi directory, run "javac *.java"

1. In terminal A, Under Paxos rmi directory, run "rmiregistry"

2. In terminal B, enter Paxos rmi directory, run "java RegisterWithKeyValueServer"

3. In terminal C, enter Paxos rmi directory, run "java KeyValueServerInterfaceClient"

4. shut down

*****************************************

   WHERE TO CHECK THE RUNNING RESULT

*****************************************

The running results have been showed on terminals.

The debug information of servers show on server terminal, like:

server4:
1: true:2:2:PUT/123/andy
2: true:3:3:GET/123
3: true:4:4:PUT/234/jack
4: true:5:5:GET/234
5: false:-1:-1:null
6: true:6:6:GET/345
7: false:-1:-1:null
8: true:7:7:GET/123
9: false:-1:-1:null
10: true:8:8:GET/123
11: false:-1:-1:null
12: true:10:10:GET/123
13: true:11:11:PUT/456/rose
14: true:12:12:GET/456
15: true:13:13:DEL/456
16: true:14:14:GET/456
17: false:-1:-1:null
18: false:-1:-1:null
19: false:-1:-1:null
20: false:-1:-1:null


The format is 
server name:
number of operation: decided or not : accepted prepare time sequence: accepted time sequence: accepted operation content


The result of clients show on client terminal, like:

Get operation: andy
Get operation: jack
Get operation: null
Get operation: andy
Get operation: andy
Get operation: null
Get operation: rose
Get operation: null

These are the results of get operations.

*****************************************

  WHERE TO READ THE RUNNING RESULT

*****************************************

The test cases have been designed for different conditions that need to be considered.
The followings are the test cases in the project, defined in KeyValueServerInterfaceClient.java


		/*
		test case:
		1 compete on the same operation( operation number is the same)
		2 server fails, if majority of servers fail, consensus will not be achieved, if minority of servers fail, consensus will be achieved.
		3 update at different servers, get at different servers , achieve data consistence
		*/
		
		// server2 fail
		setFail(server2);
		
		put(server1, "123/andy");
		
		restart(server2);
		
		// compete the same Operation. operation number goes back.
		OPERATION_NUM = new Integer(OPERATION_NUM.intValue() - 1);
		
		put(server2, "123/andy");
		
		// get result, former operation is fullfilled 
		System.out.println("Get operation: " + get(server3, "123"));			// result:	Get operation: andy
		
		
		// server1 and server2 fail
		
		setFail(server1);
		setFail(server2);
		
		put(server3, "234/jack");
		
		restart(server1);
		restart(server2);
		
		// can achive consensus, majority of servers work
		System.out.println("Get operation: " + get(server3, "234"));			// result:	Get operation: jack
		
		// server2, server3, server5 fail 
		
		setFail(server2);
		setFail(server3);
		setFail(server5);
		
		put(server3, "345/rose");
		
		restart(server2);
		restart(server3);
		restart(server5);
		
		//can not achive consensus, majority of servers fail
		System.out.println("Get operation: " + get(server3, "345"));			// result:	Get operation: null
		
		
		setFail(server2);
		// request will not be effective, server2 fails
		delete(server2, "123");
		
		restart(server2);
		
		// deletion operation fail, record stay there
		System.out.println("Get operation: " + get(server3, "123"));			// result: Get operation: andy
		
		
		// server2, server3, server5 fail
		setFail(server2);
		setFail(server3);
		setFail(server5);
		
		delete(server3, "123");
		
		restart(server2);
		restart(server3);
		restart(server5);
		
		//deletion operation fail, record stay there
		System.out.println("Get operation: " + get(server3, "123"));			// result: Get operation: andy
		
		
		// server3, server4 fail
		setFail(server3);
		setFail(server4);
		
		delete(server5, "123");
		
		restart(server3);
		restart(server4);
		
		//deletion operation success, record is removed
		System.out.println("Get operation: " + get(server3, "123"));			// result: Get operation: null
		
		put(server2, "456/rose");
		
		System.out.println("Get operation: " + get(server3, "456"));			// result: Get operation: rost
		
		delete(server3, "456");
		
		System.out.println("Get operation: " + get(server4, "456"));			// result: Get operation: null
