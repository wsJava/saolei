package saolei;

public class LeiButton implements java.io.Serializable {
	int x=0,y=0;						//x横坐标，y纵坐标
	String name,lei,amount;				//name是否标记，lei是否有雷，amount周围雷数量
	public void setX(int x)  {
		this.x = x;
	}
	public void setY(int y) {			
		this.y = y;
	}
	public void setAmount(String amount) {			
		this.amount = amount;
	}
	public void setName(String name) {
		this.name = name;			//未扫flag0，标记flag1，已扫flag2
	}
	public void setLei(String lei) {
		this.lei = lei;			//有雷lei1，无雷lei0
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public String getAmount(){
		return amount;
	}
	public String getName(){
		return name;
	}
	public String getLei(){
		return lei;
	}
}
