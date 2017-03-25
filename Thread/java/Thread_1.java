/**
进程：正在运行中的程序。

线程：就是进程中一个负责执行的控制单元（执行路径）;
一个进程可以有多个执行路径（多线程）

一个进程中至少要有一个线程。

开启多个线程是为了同时运行多部分代码。

每一个线程都有自己运行的内容，这个内容可以称为现场要执行的任务。

多线程好处，解决了多部分同时运行的问题

多线程的弊端，现场太多会效率降低。

其实应用程序的执行都是cpu作者快速切换完成的，这个切换是随机的。

JVM启动时就启动了多个线程，至少有两个线程可以分析出来。
1、执行main函数的线程。
			该线程的任务代码都定义在main函数中
			
2、负责垃圾回收的线程。
			都在垃圾回收器里边定义。
			


*/
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
		new Demo();
		new Demo();
		//通知垃圾回收器
		System.gc();
		new Demo();
		
		//两个线程完成的，先输出了Hello。
		System.out.println("Hello ");
	} 
}