import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.*;
//import java.applet.Applet;
//import java.applet.AudioClip;
//import java.net.MalformedURLException;
//import java.net.URL;
import javax.imageio.ImageIO;
/*import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;*/

public class Graphics implements WindowListener, Runnable, KeyListener, MouseListener {
public static final String TITLE = "15 Puzzle";
public static final Dimension SIZE = new Dimension(700,730);
public static final long startTime = System.currentTimeMillis();

public Frame frame;

private boolean isRunning, isDone;
private Image imgBuffer;
private Image[] images;
private PuzzleBoard gameBoard;
private long curTime, timeOffset, totaltime, avgtime,timeStop;
private int count;
private String curFrame, Time, folder;
private AudioStream menumusic;

private Button start, options,melon, regular,
backToMenu, quit, pause, resume, back, setDefault,
getNewColors,joshie,again,nah,calove, McCaffrey, Jae, cookie;

private Button[] colors;
//private AudioInputStream BACK;
//private Clip clip;

public Graphics(){
	frame = new Frame();
	frame.addWindowListener(this);
	frame.addKeyListener(this);
	frame.addMouseListener(this);
	frame.setBackground(Color.LIGHT_GRAY);
	frame.setResizable(false);
	
	images = new Image[16];
	timeOffset = 0;
	totaltime = 0;
	avgtime = 0;
	count = 0;
	timeStop = 0;
	curFrame = "Menu";
	Time = getTime();
	folder = "Regular";
	
	try {
		menumusic = getAudioClip("C:\\Users\\jonat\\Music\\menumusic.wav");
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	AudioPlayer.player.start(menumusic);
	
	//Main Menu Buttons
	start = new Button(220,330,250,100,Color.ORANGE,true,"Start","Game");
	options = new Button(220,455,250,100,Color.ORANGE,true,"Option","Options");
	quit = new Button(220,580,250,100,Color.ORANGE,true,"Quit","");
	
	//Options Button (Image Buttons)
	melon = new Button(20,125,150,60,Color.ORANGE,true,"Melon :3","");
	joshie = new Button(340,125,150,60,Color.ORANGE,true,"Joshie","");
	regular = new Button(180,125,150,60,Color.ORANGE,true,"Regular","");
	calove = new Button(20,195,150,60,Color.ORANGE,true,"Calove","");
	McCaffrey = new Button(180,195,150,60,Color.ORANGE,true,"McCaffrey","");
	Jae = new Button(340, 195, 150, 60, Color.ORANGE, true, "The Jae Bae", "");
	cookie = new Button(500, 125, 150, 60, Color.ORANGE, true, "COOKIES", "");
	
	//Options Buttons
	backToMenu = new Button(430,610,250,100,Color.ORANGE,true,"Menu","Menu");
	setDefault = new Button(60,500,200,75,Color.ORANGE,true,"Default","");
	getNewColors = new Button(450,500,200,75,Color.ORANGE,true,"New Colors","");
	
	//Game Buttons
	pause = new Button(510,650,170,60,Color.ORANGE,true,"Pause","Pause");
	
	//Pause Menu and Completion Buttons
	resume = new Button(380,390,150,50,Color.GREEN,true,"Resume","Game");
	back = new Button(160,390,150,50,Color.RED,true,"Menu","Menu");
	again = new Button(380,390,150,50,Color.GREEN,true,"Sure!","Game");
	nah = new Button(160,390,150,50,Color.RED,true,"Nah fam","Menu");
	
	//Color Buttons
	colors = new Button[12];
	getColorButtons();
	
	
	//*********MUSIC (TO BE IMPLEMENTED)**********
	/*BACK = getAudioClip();
	clip = AudioSystem.getClip();
	try {
		clip.open(BACK);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	clip.loop(2);
	clip.start();*/
	
	
	
	gameBoard = new PuzzleBoard();
	gameBoard.shuffle();
	
	frame.setSize(SIZE);
	frame.setTitle(TITLE);
	
	isRunning = true;
	isDone = false;
	frame.setVisible(true);
	imgBuffer = frame.createImage(SIZE.width, SIZE.height);
	
	
	getImages("Regular");
	
	
}

public void getColorButtons(){
	int x = 0;
	for(int i = 0; i < colors.length; i++){
		int y = 0;
		int r = (int)(Math.random() * 256);
		int g = (int)(Math.random() * 256);
		int b = (int)(Math.random() * 256);
		if(i%2 == 0) y = 335;
		else y = 415;
		if(i%2 == 0) x+=90;
		colors[i] = new Button(x,y,50,50,new Color(r,g,b));
	}
}


public void getImages(String folder){
	
	for(int i = 1;i <=15;i++){
		String temp = System.getProperty("user.dir") + "\\" + "ImageFolder"+ "\\" + folder + "\\" + i + ".jpg";
		try {
			images[i] = ImageIO.read(new File(temp));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

//**********MUSIC (TO BE IMPLEMENTED)***********

public AudioStream getAudioClip(String location) throws Exception {
    InputStream in = new FileInputStream(location);
    AudioStream audioStream = new AudioStream(in);
    return audioStream;
    
    
}




//*****************DRAW MENU********************

public void drawMenu(Graphics2D g){
	g.clearRect(0, 0, SIZE.width, SIZE.height);
	drawBackground(g);
	g.setColor(Color.RED);
	Font font = new Font("Comic Sans MS", Font.PLAIN, 92);
	g.setFont(font);
	g.drawString("15 Puzzle!!!", 120, 190);
	start.drawButton(g);
	quit.drawButton(g);
	options.drawButton(g);
	font = new Font("Comic Sans MS", Font.PLAIN,12);
	g.setFont(font);
	g.drawString("Version 2.1", 625, 720);

}

public Color drawBackground(Graphics2D g){
	g.setColor(Color.LIGHT_GRAY);
	g.fillRect(0, 0, SIZE.width, SIZE.height);
	for(int i = 0; i<colors.length; i++){
		if(colors[i].getBorder()){
			g.setColor(colors[i].getColor());
			g.fillRect(0, 0, SIZE.width, SIZE.height);
		}
	}
	return g.getColor();
}

//*****************DRAW PAUSE******************

public void drawPause(Graphics2D g){
	drawBackground(g);
	g.setColor(Color.GRAY);
	g.fillRect(145, 250, 400, 200);
	g.setColor(Color.BLACK);
	g.drawRect(145, 250, 400, 200);
	pause.drawButton(g);
	resume.drawButton(g);
	back.drawButton(g);
	g.setColor(Color.BLACK);
	Font font = new Font("Comic Sans MS", Font.PLAIN, 68);
	g.setFont(font);
	g.drawString("Paused", 240, 350);
	font = new Font("Comic Sans MS", Font.PLAIN, 12);
	g.setFont(font);
	g.drawString("Time: " + Time, 20, 680);
	g.drawString("Average Time: " + formatTime(avgtime), 20, 700);
	
}

//*****************DRAW OPTIONS*****************
public void drawOptions(Graphics2D g){
	g.clearRect(0, 0, SIZE.width, SIZE.height);
	drawBackground(g);
	melon.drawButton(g);
	regular.drawButton(g);
	joshie.drawButton(g);
	Jae.drawButton(g);
	cookie.drawButton(g);
	backToMenu.drawButton(g);
	setDefault.drawButton(g);
	getNewColors.drawButton(g);
	calove.drawButton(g);
	McCaffrey.drawButton(g);
	g.setColor(Color.BLACK);
	Font font = new Font("Comic Sans MS", Font.PLAIN, 42);
	g.setFont(font);
	g.drawString("Current Image: " + folder, 30, 100);
	g.drawString("Background colors:", 30, 310);
	for(int i = 0; i < colors.length; i++){
		colors[i].drawButton(g);
	}
}

//*****************DRAW CLOSE********************


public void drawClose(Graphics2D g){
	g.setColor(Color.GRAY);
	g.fillRect(145, 250, 400, 200);
	g.setColor(Color.BLACK);
	g.drawRect(145, 250, 400, 200);
	again.drawButton(g);
	nah.drawButton(g);
	g.setColor(Color.BLACK);
	Font font = new Font("Comic Sans MS", Font.PLAIN, 20);
	g.setFont(font);
	g.drawString("You won! You're time is " + Time, 170, 300);
	g.drawString("Wanna play again?", 270, 350);
}






//*****************DRAW GAME*********************

public void drawGame(Graphics2D g){
	int countrow = 0,countcol = 0,temp;
	g.clearRect(0, 0, SIZE.width, SIZE.height);
	drawBackground(g);
	for(int y = 40; y <=510;y+=150){
		for(int x = 50; x <=500;x+=150){
			temp = gameBoard.getTileValue(countrow, countcol);
			g.drawImage(images[temp], x, y, 150, 150,null);
			countcol++;
			countcol%=4;
		}
		countrow++;
	}
	pause.drawButton(g);
	g.setColor(Color.BLACK);
	g.drawRect(50, 40, 600, 600);
	Font font = new Font("Comic Sans MS", Font.PLAIN, 12);
	g.setFont(font);
	Time = getTime();
	g.drawString("Time: " + Time, 20, 680);
	g.drawString("Average Time: " + formatTime(avgtime), 20, 700);
}

//*****************DRAW TO FRAME*******************

public void drawToFrame(Graphics2D g){
	g = (Graphics2D)frame.getGraphics();
	g.drawImage(imgBuffer, 0, 0, SIZE.width, SIZE.height, 0, 0, SIZE.width, SIZE.height, null);
}

//*****************DRAW METHOD*********************

public void draw(){
	Graphics2D g = (Graphics2D)imgBuffer.getGraphics();
	if(curFrame.equals("Options")) drawOptions(g);
	if(curFrame.equals("Menu")) drawMenu(g);
	if(curFrame.equals("Game")) drawGame(g);
	if(curFrame.equals("Pause")) drawPause(g);
	if(curFrame.equals("End")) drawClose(g);
	drawToFrame(g);
	if(gameBoard.isSolved() && !curFrame.equals("End")) {
		drawGame(g);
		curFrame = "End";
		drawToFrame(g);
		//closeProgram();
	}
	g.dispose();
}

//******************CLOSE PROGRAM*******************

public void closeProgram(int n){
	if(n <= 0){
		//to close
		//frame.setVisible(false);
		//isRunning = false;
		//frame.dispose();
		curFrame = "Menu";
		totaltime = 0;
		count = 0;
		avgtime = 0;
	}
	else{
		totaltime += (int)(curTime);
		timeOffset = (int) (System.currentTimeMillis() - startTime);
		count++;
		avgtime = totaltime/count;
		curFrame = "Game";
	}
	gameBoard.shuffle();
}


//*****************Format Time**********************


public String formatTime(long time){
	String str = String.format("%02d",(time/3600000)) + ":" //hours
			+ String.format("%02d",(time/60000)%60) + ":" //minutes
			+ String.format("%02d",((time/1000)%60))+ ":" //seconds
			+ String.format("%03d",(time%1000));//milliseconds
	return str;
}



public String getTime(){
	curTime = (System.currentTimeMillis() - startTime);
	curTime-=timeOffset;//adjust time for restarts
	return formatTime(curTime);
	
}


@Override
public void run() {
	// 
	while(isRunning){
		draw();
		
		try{Thread.sleep(3);}
		catch(InterruptedException ie){ie.printStackTrace();}
	}
	isDone = true;
}



@Override
public void windowClosed(WindowEvent e) {
	// 
	while(true){
		if(isDone){
			System.exit(0);
		}
		try{Thread.sleep(20); }
		catch(InterruptedException ie){ie.printStackTrace();}
	}
}

@Override
public void windowClosing(WindowEvent e) {
	// 
	frame.setVisible(false);
	isRunning = false;
	frame.dispose();
}




@Override
public void windowDeactivated(WindowEvent e) {
	// 
	if(curFrame.equals("Game")){
		timeStop = (int) (System.currentTimeMillis());
		curFrame = "Pause";
	}

}

@Override
public void windowDeiconified(WindowEvent e) {
	// 
	if(curFrame.equals("Game")){
		timeStop = (int) (System.currentTimeMillis());
		curFrame = "Pause";
	}
}

@Override
public void windowIconified(WindowEvent e) {
	// 
	
}

@Override
public void windowOpened(WindowEvent e) {
	// 
	
}

@Override
public void windowActivated(WindowEvent e) {
	// 
	
}

@Override
public void keyPressed(KeyEvent e) {
	// 
}




@Override
public void keyReleased(KeyEvent e) {
	// 
	if(curFrame.equals("Game")){
		int[] location1 = gameBoard.findBlank();
		if(e.getKeyCode() == KeyEvent.VK_UP){
			int ydown = location1[0] + 1;
			if(ydown < gameBoard.getYSize()) gameBoard.swap(location1, new int[] {ydown,location1[1]});
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			int xright = location1[1] + 1;
			if(xright < gameBoard.getXSize()) gameBoard.swap(location1, new int[] {location1[0],xright});
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			int yup = location1[0] - 1;
			if(yup >= 0) gameBoard.swap(location1, new int[] {yup,location1[1]});
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			int xleft = location1[1] - 1;
			if(xleft >= 0) gameBoard.swap(location1, new int[] {location1[0],xleft});
		}
	}
}





@Override
public void keyTyped(KeyEvent e) {
	// 
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// 
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// 
	
}



@Override
public void mouseClicked(MouseEvent e) {
	// 
	for(int i = 0; i < colors.length;i++){
		if(colors[i].mouseInButton(e.getPoint()) && curFrame.equals("Options")){
			for(int n = 0; n < colors.length;n++){
				if(n == i) colors[n].setBorder(true);
				else colors[n].setBorder(false);
			}
		}
	}
	if(start.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		curFrame = start.getAction();
		timeOffset = (int) (System.currentTimeMillis() - startTime);
	}
	if(options.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		curFrame = options.getAction();
	}
	if(getNewColors.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		getColorButtons();
	}
	if(setDefault.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		for(int i = 0; i < colors.length;i++){
			colors[i].setBorder(false);
		}
	}
	if(regular.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("Regular");
		folder = "Regular";
	}
	if(melon.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("Melon");
		folder = "Melon";
	}
	if(calove.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("calove");
		folder = "Calove";
	}
	if(McCaffrey.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("McCaffrey");
		folder = "McCaffrey";
	}
	if(joshie.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("Joshie");
		folder = "Joshie";
	}
	if(Jae.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("Jae");
		folder = "Jae";
	}
	if(cookie.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		folder = "Loading...";
		getImages("cookie");
		folder = "Cookie";
	}
	if(quit.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		frame.setVisible(false);
		isRunning = false;
		frame.dispose();
	}
	if(backToMenu.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		curFrame = backToMenu.getAction();
	}
	if(resume.mouseInButton(e.getPoint()) && curFrame.equals("Pause")){
		timeOffset += (int)(System.currentTimeMillis() - timeStop);
		curFrame = resume.getAction();
	}
	if(back.mouseInButton(e.getPoint()) && curFrame.equals("Pause")){
		curFrame = back.getAction();
		gameBoard.shuffle();
	}
	if(again.mouseInButton(e.getPoint()) && curFrame.equals("End")){
		closeProgram(1);
	}
	if(nah.mouseInButton(e.getPoint()) && curFrame.equals("End")){
		closeProgram(0);
	}
	if(pause.mouseInButton(e.getPoint()) && curFrame.equals("Pause")){
		curFrame = "Game";
		timeOffset += (int)(System.currentTimeMillis() - timeStop);
	}
	else if(pause.mouseInButton(e.getPoint()) && curFrame.equals("Game")){
		curFrame = pause.getAction();
		timeStop = (int) (System.currentTimeMillis());
	}
}



@Override
public void mousePressed(MouseEvent e) {
	// 
	if(start.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		start.setColor(Color.YELLOW);
	}
	if(options.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		options.setColor(Color.YELLOW);
	}
	if(quit.mouseInButton(e.getPoint()) && curFrame.equals("Menu")){
		quit.setColor(Color.YELLOW);
	}
	if(backToMenu.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		backToMenu.setColor(Color.YELLOW);
	}
	if(regular.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		regular.setColor(Color.YELLOW);
	}
	if(Jae.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		Jae.setColor(Color.YELLOW);
	}
	if(cookie.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		cookie.setColor(Color.YELLOW);
	}
	if(melon.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		melon.setColor(Color.YELLOW);
	}
	if(joshie.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		joshie.setColor(Color.YELLOW);
	}
	if(calove.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		calove.setColor(Color.YELLOW);
	}
	if(McCaffrey.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		McCaffrey.setColor(Color.YELLOW);
	}
	if(setDefault.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		setDefault.setColor(Color.YELLOW);
	}
	if(getNewColors.mouseInButton(e.getPoint()) && curFrame.equals("Options")){
		getNewColors.setColor(Color.YELLOW);
	}
	if(pause.mouseInButton(e.getPoint()) && (curFrame.equals("Game") || curFrame.equals("Pause"))){
		pause.setColor(Color.YELLOW);
	}
	if(resume.mouseInButton(e.getPoint()) && curFrame.equals("Pause")){
		resume.setColor(Color.GREEN.darker());
	}
	if(back.mouseInButton(e.getPoint()) && curFrame.equals("Pause")){
		back.setColor(Color.RED.darker());
	}
	if(again.mouseInButton(e.getPoint()) && curFrame.equals("End")){
		again.setColor(Color.GREEN.darker());
	}
	if(nah.mouseInButton(e.getPoint()) && curFrame.equals("End")){
		nah.setColor(Color.RED.darker());
	}

}



@Override
public void mouseReleased(MouseEvent e) {
	// 
	if(curFrame.equals("Menu")){
		start.setColor(Color.ORANGE);
		options.setColor(Color.ORANGE);
		quit.setColor(Color.ORANGE);
	}
	if(curFrame.equals("Options")){
		backToMenu.setColor(Color.ORANGE);
		regular.setColor(Color.ORANGE);
		melon.setColor(Color.ORANGE);
		joshie.setColor(Color.ORANGE);
		setDefault.setColor(Color.ORANGE);
		getNewColors.setColor(Color.ORANGE);
		calove.setColor(Color.ORANGE);
		McCaffrey.setColor(Color.ORANGE);
		Jae.setColor(Color.ORANGE);
		cookie.setColor(Color.ORANGE);
	}
	if(curFrame.equals("Game") || curFrame.equals("Pause")){
		pause.setColor(Color.ORANGE);
	}
	if(curFrame.equals("Pause")){
		resume.setColor(Color.GREEN);
		back.setColor(Color.RED);
	}
	if(curFrame.equals("End")){
		again.setColor(Color.GREEN);
		nah.setColor(Color.RED);
	}
}

}
