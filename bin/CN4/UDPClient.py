from socket import *

serverName = 'localhost'
serverPort = 2341
ADDR = (serverName, serverPort)

#�����ͻ���UDP�׽���
clientSocket = socket(AF_INET, SOCK_DGRAM)

while True:
        data =raw_input('Please input:')
        if not data:
                break
        #��������˷�������
        clientSocket.sendto(data,ADDR)
        #�������Է������˵�����
        data, ADDR = clientSocket.recvfrom(1024)
        print data
        if not data:
                break
clientSocket.close()
