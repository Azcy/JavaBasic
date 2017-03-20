# 枚举类
### 定义：
一个类的对象是有限而且固定的，例如季节类，它只有4个对象。枚举类是一种特殊的类，它一样可以有自己的成员变量、方法、可以实现一个或者多个接口、也可以定义自己的构造器。**一个java源文件中最多只能定义一个public访问权限的枚举类，且该java源文件也必须和该枚举类的类类名相同
**
### 枚举类与普通类的区别：
1. 枚举类可以实现一个或多个接口，使用enum定义的枚举类默认继承了java.lang.Enum类，而不是默认继承Object类，因此枚举类不能显示继承其他父类。其中java.lang.Enum类实现了java.lang.Serializable和java.lang.Comparable两个接口。
2. 使用enum定义**、非抽象的枚举类默认会使用final修饰**，因此**枚举类不能派生子类。**
3. **枚举类的构造器只能使用private访问控制符**，如果省略了构造器的访问控制符，则默认使用private修饰；如果强制制定访问控制符，则只能制定private修饰符。
4. 枚举类的所有实例必须在枚举类的第一行显示列出，否则这个枚举类永远都不能产生实例。**例举出实例时，系统会自动添加public static final修饰**，无需程序员添加。
 
### 定义一个枚举类
	public enum SeasonEnum
	{
		//在第一行列出4个枚举实例
		SPRING,SUMMER,FALL,WINTER;
	}
SeasonEnum.values();//返回该枚举的所有实例。

访问枚举实例：

	EnumClass.variable

### 枚举类的成员变量、方法和构造器
下面定义一个Gender枚举类，包含了一个name的实例变量：
	
	public enum Gender
	{
		MALE,FEMALE;
		//定义一个public修饰的实例变量
		public String name;
	} 
下面通过如下程序来使用该枚举类。
	
	public class GenderTest
	{
		//通过Enum的valueOf（）方法来获取指定枚举类的枚举值
		Gender g=Enum.valueOf(Gender.class,"FEMALE");
		//直接wei枚举值得name实例变量赋值
		g.name=“女”;
		//直接访问枚举值得name实例变量
		System.out.println(g+"代表："+g.name);
	}
**枚举类的实例只能是枚举值，而不是随意地通过new来创建枚举类对象。**

Java应该把所有类设计成良好封装的类，所以不应该允许自己访问Gender类的name成员变量，而是通过方法来控制对name的访问。否则可能出现很混乱的情形，例如上面的程序设置了g.name=“女” 要是采用g.name=“男”
那么程序就非常的混乱，可能出现FEMALE代表男的局面。
### 程序改进：
	public enum Gender
	{
		MALE,FEMALE;
		private String name;
		public void setName(String name)
		{
			switch(this)
			{
				case MALE:
					if(name.equals("男"))
					{
						this.name=name;
					}
					else
					{
						System.out.println("参数错误");
						return;
					}
					break;
				case FEMALE:
					if(name.equals("女"))
					{
						this.name=name;
					}else
					{
						System.out.println("参数错误");
						return;
					}
					break;
			}
		}
		public String getName()
		{
			return this.name;
		}
	}
实现程序如下：

	public class EnumGenderTest
	{
		public static void main(String[] args)
		{
			//创建一个枚举的实例g
			Gender g=Gender.valueOf("FEMALE");
			g.setName("女");
			System.out.println(g+"代表:"+g.getName());
			//此时设置会提示参数错误
			g.setName("男");
			System.out.println(g+"代表："+g.getName());
		}
	}

其实，上面的枚举类Gender的做法并不好，因为枚举类同城应该设计成不可变类，也就是说，它的成员变量值不应该允许改变，这样会更加安全，而且代码简洁。

**建议使用枚举类的成员变量为private final修饰。如果所有的成员变量用final来修饰，则构造器必须为这些成员变量指定初始值。（或者在定义时指定默认值，或者在初始化块中制定初始值）**

	public enum Gender
	{
		//此处的枚举值必须调用对应的构造器来创建
		MALE("男"),FEMALE("女")；
		/**
		等同于：
		public static final Gender MALE=new Gender("男");
		public static final Gender FEMALE=new Gender("女");
		*/
		private final String name;
		//枚举类的构造器只能用private来修饰
		private Gender(String name)
		{
			this.name=name;
		}
		public String getName()
		{
			return this.name;
		}
	}

### 实现接口的枚举值
具体格式如下：

	public interface GenderDesc
	{
		void info();
	}
 	public enum Gender implements GenderDesc
	{
		//此处的枚举值必须调用对应的构造器来创建
		MALE("男")
		//花括号部分市级上是一个类体部分
		{
			public void info()
			{
				System.out.println("这个枚举值代表男性");
			}//注意：此处有个逗号
		}, 
		FEMALE("女")
		{
			public void info()
			{
				System.out.println("这个枚举值代表女性");
			}
		};//注意此处有个分号
		private final String name;
		//枚举类的构造器只能用private来修饰
		private Gender(String name)
		{
			this.name=name;
		}
	}	
	/*******************下面为调用上面枚举的方法******************/
	public class EnumGenderTest
	{
		public static void main(String[] args)
		{
			//创建一个枚举的实例g
			Gender g=Gender.valueOf("FEMALE");
			//调用方法info();
			g.info();
		}
	
	}
**注意，非抽象的枚举类才默认使用final修饰，抽象的枚举类用abstract修饰。没用final修饰的枚举类可以派生子类。**
### 包含抽象方法的枚举类

例如:

假如有一个Operation枚举类，他的4个枚举值PLUS,MINUS,TIMES,DEVIDE分别代表加、减、乘、除4种运算，该枚举只需要定义一个eval()方法来实现。

	//定义一个枚举
	enum Operation
	{
		PLUS
		{
			public double eval(double x,double y)
			{
				return x+y;
			}
		},
		MINUS
		{
			public double eval(double x,double y)
			{
				return x-y;
			}
		},
		TIMES
		{
			public double eval(double x,double y)
			{
				return x*y;
			}
		},
		DEVIDE
		{
			public double eval(double x,double y)
			{
				return x/y;
			}
		};
		//为枚举定义一个抽象方法,此方法可以由不同的枚举值提供不同的实现
		public abstract double eval(double x,double y);
	}
实现类：
	
	public class AbstractEnumTest
	{
		public static void main(String [] args)
		{
	
			System.out.println(Operation.PLUS.eval(5,3));
			System.out.println(Operation.MINUS.eval(5,3));
			System.out.println(Operation.TIMES.eval(5,3));
			System.out.println(Operation.DEVIDE.eval(5,3));
		}
	}

