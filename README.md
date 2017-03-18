# 内部类
### 定义:
定义在其他类就被成为内部类。
### 作用：
 1、内部类提供了更好的封装，可以把内部类隐藏在外部类之内，不允许同一个包中的其他类访问该类。

 2、内部类成员可以直接访问外部类的私有数据，因为内部类被当成其外部类成元，同一个类的成员之间可以相互访问。**但外部类不能访问内部类的实现细节，例如内部类的成员变量。**

3、匿名内部类适合用于创建那些仅需要一次使用的类。

**与外部类的区别：**

1、内部类比外部类可以多使用三个修饰符：private、proteced、static---外部类不可以使用这三个修饰符。

2、非静态内部类不能拥有静态成员。

外部类只能有public或者默认（default）

### 非静态内部类
	public class Test
	{
	
	/**
	内部类与外部类。
	注意：非静态内部类的成员可以访问外部类的private成员，但反过来就不成立了。
			根据静态成员不能访问非静态成员的规则，外部类的静态方法，静态代码块布能访问非静态内部类。
	*/
	private  int outsideMembers=1;
	 private   class a
	{
		private int outsideMembers=2;
			public  void set()
			{
				//通过this.outsideMembers来访问内部类实例变量
				System.out.println("内部调用内部成员"+this.outsideMembers);
				//通过Test.this.outsideMembers来调用外部类的实例变量值
				System.out.println("内部调用外部成员"+Test.this.outsideMembers);
			}	
	}
	public  void print()
	{           
		//外部类方法创建内部类的对象
		a a1=new a();
		a1.set();
		System.out.println("print");
	}
	public static void main(String[] args)
	{
		new Test().print();
		
	}
	}

### 静态内部类
import java.lang.Math;
public class Test
{
	
	/**
	内部类与外部类。
	注意：非静态内部类的成员可以访问外部类的private成员，但反过来就不成立了。
			根据静态成员不能访问非静态成员的规则，外部类的静态方法，静态代码块布能访问非静态内部类。
	*/
	private  int outsideMembers=1;

	//defind an inner class
	static class StaticInnerClass
	{
		private static int prop1=5;
		private int prop2=9;
	}   
	
	//defind a method to access inner prop
	public void accessInnerProp()
	{
		//This is a wrong access way to visit prop1
		//System.out.println(prop1);
		//The Right way:By the name of the class access to class members of a static inner class
		System.out.println("外部类调用内部类的静态的类成员"+StaticInnerClass.prop1);
		//This is a wrong access way to visit prop2 
		//System.out.println(prop1);
		//The Right way:Access to the members of a static inner class instance through an example
		System.out.println("外部类调用内部类的实例成员"+new StaticInnerClass().prop2);
	}
	

}

### 在外部类以为使用非静态内部类
在外部类以为的地方定义内部类（包括静态和非静态两种）变量的格式如下：

	OuterClass.InnerClass varName
**如果外部类有包名，则应该添加包名的前缀。**

在外部类以为的地方创建非静态内部类实例的语法如下：

	OuterInstance.new InnerConstructor()

### 例如：
	class Out
	{
		//定义一个内部类，不用控制符 define a innerclass ,and cant‘t use controls
		//即有同一个包中的其他类可以访问该内部类
		Class In
		{
			public In(String msg)
			{
				System.out.println(msg);
			}
		}
	}
	
	public class CreateInnerInstance
	{
		public static void main(String[] args)
		{
			//调用同一个包中的内部类
			Out.In in=new Out().new In("测试信息");
			/*
			可以拆分为
			适用OuterClass.InnerClass的形式定义内部类变量
			Out.In in;
			创建外部类实例，非静态内部类实例将寄生在该实例中
			Out out=new Out();
			通过外部类实例和new来调用内部类钩子器创建非静态内部类实例
			in=out.new In("测试信息");
			*/
		}
	}
**注意：非静态内部类的构造器必须通过外部类对象来调用。**


### 创建非静态内部类子类的子类

必须保证让子类构造器可以调用非静态内部类的构造器，调用非静态内部类的构造器时，必须存在一个内部类对象。

	public class SubClass extends Out.In
	{
		//显示定义SubClass的构造器
		public SubClass(Out out)
		{
			//通过传入的Out对象显示调用In的构造器
			Out.super("Hello");
		}
	}
**注意：非静态内部类的子类不一定是内部类，它可以是一个外部类。**
