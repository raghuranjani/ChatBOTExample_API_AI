package ai.api.examples;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ai.api.model.AIResponse;
import core.ChatBot;

public class ChatBotWindow extends JFrame implements KeyListener{

//	JPanel p = new JPanel();
	String fileName = "C:\\Ranjani\\captainBOT.PNG";
	JPanelWithBackground p = null;
	JTextArea dialog = new JTextArea(20,30);
	JTextArea input = new JTextArea(3,50);
	JScrollPane scroll = new JScrollPane(dialog,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


	public static void main(String[] args) {
		new ChatBotWindow();
	}

	public ChatBotWindow(){
		super("Captain Bot to your service");
		try {
			p = new JPanelWithBackground(fileName);
			setIconImage(new ImageIcon("C:\\Ranjani\\captainBOTIcon.PNG").getImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setSize(600, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		dialog.setEditable(false);
		input.addKeyListener(this);
		
		p.add(scroll);
		p.add(input);
		p.setBackground(new Color(25,200,0));
		add(p);
		setVisible(true);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			input.setEditable(false);
			String quote = input.getText();
			input.setText("");
			addText("\nYou::\t"+quote);
			quote.trim();
			CallAPIGoogle callAPIGoogle = new CallAPIGoogle();
			AIResponse response = callAPIGoogle.callGoogle(quote);
			
			if (response.getStatus().getCode() == 200) {
				String speech = response.getResult().getFulfillment().getSpeech();
				System.out.println("speech::"+speech);
				addText("\nCaptainBot::\t"+speech);
				String searchEngine = "";
				String searchParam = "";
				if(null != response.getResult().getParameters().get("search-engine")){
					searchEngine = response.getResult().getParameters().get("search-engine").toString();
					searchEngine = searchEngine.replace("\"", "");
				}else if(null != response.getResult().getParameters().get("service")){
					searchEngine = response.getResult().getParameters().get("service").toString();
					searchEngine = searchEngine.replace("\"", "");
				}

				if(null != response.getResult().getParameters().get("q")){
					searchParam = response.getResult().getParameters().get("q").toString();
					searchParam = searchParam.replace("\"", "");
				}
				System.out.println("searchEngine::"+searchEngine);
				System.out.println("searchParam::"+searchParam);
				
//				TextClientApplication.clickscreen(searchEngine,searchParam);
			} else {
				System.err.println(response.getStatus().getErrorDetails());
			}
			
		}

	}
	
	public void addText(String text){
		dialog.setText(dialog.getText()+text);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			input.setEditable(true);
		}

	}
}
