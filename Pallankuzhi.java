import java.applet.*;
import java.awt.*;
import java.awt.event.*;

class pit{
  public int chozhi;
	public int x,y;
	public boolean available;
	public void xy(int a,int b)
	{
		x=a;
		y=b;

	}
}

public class pallankuzhi extends Applet{
	
	Thread animation;
	static final int rate=10;
	int x,y;
	int size;
	pit[] kuzhi = new pit[1000];
	int player;
	int[] points = new int[2];
	int[] kasi = new int[2];
	
	public void init() {
		int i,j,k,l;
		System.out.println(">>init<<");
		setBackground(Color.black);
		x = 20;
		y = 20;
		player=0;
		k=0;
		size = 90;
		for(l=0;l<14;l++)
		{
			kuzhi[l]=new pit();
			kuzhi[l].available=true;
		}
		for(l=0;l<2;l++)
		{
			points[l]=0;
		}
		for(l=0;l<2;l++)
		{
			kasi[l]=2;
		}
		//initialising position and no.of chozhi for each kuzhi
		j=0;
		for(i=0;i<6;i++)
		{		
			kuzhi[k].chozhi=6;	
			kuzhi[k].xy(x+(i*size)+(1+(1/2))*(size/10),y+(j*size)+(1+(1/2))*(size/10));
			k++;	
		}
		j=1;i=5;
		kuzhi[k].chozhi=0;	
		kuzhi[k].xy(x+(i*size)+(1+(1/2))*(size/10),y+(j*size)+(1+(1/2))*(size/10));
		k++;
		j=2;
		for(i=5;i>=0;i--)
		{
			kuzhi[k].chozhi=6;	
			kuzhi[k].xy(x+(i*size)+(1+(1/2))*(size/10),y+(j*size)+(1+(1/2))*(size/10));
			k++;	
		}	
		j=1;i=0;
		kuzhi[k].chozhi=0;	
		kuzhi[k].xy(x+(i*size)+(1+(1/2))*(size/10),y+(j*size)+(1+(1/2))*(size/10));
		k++;
	}
	
	public void start() {
		System.out.println(">>start<");
	}
	
	public void paint(Graphics g) {
		System.out.println(">>print<<");
		drawboard(g);	
		drawchozhi(g);
	}
	
	public void drawboard(Graphics g){
		System.out.println(">>drawboard<<");
		int i,j,k;
		k=2;
		g.setColor(new Color(232,190,99));
		g.fillRect(x,y,6*size,3*size);
		for(i=0;i<14;i++) {
                {
					if(kuzhi[i].available)
                        g.setColor(new Color(172,130,39));
					else
						g.setColor(new Color(150,108,17));
						
                        g.fillOval(kuzhi[i].x,kuzhi[i].y,(size)-((1+(1/2))*2*size/10),size-((1+(1/2))*2*size/10));
						
                }
            }
		//middlerects
		g.fillRect(x+(size)+size/3,y+(size)+(size/10),size+size/3,size-(2*size/10));
		g.fillRect(x+(3*size)+size/3,y+(size)+(size/10),size+size/3,size-(2*size/10));	
	}
	public void drawchozhi(Graphics g){
	System.out.println(">>chozhi<<");
		int i,j;
		int coinx,coiny;
		int coinsize = size/8;
		for(i=0;i<14;i++) 
		{
            for(j=0;j<kuzhi[i].chozhi;j++)
			{
				coinx=kuzhi[i].x+coinsize+((j%5)*coinsize);
				coiny=kuzhi[i].y+coinsize+((j/5)*coinsize);
				g.setColor(new Color(225,225,225));
                g.fillOval(coinx,coiny,coinsize,coinsize);	
			}
		}
		
		for(i=0;i<2;i++)
		for(j=0;j<points[i];j++)
		{
			coinx=x+((1+(i*2))*size)+size/3+((j%11)*coinsize);
			coiny=y+(size)+(size/10)+((j/11)*coinsize);
			g.setColor(new Color(225,225,225));
			g.fillOval(coinx,coiny,coinsize,coinsize);
		}
		
	}
	
	public boolean mouseDown(Event e,int mousex,int mousey)
	{
		int k;
		for(k=0;k<14;k++)
		{
			if(mousex > kuzhi[k].x && mousex < kuzhi[k].x+(size)-((1+(1/2))*2*size/10) && mousey > kuzhi[k].y && mousey < kuzhi[k].y+size-((1+(1/2))*2*size/10))
			{
					System.out.println("("+k+")");
				if((player==0 && k<6 && kuzhi[k].chozhi!=0 )||(player==1 && k<13 && k>6 && kuzhi[k].chozhi!=0))
				play(k);
			}
		}	
		return true;
	}
		
	public void play(int k)
	{
		int i;
		int l=kuzhi[k].chozhi;
		kuzhi[k].chozhi=0;
		for(i=0;i<l;i++)
		{
			k++;
			k=k%14;
			while(!kuzhi[k].available)
			{
				k++;
				k=k%14;
			}
			kuzhi[k].chozhi++;
			putchozhi()
			repaint();
		}		
		
		k++;
		k=k%14;
		while(!kuzhi[k].available)
			{
				k++;
				k=k%14;
			}
		if(k==13||k==6)
		player = (player+1)%2;
		else if(kuzhi[k].chozhi==0)
		{
			k++;
			k=k%14;
			while(!kuzhi[k].available)
			{
				k++;
				k=k%14;
			}
			
			if(k==13||k==6)
			{
				if(kasi[k%6]<5)
				{
					if((player==0 && kasi[k%6]!=3) || (player==1 && kasi[k%6]!=4))
					kasi[k%6]+=(1+player);
				}
					player = (player+1)%2;
			}
			else
			{
				points[player]+=(kuzhi[k].chozhi+kuzhi[12-k].chozhi);
				kuzhi[12-k].chozhi=0;
				kuzhi[k].chozhi=0;
				player = (player+1)%2;
			}
		}
		else
		play(k);
		
		int n=0;
		for(i=0;i<6;i++)
		{
			if(kuzhi[i].chozhi==0)
				n++;
		}
		System.out.println("!"+n+"!");
		if(n==6)
		{
			for(i=7;i<13;i++)
			{
				points[1]+=kuzhi[i].chozhi;
				kuzhi[i].chozhi=0;
			}
			roundover();
		}
		n=0;
		for(i=7;i<13;i++)
		{
			if(kuzhi[i].chozhi==0)
				n++;
		}
				System.out.println("!"+n+"!");
		if(n==6)
		{
			for(i=0;i<6;i++)
			{
				points[0]+=kuzhi[i].chozhi;
				kuzhi[i].chozhi=0;
			}
			roundover();
		}
		
	}
	
	public void roundover()
	{
	System.out.println("<<roundover>>");
		for(int i=0;i<2;i++)
		{
			if(kasi[i]==5)
			{
				points[0]+=kuzhi[6+(i*7)].chozhi/2;
				points[1]+=kuzhi[6+(i*7)].chozhi/2;
				kuzhi[6+(i*7)].chozhi=0;
			}
			else if(kasi[i]!=2)
			{
				points[kasi[i]-3]+=kuzhi[6+(i*7)].chozhi;
				kuzhi[6+(i*7)].chozhi=0;
			}
		}
		repaint();
		
		System.out.println("("+points[0]+","+points[1]+")");
		
		if(points[0]<6)
		System.out.println("Player 2 wins");
		else if(points[1]<6)
		System.out.println("Player 1 wins");
		else 
		redeal();
	}

	public void redeal(){
		int n;
		for(int j=0;j<2;j++)
		{
			n=points[j]/6;
			for(int i=0;i<n;i++)
			{
				if(i<6)
				{
					kuzhi[i+(j*7)].chozhi=6;
					kuzhi[i+(j*7)].available=true;
					points[j]-=6;
				}
			}
			for(int i=0;i<6;i++)
			{
				if(kuzhi[i+(j*7)].chozhi==0)
				{
					kuzhi[i+(j*7)].available=false;
				}
			}
		}
		start();
	}
	
	public void stop() {
		System.out.println(">>stop<<");
		if(animation!=null){
			animation = null;
		}
	}
}
		
		
		
