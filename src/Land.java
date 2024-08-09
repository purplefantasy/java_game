import java.awt.image.BufferedImage;

public class Land {
	public int player_num = 0, pos=0, map_x=0, map_y=0, window_x=0, window_y=0, level=0, owner=-1, cost=1000, special=-1, cold=0;
	public BufferedImage img;
	public Snow[] snow = {new Snow(),new Snow(),new Snow(),new Snow(),new Snow()};
	public House[] house = {new House(),new House(),new House(),new House()};
	
	public Land(){
		cost+=(int)(Math.random()*2200)+1;
		
	}
	
	public void reset() {
		player_num = 0;
		map_x=0;
		map_y=0;
		window_x=0;
		window_y=0;
		level=0;
		owner=-1;
		cost=1000;
		special=-1;
		cold=0;
		cost+=(int)(Math.random()*2200)+1;
		set_map_xy();
	}
	
	public int[] house_anime() {
		for(int i=0;i<level;i++) {
			if(pos/10==0) {
				house[i].map_x=map_x+1+i*18;
				house[i].map_y=map_y+24-i*12;
			} else if(pos/10==1) {
				house[i].map_x=map_x+98+i*18;
				house[i].map_y=map_y-12+i*12;
			} else if(pos/10==2) {
				house[i].map_x=map_x+152-i*18;
				house[i].map_y=map_y+53+i*12;
			} else {
				house[i].map_x=map_x+54-i*18;
				house[i].map_y=map_y+89-i*12;
			}
		}
		int[] a = {house[0].map_x, house[0].map_y, house[1].map_x, house[1].map_y, house[2].map_x, house[2].map_y, house[3].map_x, house[3].map_y };
		return a;
	}
	
	public void start_snow() {
		for(int i=0;i<5;i++) {
			if(pos/10==0) {
				snow[i].map_x=map_x+(int)(Math.random()*100)+1;
				snow[i].map_y=map_y+(int)(Math.random()*49)+1-50;
			} else if(pos/10==1) {
				snow[i].map_x=map_x+(int)(Math.random()*100)+1+100;
				snow[i].map_y=map_y+(int)(Math.random()*49)+1-50;
			} else if(pos/10==2) {
				snow[i].map_x=map_x+(int)(Math.random()*100)+1+100;
				snow[i].map_y=map_y+(int)(Math.random()*49)+1;
			} else {
				snow[i].map_x=map_x+(int)(Math.random()*100)+1;
				snow[i].map_y=map_y+(int)(Math.random()*49)+1;
			}
			snow[i].alpha=255-i*20;
		}
		cold=1;
	}
	
	public int[] snow_anime() {
		for(int i=0;i<5;i++) {
			snow[i].map_y+=5;
			snow[i].alpha-=20;
			if(snow[i].alpha<50) {
				if(pos/10==0) {
					snow[i].map_x=map_x+(int)(Math.random()*100)+1;
					snow[i].map_y=map_y+(int)(Math.random()*49)+1-50;
				} else if(pos/10==1) {
					snow[i].map_x=map_x+(int)(Math.random()*100)+1+80;
					snow[i].map_y=map_y+(int)(Math.random()*49)+1-50;
				} else if(pos/10==2) {
					snow[i].map_x=map_x+(int)(Math.random()*100)+1+80;
					snow[i].map_y=map_y+(int)(Math.random()*49)+1+10;
				} else {
					snow[i].map_x=map_x+(int)(Math.random()*100)+1;
					snow[i].map_y=map_y+(int)(Math.random()*49)+1+10;
				}
				snow[i].alpha=255;
			}
		}
		int[] a = {snow[0].map_x, snow[0].map_y, snow[0].alpha,snow[1].map_x, snow[1].map_y, snow[1].alpha,snow[2].map_x, snow[2].map_y, snow[2].alpha,snow[3].map_x, snow[3].map_y, snow[3].alpha,snow[4].map_x, snow[4].map_y, snow[4].alpha };
		return a;
	}
	
	
	public void set_map_xy() {
		map_x = 2923*7/10;
		map_y = 2067/2;
		for(int i = 0;i<pos;i++) {
			if(i%10==0) {
				if(i/10==0){
					map_x += -20;
					map_y += 152;
				}
				if(i/10==1) {
					map_x += -72;
					map_y += -14;
				}
				if(i/10==2) {
					map_x += 172;
					map_y += -49;
				}
				if(i/10==3) {
					map_x += 224;
					map_y += 117;
				}
			}else if(i%10==9){
				if(i/10==0) {
					map_x += -224;
					map_y += 14;
				}
				if(i/10==1) {
					map_x += -172;
					map_y += -152;
				}
				if(i/10==2) {
					map_x += 72;
					map_y += -117;
				}
			}else {
				if(i/10==0) {
					map_x += -72;
					map_y += 49;
				}
				if(i/10==1) {
					map_x += -72;
					map_y += -49;
				}
				if(i/10==2) {
					map_x += 72;
					map_y += -49;
				}
				if(i/10==3) {
					map_x += 72;
					map_y += 49;
				}
			}
			
		}
	}
}
