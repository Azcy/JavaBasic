import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Thread;
public class Client{
  public static void main(String[] args)throws IOException {
    Socket socket=new Socket("127.0.0.1",30000);
    //客户端启动ClientThread线程不断地读取来自服务器的数据
    new Thread(new ClientThread(socket)).start();
    //获取该socket的输出流
    PrintStream ps=new PrintStream(socket.getOutputStream());

    //新建个BufferedReader brIn来获取键盘输入的值
    BufferedReader brIn=new BufferedReader(new InputStreamReader(System.in));
    String lineIn=null;
    while((lineIn=brIn.readLine())!=null)
    {
      //把用户输入内容写入socket对应的输出流
      ps.println(lineIn);
    }

  }
}
/**不停的接收服务端传过来的数据*/
public class ClientThread implements Runnable{
  //该线程负责处理的socket
  private Socket s;
  //该线程对应的socket输入流
  BufferedReader br=null;
  public ClientThread(Socket s)throws IOException{
    this.s=s;
    br=new BufferedReader(new InputStreamReader(s.getInputStream()));
  }

  public  void run(){
    try {
      String content = null;
      //不断地读取socket 输入流中的内容,并将这些内容打印输出
      while((content=br.readLine())!=null){
        System.out.println(content);
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }
}
