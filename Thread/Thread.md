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

	/**
	步骤:
	1.定义类实现Runnable接口。
	2.覆盖接口中的 run方法，将现场的任务代码封装到run方法中。
	3.通过Thread类创建线程对象，并将Runnable接口的子类对象作为Thread类的构造函数的参数进行传递。
	为什么？ 因为线程的任务都封装在Runnable接口子类对象的run方法中。
	所以要在线程对象创建时就必须明确要执行的任务。
	4.调用线程对象的start方法开启线程
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
					new Thread(st,"新线程1").start();
					//创建并启动第二个线程
					new Thread(st,"新线程2").start();
					/**
					或者通过以下形式来创建线程
					Thread t1=new Thread(new SecondThread());
					//设置线程的名称
					t1.setName("新线程1");
					t1.start();
					*/
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

## 线程的生命周期

四个状态的转换图如下：
![](https://github.com/Azcy/JavaBasic/blob/master/Thread/image/ThreadStateTransition.png)

**注意：**

1、启动线程使用start()方法，而不是run()方法！永远不要调用线程对象的run()方法！调用start()方法来启动线程，系统会把run()方法当成线程执行体来处理；但如果直接调用线程对象的run()方法，则run()方法立即被执行，而且在run方法返回之前其他现场无法并发执行----也就是说，如果直接调用线程对象的run()方法，系统把线程对象当成一个普通对象，而run方法也是一个普通方法，而不是线程执行体。

2、为了检测某个线程是否已经死亡，可以调用线程对象的isAlive()方法，当线程处于就绪、运行、阻塞三种状态时，该方法将返回true；当线程处于新建、死亡两种状态时，该方法返回false。


## 控制线程

### join线程
**概述：**

Thread提供了让一个线程等待另一个线程完成的方法----join()方法。

当在某个程序执行流中调用其他线程的join()方法时，调用线程将被阻塞，直到join()方法加入的join线程执行为止。

**例子：**

	public class JoinThread extends Thread
	{
		//提供一个有参数的构造器，用于设置该线程的名字
			public JoinThread(String name)
			{
				super(name);
			}
			//重写run()方法，定义线程执行体
			public void run()
			{
				for(int i=0;i<100;i++)
				{
					System.out.println(getName()+" "+i);
				}
			}
			
		public static void main(String [] args) throws Exception
		{
			//启动子线程
			new JoinThread("新线程").start();
				for(int i=0;i<100;i++)
				{
					if(i==20)
					{
						JoinThread jt=new JoinThread("被Join的线程");
						jt.start();
						//main 线程调用了jt线程的join()方法，main线程
						//必须等jt执行结束才会向下执行
						jt.join();
					}
						System.out.println(Thread.currentThread().getName()+" "+i);
				}
		}
	}
	输出结果：
	被Join的线程 95
	被Join的线程 96
	被Join的线程 97
	被Join的线程 98
	被Join的线程 99
	main 20
	main 21
	main 22
	main 23
	main 24

**分析：**

上面的程序一共有3个线程，主方法开始启动了名为"新线程"的子线程，该线程将会和main线程并发执行，当主线程的循环变量i等于20时，启动了名为“被join的线程”的线程，该线程不会和main线程进行并发执行，main线程必须等被join的线程执行完后才可以向下执行。


### join()方法有如下三种重载方式
1. join():等待被join的线程执行完成。
2. join(long millis):等待被join的线程的时间最长为millis毫秒。如果在millis毫秒内被join的线程还没有执行结束，则不再等待。
3. join(long millis,int nanos):等待被join的线程的时间最长为millis毫秒加nanos毫微秒。


## 后台线程(Daemon Thread)
**定义：**后台运行的，任务是为其他线程提供服务，又称为“守护线程”、“精灵线程”。jvm的垃圾回收线程就是典型的后台线程。

**特征：**

1. 如果所有的前台线程都死亡，则后台线程也会自动死亡。
2. 调用Thread对象的setDaemon（true）方法可将指定的线程设置成后台线程。
3. isDaemon()方法，判断是否为后台线程。

**注意：**

前台线程死亡后，jvm会通知后台线程死亡，但从它接收指令到做出响应，需要一定时间。**而且要将某个线程设置为后台线程，必须在线程启动之前设置，**也就是说，setDaemon(true)必须在start()方法之前调用,否则会引发IllegalThreadStateException异常。

## 线程的睡眠:sleep
让当前正在执行的线程暂停一段时间，并进入阻塞状态。

### sleep()方法的两种重载方式：
1. static void sleep(long millis)：让当前正在执行的线程暂停millis毫秒，并进入阻塞状态，该方法受到系统计时器和线程调度器的精确与准确度的影响。
2. static void sleep(long millis,int nanos):让当前正在执行的线程暂停millis毫秒加nanos毫微秒，并进入阻塞状态，该方法受到系统计时器和线程调度器的精确与准确度的影响。



## 线程让步:yield

yield()方法是一个和sleep()方法有点类似的方法，它也是个Thread提供的静态方法，**也可以让当前正在执行的线程暂停，但它不会阻塞该线程，它只会将该线程转入就绪状态。**

实际上调用yield()之后，线程让执行几乎让给了优先级相同的或更高优先级的其他线程。
如果该线程为最高级的线程，则系统重新调度还是继续执行该线程。

设置线程的优先级:

子线程对象.setPriority(Thread.MAX_PRIORITY);//为设置最高优先级


## 改变线程优先级

每个线程默认的优先级都与创建它的父线程优先级相同，在默认情况下，main线程具有普通优先级，由main线程创建的子线程也具有普通优先级

Thread类提供了setPriority(int newPriority)、getPriority()方法来设置和返回指定线程的优先级，该方法的参数可以是一个整数范围是1-10之间，也可以用Thread类的如下三个静态常量。
1. MAX_PRIORITY:其值是10。
2. MIN_PRIORITY:其值是1。
3. NORM_PRIORITY:其值是5。

## 线程同步
### 线程安全问题

###同步代码块
**同步监视器的目的**：阻止两个线程对痛一个共享资源进行并发访问，因此通常推荐使用可能被并发访问的共享资源充当同步监视器。

使用同步监视器的通用方法就是同步代码块：

	synchronized(obj)
	{
		...
		//此处的代码就是同步代码块
	}
上面预防格式中synchronized后括号里的obj就是同步监视器，上面的代码含义是：线程开始执行同步代码块之前，必须先获得对同步监视器的锁定。

**注意：**任何时刻只能有一个线程可以获得对同步监视器的锁定，

当同步代码块执行完成后，该线程会释放对该同步监视器的锁定。

## 线程通信
### 传统的线程通信
假设现在系统中有两个线程，这两个线程分别代表存款者和取钱者---现在假设有一种特殊的要求，就是要求存款者和取款这不断的重复存款取款的动作，而且每当存款者将钱存入指定账户后，取钱者就立即取出该笔钱。不允许存款者连续两次存钱，也不允许取钱者连续两次取钱。。

可以借助Object类的wait()、notify()和notifyAll()三个方法。这三个方法不属于Thread类，属于Object类，但这三个方法必须由同步监视器对象来调用可以分为以下两种情况。
1. 对于使用synchronized修饰的同步方法，因为该类的默认实例（this）就是同步监视器，所以可以在同步方法中调用这三个方法。
2. 对于使用synchronized修饰的同步代码块，同步监视器是synchronized后括号的对象，所以必须使用该对象调用这三个方法。


**这三个方法：**

1. **wait():**导致当前线程等待，直到其他线程调用该同步监视器的notify()方法或者notifyAll()方法来唤醒该线程。该wait()方法有三种形式---
		
		1.无时间参数的wait（一直等待，直到其他线程通知）
		2.带毫秒参数的wait()和带毫秒、毫微秒参数的wait（这两种方法都是等待指定的时间后自动苏醒）。
调用wait（）方法的当前线程会释放对该同步监视器的锁定。
2. **notify()：**唤醒在此同步监视器上等待的单个线程。如果所有线程都在此同步监视器上等待，则会选择唤醒其中的一个线程。选择是忍一下的。只有使用了wait()方法后，才可以执行唤醒该线程。
3. **notifyAll():**唤醒在此同步监视器上等待的所有线程。只有使用了wait()方法后，才可以执行唤醒该线程。


### 使用Condition控制线程通信（Lock代替了同步方法或同步代码块，Condition替代了同步监视器功能）
如果程序不使用synchronized关键字来保证同步，而是直接使用Lock对象来保证同步，则系统中不存在隐式的同步监视器，也就不能够使用wait()、notify()和notifyAll()三个方法进行线程通信了。

因此要使用Condition类来保持协调，提供了如下三个方法。
1. **await():**导致当前线程等待，直到其他线程调用该Condition的signal()方法或者signalAll()方法来唤醒该线程。
2. **signal()：**唤醒在此Lock对象上等待的单个线程。如果所有线程都在此Lock对象上等待，则会选择唤醒其中的一个线程。选择是忍一下的。只有使用了await()方法后，才可以执行唤醒该线程。
3. **signalAll()：**唤醒在此Lock对象上等待的所有线程。只有使用了await()方法后，才可以执行唤醒该线程。


### 使用阻塞队列(BlockingQueue)控制线程通信
**特征：**

当生产者线程试图向BlockingQueue中放入元素时，如果该队列已满，则该线程被阻塞；当消费者线程试图从BlockingQueue中取出元素时，如果该队列已空，则该线程被阻塞。

程序的两个线程通过交替向BlockingQueue中放入元素、取出元素，即可很好的控制线程的通信。

**BlockingQueue提供如下两个支持阻塞的方法：**

1. put(E e): 尝试把E元素放入 BlockingQueue中，如果该队列的元素已经满，则阻塞该线程。
2. take():尝试从BlockingQueue的头部取出元素，如果该队列元素已空，则阻塞该线程。

**BlockingQueue继承了Queue接口，也可以使用接口的方法：**

1. 在队列尾部插入元素。包括add(E e)、offer(E e)和put(E e)方法，当该队列已满时，这三个方法分别会抛出异常、返回false、阻塞队列。
2. 在队列的头部删除并返回删除元素。包括remove()、poll()、take()方法。当该队列已空时，这三个方法分别会抛出异常、返回false、阻塞队列。
3. 在队列头部取出但不删除元素。包括element()和peek()方法，当队列已空时，这两个方法分别抛出异常、返回false。

**BlockingQueue包含的方法之间的对应关系如表**

|            /    | 抛出异常	    | 不同返回值  |	阻塞线程|指定超时时长|
| ------------- |:-------------:| -----:|
| 队尾插入元素   |add(e) 	|offer(e) |put(e)| off(e,time,unit)|
| 队尾删除元素   |remove()       |   poll() |  take()|poll(time,unit)
| 获取、不删除元素 | element()      |    peek() |无|无

----------
**具体例子：**
	
	/**
	 * Created by mzcy on 2017/3/25.
	 */
	public class BlockingQueueTest {
	
	    public static void main(String[] args)throws Exception {
	        //定义一个长度为1的阻塞队列
	        BlockingQueue<String> bq=new ArrayBlockingQueue<>(1);
	        //启动3个生产者线程
	        new Producer(bq).start();
	        new Producer(bq).start();
	        new Producer(bq).start();
	        //启动一个消费者线程
	        new Consumer(bq).start();
	
	    }
	}
	class Producer extends Thread
	{
	    private BlockingQueue<String> bq;
	    public Producer(BlockingQueue<String> bq)
	    {
	        this.bq=bq;
	    }
	    public void  run()
	    {
	        String[] strArr=new String[]{
	                "Java","Struts","Sprint"
	        };
	        for (int i=0;i<9999999;i++)
	        {
	            System.out.println(getName()+"生产者准备生产集合元素！");
	            try {
	                Thread.sleep(200);
	                //尝试放入元素，如果队列已满，则线程阻塞
	                bq.put(strArr[i%3]);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            System.out.println(getName()+"生产完成："+bq);
	        }
	    }
	}
	class Consumer extends Thread
	{
	    private BlockingQueue<String> bq;
	    public Consumer(BlockingQueue<String> bq)
	    {
	        this.bq=bq;
	    }
	    public void  run()
	    {
	      while (true)
	      {
	          System.out.println(getName()+"消费者准备消费集合元素！");
	          try {
	              Thread.sleep(200);
	              //尝试取出元素，如果队列已空，则线程被阻塞
	              bq.take();
	          } catch (InterruptedException e) {
	              e.printStackTrace();
	          }
	          System.out.println(getName()+"消费完成"+bq);
	      }
	
	    }
	}









----------
3/25/2017 5:53:02 PM 