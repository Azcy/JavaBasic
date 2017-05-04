/**负责处理每个线程通信的线程类*/
publi class ServerThread implements Runnable
{
  //定义当前线程所处理的Socket
  Socket s=null;
  //该线程所处理的Socket对应的输入流
  BufferedReader br=null;
  public ServerThread(Socket s)throws IOException
  {
    this.s=s;
    br=new BufferedReader(new InputStreamReader(s.getInputStream()));
  }

  public void run()
  {
    try
    {
      String content=null;
      //采用循环不断地从socket中读取客户端发送过来的数据
      while ((content = readFromClient())!=null)
      {
        //遍历socketlist中的每个socket
        //将读到的内容向每个Socket发送一次
        for (Socket s: Server.socketlist )
        {
          PrintStream ps=new PrintStream(s.getOutputStream());
          ps.println(content);
          System.out.println("客户端:"+content);
        }

      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  //定义读取客户端数据的方法
  private String readFromClient()
  {
    try
    {
      return br.readLine();
    }
    //如果遇到异常，则表明该socket对应的客户端已经关闭
    catch (IOException e)
    {
      //删除该socket
      Server.socketlist.remove(s);
    }
    return null;
  }
}
