import java.awt.image.BufferedImage;

public class Player {
	public int cash=30000, pos=0, card_num=0, map_x=0, map_y=0, land_num=0, time=1000, turn=-1, charactor=0;
	public Boolean AI=false, player=false;
	public int[] card;
	public BufferedImage img;
	public Boolean lose=false;
	
	public Player(BufferedImage[] imgs){
		card = new int[100];
		img = imgs[charactor];
		ini_xy();
	}
	
	public void ini_xy(){
		map_x = 2923*7/10+180;
		map_y = 2067/2-60;
	}
	
	public double[] move_xy(int timer){
		int j;
		double x=0.0, y=0.0;
		for(int i = pos-timer/10;i<pos;i++) {
			j=i;
			if(j<0)j+=40;
			if(j%10==0) {
				if(j/10==0){
					x += -135;
					y += 125;
				}
				if(j/10==1) {
					x += -172;
					y += -95;
				}
				if(j/10==2) {
					x += 152;
					y += -117;
				}
				if(j/10==3) {
					x += 175;
					y += 109;
				}
			}else if(j%10==9){
				if(j/10==0) {
					x += -180;
					y += 95;
				}
				if(j/10==1) {
					x += -155;
					y += -125;
				}
				if(j/10==2) {
					x += 180;
					y += -110;
				}
				if(j/10==3) {
					x += 155;
					y += 105;
				}
			}else {
				if(j/10==0) {
					x += -72;
					y += 49;
				}
				if(j/10==1) {
					x += -72;
					y += -49;
				}
				if(j/10==2) {
					x += 72;
					y += -49;
				}
				if(j/10==3) {
					x += 72;
					y += 49;
				}
			}
		}
		int i = pos-timer/10-1;
		if(i<0)i+=40;
		double yd = 10;
		for(int j1=0;j1<timer%10;j1++){
			if(j1<5)y+=yd;
			else y-=yd;
			if(i%10==0) {
				if(i/10==0){
					x += -13.5;
					y += 12.5;
				}
				if(i/10==1) {
					x += -17.2;
					y += -9.5;
				}
				if(i/10==2) {
					x += 15.2;
					y += -11.7;
				}
				if(i/10==3) {
					x += 17.5;
					y += 10.9;
				}
			}else if(i%10==9){
				if(i/10==0) {
					x += -18.0;
					y += 9.5;
				}
				if(i/10==1) {
					x += -15.5;
					y += -12.5;
				}
				if(i/10==2) {
					x += 18.0;
					y += -11.0;
				}
				if(i/10==3) {
					x += 15.5;
					y += 10.5;
				}
			}else {
				if(i/10==0) {
					x += -7.2;
					y += 4.9;
				}
				if(i/10==1) {
					x += -7.2;
					y += -4.9;
				}
				if(i/10==2) {
					x += 7.2;
					y += -4.9;
				}
				if(i/10==3) {
					x += 7.2;
					y += 4.9;
				}
			}
		}
		double[] a = { -x,  -y};
		return a;
	}
	
	public double[] set_map_xy(int w, int h, int timer) {
		if(pos>=40) {
			pos-=40;
			cash+=2000;
		}
		ini_xy();
		double[] b = {0,0};
		b = move_xy(timer);
		map_x+=b[0];
		map_y+=b[1];
		for(int i = 0;i<pos;i++) {
			if(i%10==0) {
				if(i/10==0){
					map_x += -135;
					map_y += 125;
				}
				if(i/10==1) {
					map_x += -172;
					map_y += -95;
				}
				if(i/10==2) {
					map_x += 152;
					map_y += -117;
				}
				if(i/10==3) {
					map_x += 175;
					map_y += 109;
				}
			}else if(i%10==9){
				if(i/10==0) {
					map_x += -180;
					map_y += 95;
				}
				if(i/10==1) {
					map_x += -155;
					map_y += -125;
				}
				if(i/10==2) {
					map_x += 180;
					map_y += -110;
				}
			}else {
				if(i/10==0) {
					map_x += -72;
					map_y += 50;
				}
				if(i/10==1) {
					map_x += -72;
					map_y += -50;
				}
				if(i/10==2) {
					map_x += 72;
					map_y += -50;
				}
				if(i/10==3) {
					map_x += 72;
					map_y += 50;
				}
			}
		}
		double[] a = {map_x+60-w/2, map_y+(194*0.618)-h/2};
		return a;
	}
	
	public void change_charactor(int num, BufferedImage[] imgs) {
		if(num!=-1)img = imgs[num];
		if(num==-1) {
			AI=false;
			player=false;
		}else if(num==0) {
			AI=true;
			player=false;
		}else {
			AI=false;
			player=true;
		}
		charactor=num;
	}

}
