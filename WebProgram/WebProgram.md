#网络编程

----------

## 一、网络基础知识

### 1.计算机网络：

----------


所谓计算机网络就是吧分布在不同地理区域的计算机与专门的外部设备用通信线路互连成一个规模大、功能强的网络系统，从而使众多的计算机可以方便地互相传递信息，共享硬件、软件、数据信息等资源。

#### 主要功能：
1. 资源共享
2. 信息传输与集中处理
3. 均和负荷与分布处理
4. 综合信息服务

#### 计算机网络分类
##### 按照规模大小和延伸范围划分：
1. 局域网（LAN）
2. 城域网（MAN）
3. 广域网（WAN）

Internet可以视为世界上最大的广域网。

##### 按照网络的拓扑结构划分：
1. 星型网络
2. 总线型网络
3. 环型网络
4. 树型网络
5. 星型环型网络

##### 按照网络的传说介质划分
1. 双绞线网
2. 同轴电缆网
3. 光纤网
4. 卫星网


#### 通信协议：
计算机网络中实现通信必须有一些规定，这些约定被称为通信协议。

#### 通信协议功能：
通信协议负责对传输速率、传输代码、代码结构、传输控制步骤、出错控制等制定处理标准。

#### 通信协议组成：
1. 语义部分，用于决定双方对话的类型；
2. 语法部分，用于决定双方对话的格式；
3. 变换规则，用于决定通信双方的应答关系；

#### 开发系统互连参考模型(OSI)把计算机网络分为：
1. 物理层
2. 数据链路层
3. 网络层
4. 传输层
5. 会话层
6. 表示层
7. 应用层




### 2.协议

----------



#### IP协议：
IP（Internet Protocol）协议又称为互联网协议，是支持网间互联的数据报协议。

#### TCP协议：
TCP(Transmission Control Protocol)协议，即传输控制协议，它规定一种可靠的数据信息传递服务。

#### TCP/IP的分层模型
![](https://github.com/Azcy/JavaBasic/blob/master/WebProgram/image/OSITCPIP.png)

----------

### 3.IP地址和端口号
#### Ip地址
ip地址用于唯一地标识网络中的一个通信实体，这个通信实体既可以是一台主机也可以是一台打印机，或者是路由器的某个端口。而在基于ip协议网络中传输的数据包，都必须使用ip地址来进行标识。

#### IP地址分类
A类:1.0.0.0~126.255.255.255,默认子网掩码/8,即255.0.0.0 (其中127.0.0.0~127.255.255.255为环回地址,用于本地环回测试等用途)；

B类:128.0.0.0~191.255.255.255,默认子网掩码/16,即255.255.0.0；

C类:192.0.0.0~223.255.255.255,默认子网掩码/24,即255.255.255.0；

D类:224.0.0.0~239.255.255.255,一般于用组播

E类:240.0.0.0~255.255.255.255(其中255.255.255.255为全网广播地址),E类地址一般用于研究用途..

IPv4中还有一种私有地址,即比如内部局域网所用的地址,分别为:

10.0.0.0~10.255.255.255；

172.16.0.0~172.31.0.0；

192.168.0.0~192.168.255.255


#### 端口
端口是一个16位的整数，用于表示数据交给哪个通信程序处理。因此，端口就是应用程序与外界交流的入口，它是一种抽象的软件结构，包括一些数据结构和I/O（基本输入/输出）缓冲区。

#### 端口号可以从0到65535，通常将它分为三类
1. 公认端口（Well Known Ports）:从0到123，它们紧密绑定（Binding）一些特定的服务。
2. 注册端口(Registered Ports):从1024到49151，它们松散地绑定一些服务器。应用程序通常应该使用这个范围内 的端口。
3. 动态和/或私有端口（Dynamic and/or Private Ports）：从49152到65535，这些端口是应用程序使用的动态端口，应用程序一般不会主动使用这些端口。

**为了帮助理解**：可以把IP地址理解为某个人所在地方的地址，房号为端口号。因此如果把应用程序当作人，把计算机网络当作类似邮递员的角色，当一个程序需要发送数据时，需要指定目的地的IP地址和端口，如果指定了正确的IP地址和端口号，计算机网络就可以将数据送给该IP地址和端口所对应的程序。

----------

## 二、Java的基本网络支持
Java为网络支持提供了java.net包，该包下的URL和URLConnection等类提供了以编程方式访问Web服务的功能，而URLDecoder和URLEncoder则提供了普通字符串和 application/x-www-form-urlencode MIME 字符串相互转换的静态方法。

### 1.使用InetAddress
InetAddress下有两个子类：Inet4Address、Inet6Address，分别代表
Internet Protocol version 4(IPv4)地址和Internet Protocol version 6(IPv6)地址。

InetAddress类没有提供构造器，而是提供了如下两个静态方法来获取InetAddress实例。

	1. getByName(String host)：根据主机获取对应的InetAddress对象。
	2. getByAddress(byte[] addr):根据原始ip地址来获取对应的InetAddress对象。
 
InetAddress提供如下方法：
	
	1. String getCanonicalHostName():获取此ip地址的全限定域名。
	2. String getHostAddress():返回该InetAddress实例对应的ip地址字符串。
	3. String getHostName():获取此ip地址的主机名。
	4.  getLocalHost():获取本机ip地址对应的InetAddress实例

实例：

	import java.net.InetAddress ;
	public class InetAddresstest
	{
		public static void main(String[] args) throws Exception
		{
			//根据主机名来获取对应的InetAddress实例
			InetAddress ip=InetAddress.getByName("zcy");
			//判断是否可达
			System.out.println("creazyit是否可达："+ip.isReachable(2000));
			//获取InetAddress实例的ip字符串
			System.out.println("ip:"+ip.getHostAddress()+","+"主机名:"+ip.getHostName()+",实例:"+ip.getLocalHost());
			
			//根据原始ip地址来获取对应的InetAddress实例
			InetAddress local=InetAddress.getByAddress(new byte[]{127,0,0,1});
			System.out.println("本机是否可达："+local.isReachable(5000));
			//获取该InetAddress实例对应的全限定域名
			System.out.println(local.getCanonicalHostName());
		}
	}

打印结果：

	creazyit是否可达：true
	ip:192.168.97.2,主机名:zcy,实例:zcy/192.168.97.2
	本机是否可达：true
	127.0.0.1

### 2.使用URLDecoder和URLEncoder

URLDecoder类包含一个decode(String s,String enc)静态方法，它可以将看上去是乱码的特殊字符串转换成普通字符串。

URLEncoder类包含一个encode(String s,String enc)静态方法，它可以将普通字符串转换成application/x-www-form-urlencode MIME字符串。

实例：

	import java.net.*;
	public class URLDecoderTest
	{
		
	public static void main(String [] args) throws Exception
		{
			//将普通字符串转换成application/x-www-form-urlencode MIME字符串
			String urlStr=URLEncoder.encode("苏黎世的从前","GBK");
			System.out.println(urlStr);
			//将application/x-www-form-urlencode MIME字符串转换成普通字符串
			String keyName=URLDecoder.decode(urlStr,"GBK");
			System.out.println(keyName);
		}
	}
打印结果：
	
	%CB%D5%C0%E8%CA%C0%B5%C4%B4%D3%C7%B0
	苏黎世的从前
### 3.URL、URLConnection和URLPermission
URL(Uniform Resourc资源e Locator)对象代表统一资源定位器，它是指向互联网“资源”的指针。资源可以是简单的文件或目录，也可以是对更为复杂对象的引用，例如对数据库或搜索引擎的查询。在通常情况下，**URL**可以由**协议名、主机、端口和资源**组成，即满足如下格式：

	protocol://host:port/resourceName

例如如下的URL地址：

	http://www.crazyit.org/index.php

URL对应的方法：

1. String getFile():获取该URL的资源名
2. String getHost():获取URL的主机名
3. String getPath()：获取该URL的路径部分
4. int getPort():获取该URL的端口号
5. String getProtocol():获取该URL的协议名称
6. String getQuery():获取该URL的查询字符串部分
7. URLConnection openConnection():返回一个URLConnection对象，它代表了与URL所引用的远程对象的连接
8. InputStream openStream(): 打开与此URL的连接，并返回一个用于读取该URL资源的InputStream