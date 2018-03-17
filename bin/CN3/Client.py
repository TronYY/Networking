import sys
from socket import *
serverHost = "localhost"
serverPort = 10000

#发送至服务端的默认文本
message = ['Hello network world']
#如果参数大于1的话，连结的服务端为第一个参数
if len(sys.argv) > 1:
    serverHost = sys.argv[1]
    #如果参数大于2的话，连结的文字为第二个参数
    if len(sys.argv) > 2:
        message = sys.argv[2:]

#建立一个tcp/ip套接字对象
sockobj = socket(AF_INET, SOCK_STREAM)
#连结至服务器及端口
sockobj.connect((serverHost, serverPort))

for line in message:
    #经过套按字发送line至服务端
    sockobj.send(line)
    #从服务端接收到的数据，上限为1k
    data = sockobj.recv(1024)
    #确认他是引用的，是'x'
    print ('Client received:', repr(data))

#关闭套接字
sockobj.close( )
