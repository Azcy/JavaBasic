/**
����ͨ��ʵ��Runnable�ӿڴ����߳�

˼·������һ����ʵ��Runnable�ӿڣ���дrun()������ʵ��������start����

���裺
1������Runnable�ӿڵ�ʵ���࣬����д�ýӿ��е�run()��������run()������ͬ���Ǹ��̵߳��߳�ִ���塣
	
2������Runnableʵ�����ʵ�������Դ�ʵ����ΪThread��target������Thread���󣬸�Thread��������������ֳ�����

3�������̶߳����start()�������������̡߳�
*/
/**
ͨ��Runnable�ӿ��������߳���
*/
public class SecondThread implements Runnable
{
	private int i;
	
	//run()����ͬ�����̵߳�ִ����
	public void run()
	{
		
		for(;i<100;i++)
		{
			//���߳���̳�Thread��ʱ��ֱ��ʹ��this���ɻ�õ�ǰ�߳�
			//Thread�����getname()���ص�ǰ�̵߳�����
			System.out.println(Thread.currentThread().getName()+"���߳�"+i);
		}
	}
	
	public static void main(String[] args)
	{
		
		for(int i=0;i<100;i++)
		{
			//����Thread��currentThread()������ȡ��ǰ�߳�
			System.out.println(Thread.currentThread().getName()+" "+i);
			if(i==20)
			{
				SecondThread st=new SecondThread();
				//ͨ�� new Thread(target,name)�������������߳�
				//new Thread(st,"���߳�1").start();
				//�����������ڶ����߳�
				//new Thread(st,"���߳�2").start();
				//����ͨ��������ʽ�������߳�
					Thread t1=new Thread(new SecondThread());
					//�����̵߳�����
					t1.setName("���߳�1");
					t1.start();
			}
		}
		
	}
}
/**
�����
���߳�2���߳�0
���߳�1���߳�0
���߳�2���߳�1
���߳�2���߳�3
main 22
���߳�2���߳�4
���߳�1���߳�2
���߳�2���߳�5
main 23
���߳�2���߳�7
���߳�1���߳�6
���߳�2���߳�8
main 24
���߳�2���߳�10
���߳�1���߳�9
���߳�2���߳�11
main 25
���߳�2���߳�13
���߳�1���߳�12
���߳�2���߳�14

ͨ��������Կ����������߳�i�����������ģ������ǿ��Թ����߳����ʵ��������
*/