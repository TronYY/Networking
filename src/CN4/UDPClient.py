from socket import *

serverName = 'localhost'
serverPort = 2341
ADDR = (serverName, serverPort)

#创建客户端UDP套接字
clientSocket = socket(AF_INET, SOCK_DGRAM)

while True:
        data =raw_input('Please input:')
        if not data:
                break
        #向服务器端发送数据
        clientSocket.sendto(data,ADDR)
        #接收来自服务器端的数据
        data, ADDR = clientSocket.recvfrom(1024)
        print data
        if not data:
                break
clientSocket.close()
