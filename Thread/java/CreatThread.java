/**
需求：
创建一个线程

思路：
1、有两种创建线程的方法，一种，通过继承Thread类创建线程，第二种，实现Runnable接口创建线程。

2、创建线程的目的就是为了开启一条执行路径，去运行制定的代码或其他代码实现同时运行。
	
而运行的制定代码就是这个实行路径的任务。。

3、jvm创建的主线程的任务都定义在了主函数中。

4、而自定义的线程它的任务在哪儿呢？
这个任务就是通过Thread泪中的run方法来提醒，也就是说，run方法就封装了自定义线程任务的函数

run方法中定义的就是线程要运行的任务代码。

5、开启现场时为了运行指定的代码，所以只有继承Thread类，并且复写run方法。
将允许的代码定义在run方法中。

步骤：
第一种：
1、定义Thread类的子类，并且重写该类的run()方法，该run()方法的方法体就代表了现场需要完成的任务。
因此run()方法称为线程的执行体
2、创建Thread子类的实例，即创建了线程对象
3、调用线程对象的start()来启动该线程

第二种：
1、定义Runnable接口的实现类，并重写该接口中的run()方法，该run()方法体同样是该线程的线程执行体。
2、创建Runnable实现类的实例，并以此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的现场对象。
3、调用线程对象的start()方法来启动该线程。


*/

/**
通过创建Thread类来创建线程类
Thread.currentThread(): 是静态类，返回当前正在执行的线程对象
getName()：该方法是Thread类的实例方法，返回调用该方法的线程名字。
setName（String name）为线程设置名字
*/
public class CreatThread  extends Thread
{
	
	private int i;

	//重写 run()方法，run（）方法的方法体就是线程的执行体
	public void run()
	{
		
		for(;i<100;i++)
		{
			//当线程类继承Thread类时，直接使用this即可获得当前线程
			//Thread对象的getname()返回当前线程的名字
			System.out.println(getName()+"的线程"+i);
		}
	}
	
	public static void main(String[] args)
	{
		//创建d1的线程的对象
		//CreatThread d1=new CreatThread("线程1");
		//创建d2的线程的对象
		//CreatThread d2=new CreatThread("线程2");
	
		
		for(int i=0;i<100;i++)
		{
			//调用Thread的currentThread()方法获取当前线程
			System.out.println(Thread.currentThread().getName()+" "+i);
			if(i==20)
			{
				//创建并启动第一个线程
				new CreatThread().start();
				//创建并启动第二个线程
				new CreatThread().start();
			}
		}
	}
	
	//结论线程1和2的运行是随机的。

}