#### ʹ�� OpenSSL ������ǩ��֤��


## 1.������֤���˽Կ
openssl genrsa -out ca.key 1024

## 2.ʹ��˽Կ������֤��
openssl req -new -x509 -days 36500 -key ca.key -out ca.crt -subj "/C=CN/ST=Fujian/L=Xiamen/O=Your Company Name/OU=Your Root CA"

## 3.����������˽Կ
openssl genrsa -out server.key 1024

## 4.ʹ�÷�����˽Կ����֤�������ļ�
openssl req -new -key server.key -out server.csr -subj "/C=CN/ST=Fujian/L=Xiamen/O=Your Company Name/OU=youwebsite.org/CN=yourwebsite.org"

## 5.׼������
mkdir -p demoCA/newcerts
touch demoCA/index.txt
echo '01' > demoCA/serial

## 6.����������֤�鲢ʹ��ca��֤��ǩ��
openssl ca -in server.csr -out server.crt -cert ca.crt -keyfile ca.key


## ---�鿴��ͬ��ʽ�ļ������������﷨
# openssl rsa -noout -text -in ca.key
# openssl x509 -noout -text -in ca.crt
# openssl rsa -noout -text -in server.key
# openssl req -noout -text -in server.csr
# openssl x509 -noout -text -in server.crt

## ����֤����򵥷�ʽ
# openssl r