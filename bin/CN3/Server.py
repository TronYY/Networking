#socket server��
#��ȡsocket���켰����
from socket import *
#''���������Ϊlocalhost
myHost = ''
#��һ���Ǳ����˿ں��Ͻ��м���
myPort = 10000

#����һ��TCP socket����
sockobj = socket(AF_INET, SOCK_STREAM)
#�������˿ں�
sockobj.bind((myHost, myPort))
#����������5������
sockobj.listen(5)

#ֱ�����̽���ʱ�Ž���ѭ��
while True:
    
    #�ȴ���һ���ͻ�������
    connection, address = sockobj.accept( )
    #������һ���µ�socket
    print ('Server connected by', address)
    while True:
        #��ȡ�ͻ����׽��ֵ���һ��
        data = connection.recv(1024)
        #���û�������Ļ�����ô����ѭ��
        if not data: break
        #����һ���ظ����ͻ���
        connection.send('Echo=>' + data)
    #��socket�ر�ʱeof
    connection.close( )
