from socket import*
from time import ctime

HOST = ''
serverPort = 2341
ADDR = (HOST, serverPort)

#����һ����������UDP�׽���
serverSocket = socket(AF_INET, SOCK_DGRAM)
#�󶨷������׽���
serverSocket.bind(ADDR)

while True:
    	print 'waiting for message...'
	#�������Կͻ��˵�����
    	data, addr = serverSocket.recvfrom(1024)
	#��ͻ��˷�������
    	serverSocket.sendto('[%s] %s' % (ctime(), data), addr)
    	print '...received from and returned to:', addr
	

