import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Graphics.*;

import javax.swing.*;

/*
 * Press "ENTER" to start the game
 */
public class Pacman extends JPanel implements ActionListener{
	private final int LEFT = 0;
	private final int DOWN = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int step = 24;
	private final int xlimit = step *28;
	private final int ylimit = step *32;
	private boolean welcome_screen  = true;
	private boolean paused, invulnerable,returned_invulnerable, invulnerable2, returned_invulnerable2;
	private boolean automate;
	private int x_pacman, x_pacman2 ;
	private int y_pacman, y_pacman2;
	private int x_blinky, x_inky, x_pinky, x_clyde;
	private int y_blinky, y_inky, y_pinky, y_clyde;
	private int view_blinky, view_inky, view_pinky, view_clyde;
	private int vision_blinky, vision_inky, vision_pinky, vision_clyde;
	private int view_pacman, view_pacman2,  lastview_pacman, lastview_pacman2;
	private int pacman_lives, pacman2_lives;
	private int pacman_scores, pacman2_scores;
	private int speed_pacman, speed_pacman2, speed_ghost;
	private int pacman_mouth, pacman2_mouth;
	//for paused type, 1 means game start countdown, 2 means player 1 dies, 3 means player 2 dies.
	private int paused_type;
	private long paused_time, invulnerable_time, invulnerable_time2;
	private Image blinky, inky, pinky, clyde;
	private Image pacman, pacman_left,pacman_right, pacman_up,pacman_down;
	private Image pacman_leftdie,pacman_rightdie, pacman_updie,pacman_downdie;
	private Image pacman_invulnerable;
	private Image pacman_leftclosed,pacman_rightclosed, pacman_upclosed,pacman_downclosed, pacman_closed;
	private Image pacman2,pacman2_left, pacman2_right, pacman2_up, pacman2_down;
	private Image pacman2_leftclosed,pacman2_rightclosed, pacman2_upclosed,pacman2_downclosed, pacman2_closed;
	private Image welcome, pill;
	private Timer timer ;
	private Random rand = new Random();
	private Rectangle rec[];
	private int [] pos_scores;
	private int[][] pos_legal = {
			{1,1 },{2,1},{3,1},{4,1},{5,1},{6,1},{7,1},{8,1},{9,1},{10,1},{11,1},{12,1},{15,1},
			{16,1},{17,1},{18,1},{19,1},{20,1},{21,1},{22,1},{23,1},{24,1},{25,1},{26,1},
			{1,2}, { 6,2}, {12,2}, {15,2}, {21,2},{26,2},
			{1,3}, { 6,3}, {12,3}, {15,3}, {21,3},{26,3},
			{1,4}, { 6,4}, {12,4}, {15,4}, {21,4},{26,4},
			{1,5 },{2,5},{3,5},{4,5},{5,5},{6,5},{7,5},{8,5},{9,5},{10,5},{11,5},{12,5},{13,5},
			{14, 5},{15,5},{16,5},{17,5},{18,5},{19,5},{20,5},{21,5},{22,5},{23,5},
			{24,5},{25,5},{26,5},
			{1,6}, { 6,6}, {9,6}, {18,6}, {21,6},{26,6},
			{1,7}, { 6,7}, {9,7}, {18,7}, {21,7},{26,7},
			{1,8 },{2,8},{3,8},{4,8},{5,8},{6,8},{9,8},{10,8},{11,8},{12,8},
			{15,8},{16,8},{17,8},{18,8},{21,8},{22,8},{23,8},
			{24,8},{25,8},{26,8},
			{6,9}, {12,9}, {15, 9}, {21,9},
			{6,10}, {12,10}, {15, 10}, {21,10},
			{6,11}, {9,11}, {10, 11}, {11, 11},{12,11}, {13,11}, {14,11},{15, 11},{16,11},
			{17,11},{18,11}, {21,11},
			{6,12}, {9,12}, {18, 12}, {21,12},
			{6,13}, {9,13}, {18, 13}, {21,13},
			{0, 14}, {1,14},{2,14},{3,14},{4,14},{5,14},{6,14},{7,14},{8,14},{9,14},
			{18,14},{19,14},{20,14},{21,14},{22,14},{23,14},
			{24,14},{25,14},{26,14},{27,14},
			{6,15}, {9,15}, {18, 15}, {21,15},
			{6,16}, {9,16}, {18, 16}, {21,16},
			{6,17}, {9,17}, {10, 17}, {11, 17},{12,17}, {13,17}, {14,17},{15, 17},{16,17},
			{17,17},{18,17}, {21,17},
			{6,18}, {9,18}, {18, 18}, {21,18},
			{6,19}, {9,19}, {18, 19}, {21,19},
			{1,20},{2,20},{3,20},{4,20},{5,20},{6,20},{7,20},{8,20},{9,20},{10,20},{11,20},{12,20},
			{15, 20}, {16, 20}, {17,20}, {18,20},{19,20},{20,20},{21,20},{22,20},{23,20},
			{24,20},{25,20},{26,20},
			{1,21}, {6,21},{12,21}, {15,21}, {21,21},{26,21},
			{1,22}, {6,22},{12,22}, {15,22}, {21,22},{26,22},
			{1,23},{2,23},{3,23},{6,23},{7,23},{8,23},{9,23},{10,23},{11,23},{12,23},{13,23},
			{14,23},{15, 23}, {16, 23}, {17,23}, {18,23},{19,23},{20,23},{21,23},
			{24,23},{25,23},{26,23},
			{3, 24}, {6,24}, {9, 24}, {18, 24}, {21, 24}, {24, 24},
			{3, 25}, {6,25}, {9, 25}, {18, 25}, {21, 25}, {24, 25},
			{1,26},{2,26},{3,26},{4,26}, {5, 26},{6,26},{9,26},{10,26},{11,26},{12,26},
			{15, 26}, {16, 26}, {17,26}, {18,26},{21, 26}, {22, 26}, {23, 26}, {24, 26}, 
			{25, 26}, {26, 26},
			{1, 27}, {12, 27}, {15, 27}, {26, 27},
			{1, 28}, {12, 28}, {15, 28}, {26, 28},
			{1,29 },{2,29},{3,29},{4,29},{5,29},{6,29},{7,29},{8,29},{9,29},{10,29},{11,29},{12,29},
			{13,29}, {14, 29}, {15,29},
			{16,29},{17,29},{18,29},{19,29},{20,29},{21,29},{22,29},{23,29},{24,29},{25,29},{26,29},

	};

	int [][]rect_pos = {
			{0, 0, xlimit, step/2},
			{0, step/2, step/2 ,step *9},
			{0, step *9 + step /2, step *5+ step/2, step /2},
			{step * 5, step * 10, step /2, step *3 + step /2},
			{0, step * 13, step * 5, step/2},
			{0, 15 * step + step/2, step * 5, step /2},
			{5 *step, 15*step + step/2, step/2, step *4},
			{0, 19*step, 5*step, step/2},
			{0, 19*step + step/2, step/2, 11*step+step/2},
			{step/2, 30*step + step /2, xlimit-step/2, step/2},
			{xlimit-step/2, 19*step + step/2, step/2, 11*step},
			{xlimit-5*step, step *19, step *5, step/2},
			{xlimit - 5*step - step/2, step * 15 +step/2, step/2, step*4},
			{xlimit- 5*step, step * 15 +step/2, 5 *step, step/2},
			{xlimit - 5*step, step * 13, 5*step, step/2},
			{xlimit - 5*step - step/2, step * 9 + step/2, step/2, step * 4},
			{xlimit - 5*step, step *9+step/2, step * 5, step/2},
			{xlimit - step/2 , step /2, step/2, step * 9},
			{xlimit - step *5 - step/2, step *6 + step/2, step *3, step},
			{13 * step + step /2, step/2, step, step * 4},
			{step * 2 + step / 2, step * 2 + step / 2, step *3, step *2},
			{step * 7 + step / 2, step * 2 + step / 2, step *4, step *2},
			{step * 16 + step / 2,step * 2 + step / 2, step *4, step *2},
			{step * 22 + step / 2,step * 2 + step / 2, step *3, step *2},
			{step * 2 + step / 2,step * 6 + step / 2, step *3, step },
			{step * 7 + step/2, step *6 +step/2, step, step *7},
			{step * 8 + step/2, step * 9 + step/2, step * 3, step},
			{step * 10 + step/2, step * 6 + step/2 , 7 * step, step},
			{step * 13 + step/2, step * 7 + step/2 , step, step *3},
			{step * 16 + step/2, step * 9 + step/2, step *3, step},
			{step * 19 + step/2, step * 6 + step/2, step, step *7},
			//center
			{step * 10 + step/2, step *12 + step/2, step *7, step /2},
			{step * 10 + step/2, step * 13, step/2, step * 3},
			{step * 10 + step/2, step * 16, step * 7, step/2},
			{step * 17, step * 13, step/2, step * 3},
			{step *7 +step/2, step*15+step/2, step, step * 4},
			{xlimit - 8*step - step/2, step * 15+ step/2, step, step *4},
			{step * 10 + step/2, step * 18 + step/2, step * 7, step },
			{step * 13 + step/2, step * 19 + step/2, step, step * 3},
			{step /2, step * 24 + step/2, step * 2, step},
			{step *2 + step/2, step * 21 + step/2, step *3, step},
			{step * 4 + step/2,  step *22 + step/2, step, step*3},
			{step * 7 + step/2, step *21 + step/2, step * 4, step},
			{step * 16 + step/2, step *21 + step/2, step * 4, step},
			{xlimit - step * 5 - step/2, step * 21 + step/2, step * 3, step},
			{xlimit - step * 5 - step/2, step *22 + step/2, step, step *3},
			{xlimit - step *2 - step/2, step * 24 + step/2, step *2, step},
			{step *2 + step/2, step *27 + step/2, step *9, step},
			{step * 7 + step/2, step * 24 +step/2, step, step * 3},
			{step * 10 + step/2, step *24 + step/2, step* 7, step},
			{step *13  + step/2, step * 25 + step/2, step, step *3},
			{step *16  + step/2, step * 27 + step/2, step *9 , step},
			{step * 19 + step/2, step * 24 + step/2, step , step *3},
	};


	public Pacman(){
		for(int i = 0; i< pos_legal.length; ++i){
			pos_legal[i][0] = pos_legal[i][0] *step;
			pos_legal[i][1] = pos_legal[i][1] *step;
		}
		init_variable();

		load_images();
		timer = new Timer(40, this);
		timer.start();

		addKeyListener(new KeyListener());
		setFocusable(true);
		setBackground(Color.black);
	}

	private void Play(Graphics g) {
		DrawPills(g);
		DrawScores(g);
		DrawMaze(g);
		DrawPacman(g);
		DrawGhost(g);
	}

	private void Draw(Graphics g) {
		if(welcome_screen){
			show_welcome(g);
		}
		else if(paused){
			deal_wth_paused(g);
		}
		else
			Play(g);
	}

	/*
	 * I call this function when the game starts.
	 */
	public void init_variable(){
		automate = true;
		paused = true;
		paused_type = 1; //start countdown
		paused_time = System.currentTimeMillis() + 3000;

		invulnerable = invulnerable2 = false;
		returned_invulnerable = returned_invulnerable2 = false;
		
		x_pacman2 = 14*step - step/2;
		x_pacman = step * 13 -step/2;
		y_pacman  = y_pacman2 = 23 *step -step/2;
		x_blinky = step * 10 -step/2;
		x_inky = step * 12 - step/2;
		y_blinky = y_inky = step *11 - step/2;
		x_pinky = step * 15 - step/2;
		x_clyde = step * 17 - step/2;
		y_pinky = y_clyde = step * 11-step/2;
		pacman_mouth = pacman2_mouth = 3;
		pacman_lives = pacman2_lives = 3;
		pacman_scores = pacman2_scores = 0;
		view_pacman =lastview_pacman = RIGHT;
		view_pacman2 = lastview_pacman2 =  LEFT;
		view_blinky = view_pinky = RIGHT;
		view_inky = view_clyde = LEFT;
		vision_blinky = vision_pinky = step * 8;
		vision_inky = vision_clyde =0;
		/*You can change the speed to cooperate with your automate
		 **The only allowed value are: 1, 2, 3, 6, 12.
		 * Here speed means how many pixels it advances.
		 */
		speed_pacman = speed_pacman2 = speed_ghost = 6;
		pos_scores = new int[pos_legal.length];

		rec = new Rectangle[pos_legal.length];
		for(int  i = 0; i< pos_legal.length; ++i){
			if(!(pos_legal[i][1] >= step *9 && pos_legal[i][1]  <= 19 *step &&
					(pos_legal[i][0]!=6*step&&pos_legal[i][0]!=21*step)||
					(pos_legal[i][0]==13*step||pos_legal[i][0]==14*step) && pos_legal[i][1]== step*23))
				pos_scores[i] = 1;
			rec[i] = new Rectangle(pos_legal[i][0], pos_legal[i][1], step, step);
		}
	}

	private void load_images() {
		blinky = new ImageIcon(getClass().getResource("/images/blinky.png")).getImage();
		inky = new ImageIcon(getClass().getResource("/images/inky.png")).getImage();
		pinky = new ImageIcon(getClass().getResource("/images/pinky.png")).getImage();
		clyde = new ImageIcon(getClass().getResource("/images/clyde.png")).getImage();
		pacman = new ImageIcon(getClass().getResource("/images/pacman.png")).getImage();
		pacman_left = new ImageIcon(getClass().getResource("/images/pacman_left.png")).getImage();
		pacman_right = new ImageIcon(getClass().getResource("/images/pacman_right.png")).getImage();
		pacman_up = new ImageIcon(getClass().getResource("/images/pacman_up.png")).getImage();
		pacman_down = new ImageIcon(getClass().getResource("/images/pacman_down.png")).getImage();
		pacman_leftclosed = new ImageIcon(getClass().getResource("/images/pacman_leftclosed.png")).getImage();
		pacman_rightclosed = new ImageIcon(getClass().getResource("/images/pacman_rightclosed.png")).getImage();
		pacman_upclosed = new ImageIcon(getClass().getResource("/images/pacman_upclosed.png")).getImage();
		pacman_downclosed = new ImageIcon(getClass().getResource("/images/pacman_downclosed.png")).getImage();
		pacman_closed = new ImageIcon(getClass().getResource("/images/pacman_closed.png")).getImage();

		pacman_leftdie = new ImageIcon(getClass().getResource("/images/pacman_leftdie.png")).getImage();
		pacman_rightdie = new ImageIcon(getClass().getResource("/images/pacman_rightdie.png")).getImage();
		pacman_updie = new ImageIcon(getClass().getResource("/images/pacman_updie.png")).getImage();
		pacman_downdie = new ImageIcon(getClass().getResource("/images/pacman_downdie.png")).getImage();

		pacman_invulnerable = null;

		pacman2 = new ImageIcon(getClass().getResource("/images/pacman2.png")).getImage();
		pacman2_left = new ImageIcon(getClass().getResource("/images/pacman2_left.png")).getImage();
		pacman2_right = new ImageIcon(getClass().getResource("/images/pacman2_right.png")).getImage();
		pacman2_up = new ImageIcon(getClass().getResource("/images/pacman2_up.png")).getImage();
		pacman2_down = new ImageIcon(getClass().getResource("/images/pacman2_down.png")).getImage();
		pacman2_leftclosed = new ImageIcon(getClass().getResource("/images/pacman2_leftclosed.png")).getImage();
		pacman2_rightclosed = new ImageIcon(getClass().getResource("/images/pacman2_rightclosed.png")).getImage();
		pacman2_upclosed = new ImageIcon(getClass().getResource("/images/pacman2_upclosed.png")).getImage();
		pacman2_downclosed = new ImageIcon(getClass().getResource("/images/pacman2_downclosed.png")).getImage();
		pacman2_closed = new ImageIcon(getClass().getResource("/images/pacman2_closed.png")).getImage();
		welcome = new ImageIcon(getClass().getResource("/images/welcome.jpg")).getImage();
		pill = new ImageIcon(getClass().getResource("/images/pill.png")).getImage();
	}

	public void show_welcome(Graphics g){
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		if(is_pill_finished()){
			g.drawString("Player 1 gets "+pacman_scores+" scores," , 250, 300);
			g.drawString("Player 2 gets "+pacman2_scores+" scores.", 250, 350);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			if(pacman_scores > pacman2_scores){
				g.drawString("Player 1 Wins!!!", 250, 450);
			}
			else{
				g.drawString("Player 2 Wins!", 250, 450);
			}
		}
		else if(pacman_lives >0 && pacman2_lives>0){
			/*
			g.drawString("Vincent MESNIER", 300, 100);
			g.drawString("S�bastien TOUSSAINT", 300,  150);
			g.drawString("Quentin TORCK", 300, 200);
			g.drawString("Xueyong QIAN", 300, 250);
			g.drawString("Youcef HAMDANI", 300, 300);
			g.drawString("Thibault SAUSSAC", 300, 350);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("Vous Pr�sentent:", 250, 450);
			 */
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("Press \"enter\" to start", 200, 250);
			g.drawString("Press \"space\" bar to pause", 150, 400);

		}
		else if(pacman_lives == 0){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("Player 1 loses all 3 lives!", 250, 350);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("Player 2 Wins!!!", 250, 450);
		}
		else{
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("Player 2 loses all 3 lives!", 250, 350);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString("Player 1 Wins!!!", 250, 450);
		}
		g.drawImage(welcome, 236, ylimit/2 + 100, this);
	}

	public void show_status(Graphics g, String s, int x, int y){
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString(s, x, y);
	}

	public void show_status(Graphics g, String s){
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString(s, 250, 430);
	}
	/*
	 * case 1 is when game starts, case 2 is when player 1 dies, 
	 * case 3 is when player 2 dies, case 4 is when player presses the "space" key to pause or unpause
	 */
	private void deal_wth_paused(Graphics g){
		switch(paused_type){
		case 1:
			if(paused_time > System.currentTimeMillis()){
				Play(g);
				show_status(g, "Get Ready..." + ((paused_time-System.currentTimeMillis())/1000+1));
			}
			else{
				paused = false;
			}
			break;
		case 2:
			if(paused_time > System.currentTimeMillis()){
				Play(g);
				show_status(g,"Player 1 dies!");
			}
			else{
				x_pacman = 13*step - step/2; 
				y_pacman  = 23 *step -step/2;
				pacman_mouth  = 3;
				view_pacman =lastview_pacman = RIGHT;
				speed_pacman =  6;

				invulnerable = true;
				invulnerable_time = System.currentTimeMillis() + 3000;
				paused = false;
				if(pacman_lives == 0)
					welcome_screen = true;
			}
			break;
		case 3:
			if(paused_time > System.currentTimeMillis()){
				Play(g);
				show_status(g, "Player 2 dies!");
			}
			else{
				x_pacman2 = 14*step - step/2;
				y_pacman2 =  23 *step -step/2;
				pacman2_mouth = 3;
				view_pacman2 = lastview_pacman2 =  LEFT;
				speed_pacman2 = 6;

				invulnerable2 = true;
				invulnerable_time2 = System.currentTimeMillis() + 3000;
				paused = false;
				if(pacman2_lives == 0)
					welcome_screen = true;
			}
			break;
		case 4:
			Play(g);
			show_status(g,"Press SPACE again to unpause!", 120, 430);
			break;
		}
	}

	private boolean is_inRect(int x, int y){
		for(int i = 0 ;i<pos_legal.length;++i){
			if(rec[i].contains(x, y)){
				return true;
			}
		}
		return false;
	}

	/*
	 * Detect if the point(x,y) is valid or not to move to
	 */
	public boolean is_movable(int pos_x, int pos_y){
		if((pos_y == 14*step - step/2)&&(pos_x > -2*step && pos_x <=0 ||
				pos_x < 28*step && pos_x >= 26*step)){
			return true;
		}
		for(int i = 0; i< pos_legal.length; ++i){
			if((pos_x+step/2 == pos_legal[i][0]) && (pos_y+step/2 == pos_legal[i][1])){
				return true;
			}
		}

		if((((pos_y - step/2) % step == 0) &&is_inRect(pos_x + step/2, pos_y+step/2) &&
				is_inRect(pos_x + 3*step/2, pos_y+step/2)) ||
				(((pos_x - step/2)%step ==0) &&is_inRect(pos_x + step/2, pos_y+step/2) &&
						is_inRect(pos_x + step/2, pos_y+3*step/2))) 
			return true;
		return false;

	}

	/*
	 * Because Java doesn't support pointer operation, so I have to return an array which contains
	 * the changed x and y position and also the value 1 or 0 to indicate if the position is movable.
	 */
	public int[] move_nextview(int view, int x, int y, int speed){
		int []arr = {x, y, 0};
		switch(view){
		case DOWN: 
			if(is_movable(x, y+ speed) ){
				arr[1]+= speed;
				arr[2] = 1;
			}
			return arr;
		case UP: 
			if(is_movable(x, y-speed)){
				arr[1]-= speed;
				arr[2] = 1;
			}
			return arr;
		case LEFT:
			if(is_movable(x-speed, y)){
				arr[0] -= speed;
				arr[2] = 1;
			}
			else if(x== -2*step + speed && y==14 *step - step/2){
				arr[0] = 28 *step ;
				arr[2] = 1;
			}
			return arr;
		case RIGHT: 
			if(is_movable(x+speed, y)){
				arr[0] += speed;
				arr[2] = 1;
			}
			else{
				if(x== 28*step - speed && y== 14*step-step/2){
					arr[0] = -2 * step;
					arr[2] = 1;
				}
			}
			return arr;
		}
		return arr;
	}

	public void DrawScores(Graphics g){
		g.setColor(Color.blue);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.fillRect(xlimit, 0, step * 8, getHeight()/2);
		g.setColor(Color.yellow);
		g.drawString("Player 1", xlimit + step, 3*step/2);
		for(int i = 0; i< pacman_lives; ++i){
			g.drawImage(pacman, xlimit + step + i* 2* step, 3*step,this);
		}
		g.drawString("Scores", xlimit + step, ylimit/4);
		g.drawString(Integer.toString(pacman_scores), xlimit + step, ylimit/4 + step *2);

		g.setColor(Color.gray);
		g.fillRect(xlimit, ylimit/2, step * 8, getHeight()/2);
		g.setColor(Color.red);
		g.drawString("Player 2", xlimit + step, ylimit/2 + 3*step/2);
		for(int i = 0; i< pacman2_lives; ++i){
			g.drawImage(pacman2, xlimit + step + i* 2* step, ylimit/2 + 3*step, this);
		}
		g.drawString("Scores", xlimit + step, 3*ylimit/4);
		g.drawString(Integer.toString(pacman2_scores), xlimit + step, 3* ylimit/4 + step *2);
	}

	public boolean is_pill_finished(){
		for(int i = 0; i< pos_legal.length; ++i){
			if(pos_scores[i] == 1)
				return false;
		}
		return true;
	}

	public void DrawPills(Graphics g)
	{
		if(!paused){
			for(int i = 0; i< pos_legal.length; ++i){
				if(pos_legal[i][0] == x_pacman + step/2 && pos_legal[i][1] == y_pacman + step/2){
					if(pos_scores[i] == 1){
						pos_scores[i] = 0;
						pacman_scores += 10;
					}
				}
				if(pos_legal[i][0] == x_pacman2 + step/2 && pos_legal[i][1] == y_pacman2 + step/2){
					if(pos_scores[i] == 1){
						pos_scores[i] = 0;
						pacman2_scores += 10;
					}
				}
			}
		}
		if(is_pill_finished()){
			welcome_screen = true;
		}
		for(int i = 0; i< pos_legal.length; ++i){
			if(pos_scores[i] == 1)
				g.drawImage(pill,pos_legal[i][0] + step/2 - 6,pos_legal[i][1]+ step/2-6, this );
		}
	}

	public void DrawMaze(Graphics g){
		g.setColor(Color.blue);

		for(int i = 0; i< rect_pos.length; ++i){
			g.drawRect(rect_pos[i][0], rect_pos[i][1], rect_pos[i][2], rect_pos[i][3]);
		}
	}

	public int[] move_pacman(int view,int vision, int x, int y){
		int []arr = new int [3];

		for(int i = view, count = 0; count<4;++count, i = (i+1)%4 ){
			if(is_ghost_ahead(i, vision, x, y)){
				arr = move_nextview((i+2)%4, x, y, speed_pacman2);
				if(arr[2] == 1){
					arr[2] = (i+2)%4;
					return arr;
				}
				else{
					arr = move_nextview((i+1)%4, x, y, speed_pacman2);
					if(arr[2] == 1){
						arr[2] = (i+1)%4;
						return arr;
					}
					else{
						arr = move_nextview((i+3)%4, x, y, speed_pacman2);
						arr[2] = (i+3)%4;
						return arr;
					}
				}
			}
		}

		arr = move_nextview((view+1)%4, x, y, speed_pacman2);
		if(arr[2] ==1){
			return get_next_random_pos(x, y, speed_pacman2);
		}
		else{
			arr = move_nextview((view+3)%4, x, y,  speed_pacman2);
			if(arr[2] == 1){
				return get_next_random_pos(x, y,speed_pacman2);
			}
			else{
				arr = move_nextview(view, x, y,  speed_pacman2);
				arr[2] = view;
			}
		}
		return arr;
	}


	/*
	 * For each pacman, I call move_onepacman to get the changed x and y position and assign them to
	 * corresponding x_pacman and y_pacman, finally return the value to indicate if I can move the pacman.
	 */
	private boolean is_drawpacman(int view, int choice){
		int []arr = new int[3];
		switch(choice){
		case 1:
			arr = move_nextview(view, x_pacman, y_pacman, speed_pacman);
			x_pacman = arr[0];
			y_pacman = arr[1];
			if(arr[2] == 1)
				lastview_pacman = view;
			return arr[2] == 1 ? true: false;
		case 2:
			if(!automate){
				arr = move_nextview(view, x_pacman2, y_pacman2, speed_pacman2);
				x_pacman2 = arr[0];
				y_pacman2 = arr[1];
				if(arr[2] == 1)
					lastview_pacman2 = view;
				return arr[2] == 1 ? true: false;
			}
			else{
				arr = move_pacman(view, step*8, x_pacman2, y_pacman2);
				x_pacman2 = arr[0];
				y_pacman2 = arr[1];
				view_pacman2 = arr[2];
				return true;
			}
		}
		return false;
	}

	public boolean is_collision(int x, int y, int choice){
		if(choice == 1 && invulnerable || choice == 2 && invulnerable2)
			return false;
		Rectangle r = new Rectangle(x, y, 2 *step - step/2 , 2 *step -step/2 );
		Rectangle ghost1 = new Rectangle(x_blinky, y_blinky, 2*step - step/2 , 2*step - step/2);
		Rectangle ghost2 = new Rectangle(x_inky, y_inky, 2 *step - step/2 , 2 *step - step/2);
		Rectangle ghost3 = new Rectangle(x_pinky, y_pinky, 2 *step - step/2 , 2 *step - step/2);
		Rectangle ghost4 = new Rectangle(x_clyde, y_clyde, 2 *step - step/2 , 2 *step - step/2);
		if(r.intersects(ghost1))
			return true;
		if(r.intersects(ghost2))
			return true;
		if(r.intersects(ghost3))
			return true;
		if(r.intersects(ghost4))
			return true;
		return false;
	}

	/*
	 * According to current direction "view", return the corresponding Image;
	 * This function is just to ease the animation of pacman.
	 */
	public Image get_pacman(int view, int choice){
		if(choice == 1){
			if(invulnerable){
				if(returned_invulnerable){
					returned_invulnerable = false;
					return pacman_invulnerable;
				}
				else
					returned_invulnerable = true;
			}
			switch(view){
			case LEFT:
				if(paused && paused_type == 2)
					return pacman_leftdie;
				switch(pacman_mouth){
				case 3:
					return pacman_left;
				case 2:
					return pacman_leftclosed;
				case 1:
					return pacman_closed;
				}
			case RIGHT:
				if(paused && paused_type == 2)
					return pacman_rightdie;
				switch(pacman_mouth){
				case 3:
					return pacman_right;
				case 2:
					return pacman_rightclosed;
				case 1:
					return pacman_closed;
				}
			case UP:
				if(paused&& paused_type == 2)
					return pacman_updie;
				switch(pacman_mouth){
				case 3:
					return pacman_up;
				case 2:
					return pacman_upclosed;
				case 1:
					return pacman_closed;
				}
			case DOWN:
				if(paused&& paused_type == 2)
					return pacman_downdie;
				switch(pacman_mouth){
				case 3:
					return pacman_down;
				case 2:
					return pacman_downclosed;
				case 1:
					return pacman_closed;
				}
			}
		}
		else if(choice == 2){
			if(invulnerable2){
				if(returned_invulnerable2){
					returned_invulnerable2 = false;
					return pacman_invulnerable;
				}
				else
					returned_invulnerable2 = true;
			}
			switch(view){
			case LEFT:
				if(paused&& paused_type == 3)
					return pacman_leftdie;
				switch(pacman2_mouth){
				case 3:
					return pacman2_left;
				case 2:
					return pacman2_leftclosed;
				case 1:
					return pacman2_closed;
				}
			case RIGHT:
				if(paused && paused_type == 3)
					return pacman_rightdie;
				switch(pacman2_mouth){
				case 3:
					return pacman2_right;
				case 2:
					return pacman2_rightclosed;
				case 1:
					return pacman2_closed;
				}
			case UP:
				if(paused && paused_type == 3)
					return pacman_updie;
				switch(pacman2_mouth){
				case 3:
					return pacman2_up;
				case 2:
					return pacman2_upclosed;
				case 1:
					return pacman2_closed;
				}
			case DOWN:
				if(paused&& paused_type == 3)
					return pacman_downdie;
				switch(pacman2_mouth){
				case 3:
					return pacman2_down;
				case 2:
					return pacman2_downclosed;
				case 1:
					return pacman2_closed;
				}
			}
		}
		return pacman;
	}

	private void DrawPacman(Graphics g) {
		int view = view_pacman;
		int view2 = view_pacman2;
		if(!paused){
			if(invulnerable && invulnerable_time <= System.currentTimeMillis()){
				invulnerable = false;
			}
			if(invulnerable2 && invulnerable_time2 <= System.currentTimeMillis()){
				invulnerable2 = false;
			}
			if(is_collision(x_pacman, y_pacman, 1)){
				--pacman_lives ;
				paused = true;
				paused_type = 2;
				paused_time = System.currentTimeMillis() + 1000;
				return;
			}

			if(is_collision(x_pacman2, y_pacman2, 2)){
				--pacman2_lives ;
				paused = true;
				paused_type = 3;
				paused_time = System.currentTimeMillis()+1000;
				return;
			}

			if(!is_drawpacman(view_pacman, 1)){
				if(!is_drawpacman(lastview_pacman,1))
					pacman_mouth = 3;
				view = lastview_pacman;
			}
			if(automate){
				is_drawpacman(view_pacman2, 2);
				view2 = view_pacman2;
			}
			else if(!is_drawpacman(view_pacman2, 2)){
				if(!is_drawpacman(lastview_pacman2,2))
					pacman2_mouth = 3;
				view2 = lastview_pacman2;
			}
		}

		if(x_pacman > 26*step){
			g.drawImage(get_pacman(view, 1), x_pacman, y_pacman, 28*step, y_pacman + 2*step, 0,0,28*step-x_pacman,2*step, this);
		}
		else
			g.drawImage(get_pacman(view, 1), x_pacman, y_pacman, this);

		if(x_pacman2 > 26*step){
			g.drawImage(get_pacman(view2, 2), x_pacman2, y_pacman2, 28*step, y_pacman2 + 2*step, 0,0,28*step-x_pacman2,2*step, this);
		}
		else
			g.drawImage(get_pacman(view2, 2), x_pacman2, y_pacman2,  this);

		if(!paused){
			if(pacman_mouth >1)
				--pacman_mouth;
			else
				pacman_mouth = 3;
			if(pacman2_mouth >1)
				--pacman2_mouth;
			else
				pacman2_mouth = 3;
		}
	}

	/*
	 * Get next random position for ghost
	 */
	private int[] get_next_random_pos(int x, int y, int speed){
		int []arr = new int[3];
		int pos;
		while(true){
			pos = rand.nextInt(4);
			arr = move_nextview(pos, x, y,speed);
			if(arr[2] == 1){
				arr[2] = pos;
				return arr;
			}
		}
	}

	public boolean is_pacman_ahead(int view, int vision, int x, int y){
		Rectangle rec1 = new Rectangle(x_pacman, y_pacman, step*2 - step/2, step*2 - step/2);
		Rectangle rec2 = new Rectangle(x_pacman2, y_pacman2, step*2 - step/2, step*2 - step/2);
		switch(view){
		case 0:
			for(int i =speed_ghost ; i< vision; i+= speed_ghost){
				if(!is_movable(x-i, y))
					return false;
				else if(!invulnerable &&rec1.contains(x-i, y)||!invulnerable2&& rec2.contains(x-i, y))
					return true;
			}
			break;
		case 1:
			for(int i =speed_ghost ; i< vision; i+= speed_ghost){
				if(!is_movable(x, y+i))
					return false;
				else if(!invulnerable &&rec1.contains(x, y+i)|| !invulnerable2 &&rec2.contains(x, y+i))
					return true;
			}
			break;
		case 2:
			for(int i =speed_ghost ; i< vision; i+= speed_ghost){
				if(!is_movable(x+i, y))
					return false;
				else if(!invulnerable &&rec1.contains(x+i, y)|| !invulnerable2 &&rec2.contains(x+i, y))
					return true;
			}
			break;
		case 3:
			for(int i =speed_ghost ; i< vision; i+= speed_ghost){
				if(!is_movable(x, y-i))
					return false;
				else if(!invulnerable &&rec1.contains(x, y-i)|| !invulnerable2 &&rec2.contains(x, y-i))
					return true;
			}
			break;
		}
		return false;
	}

	public boolean is_ghost_ahead(int view, int vision, int x, int y){
		switch(view){
		case 0:
			for(int i =speed_pacman2 ; i< vision; i+= speed_pacman2){
				if(!is_movable(x-i, y))
					return false;
				else if(is_collision(x_pacman2-i, y_pacman2, 2))
					return true;
			}
			break;
		case 1:
			for(int i =speed_pacman2 ; i< vision; i+= speed_pacman2){
				if(!is_movable(x, y+i))
					return false;
				else if(is_collision(x_pacman2, y_pacman2+i, 2))
					return true;
			}
			break;
		case 2:
			for(int i =speed_pacman2 ; i< vision; i+= speed_pacman2){
				if(!is_movable(x+i, y))
					return false;
				else if( is_collision(x_pacman2+i, y_pacman2, 2))
					return true;
			}
			break;
		case 3:
			for(int i =speed_pacman2 ; i< vision; i+= speed_pacman2){
				if(!is_movable(x, y-i))
					return false;
				else if( is_collision(x_pacman2, y_pacman2-i, 2))
					return true;
			}
			break;
		}
		return false;
	}
	/*
	 * return the changed x and y position as a array
	 */
	private int[] MoveGhost(int view,int vision, int x, int y){
		int []arr = new int [3];

		for(int i = view, count = 0; count<4;++count, i = (i+1)%4 ){
			if(is_pacman_ahead(i, vision, x, y)){
				arr = move_nextview(i, x, y, speed_ghost);
				arr[2] = i;
				return arr;
			}
		}
		arr = move_nextview((view+1)%4, x, y, speed_ghost);
		if(arr[2] ==1){
			return get_next_random_pos(x, y, speed_ghost);
		}
		else{
			arr = move_nextview((view+3)%4, x, y, speed_ghost);
			if(arr[2] == 1){
				return get_next_random_pos(x, y, speed_ghost);
			}
			else{
				arr = move_nextview(view, x, y, speed_ghost);
				arr[2] = view;
			}
		}
		return arr;
	}


	private int[] MoveGhost(int view,int vision, int x, int y, boolean inverse){
		int []arr = new int [3];
		if(inverse){
			for(int i = view, count = 0; count<4;++count, i = (i+1)%4 ){
				if(is_pacman_ahead(i, vision, x, y)){
					arr = move_nextview((i+2)%4, x, y, speed_ghost);
					if(arr[2]==1)
						arr[2] = (i+2)%4;
					else{
						arr = move_nextview((i+1)%4, x, y, speed_ghost);
						if(arr[2] ==1){
							arr[2] =( i+1)%4;
						}
						else{
							arr = move_nextview((i+3)%4, x, y, speed_ghost);
							arr[2] = (i+3)%4;
						}
					}
					return arr;
				}
			}
		}
		arr = move_nextview((view+1)%4, x, y, speed_ghost);
		if(arr[2] ==1){
			return get_next_random_pos(x, y, speed_ghost);
		}
		else{
			arr = move_nextview((view+3)%4, x, y, speed_ghost);
			if(arr[2] == 1){
				return get_next_random_pos(x, y, speed_ghost);
			}
			else{
				arr = move_nextview(view, x, y, speed_ghost);
				arr[2] = view;
			}
		}
		return arr;
	}

	private void DrawGhost(Graphics g){
		if(!paused){
			int []arr = new int[3];
			arr = MoveGhost(view_blinky,vision_blinky, x_blinky, y_blinky );
			x_blinky = arr[0];
			y_blinky = arr[1];
			view_blinky = arr[2];
			arr = MoveGhost(view_pinky,vision_pinky,x_pinky, y_pinky, true );
			x_pinky = arr[0];
			y_pinky = arr[1];
			view_pinky = arr[2];
			arr = MoveGhost( view_inky,vision_inky, x_inky, y_inky );
			x_inky = arr[0];
			y_inky = arr[1];
			view_inky = arr[2];
			arr = MoveGhost(view_clyde,vision_clyde,  x_clyde, y_clyde );
			x_clyde = arr[0];
			y_clyde = arr[1];
			view_clyde = arr[2];
		}
		if(x_blinky > 26*step)
			g.drawImage(blinky, x_blinky, y_blinky, 28*step, y_blinky + 2*step, 0,0,28*step-x_blinky,2*step, this);
		else
			g.drawImage(blinky, x_blinky, y_blinky, this);
		if(x_pinky > 26*step)
			g.drawImage(pinky, x_pinky, y_pinky, 28*step, y_pinky + 2*step, 0,0,28*step-x_pinky,2*step, this);
		else
			g.drawImage(pinky, x_pinky, y_pinky, this);
		if(x_inky > 26*step)
			g.drawImage(inky, x_inky, y_inky, 28*step, y_inky + 2*step, 0,0,28*step-x_inky,2*step, this);
		else
			g.drawImage(inky, x_inky, y_inky, this);

		if(x_clyde > 26*step)
			g.drawImage(clyde, x_clyde, y_clyde, 28*step, y_clyde + 2*step, 0,0,28*step-x_clyde,2*step, this);
		else
			g.drawImage(clyde, x_clyde, y_clyde, this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw(g);
	}

	/*
	 * I detect the key and only change the direction "view_pacman", and leave the other functions
	 * to do the corresponding move or draw. 
	 */
	class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if((welcome_screen && key ==KeyEvent.VK_ENTER)||
					paused&&key==KeyEvent.VK_SPACE || !paused){
				switch(key){
				case KeyEvent.VK_DOWN: 
					view_pacman = DOWN;
					break;
				case KeyEvent.VK_UP: 
					view_pacman = UP;
					break;
				case KeyEvent.VK_LEFT:
					view_pacman = LEFT;
					break;
				case KeyEvent.VK_RIGHT: 
					view_pacman = RIGHT;
					break;
				case KeyEvent.VK_S: 
					if(!automate)
						view_pacman2 = DOWN;
					break;
				case KeyEvent.VK_W: 
					if(!automate)
						view_pacman2 = UP;
					break;
				case KeyEvent.VK_A:
					if(!automate)
						view_pacman2 = LEFT;
					break;
				case KeyEvent.VK_D: 
					if(!automate)
						view_pacman2 = RIGHT;
					break;
				case KeyEvent.VK_ENTER:
					welcome_screen = false;
					init_variable();
					break;
				case KeyEvent.VK_SPACE:
					if(welcome_screen == false && ((paused&& paused_type == 4)||!paused)){
						if(paused){
							paused = false;
						}
						else{
							paused = true;
							paused_type = 4;
						}
					}
				default:
					break;
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("repaint");
		repaint();
	}
}
