import java.net.Socket;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.*;
import java.util.Collections;
public class  Server
{
  //定义保存所有socket的ArrayList，并将其包装为线程安全的
  public static List<Socket> socketList=Collections.synchronizedList(new ArrayList<>());

  public static void main(String[] args)throws IOException
  {
    //创建一个serverSocket,用于监听客户端Socket的请求
    ServerSocket serversocket=new ServerSocket(30000);
    //采取循环不断地接收来自客户端的请求
    while(true)
    {
      //每当接收到客户端socket的请求时，服务端都生成一个对应的socket
      Socket s=serversocket.accept();

      socketList.add(s);

      //每当客户端连接猴启动一个serverthread线程为客户端服务
      new Thread(new SerServerThread(s)).start();
    }
  }
}
