# 多线程
## 线程和进程
进程：是系统进行资源分配和调度的一个独立单位。

特征：

	1、独立性：进程是系统中独立存在的实体，它可以拥有自己独立的资源，每个进程都拥有自己私有空间。
	在没有经过进程本身允许的情况下，一个用户进程不可以直接访问其他进程的地址空间。

	2、动态性：进程与程序的区别在于，程序之时一个静态的指令集合，
	而进程是一个正在系统中活动的指令集合。

	3、并发性：多个线程可以在单个处理器上并发执行，多个线程之间不会互相影响。

**注意：**

	并发性和并行性是两个概念，并行指在同一时刻，有多条指令在多个处理器上同时执行；
	并发指在同一时刻只能有一条指令执行，但多个进程指令被快速轮换执行，是的宏观上具有多个进程同时执行的效果。

线程：被称作轻量级进程，是进程的执行单元，就是进程中一个负责执行的控制单元（执行路径）；一个进程可以有多个执行路径（多线程）。

### jvm的启动时就启动了多个线程

	/**
	
	JVM启动时就启动了多个线程，至少有两个线程可以分析出来。
	1、执行main函数的线程。
				该线程的任务代码都定义在main函数中
				
	2、负责垃圾回收的线程。
				都在垃圾回收器里边定义。
				
	*/

	//创建一个垃圾回收的类
	class Demo extends Object
	{
		public void finalize()
		{
			System.out.println("demo ok");
			
		}
	}
	
	public class Thread_1
	{
		public static void main(String[] args)
		{
			//调用垃圾回收类
			new Demo();
			new Demo();
			//通知垃圾回收器
			System.gc();
			new Demo();
			
			//两个线程完成的，先输出了Hello。
			System.out.println("Hello ");
		} 
	}

	打印的结果：
	先输出hello，后输出demo ok，同时demo ok，有时是1-3个。
	说明jvm是有多个线程同时运行的，当主线程运行完毕时，其他线程可能还没有运行完毕。
**简而言之，一个程序运行后至少有一个进程，一个进程里可以包含多个线程，但至少要包含一个线程。**


## 线程的创建和启动
### 一、通过继承Thread类创建线程类
**步骤：**
	
1、定义Thread类的子类，并且重写该类的run()方法，该run()方法的方法体就代表了现场需要完成的任务。因此run()方法称为线程的执行体
	
2、创建Thread子类的实例，即创建了线程对象
	
3、调用线程对象的start()来启动该线程

**例子1：**
	
	/**
	通过创建Thread类来创建线程类
	Thread.currentThread(): 是静态类，返回当前正在执行的线程对象。
	getName()：该方法是Thread类的实例方法，返回调用该方法的线程名字。
	setName（String name）为线程设置名字。
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
		
		//结论线程1和2的运行的先后次序是随机的。
	
	}
[具体思路详见代码](https://github.com/Azcy/JavaBasic/blob/master/Thread/CreatThread.java)

**注意：使用继承Thread类的方法来创建线程类时，多个线程之间无法共享显存类的实例变量**

### 二、实现Runnable接口创建线程类
**步骤：**
	
1、定义Runnable接口的实现类，并重写该接口中的run()方法，该run()方法体同样是该线程的线程执行体。
	
2、创建Runnable实现类的实例，并以此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的现场对象。例如：
	
	//创建Runnable实例类的对象
	SecondThread st=new SecondThread();
	//以Runnable实现类的对象作为Thread的target来创建Thread对象，即线程对象
	new Thread(st);

也可以在创建Thread对象时为该Thread对象指定一个名字，代码如下：
	new Thread(st,"新线程1");

3、调用线程对象的start()方法来启动该线程。

**提示：**Runable对象仅仅作为对象Thread
对象的tagart，Runnable实现类里包含的run()方法仅作为线程执行体。而实际的线程对象依然是Thread实例，只是该Thread线程负责执行其target的run()方法。

**例子2：**
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
					new Thread(st,"新线程1").start();
					//创建并启动第二个线程
					new Thread(st,"新线程2").start();
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

[具体思路详见代码](https://github.com/Azcy/JavaBasic/blob/master/Thread/SecondThread.java)



