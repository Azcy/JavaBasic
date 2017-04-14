import java.net.*;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
public class UDPSendDemo  {
/**
*/
  public static void main(String[] args) throws SocketException, UnknownHostException,IOException
  {
    System.out.println("发送端启动.....");
    /**
    创建UDP传输的发送端
    思路：
    1.建立udp的socket服务
    2.将要发送的数据封装到数据包中。
    3.通过udp的socket服务将数据包发送出去
    4.关闭socket服务
    */
    //1.udpsocket服务，使用DatagramSocket对象。
    DatagramSocket ds=new DatagramSocket();

    //2.将要发送的数据封装到数据包中
    String str="udp传输演示：哥们来了！";

    //使用Datagrampacket将数据封装到该对象包中。
    byte[] buf=str.getBytes();

    DatagramPacket dp=new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.31.127"),10000);

    //3.通过udp的socket服务将数据包发送出去。使用sends方法
    ds.send(dp);


    }


    //4.关闭资源
    ds.close();


  }

}
