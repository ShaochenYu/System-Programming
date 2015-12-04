***********************************

	HOW TO RUN THE PROJECT 

***********************************

0. In terminal A, enter RMI directory, run "javac *.java"

1. In terminal A, Under RMI directory, run "rmiregistry"

2. In terminal B, enter RMI directory, run "java RegisterWithKeyValueServer"

3. In terminal C, enter RMI directory, run "java KeyValueServerInterfaceClient ip_address"
	*ip_address is the ip address your server run on. if you run it locally, just enter your local ip address
	*if you run server on different machine, please compile KeyValueServerInterface.java with RegisterWithKeyValueServer.java, then ping server ip address on client machine, that is to confirm your os can find server machine, if you have connection timeout, then it may mean that your port number 1099 is occupied, so connection failed, you can restart your computer, or if you are using Linux, you may need to configure your system file. For other problems, please contact me.
	
4. shut down

