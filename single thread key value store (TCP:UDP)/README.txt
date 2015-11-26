**********************************

	HOW TO RUN THE PROJECT

**********************************

1.	configure ip address and port number in makefile under 
	parent directory.
	 
	you will see such content in makefile:
	
	<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	target:
		javac LogHelper.java
		javac TcpServer.java
		javac UdpServer.java
		javac TcpClient.java
		javac UdpClient.java
		javac UtilTool.java

	runtcpserver:
		java TcpServer 10000

	runtcpclient:
		java TcpClient 10.241.45.228 10000

	runudpserver:
		java UdpServer 10000

	runudpclient:
		java UdpClient 10.241.45.228 10000
	
	<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	change the ip address (10.241.45.228) to your local host,
	and change the port number (10000) to your like.
	
2.	In terminal, go to parent directory where you can find
	makefile, run "make", this will generate the whole .class you need to use.
	
	For TCP:(please run server before run client)
	
3.	In terminal, go to parent directory, run "make runtcpserver"
	you will start the tcp server.
	
4.  In a different terminal with that in Step 3, go to parent directory, run "make runtcpclient"
	you will start the tcp client.

5	In step 3's terminal, use control + C to shut down the tcp server.
	
	For UDP:(please run server before run client)
	
6. 	In terminal, go to parent directory, run "make runudpserver"
	you will start the udp server.

7.	In a different terminal with that in Step 6, go to parent directory, run "make runudpclient"
	you will start the udp client.

8.	In step 6's terminal, use control + C to shut down the udp server.

*****************************************

	WHERE TO CHECK THE RUNNING RESULT

*****************************************

The running results have been stored in log files in parent directory.
The following shows where are the applications' log.

tcp server log ------ tcpServerLogger
tcp client log ------ tcpClientLogger
udp server log ------ UdpServerLogger
udp client log ------ UdpClientLogger
