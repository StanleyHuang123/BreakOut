import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;

// top-level container class
class Breakout extends JComponent{

	//const

	// constructor for the game
	// instantiates all of the top-level classes (model, view)
	// and tells the model to start the game
	//private int mouseX;
    //private int mouseY;
    private String s;
    private int paddle_speed = 0;
    //private int ball_speed;
    private Timer timer;
    private int direction_status = 0;
    public static final int Delay = 1000;
    public static final int Period = 10;
    private Paddle p;
    private Bricks b;
    private Ball ball;
    private final int row_num = 5;
    private final int col_num = 10;
    private Bricks[][] b_all = new Bricks[col_num][row_num];
    private int mouseX;
    private Boolean start = false;
    private final int paddle_height = 10;
    private int process = 0;
    private int fps_p = 0;
    private int score = 0;
    private int win_score = 0;
    private int cur_bricks = 0;
    private int num_bricks = 50;
    //private int col = 0;
   // private int control;
    public void start_paint(Graphics2D g2){
    			g2.setColor(Color.decode("#32CD32"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,40));
        		String break_S = "Breakout";
        		g2.drawString(break_S, getWidth() / 4 + getWidth() / 11, getHeight() / 2 - 200);

				g2.setColor(Color.decode("#FF4500"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        		String name_S = "Yuanjie Huang";
        		g2.drawString(name_S, getWidth() / 4 + getWidth() / 16, getHeight() / 2 - 150);
        		g2.setColor(Color.decode("#FF4500"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        	    String id_S = "20413179";
        	    g2.drawString(id_S, getWidth() / 3 + getWidth() / 64, getHeight() / 2 - 100);
        	    //description
        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String intro_S = "Press <- or Click left part of game screen to";
        	    g2.drawString(intro_S, getWidth() / 4, getHeight() / 2 - 50);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String intro_Sl = "move paddle left";
        	    g2.drawString(intro_Sl, getWidth() / 4, getHeight() / 2 - 20);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String intro_Sr = "Press -> or Click right part of game screen to";
        	    g2.drawString(intro_Sr, getWidth() / 4, getHeight() / 2 + 50);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String intro_Srr = "move paddle right";
        	    g2.drawString(intro_Srr, getWidth() / 4, getHeight() / 2 + 80);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String speed_S = "Press U to speed up and Press D to speed down";
        	    g2.drawString(speed_S, getWidth() / 4, getHeight() / 2 + 150);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String cheating_S = "Press C to end the game immediately";
        	    g2.drawString(cheating_S, getWidth() / 4, getHeight() / 2 + 200);

        	    g2.setColor(Color.decode("#1E90FF"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        	    String cheating_Ss = "and you win";
        	    g2.drawString(cheating_Ss, getWidth() / 4, getHeight() / 2 + 230);

        	    g2.setColor(Color.decode("#708090"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,15));
        	    String enter_Ss = "Press Enter to start the game";
        	    g2.drawString(enter_Ss, (5 * getWidth()) / 8, (11 * getHeight()) / 12);
		this.addKeyListener(new KeyAdapter() {			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					process = 4;
				}
			}
		});
	}
	Breakout(int sp, int fps){
		//intial of board
		//add(new Board());	
		//this.setFocusable(true);
		//System.out.println("board width is " + getWidth());
		//process = 1;
		fps_p = fps;
		this.addKeyListener(new KeyAdapter() {			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					//move right
					s = "move right";
					direction_status = 1;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					//move left
					s = "move left";
					direction_status = -1;
				}
				//if(e.getKeyCode() == KeyEvent.VK_ENTER){
					//game start
					//start = true;
				//}
			}
		});
		this.addKeyListener(new KeyAdapter() {			
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					//move right
					//s = "move right";
					direction_status = 0;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					//move left
					//s = "move left";
					direction_status = 0;
				}
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				mouseX = e.getX();
				if(mouseX >= (getWidth() / 2)){
					direction_status = 1;
				}
				else{
					direction_status = -1;
				}
			}
		});
		p = new Paddle();
		//System.out.println("now width is " + getWidth());

		ball = new Ball(-10, -10, 0, 0, sp);
		for(int x = 0; x < col_num; x++){
			for(int y = 0; y < row_num; y++){
				Random r = new Random();
				int result = r.nextInt(5);
				//System.out.println("brick: " + x + ", " + y + " result");
				int tmp_res = result;
				tmp_res++;
				win_score += tmp_res;
				b = new Bricks(x, y, result);
				b_all[x][y] = b;
			}
		}
		this.addKeyListener(new KeyAdapter() {			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_C){
					for(int x = 0; x < col_num; x++){
						for(int y = 0; y < row_num; y++){
							b_all[x][y].z = 5;
							cur_bricks = num_bricks;
							score = win_score;
							process = 2;
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_U){
					ball.speed++;
				}
				if(e.getKeyCode() == KeyEvent.VK_D){
					ball.speed--;
				}
			}
		});

		//check();

		//System.out.println("board width is " + getWidth());
		timer = new Timer();
		int new_fps = 1000 / fps;
		timer.schedule(new TimerTask(){
			public void run(){
				//p.move();
				//ball.b_move();
				check();
				repaint();
			}
		}, Delay, new_fps);
		this.setFocusable(true);
	}
	private void check(){
		//System.out.println("ball's x is " + ball.x);
		//check paddle
		int bot_bound = (getHeight() - getWidth() / 40) - paddle_height;
		//System.out.println("bot_bound is " + bot_bound);
		if(ball.y >= bot_bound){
			//System.out.println("ball.y of paddle is " + ball.y);
			//System.out.println("paddle left is " + p.p_leftx() + " and ball.x is " + ball.x + " right is " + p.p_rightx());

			if(ball.x >= p.p_leftx() && ball.x <= (p.p_rightx() + getWidth() / 40)){
				//System.out.println("???");
				ball.dir_y = -1;
				//System.out.println("direction_status is " + direction_status);
				//ball.dir_x = direction_status;
				if(ball.dir_x != direction_status){
					if(direction_status != 0){
						ball.dir_x *= -1;
					}
				}
			}
		}
		//check bricks
		if(ball.y <= getHeight() / 4){
			if(process == 1){
				if(ball.x < 0){
					ball.x = 1;
				}
				if(ball.y < 0){
					ball.y = 1;
				}
			}
	
			int x_cal = ball.x / (getWidth() / 10);
			int x_val = ball.x % (getWidth() / 10);
			int y_cal = ball.y / (getHeight() / 20);
			int y_val = ball.y % (getHeight() / 20);
			
			
			int new_spped = ball.speed;
			if(y_val < new_spped && x_val > new_spped){
				if(y_cal != 0){
					int tmp = y_cal;
					tmp--;
					if(b_all[x_cal][tmp].z != 5){
						int tmp_sc = b_all[x_cal][tmp].z;
						tmp_sc++;
						score += tmp_sc;
						cur_bricks++;
						if(score == win_score && cur_bricks == num_bricks){
							process = 2;
						}
						
						ball.dir_y *= -1;
				
						b_all[x_cal][tmp].z = 5;
					}
				}
				
			}
			if(y_val > (getHeight() / 20 - new_spped) && x_val > new_spped){
				//if(y_cal != 4){
				int tmp = y_cal;
					//tmp++;
				if(b_all[x_cal][tmp].z != 5){
					int tmp_sc = b_all[x_cal][tmp].z;
					tmp_sc++;
					score += tmp_sc;
					cur_bricks++;
					if(score == win_score && cur_bricks == num_bricks){
						process = 2;
					}
	
					ball.dir_y *= -1;
				
					b_all[x_cal][tmp].z = 5;
		
				}
			}
			
			if(y_val >= getHeight() / 40 && x_val > new_spped){
				if(y_cal != 4){
					int tmp = y_cal;
					tmp++;
					if(b_all[x_cal][tmp].z != 5){
						int tmp_sc = b_all[x_cal][tmp].z;
						tmp_sc++;
						score += tmp_sc;
						cur_bricks++;
						if(score == win_score && cur_bricks == num_bricks){
							process = 2;
						}
						
						ball.dir_y *= -1;
				
						b_all[x_cal][tmp].z = 5;
					}
				}
			}
			if(x_val < new_spped && y_val > new_spped){
				if(x_cal != 0){
					int tmp = x_cal;
					tmp--;
					if(b_all[tmp][y_cal].z != 5){
						int tmp_sc = b_all[tmp][y_cal].z;
						tmp_sc++;
						score += tmp_sc;
						cur_bricks++;
						if(score == win_score && cur_bricks == num_bricks){
							process = 2;
						}
						
						ball.dir_x *= -1;
				
						b_all[tmp][y_cal].z = 5;
					}
				}
				else{
			
					ball.dir_x *= -1;
					b_all[x_cal][y_cal].z = 5;
				}
			}
			if(x_val > (getWidth() - new_spped) && y_val > new_spped){
				if(b_all[x_cal][y_cal].z != 5){
					int tmp_sc = b_all[x_cal][y_cal].z;
						tmp_sc++;
						score += tmp_sc;
						cur_bricks++;
						if(score == win_score && cur_bricks == num_bricks){
							process = 2;
						}
	
						ball.dir_x *= -1;
			
						b_all[x_cal][y_cal].z = 5;
					}
			}
			if(x_val >= (3 * getWidth()) / 40 && y_val > new_spped){
				if(x_cal != 9){
					int tmp = x_cal;
					tmp++;
					if(b_all[tmp][y_cal].z != 5){
						int tmp_sc = b_all[tmp][y_cal].z;
						tmp_sc++;
						score += tmp_sc;
						cur_bricks++;
						if(score == win_score && cur_bricks == num_bricks){
							process = 2;
						}
		
						ball.dir_x *= -1;
		
						b_all[tmp][y_cal].z = 5;
					}
				}
			}

		}
		if(ball.y >= getHeight()){
			process = 2;
		}
	}

	private class Paddle{
		//public paint class
		int right_x, left_x;
		int p_y = getHeight() - paddle_height;
		Paddle(){}
		public void move(){
			int current_loc = (getWidth()/2 - getWidth()/16) + paddle_speed;
			if(current_loc <= 0){
				if(direction_status == -1){
					direction_status = 0;
				}
			}
			if(current_loc >= (7 * getWidth())/8){
				if(direction_status == 1){
					direction_status = 0;
				}
			}
			paddle_speed += direction_status * 8;

			this.left_x = (getWidth()/2 - (getWidth()/12) + paddle_speed);
		}
		public int p_leftx(){
			return this.left_x;
		}
		public int p_rightx(){
			this.right_x = this.left_x + getWidth() / 8;
			return this.right_x;
		}
		public int pv_y(){
			return this.p_y;
		}
		public void p_paint_K(Graphics2D p_g2){
				this.move();
				p_g2.setColor(Color.decode("#4169E1"));
				p_g2.fillRoundRect(this.left_x, (getHeight() - 10), (getWidth() / 6), paddle_height, 10, 10);
		}
	}
	private class Bricks{
		int x, y, z;

		Bricks(int a, int b, int c){
			this.x = a;
			this.y = b;
			this.z = c;
		}
		public int leftcor_x(){
			return this.x * (getWidth() / 10);
		}
		public int leftcor_y(){
			return this.x * (getHeight() / 20);
		}
		public int b_color(){
			return this.z;
		}
		public void b_paint(Graphics2D b_g2){
			int b_width = getWidth() / 10;
	    	int b_height = getHeight() / 20;

			switch(z){
				case 0:
				//dark purple
					b_g2.setColor(Color.decode("#7B68EE"));
					break;
				case 1:
				//lighter blue
					b_g2.setColor(Color.decode("#7FFFD4"));
					//b_g2.setColor(Color.blue);
					break;
				case 2:
					//light blue
					b_g2.setColor(Color.decode("#00BFFF"));
					//b_g2.setColor(Color.red);
					break;
				case 3:
				//light purple
					b_g2.setColor(Color.decode("#EE82EE"));
					//b_g2.setColor(Color.green);
					break;
				case 4:
				//light green
					b_g2.setColor(Color.decode("#ADFF2F"));
					//b_g2.setColor(Color.yellow);
					break;
				default:
					b_g2.setColor(Color.decode("#FFFFE0"));
					break;
				}
				//System.out.println("color is " + Color.BLUE);
				//if(process == 1){
					b_g2.fillRoundRect(this.x * b_width + 2, this.y * b_height + 2, b_width - 4, b_height - 4, 20, 20);
				//}
		}
	}
	private class Ball{
		int x, y, dir_x, dir_y, speed;
		Ball(int a, int b, int c, int d, int e){
			this.x = a;
			this.y = b;
			this.dir_x = c;
			this.dir_y = d;
			this.speed = e;
		}
		public int b_x(){
			return this.x;
		}
		public int b_y(){
			return this.y;
		}
		
		public void b_move(){
		
			int start_ball = 0;
	
			if(x == -10 && process == 4){
				dir_x = 1;
				x = getWidth() / 2 - getWidth() / 80;
				start_ball++;
			
			}
			if(y == -10 && process == 4){
				dir_y = -1;
				y = getHeight() / 2 + 200;
				start_ball++;
			
			}
	
			if(start_ball == 2){
				process = 1;
			}
	
			int radius = getWidth() / 80;
		
			if(x <= 0 || x >= (39 * getWidth()) / 40){
				//System.out.println("now x is " + x);
				dir_x *= -1;
			}
	
			if(y <= 0){
				dir_y *= -1;
			}

			x += dir_x * speed;
		
			y += dir_y * speed;
		
		}
		
		public void ball_paint(Graphics2D ball_g2){
			//this.intial_ball();
			this.b_move();
			ball_g2.setColor(Color.MAGENTA);

		
		
			ball_g2.fillOval(x, y, getWidth() / 40, getWidth() / 40);
	
		}
	}

	public void paintComponent(Graphics g) { 
        	Graphics2D g2 = (Graphics2D) g;
        	
        	if(process == 0){
        		start_paint(g2);
        	}
        	if(process == 1 || process == 4){
        		
        		for(int x = 0; x < col_num; x++){
        			for(int y = 0; y < row_num; y++){
        				b_all[x][y].b_paint(g2);
        			}
        		}
        		ball.ball_paint(g2);
        		p.p_paint_K(g2);
        	    g2.setColor(Color.decode("#4B0082"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
      
        		String p_x = "fps " + fps_p;
        		g2.drawString(p_x, 0, getHeight() - 30);
        		String score_s = "score " + score;
        		g2.setColor(Color.decode("#4B0082"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,20));
        		g2.drawString(score_s, getWidth() - 100, getHeight() - 30);
        
        	}
        	if(process == 2){
        		//game over
        		g2.setColor(Color.decode("#4B0082"));
        	    g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        		String over = "Game Over";
        		g2.drawString(over, getWidth() / 2 - 100, getHeight() / 2 - 150);
        		if(score == win_score && cur_bricks == num_bricks){
        			String win_game = "Congratulations! You Win!";
        			String score_w = "Score: " + score;
        			g2.setColor(Color.decode("#4B0082"));
        	    	g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        			g2.drawString(win_game, getWidth() / 2 - 230, getHeight() / 2 );
        			g2.setColor(Color.decode("#4B0082"));
        	    	g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        			g2.drawString(score_w, getWidth() / 2 - 100, getHeight() / 2 + 150);
        		}
        		else{
        			String lose_game = "Sorry! You Lose :(";
        			String score_w = "Score: " + score;
        			g2.setColor(Color.decode("#4B0082"));
        	    	g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        			g2.drawString(lose_game, getWidth() / 2 - 170, getHeight() / 2);
        			g2.setColor(Color.decode("#4B0082"));
        	    	g2.setFont(new Font("Jokerman",Font.PLAIN,30));
        			g2.drawString(score_w, getWidth() / 2 - 100, getHeight() / 2 + 150);
        		}
        	}
    	}

	// entry point for the application
	public static void main(String[] args) {
		JFrame f = new JFrame("Break Out"); // jframe is the app window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 622); // window size
       
        int ball_speed = Integer.parseInt(args[1]);
        int fps = Integer.parseInt(args[0]);
        Breakout canvas = new Breakout(ball_speed, fps);
        f.setResizable(true);  //resizable
        f.setContentPane(canvas); // add canvas to jframe
        f.setVisible(true);
        f.setBackground(Color.decode("#FFFFE0"));
	}
}
