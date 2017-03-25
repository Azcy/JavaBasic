public class DrawThread
{

}
class Account
{
	//封装账号编号、账户余额的两个成员变量
	private String accountNo;
	private double balance;
	public Account()
	{}
	//构造器
	public Account(String accountNo,double balance)
	{
		this.accountNo=accountNo;
		this.balance=balance;
	}
	//下面两个方法根据accountNo来重写hashCode()和equals()方法
	
}