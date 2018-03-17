from socket import*
from time import ctime

HOST = ''
serverPort = 2341
ADDR = (HOST, serverPort)

#创建一个服务器端UDP套接字
serverSocket = socket(AF_INET, SOCK_DGRAM)
#绑定服务器套接字
serverSocket.bind(ADDR)

while True:
    	print 'waiting for message...'
	#接收来自客户端的数据
    	data, addr = serverSocket.recvfrom(1024)
	#向客户端发送数据
    	serverSocket.sendto('[%s] %s' % (ctime(), data), addr)
    	print '...received from and returned to:', addr
	

