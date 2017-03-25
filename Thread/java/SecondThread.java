/**
需求：通过实现Runnable接口创建线程

思路：定义一个类实现Runnable接口，重写run()，创建实例，调用start方法

步骤：
1、定义Runnable接口的实现类，并重写该接口中的run()方法，该run()方法体同样是该线程的线程执行体。
	
2、创建Runnable实现类的实例，并以此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的现场对象。

3、调用线程对象的start()方法来启动该线程。
*/
/**
通过Runnable接口来创建线程类
*/
public class SecondThread implements Runnable
{
	private int i;
	
	//run()方法同样是线程的执行体
	public void run()
	{
		
		for(;i<100;i++)
		{
			//当线程类继承Thread类时，直接使用this即可获得当前线程
			//Thread对象的getname()返回当前线程的名字
			System.out.println(Thread.currentThread().getName()+"的线程"+i);
		}
	}
	
	public static void main(String[] args)
	{
		
		for(int i=0;i<100;i++)
		{
			//调用Thread的currentThread()方法获取当前线程
			System.out.println(Thread.currentThread().getName()+" "+i);
			if(i==20)
			{
				SecondThread st=new SecondThread();
				//通过 new Thread(target,name)方法来创建新线程
				//new Thread(st,"新线程1").start();
				//创建并启动第二个线程
				//new Thread(st,"新线程2").start();
				//或者通过以下形式来创建线程
					Thread t1=new Thread(new SecondThread());
					//设置线程的名称
					t1.setName("新线程1");
					t1.start();
			}
		}
		
	}
}
/**
输出：
新线程2的线程0
新线程1的线程0
新线程2的线程1
新线程2的线程3
main 22
新线程2的线程4
新线程1的线程2
新线程2的线程5
main 23
新线程2的线程7
新线程1的线程6
新线程2的线程8
main 24
新线程2的线程10
新线程1的线程9
新线程2的线程11
main 25
新线程2的线程13
新线程1的线程12
新线程2的线程14

通过结果可以看出两个子线程i变量是连续的，所以是可以共享线程类的实例变量。
*/