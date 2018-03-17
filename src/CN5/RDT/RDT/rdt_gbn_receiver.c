#include "net_exp.h"

void usage( char **argv )
{
	printf( "wrong argument!\n" );
	printf( "usage: %s save_file_name\n",  argv[0] );
}

/*
	Go-Back-N Ð­ÒéœÓÊÕ¶ËœÓÊÜº¯Êý
	ÊäÈë²ÎÊý£º
		save_file_name£º ±£ŽæÎÄŒþÃû
		sock_fd£ºœÓÊÜÊýŸÝµÄsocket
*/
int receive_file( char *save_file_name, int sock_fd )
{
	char reply_pkt_buf[RDT_PKT_LEN];
	int reply_pkt_len;
	
	char rdt_pkt[RDT_PKT_LEN];//rdt_pkt包
	char rdt_data[RDT_DATA_LEN];//rdt数据
	uint32_t seq_net_order;
	int seq_num;//数据包的序列号
	int flag;
	int exp_seq_num;
	
	int total_recv_byte = 0;
	
	struct sockaddr_in client_addr;
	int i, j, sin_len, pkt_len, data_len;
	
	int counter = 1;
	FILE *fp;
	
	if( (fp = fopen( save_file_name, "w" )) == NULL )//打开save_file_name文件
	{
		printf( "open file : %s failed.\n",  save_file_name );
		return 1;
	}
	
	memset( &client_addr, 0, sizeof(client_addr) );//初始化clinet_addr
	sin_len = sizeof( client_addr );

	exp_seq_num = RDT_BEGIN_SEQ;//rdt数据包的初始序列号
	 
	//TODO
	while(1) //œÓÊÕRDTÊýŸÝ°ü£¬Ö±µœËùÓÐÊýŸÝÈ«²¿œÓÊÕÍê±Ï
	{			
		/*
			step 1. œÓÊÕRDTÊýŸÝ°ü :	recvfrom()
			step 2. œâ·â×°RDTÊýŸÝ°ü : unpack_rdt_pkt()
			step 3. Œì²éŽËÊýŸÝ°üÊÇ·ñÎªÆÚŽýµÄÊýŸÝ°ü : seq_num==exp_seq_num
			step 4. ·â×°Ò»žöÐÂµÄRDTÊýŸÝ°ü(ACK°ü) : pack_rdt_pkt()
			step 5. µ÷ÓÃ²»¿É¿¿ÊýŸÝŽ«Êä·¢ËÍÐÂµÄRDTÊýŸÝ°ü(ACK°ü): udt_sendto()
		*/													
	
		if(recvfrom(sock_fd,rdt_pkt,sizeof(rdt_pkt),0,(struct sockaddr*)&client_addr,&sin_len)==0)
			{
				printf("error recvfrom");
				break;
				};
			
		if(unpack_rdt_pkt(rdt_data,rdt_pkt,sizeof(rdt_pkt),&seq_num,&flag)==0)
			{printf("error unpack");
			break;}
			pack_rdt_pkt(rdt_data,rdt_pkt,sizeof(reply_pkt_buf),seq_num,flag);//创建应答包
			udt_sendto(sock_fd,rdt_pkt,sizeof(rdt_pkt),RDT_CTRL_ACK ,(struct sockaddr*)&client_addr,sin_len);
			
		if(seq_num==exp_seq_num)//序列号比对相等时将回复应答包
			{
			//*reply_pkt_buf=rdt_data;
			//reply_pkt_len=sizeof(reply_pkt_buf);
			exp_seq_num++;
			fprintf(fp,"%s\n",rdt_data);//将收到的数据存入文件中
			total_recv_byte+=sizeof(rdt_data);
				}
}
	printf( "\n\nreceive file succeed. write to file %s\ntotal recv %d byte\n", 
				save_file_name, total_recv_byte );
		
	fflush( fp );
	fclose( fp );
	return 0;
}

int main( int argc, char **argv )
{
	struct sockaddr_in recv_addr;
	int sin_len;
	int sock_fd;
	int pkt_len;
	
	srand ( time(NULL) );
	
	if( argc != 2 )
	{
		usage( argv );
		exit(0);
	}
	
	memset( &recv_addr, 0, sizeof(recv_addr) );
	recv_addr.sin_family = AF_INET;
	recv_addr.sin_addr.s_addr = htonl( INADDR_ANY );
	recv_addr.sin_port = htons( RDT_RECV_PORT );
	
	if( ( sock_fd = socket( AF_INET, SOCK_DGRAM, 0 ) ) == -1 )
	{
		printf( "error! information: %s\n", strerror(errno) );
		exit(1);	
	}
	
	if( bind( sock_fd, (struct sockaddr *)&recv_addr, sizeof(recv_addr) ) == -1 )
	{
		close( sock_fd );
		printf( "error! information: %s\n", strerror(errno) );
		exit(1);	
	}
	
	if( receive_file( argv[1], sock_fd ) != 0 )
	{
		printf( "receive file %s failed.\n", argv[1] );
		close( sock_fd );
		exit(1);
	}
	
	close( sock_fd );
	return 0;
}

