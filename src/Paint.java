import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Paint extends JPanel implements KeyListener {
	static BufferedImage img=null;
	static Color pencolor = Color.black;
	static Color backgroundcolor1 = Color.white;
	static boolean m = false;
	Random ran = new Random();
	Color c1 = new Color(0,0,0,0);
	Boolean key_up=false, key_down=false, key_left=false, key_right=false, key_esc=false, key_z=false, key_space=false, mouse_press=false, mouse_sc=false, mouse_pressed=false;
	Double window_x = 0.0, window_y = 0.0;  //window position
	BufferedImage noplayer_img, AI_board, selector_l_img, selector_r_img, selector_pl_img, selector_pr_img, board_img, snow_img;
	BufferedImage[] player_board = {null, null, null, null, null, null}, house_img= {null, null, null, null, null, null, null}, house_f_img= {null, null, null, null, null, null, null};
	BufferedImage[] P_img = {null, null, null, null, null, null}, AI_img = {null, null, null, null, null}, map_select;
	BufferedImage map_img, random_map_select_img, select_rect_img, select_window_img, land_AI_img, land_AI_f_img, land_AI_f2_img, land_AI_f3_img;
	BufferedImage[] player_charactor= {ImageIO.read(new File("img\\AI_charactor.png")),ImageIO.read(new File("img\\player1_charactor.png")), ImageIO.read(new File("img\\player2_charactor.png")),ImageIO.read(new File("img\\player3_charactor.png")),ImageIO.read(new File("img\\player4_charactor.png")),ImageIO.read(new File("img\\player5_charactor.png")),ImageIO.read(new File("img\\player6_charactor.png"))};
	Player[] players = {new Player(player_charactor), new Player(player_charactor), new Player(player_charactor), new Player(player_charactor), new Player(player_charactor), new Player(player_charactor)};
	int player_num=2, player_num2=1, AI_num=0, player_turn=1, mouse_x, mouse_y, mouse_ox, mouse_oy, mouse_px, mouse_py, mouse_rx, mouse_ry;
	int window=0;
	int timer1, timer_c=0, timer_g_dice=0, timer_g_move=-2;
	Boolean sw1 = false, sw2=false, sc_ok=true, ini=true, ini2=true, ini3=true, ini4=true;
	int sc = 0, sc2=3;
	int key_cd=0;
	int[] player = {1, 1, 0, 0, 0, 0};
	int charactor_num = 6;
	BufferedImage[] bg, land_img= {null, null, null, null, null, null}, land_f_img= {null, null, null, null, null, null}, land_f2_img= {null, null, null, null, null, null}, land_f3_img= {null, null, null, null, null, null};
	int map_index, turn;
	Land[] lands = {new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land(),new Land()};
	int dice1=0, dice2=0, lose_num=0;
	int timeLimit=1000;
	Boolean bool2=false, bool4=false;
	Jframe pane;
	
	public Paint(Jframe p) throws IOException{
		pane = p;
		setFocusable(true);
		addMouseListener(new mouse1());
		addMouseMotionListener(new mouse1());
		this.addKeyListener(this);
		ini();
		
	}

	public void ini() throws IOException {
		players[0].change_charactor(1, player_charactor);
		players[1].change_charactor(2, player_charactor);
		map_img=ImageIO.read(new File("img\\map.png"));


	}
	
	public void change_window(Graphics g){
		if(timer1>63) timer1 = 63;
		sc_ok=false;
		g.setColor(new Color(0,0,0,timer1*4));
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		timer1+=2;
		if(timer1>63) timer1 = 63;
	}
	
	public void change_window2(Graphics g){
		timer1-=2;
		if(timer1<0) timer1 = 0;
		g.setColor(new Color(0,0,0,timer1*4));
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		if(timer1==0){
			sw1=false;
			sw2=false;
			sc_ok=true;
		}
	}
	
	public void brink_color_white(Graphics g, int degree) {
		if(timer_c<0) {
			g.setColor(new Color(255, 255, 255, -timer_c));
		}else {
			g.setColor(new Color(255, 255, 255, timer_c));
		}
		timer_c+=degree;
		if(timer_c>255) {
			timer_c-=255*2;
		}
		if(timer_c<-255) {
			timer_c+=255*2;
		}
	}
	
	
	public void draw_section(Graphics g, String[] name, int x, int y, Color c1, Color c2,int sc_num, int height) {
		key_cd--;
		g.setFont(new Font("Dialog", Font.PLAIN, height));
		g.setColor(c1);
		mouse_sc=false;
		for(int i=0;i<name.length;i++) {
			g.drawString(name[i], this.getWidth()/2+x, this.getHeight()/2+y+i*height);
			if(mouse_x>=this.getWidth()/2+x&&mouse_x<height*4+this.getWidth()/2+x&&mouse_y>this.getHeight()/2+y+i*height-height*0.7&&mouse_y<this.getHeight()/2+y+i*height+height*0.2&&sc_ok) {
				sc=i;
				mouse_sc=true;
			}
			if(sc==i) {
				g.setColor(c2);
				g.drawString(name[i], this.getWidth()/2+x+2, this.getHeight()/2+y+2+i*height);
				g.setColor(c1);
			}
		}
		if(key_cd<0&&sc_ok){
			if(key_up){
				sc--;
				if(sc<0)sc=name.length-1;
				key_cd=7;
			}
			if(key_down){
				sc++;
				if(sc>=name.length)sc=0;
				key_cd=7;
			}
		}
		
	}
	
	public Boolean draw_button(Graphics g, String name, double x_rate, double y_rate, Color c1, Color c2, double height_rate) {
		g.setFont(new Font("Dialog", Font.PLAIN, (int)(this.getHeight()*height_rate)));
		if(check_press(this.getWidth()/2+(int)(x_rate*getWidth()), this.getHeight()/2+(int)((y_rate-height_rate*0.75)*this.getHeight()), (int)(this.getHeight()*3*height_rate), (int)(this.getHeight()*height_rate*0.8))) {
			g.setColor(c1);
		} else {
			g.setColor(c2);
		}
		g.drawString(name, this.getWidth()/2+(int)(x_rate*getWidth()), this.getHeight()/2+(int)(y_rate*this.getHeight()));
		if(check_click(this.getWidth()/2+(int)(x_rate*getWidth()), this.getHeight()/2+(int)((y_rate-height_rate*0.75)*this.getHeight()), (int)(this.getHeight()*3*height_rate), (int)(this.getHeight()*height_rate*0.8))){
			return true;
		}
		return false;
	}
	
	public void draw_text(Graphics g, String name, double x_rate, double y_rate, Color c1, double height_rate) {
		g.setFont(new Font("Dialog", Font.PLAIN, (int)(this.getHeight()*height_rate)));
		g.setColor(c1);
		g.drawString(name, this.getWidth()/2+(int)(x_rate*getWidth()), this.getHeight()/2+(int)(y_rate*this.getHeight()));
	}
	
	private int dx = ran.nextInt(10)+1, dy = ran.nextInt(10)+1, x=0, y=0, w=50, h=50;
	public void drawgame(Graphics g){
		
		if(window==0){
			g.setColor(new Color(0,0,0,255));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			if(timer1==63){
				window=2;
			}
			brink_color_white(g, 5);
			g.setFont(new Font("Dialog", Font.PLAIN, 50));
			g.drawString("Press space to continue", this.getWidth()/2-270, this.getHeight()/2+150);
			if(key_space && sw2==false ){
				sw2 = true;
				timer1=0;
				
			}
			if(sw2)change_window(g);
		}
		if(window==1){
			g.setColor(new Color(0,0,0,255));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			
			if(timer1==63&&sw2){
				window=2;
			}
			Color c1 = new Color(82, 74, 55,255);
			Color c2 =new Color(100, 100, 100,100);
			
			String[] a={"Start","Exit"};
			draw_section(g, a, -200, -50, c1, c2,0, 100);
			
			if(sw1)change_window2(g);
			if((key_space||mouse_sc&&mouse_press&& sw1==false)&&sc_ok) {
				if(sc==0 && sw2==false) {
					sw2=true;
					timer1=0;
					
				}
				if(sc==1) {
					sw2=true;
					timer1=0;
				};					//continue game
				if(sc==2)System.exit(0);	//exit game
			}
			if(sw2)change_window(g);
		}
		if(window==2) {
			if(timer1==63&&sw1){
				window=4;
				for(int i=0;i<player_num;i++)players[i].set_map_xy(0, 0, 0);
			}
			g.setColor(new Color(50,0,200,255));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			
			select_p(g, this.getWidth(), 100);
			
			Color c1 = new Color(82, 74, 55,255);
			Color c2 = new Color(182, 174, 155,255);
			Boolean bool = draw_button(g, "Play", -0.07, +0.4 , c1, c2, 0.1);
			if(mouse_pressed&&sw2==false&&sw1==false&&bool&&sc_ok) {
				for(int i=4;i>0;i--){
					for(int j=i;j<5;j++){
						if(!players[j].player&&!players[j].AI){
							players[j].player=players[j+1].player;
							players[j].AI=players[j+1].AI;
							players[j].img=players[j+1].img;
							players[j].charactor=players[j+1].charactor;
							players[j+1].turn=-1;
							players[j+1].player=false;
							players[j+1].AI=false;
						}
					}
				}
				if(ini3) {
					ini3();
					ini3=false;
				}
				if(ini4) {
					ini4();
					ini4=false;
					if(window_x>2923-this.getWidth())window_x=2923.0-this.getWidth();
					if(window_y>2067-this.getHeight())window_y=2067.0-this.getHeight();
				}
				for(int i=0;i<player_num;i++)players[i].turn=i;
				map_index=1;
				sw1=true;
				timer1=0;
			}
			if(sw2)change_window2(g);
			if(sw1)change_window(g);
			
		}
		if(window==3) {
			if(timer1==63&&sw2){
				window=4;
			}
			g.setColor(new Color(50,0,200,255));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			
			select_m(g);
			
			Color c1 = new Color(82, 74, 55,255);
			Color c2 = new Color(182, 174, 155,255);
			Boolean bool = draw_button(g, "Game Start", -0.13, +0.4 , c1, c2, 0.1);
			if(mouse_pressed&& sw1==false&&sw2==false&&bool&&sc_ok) {
				for(int i=4;i>0;i--){
					for(int j=i;j<5;j++){
						if(!players[j].player&&!players[j].AI){
							players[j].player=players[j+1].player;
							players[j].AI=players[j+1].AI;
							players[j].img=players[j+1].img;
							players[j].charactor=players[j+1].charactor;
							players[j+1].turn=-1;
							players[j+1].player=false;
							players[j+1].AI=false;
						}
					}
				}
				for(int i=0;i<player_num;i++)players[i].turn=i;
				map_index=sc;
				sw2=true;
				timer1=0;
			}
			if(sw1)change_window2(g);
			if(sw2)change_window(g);
			
		}
		if(window==4){
			if(timer1==63&&sw2){
				window=2;
				bool4=false;
			}
			g.drawImage(bg[map_index], (int)Math.round(-window_x), (int)Math.round(-window_y), null);
			
			for(int i = 0;i<40;i++) {
				g.drawImage(lands[i].img, (int)Math.round(-window_x+lands[i].map_x), (int)Math.round(-window_y+lands[i].map_y), null);
				
			}
			
			for(int i = 0;i<10;i++) {//draw house
				if(lands[i].owner>=0) {
					int[] a = {0,0,0,0,0,0,0,0};
					a=lands[i].house_anime();
					for(int j=lands[i].level-1;j>=0;j--) {
						g.drawImage(house_img[players[lands[i].owner].charactor], (int)Math.round(-window_x+a[0+j*2]), (int)Math.round(-window_y+a[1+j*2]),40 ,40 , null);
					}
				}
			}
			
			for(int i = 29;i>=11;i--) {//draw house
				if(lands[i].owner>=0) {
					int[] a = {0,0,0,0,0,0,0,0};
					a=lands[i].house_anime();
					if(i<21) {
						for(int j=0;j<lands[i].level;j++) {
							g.drawImage(house_f_img[players[lands[i].owner].charactor], (int)Math.round(-window_x+a[0+j*2]), (int)Math.round(-window_y+a[1+j*2]),40 ,40 , null);
						}
					}else {
						for(int j=0;j<lands[i].level;j++) {
							g.drawImage(house_img[players[lands[i].owner].charactor], (int)Math.round(-window_x+a[0+j*2]), (int)Math.round(-window_y+a[1+j*2]),40 ,40 , null);
						}
					}
				}
			}
			
			for(int i = 31;i<40;i++) {//draw house
				if(lands[i].owner>=0) {
					int[] a = {0,0,0,0,0,0,0,0};
					a=lands[i].house_anime();
					for(int j=lands[i].level-1;j>=0;j--) {
						g.drawImage(house_f_img[players[lands[i].owner].charactor], (int)Math.round(-window_x+a[0+j*2]), (int)Math.round(-window_y+a[1+j*2]),40 ,40 , null);
					}
				}
			}
			
			player_num2=1;
			AI_num=0;
			
			//draw player
			if(player_num>5)g.drawImage(players[5].img, (int)(-window_x+players[5].map_x), (int)(-window_y+players[5].map_y), 120, 194, null);
			if(player_num>4)g.drawImage(players[4].img, (int)(-window_x+players[4].map_x), (int)(-window_y+players[4].map_y), 120, 194, null);
			if(player_num>3)g.drawImage(players[3].img, (int)(-window_x+players[3].map_x), (int)(-window_y+players[3].map_y), 120, 194, null);
			if(player_num>2)g.drawImage(players[2].img, (int)(-window_x+players[2].map_x), (int)(-window_y+players[2].map_y), 120, 194, null);
			g.drawImage(players[1].img, (int)(-window_x+players[1].map_x), (int)(-window_y+players[1].map_y), 120, 194, null);
			g.drawImage(players[0].img, (int)(-window_x+players[0].map_x), (int)(-window_y+players[0].map_y), 120, 194, null);
			
			
			
			for(int i = 0;i<40;i++) {//draw snow
				if(lands[i].cold>0) {
					int[] a = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
					a=lands[i].snow_anime();
					for(int j=0;j<5;j++) {
						g.drawImage(snow_img, (int)Math.round(-window_x+a[0+j*3]), (int)Math.round(-window_y+a[1+j*3]), 20, 20, null);
					}
				}
			}
			
			//draw cash
			g.drawImage(P_img[0], this.getWidth()*17/20, this.getHeight()/10,this.getWidth()/25,this.getHeight()/25, null);
			g.setFont(new Font("Dialog", Font.PLAIN, this.getHeight()/25));
			g.setColor(new Color(255,0,0,255));
			g.drawString(String.valueOf(players[0].cash), this.getWidth()*36/40, this.getHeight()*68/500);
			for(int i=1;i<player_num;i++) {
				if(players[i].AI) {
					g.drawImage(AI_img[AI_num], this.getWidth()*17/20, this.getHeight()/10+i*this.getHeight()/20, this.getWidth()/20,this.getHeight()/25, null);
					AI_num++;
				}else {
					g.drawImage(P_img[player_num2], this.getWidth()*17/20, this.getHeight()/10+i*this.getHeight()/20, this.getWidth()/25,this.getHeight()/25, null);
					player_num2++;
				}
				g.drawString(String.valueOf(players[i].cash), this.getWidth()*36/40, this.getHeight()*68/500+i*this.getHeight()/20);
			}
			
			
			Color c1 = new Color(82, 74, 55,255);
			Color c2 = new Color(182, 174, 155,255);
			Boolean bool=false, bool3=false;
			g.setColor(c1);
			g.setFont(new Font("Dialog", Font.PLAIN, this.getHeight()/10));
			
			
			bool2=false;
			bool=false;
			
			if(timer_g_move<-1){
				if(timer_g_dice<=0){
					for(int i=0;i<player_num;i++){
						if(players[i].turn==0&&players[i].AI){
							timer_g_dice=50;
						}
					}
				}
				if(timer_g_dice<=0){//lose skip
					for(int i=0;i<player_num;i++){
						if(players[i].cash<=0&&players[i].turn==0){
							timer_g_move=-1;
						}
					}
				}
				
				if(timer_g_dice<=0){//win
					if(lose_num==player_num-1)draw_text(g, "Skill", -0.33, +0.4 , c2, 0.1);
				}
				
				if(!bool4&&timer_g_dice<=0) {
					for(int i=0;i<player_num;i++){
						if(players[i].turn==0){
							if(players[i].charactor!=2)bool3 = draw_button(g, "Skill", -0.33, +0.4 , c1, c2, 0.1);
						}
					}
				}
				if(bool3) skill(g);
				if(bool4) {                          //menu
					g.drawImage(board_img, 0, 0, this.getWidth(), this.getHeight(), null);
					String[] a={"return main menu","resume","exit game"};
					Color c3 = new Color(82, 74, 55,255);
					Color c4 =new Color(100, 100, 100,100);
					draw_section(g, a, -500, -200, c3, c4,0, 100);
					
					if((key_space||mouse_sc&&mouse_press&& sw1==false)&&sc_ok) {
						if(sc==0) {
							for(int i=0;i<6;i++) players[i].reset();
							players[0].change_charactor(1, player_charactor);
							players[1].change_charactor(2, player_charactor);
							ini3=false;
							sw2=true;
							timer1=0;
							for(int i=0;i<40;i++) lands[i].reset();
						}
						if(sc==1)bool4=false;					//resume game
						if(sc==2)System.exit(0);	//exit game
					}
				}else {
					bool4 = draw_button(g, "Menu", -0.45, -0.4 , c1, c2, 0.05);
				}
				if(!bool4)if(timer_g_dice<=0)bool = draw_button(g, "Go!", -0.13, +0.4 , c1, c2, 0.1);
				else {
					int rnd=(int)(Math.random()*6)+1;
					int rnd2=(int)(Math.random()*6)+1;
					if(timer_g_dice>1&&timer_g_dice%2==0){
						rnd=(int)(Math.random()*6)+1;
						rnd2=(int)(Math.random()*6)+1;
					}
					if(timer_g_dice>1){
						g.drawString(String.valueOf(rnd), this.getWidth()*4/5, this.getHeight()*4/5);
						g.drawString(String.valueOf(rnd2), this.getWidth()*9/10, this.getHeight()*4/5);
					}
					else {
						dice1 = (int)(Math.random()*6)+1;
						dice2 = (int)(Math.random()*6)+1;
					}
					timer_g_dice--;
				}
				
				if(bool&&timer_g_dice<=0){
					timer_g_dice=50;
				}
				if(dice1>0){
					g.drawString(String.valueOf(dice1), this.getWidth()*4/5, this.getHeight()*4/5);
					g.drawString(String.valueOf(dice2), this.getWidth()*9/10, this.getHeight()*4/5);
					timer_g_move=(dice1+dice2)*10;
				}
			}
			else {
				double[] a = {0,0};
				for(int i=0;i<player_num;i++){
					if(players[i].turn==0){
						if(timer_g_move==(dice1+dice2)*10)players[i].pos+=dice1+dice2;
						if(timer_g_move>=0){
							a = players[i].set_map_xy(this.getWidth(), this.getHeight(), timer_g_move);
							window_x = a[0];
							window_y = a[1];
						}
					}
				}
				if(timer_g_move>=0)timer_g_move--;
				if(timer_g_move==-1){
					for(int i=0;i<player_num;i++){
						if(players[i].turn==0){
							g.drawString(String.valueOf(players[i].time/50), this.getWidth()/2, this.getHeight()*1/5);
							if(lands[players[i].pos].owner==-1&&players[i].pos%10!=0){
								if(players[i].AI){//AI buy
									if(players[i].cash/2>=lands[players[i].pos].cost){
										players[i].cash-=lands[players[i].pos].cost;
										lands[players[i].pos].owner=i;
										lands[players[i].pos].level=4;
										if(players[i].pos/10==0)lands[players[i].pos].img=land_AI_img;
										if(players[i].pos/10==1)lands[players[i].pos].img=land_AI_f_img;
										if(players[i].pos/10==2)lands[players[i].pos].img=land_AI_f2_img;
										if(players[i].pos/10==3)lands[players[i].pos].img=land_AI_f3_img;
									}
									bool2=true;
								} else{
									Boolean bol, bol2;
									draw_text(g, "$"+String.valueOf(lands[players[i].pos].cost),-0.13, +0.3 , c2, 0.05 );
									bol = draw_button(g,"Buy",-0.13, +0.4 , c1, c2, 0.1);
									bol2 = draw_button(g,"Stay",0.23, +0.4 , c1, c2, 0.1);
									bool3 = draw_button(g, "Skill", -0.33, +0.4 , c1, c2, 0.1);
									if(bool3) skill(g);//skill
									if(bol||bol2){
										if(bol){//buy
											if(players[i].cash>=lands[players[i].pos].cost){
												players[i].cash-=lands[players[i].pos].cost;
												lands[players[i].pos].owner=i;
												lands[players[i].pos].level=1;
												
												if(players[i].pos/10==0)lands[players[i].pos].img=land_img[players[i].charactor-1];
												if(players[i].pos/10==1)lands[players[i].pos].img=land_f_img[players[i].charactor-1];
												if(players[i].pos/10==2)lands[players[i].pos].img=land_f2_img[players[i].charactor-1];
												if(players[i].pos/10==3)lands[players[i].pos].img=land_f3_img[players[i].charactor-1];
												bool2=true;
											}
										}else{//stay
											bool2=true;
										}
									}
								}
							}else{
								if(players[i].pos%10==0||lands[players[i].pos].special>0){ //special land
									bool2=true;
								}else if(lands[players[i].pos].owner==i){
									if(players[i].AI){//AI upgrade
										if(players[i].cash/2>=lands[players[i].pos].cost/2&&lands[players[i].pos].level<4&&lands[players[i].pos].cold<1){
											players[i].cash-=lands[players[i].pos].cost/2;
											lands[players[i].pos].level++;
										}
										bool2=true;
									}else{
										Boolean bol = false, bol2;
										if(lands[players[i].pos].cold<1||players[i].charactor==1) {
											draw_text(g, "$"+String.valueOf(lands[players[i].pos].cost/2),-0.13, +0.3 , c2, 0.05 );
											bol = draw_button(g,"Upgrade",-0.13, +0.4 , c1, c2, 0.1);
										}
										bol2 = draw_button(g,"Stay",0.23, +0.4 , c1, c2, 0.1);
										if(lands[players[i].pos].cold<1||players[i].charactor==1)bool3 = draw_button(g, "Skill", -0.33, +0.4 , c1, c2, 0.1);
										if(bool3) skill2(g);//skill
										if(bol||bol2){
											if(bol){//upgrade
												if(players[i].cash>=lands[players[i].pos].cost/2&&lands[players[i].pos].level<4){
													players[i].cash-=lands[players[i].pos].cost/2;
													lands[players[i].pos].level++;
													bool2=true;
												}
											}else{//stay
												bool2=true;
											}
										}
									}
								}else {
									if(players[i].AI){//AI pay
										if(players[i].cash>lands[players[i].pos].cost*lands[players[i].pos].level/2){
											players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/2;
											players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/2;
											bool2=true;
										}
										else {			// no enough money to pay
											players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/2;
											players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/2;
											bool2=true;
										}
									}else {
										Boolean  bol;
										draw_text(g, "$"+String.valueOf(lands[players[i].pos].cost*lands[players[i].pos].level/2),-0.13, +0.3 , c2, 0.05 );
										bol = draw_button(g,"Pay",-0.13, +0.4 , c1, c2, 0.1);
										bool3 = draw_button(g, "Skill", -0.33, +0.4 , c1, c2, 0.1);
										if(bool3) skill3(g);//skill
										if(bol){			//pay
											if(players[i].cash>lands[players[i].pos].cost*lands[players[i].pos].level/2&&players[i].cash>=0){
												players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/2;
												players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/2;
												bool2=true;
											}
											else {			// no enough money to pay
												if(players[i].cash>=0){
													players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/2;
													players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/2;
													lose_num++;
												}
												bool2=true;
											}
										}
									}
								}
							}
							players[i].time--;
						}
						if(players[i].time==0)bool2=true; //time over
					}
					if(bool2){    //change player
						for(int i=0;i<player_num;i++){
							if(players[i].turn==0){
								players[i].time=timeLimit;
								players[i].turn=player_num-1;
								players[i].set_map_xy(this.getWidth(), this.getHeight(), 0);
							}
							else players[i].turn--;
						}
						dice1=0;
						dice2=0;
						for(int i=0;i<player_num;i++){
							if(players[i].turn==0){
								double[] a1 = {0,0};
								a1 = players[i].set_map_xy(this.getWidth(), this.getHeight(), 0);
								window_x=a1[0];
								window_y=a1[1];
							}
						}
						timer_g_move--;
					}
				} else{
					g.drawString(String.valueOf(dice1), this.getWidth()*4/5, this.getHeight()*4/5);
					g.drawString(String.valueOf(dice2), this.getWidth()*9/10, this.getHeight()*4/5);
				}
			}
			if(sw1)change_window2(g);
			if(sw2)change_window(g);
			
			if(timer_g_move<1&&!bool4)do_mouse_drag();
		}
		g.setColor(pencolor);
		
		repaint();
	}
	
	public void skill(Graphics g) {
		for(int i=0;i<player_num;i++){
			if(players[i].turn==0){
				if(players[i].charactor==1) {
					for(int j=0;j<40;j++)lands[j].cold=0;
					for(int j=0;j<5;j++) {
						int k = players[i].pos-2+j;
						if(k%10==0) {
							if(j<2)k--;
							else k++;
						}
						if(k<0)k+=40;
						if(k>=40)k-=40;
						lands[k].start_snow();
					}
				}
				if(players[i].charactor==2) {
					int dice = (int)(Math.random()*6)+1;
					if(dice<5) {
						if(players[i].cash>=lands[players[i].pos].cost/2){
							players[i].cash-=lands[players[i].pos].cost/2;
							lands[players[i].pos].owner=i;
							lands[players[i].pos].level=1;
							
							if(players[i].pos/10==0)lands[players[i].pos].img=land_img[players[i].charactor-1];
							if(players[i].pos/10==1)lands[players[i].pos].img=land_f_img[players[i].charactor-1];
							if(players[i].pos/10==2)lands[players[i].pos].img=land_f2_img[players[i].charactor-1];
							if(players[i].pos/10==3)lands[players[i].pos].img=land_f3_img[players[i].charactor-1];
							bool2=true;
						}
					} else {
						if(players[i].cash>=lands[players[i].pos].cost*2){
							players[i].cash-=lands[players[i].pos].cost*2;
							lands[players[i].pos].owner=i;
							lands[players[i].pos].level=1;
							
							if(players[i].pos/10==0)lands[players[i].pos].img=land_img[players[i].charactor-1];
							if(players[i].pos/10==1)lands[players[i].pos].img=land_f_img[players[i].charactor-1];
							if(players[i].pos/10==2)lands[players[i].pos].img=land_f2_img[players[i].charactor-1];
							if(players[i].pos/10==3)lands[players[i].pos].img=land_f3_img[players[i].charactor-1];
							bool2=true;
						}
					}
				}
			}
		}
	}
	
	public void skill2(Graphics g) {
		for(int i=0;i<player_num;i++){
			if(players[i].turn==0){
				if(players[i].charactor==1) {
					skill(g);
				}
				if(players[i].charactor==2) {
					int dice = (int)(Math.random()*6)+1;
					if(dice<5) {
						if(players[i].cash>=lands[players[i].pos].cost/4&&lands[players[i].pos].level<4&&(lands[players[i].pos].cold<1||players[i].charactor==1)){
							players[i].cash-=lands[players[i].pos].cost/4;
							lands[players[i].pos].level++;
							bool2=true;
						}
					} else {
						if(players[i].cash>=lands[players[i].pos].cost&&lands[players[i].pos].level<4&&(lands[players[i].pos].cold<1||players[i].charactor==1)){
							players[i].cash-=lands[players[i].pos].cost;
							lands[players[i].pos].level++;
							bool2=true;
						}
					}
				}
			}
		}
	}
	
	public void skill3(Graphics g) {
		for(int i=0;i<player_num;i++){
			if(players[i].turn==0){
				if(players[i].charactor==1) {
					skill(g);
				}
				if(players[i].charactor==2) {
					int dice = (int)(Math.random()*6)+1;
					if(dice<5) {
						if(players[i].cash>lands[players[i].pos].cost*lands[players[i].pos].level/4){
							players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/4;
							players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/4;
						}else{
							if(players[i].cash>=0){
								players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/4;
								players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/4;
								lose_num++;
							}
						}
						bool2=true;
					} else {
						if(players[i].cash>lands[players[i].pos].cost*lands[players[i].pos].level){
							players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level;
							players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level;
						}else{
							if(players[i].cash>=0){
								players[i].cash-=lands[players[i].pos].cost*lands[players[i].pos].level/4;
								players[lands[players[i].pos].owner].cash+=lands[players[i].pos].cost*lands[players[i].pos].level/4;
								lose_num++;
							}
						}
						bool2=true;
					}
				}
			}
		}
	}
	
	public void ini4() {
		window_x = 2923*0.9-this.getWidth()/2;
		window_y = 2067.0/2-this.getHeight()/2;
		try {
			for(int i = 0;i<40;i++) {
				if(i%10==0) {
					lands[i].img=ImageIO.read(new File("img\\land_c.png"));
				}else if(i/10%2==0) {
					lands[i].img=ImageIO.read(new File("img\\land.png"));
				}else {
					lands[i].img=ImageIO.read(new File("img\\land_f.png"));
				}
				lands[i].pos=i;
				lands[i].set_map_xy();
			}
			house_img[0]=ImageIO.read(new File("img\\house_a.png"));
			house_img[1]=ImageIO.read(new File("img\\house_1.png"));
			house_img[2]=ImageIO.read(new File("img\\house_2.png"));
			house_img[3]=ImageIO.read(new File("img\\house_3.png"));
			house_img[4]=ImageIO.read(new File("img\\house_4.png"));
			house_img[5]=ImageIO.read(new File("img\\house_5.png"));
			house_img[6]=ImageIO.read(new File("img\\house_6.png"));
			house_f_img[0]=ImageIO.read(new File("img\\house_a.png"));
			house_f_img[1]=ImageIO.read(new File("img\\house_1.png"));
			house_f_img[2]=ImageIO.read(new File("img\\house_2.png"));
			house_f_img[3]=ImageIO.read(new File("img\\house_3.png"));
			house_f_img[4]=ImageIO.read(new File("img\\house_4.png"));
			house_f_img[5]=ImageIO.read(new File("img\\house_5.png"));
			house_f_img[6]=ImageIO.read(new File("img\\house_6.png"));
			board_img=ImageIO.read(new File("img\\board.png"));
			land_img[0]=ImageIO.read(new File("img\\land_1.png"));
			land_img[1]=ImageIO.read(new File("img\\land_2.png"));
			land_img[2]=ImageIO.read(new File("img\\land_3.png"));
			land_img[3]=ImageIO.read(new File("img\\land_4.png"));
			land_img[4]=ImageIO.read(new File("img\\land_5.png"));
			land_img[5]=ImageIO.read(new File("img\\land_6.png"));
			land_AI_img=ImageIO.read(new File("img\\land_a.png"));
			land_f_img[0]=ImageIO.read(new File("img\\land_1.png"));
			land_f_img[1]=ImageIO.read(new File("img\\land_2.png"));
			land_f_img[2]=ImageIO.read(new File("img\\land_3.png"));
			land_f_img[3]=ImageIO.read(new File("img\\land_4.png"));
			land_f_img[4]=ImageIO.read(new File("img\\land_5.png"));
			land_f_img[5]=ImageIO.read(new File("img\\land_6.png"));
			land_AI_f_img=ImageIO.read(new File("img\\land_a.png"));
			land_f2_img[0]=ImageIO.read(new File("img\\land_1.png"));
			land_f2_img[1]=ImageIO.read(new File("img\\land_2.png"));
			land_f2_img[2]=ImageIO.read(new File("img\\land_3.png"));
			land_f2_img[3]=ImageIO.read(new File("img\\land_4.png"));
			land_f2_img[4]=ImageIO.read(new File("img\\land_5.png"));
			land_f2_img[5]=ImageIO.read(new File("img\\land_6.png"));
			land_AI_f2_img=ImageIO.read(new File("img\\land_a.png"));
			land_f3_img[0]=ImageIO.read(new File("img\\land_1.png"));
			land_f3_img[1]=ImageIO.read(new File("img\\land_2.png"));
			land_f3_img[2]=ImageIO.read(new File("img\\land_3.png"));
			land_f3_img[3]=ImageIO.read(new File("img\\land_4.png"));
			land_f3_img[4]=ImageIO.read(new File("img\\land_5.png"));
			land_f3_img[5]=ImageIO.read(new File("img\\land_6.png"));
			land_AI_f3_img=ImageIO.read(new File("img\\land_a.png"));
			snow_img=ImageIO.read(new File("img\\snow.png"));
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-land_AI_f_img.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			land_AI_f_img = op.filter(land_AI_f_img, null);
			
			tx = AffineTransform.getScaleInstance(-1, -1);
			tx.translate(-land_AI_f2_img.getWidth(null), -land_AI_f2_img.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			land_AI_f2_img = op.filter(land_AI_f2_img, null);
			
			tx = AffineTransform.getScaleInstance(1, -1);
			tx.translate(0, -land_AI_f3_img.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			land_AI_f3_img = op.filter(land_AI_f3_img, null);
			for(int i=0;i<6;i++) {
				tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-land_f_img[i].getWidth(null), 0);
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				land_f_img[i] = op.filter(land_f_img[i], null);
				
				tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-house_f_img[i].getWidth(null), 0);
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				house_f_img[i] = op.filter(house_f_img[i], null);
				
				tx = AffineTransform.getScaleInstance(-1, -1);
				tx.translate(-land_f2_img[i].getWidth(null), -land_f2_img[i].getHeight(null));
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				land_f2_img[i] = op.filter(land_f2_img[i], null);
				
				tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -land_f3_img[i].getHeight(null));
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				land_f3_img[i] = op.filter(land_f3_img[i], null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void select_m(Graphics g) {
		if(ini3) {
			ini3();
			ini3=false;
			sc=1;
		}
		
		g.drawImage(bg[sc], this.getWidth()*1/20, this.getHeight()/10, this.getWidth()*2/5, this.getHeight()*3/5, null);
		g.drawImage(select_window_img, this.getWidth()*11/20, this.getHeight()/10, this.getWidth()*2/5, this.getHeight()*3/5, null);
		int sww =this.getWidth()*2/5, swx=this.getWidth()*11/20, swy=this.getHeight()/10;
		g.drawImage(random_map_select_img, swx+sww/34, swy+sww/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[0], swx+sww*12/34, swy+sww/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[1], swx+sww*23/34, swy+sww/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[2], swx+sww/34, swy+sww*2/34+sww*5/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[3], swx+sww*12/34, swy+sww*2/34+sww*5/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[4], swx+sww*23/34, swy+sww*2/34+sww*5/34, sww*10/34, sww*5/34, null);
		g.drawImage(map_select[5], swx+sww/34, swy+sww*3/34+sww*5/17, sww*10/34, sww*5/34, null);
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(i*3+j<=6) {
					if(check_press(swx+sww/34+j*sww*11/34, swy+(i+1)*sww/34+i*sww*5/34, sww*10/34, sww*5/34))sc=i*3+j;
					if(sc==i*3+j) {
						g.drawImage(select_rect_img, swx+sww/34+j*sww*11/34, swy+(i+1)*sww/34+i*sww*5/34, sww*10/34, sww*5/34, null);
					}
				}
			}
		}
	}
	
	public void do_mouse_drag() {
		if(mouse_press) {
			window_x-=mouse_x-mouse_ox;
			window_y-=mouse_y-mouse_oy;
		}
		if(window_x>2923-this.getWidth())window_x=2923.0-this.getWidth();
		if(window_y>2067-this.getHeight())window_y=2067.0-this.getHeight();
		if(window_x<0)window_x=0.0;
		if(window_y<0)window_y=0.0;
		mouse_ox=mouse_x;
		mouse_oy=mouse_y;
	}
	
	private boolean check_move(int x, int y, int w, int h) {
		if(mouse_x>x&&mouse_x<x+w&&mouse_y>y&&mouse_y<y+h)return true;
		return false;
	}

	private void ini3(){
		bg = new BufferedImage[20];
		map_select = new BufferedImage[20];
		try {
			bg[0]=ImageIO.read(new File("img\\bg0.png"));
			bg[1]=ImageIO.read(new File("img\\bg1.png"));
			bg[2]=ImageIO.read(new File("img\\bg2.png"));
			bg[3]=ImageIO.read(new File("img\\bg3.png"));
			bg[4]=ImageIO.read(new File("img\\bg4.png"));
			bg[5]=ImageIO.read(new File("img\\bg5.png"));
			bg[6]=ImageIO.read(new File("img\\bg6.png"));
			map_select[0]=ImageIO.read(new File("img\\map1_select.png"));
			map_select[1]=ImageIO.read(new File("img\\map2_select.png"));
			map_select[2]=ImageIO.read(new File("img\\map3_select.png"));
			map_select[3]=ImageIO.read(new File("img\\map4_select.png"));
			map_select[4]=ImageIO.read(new File("img\\map5_select.png"));
			map_select[5]=ImageIO.read(new File("img\\map6_select.png"));
			select_rect_img=ImageIO.read(new File("img\\select_rect.png"));
			random_map_select_img=ImageIO.read(new File("img\\random_map_select.png"));
			select_window_img=ImageIO.read(new File("img\\select_window.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setcolor1(Color c) {
		pencolor = c;
	}
	
	public static Color getcolor2() {
		return pencolor;
	}
	
	public void paintComponent( Graphics g2 ){   //show image
		if(img==null){
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			int width = gd.getDisplayMode().getWidth();
			int height = gd.getDisplayMode().getHeight();
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g =(Graphics2D)img.getGraphics();
			g.setColor(Color.white);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		Graphics2D g =(Graphics2D)img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		drawgame(g);
		
		
		g2.drawImage(img,0, 0,null);
		mouse_pressed=false;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP){
			key_up=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			key_down=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			key_left=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			key_right=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			key_esc=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z){
			key_z=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			key_space=true;
			
		}
	}
	
	

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP){
			key_up=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			key_down=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			key_left=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			key_right=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			key_esc=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z){
			key_z=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			key_space=false;
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	class mouse1 extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			mouse_px=e.getX();
			mouse_py=e.getY();
			mouse_press=true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			mouse_rx=e.getX();
			mouse_ry=e.getY();
			mouse_press=false;
			mouse_pressed=true;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mouse_x=e.getX();
			mouse_y=e.getY();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			mouse_ox=mouse_x;
			mouse_oy=mouse_y;
			mouse_x=e.getX();
			mouse_y=e.getY();
		}
	}
	
	public Boolean check_click(int x, int y, int w, int h) {
		if(mouse_pressed&&mouse_px>x&&mouse_px<x+w&&mouse_rx>x&&mouse_rx<x+w&&mouse_py>y&&mouse_py<y+h&&mouse_ry>y&&mouse_ry<y+h)return true;
		return false;
	}
	
	public Boolean check_press(int x, int y, int w, int h) {
		if(mouse_press&&mouse_px>x&&mouse_px<x+w&&mouse_py>y&&mouse_py<y+h&&mouse_x>x&&mouse_x<x+w&&mouse_y>y&&mouse_y<y+h)return true;
		return false;
	}
	

	public void select_p(Graphics g, int w, int y) {
		if(ini2) {
			ini2();
			players[0].player=true;
			players[1].player=true;
			players[0].turn=0;
			players[1].turn=1;
			ini2=false;
			System.out.print(players[0].turn);
		}
		g.drawImage(player_board[players[0].charactor-1], w/49, y, w/7, (int)(w/0.618/7), null);
		
		g.drawImage(P_img[0], w/49 + w/28 , (int)(w/0.618/7*1.05+y), w/14, (int)(w/14*0.618), null);
		if(check_press(w/49+w*2/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))){
			g.drawImage(selector_pl_img, w/49+w*2/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
		} else {
			g.drawImage(selector_l_img, w/49+w*2/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
		}
		if(check_press(w/49+w*12/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))){
			g.drawImage(selector_pr_img, w/49+w*12/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
		} else {
			g.drawImage(selector_r_img, w/49+w*12/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
		}
		if(check_click(w/49+w*2/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))) {
			int num=players[0].charactor;
			num--;
			if(num<1)num=charactor_num;
			players[0].change_charactor(num, player_charactor);
		}
		if(check_click(w/49+w*12/105, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))) {
			int num=players[0].charactor;
			num++;
			if(num>charactor_num)num=1;
			players[0].change_charactor(num, player_charactor);
		}
		player_num2=1;
		AI_num=0;
		for(int i = 1;i<6;i++) {
			if(player[i]==1)g.drawImage(player_board[players[i].charactor-1], w/49+w*8*i/49, y, w/7, (int)(w/0.618/7), null);
			if(player[i]==2)g.drawImage(AI_board, w/49+w*8*i/49, y, w/7, (int)(w/0.618/7), null);
			if(player[i]==0){
				g.drawImage(noplayer_img, w/49+w*8*i/49, y, w/7, (int)(w/0.618/7), null);
			}else{
				if(players[i].player) {
					if(check_press(w/49+w*2/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))){
						g.drawImage(selector_pl_img, w/49+w*2/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
					} else {
						g.drawImage(selector_l_img, w/49+w*2/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
					}
					if(check_click(w/49+w*2/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))) {
						int num=players[i].charactor;
						num--;
						if(num<1)num=charactor_num;
						players[i].change_charactor(num, player_charactor);
					}
					if(check_press(w/49+w*12/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))){
						g.drawImage(selector_pr_img, w/49+w*12/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
					} else {
						g.drawImage(selector_r_img, w/49+w*12/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140), null);
					}
					if(check_click(w/49+w*12/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))) {
						int num=players[i].charactor;
						num++;
						if(num>charactor_num)num=1;
						players[i].change_charactor(num, player_charactor);
					}
				}
			}
			if(players[i].player){
				g.drawImage(P_img[player_num2], w/49 + w/28 +w*8*i/49, (int)(w/0.618/7*1.05+y), w/14, (int)(w/14*0.618), null);
				player_num2+=1;
			}
			if(players[i].AI){
				g.drawImage(AI_img[AI_num], w/49 + w/35 +w*8*i/49, (int)(w/0.618/7*1.05+y), w*3/35, (int)(w/14*0.618), null);
				AI_num+=1;
			}
			if((player[i]==0||player[i]!=0&&(!check_click(w/49+w*2/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140))||players[i].AI)&&!check_click(w/49+w*12/105+w*8*i/49, y+(int)(w*17/0.618/140), w/105, (int)(w/0.618/140)))&&check_click(w/49+w*8*i/49, y, w/7, (int)(w/0.618/7))) {
				if(player[i]==2) {
					if(player_num>2) {
						player[i]=0;
						players[i].change_charactor(-1, player_charactor);
						players[i].turn=-1;
						player_num-=1;
						continue;
					}else {
						player[i]=1;
						players[i].change_charactor(1, player_charactor);
						continue;
					}
				}
				if(player[i]==1) {
					player[i]=2;
					players[i].change_charactor(0, player_charactor);
				}
				if(player[i]==0) {
					player[i]=1;
					players[i].change_charactor(1, player_charactor);
					players[i].turn=player_num;
					player_num+=1;
				}
			}
		}
	}
	
	private void ini2() {
		try {
			noplayer_img=ImageIO.read(new File("img\\noplayer.png"));
			player_board[0]=ImageIO.read(new File("img\\player1_board.png"));
			player_board[1]=ImageIO.read(new File("img\\player2_board.png"));
			player_board[2]=ImageIO.read(new File("img\\player3_board.png"));
			player_board[3]=ImageIO.read(new File("img\\player4_board.png"));
			player_board[4]=ImageIO.read(new File("img\\player5_board.png"));
			player_board[5]=ImageIO.read(new File("img\\player6_board.png"));
			AI_board=ImageIO.read(new File("img\\AI_board.png"));
			P_img[0]=ImageIO.read(new File("img\\P1.png"));
			P_img[1]=ImageIO.read(new File("img\\P2.png"));
			P_img[2]=ImageIO.read(new File("img\\P3.png"));
			P_img[3]=ImageIO.read(new File("img\\P4.png"));
			P_img[4]=ImageIO.read(new File("img\\P5.png"));
			P_img[5]=ImageIO.read(new File("img\\P6.png"));
			AI_img[0]=ImageIO.read(new File("img\\AI1.png"));
			AI_img[1]=ImageIO.read(new File("img\\AI2.png"));
			AI_img[2]=ImageIO.read(new File("img\\AI3.png"));
			AI_img[3]=ImageIO.read(new File("img\\AI4.png"));
			AI_img[4]=ImageIO.read(new File("img\\AI5.png"));
			selector_l_img=ImageIO.read(new File("img\\selector_l.png"));
			selector_r_img=ImageIO.read(new File("img\\selector_r.png"));
			selector_pl_img=ImageIO.read(new File("img\\selector_pl.png"));
			selector_pr_img=ImageIO.read(new File("img\\selector_pr.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


