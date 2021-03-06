import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.event.*;

public class BlackJack extends JPanel{
    private Deck deck;
    private ArrayList<Player> playerList;
    private ArrayList<Integer> playerScores;
    private Player dealer;
    private boolean dealerDone;
    private boolean hasHit;
    private int selection;
    private boolean blackJack,push,bust,win,lose,cont = false;
    JButton hit = new JButton("HIT");
    JButton stay = new JButton("STAY");
    JButton doubleDown = new JButton("DOUBLE DOWN");
    JButton split = new JButton("SPLIT");
    private BufferedImage imageBackground;
    private ArrayList<BufferedImage> deckBack;
    private BufferedImage imageBack;
    private BufferedImage removeBack;
    private BufferedImage cardBack;
    private BufferedImage imageEnd;
    public BlackJack(){
        deck = new Deck();
        playerList = new ArrayList<Player>();

        dealer = new Player("Dealer",true,false);
        selection = 0;
        add(hit);
        hit.setBounds(1700, 100, 100, 50);
        add(stay);
        stay.setBounds(1700, 150, 100, 50);     

        try{
            cardBack = ImageIO.read(new File("BACK.jpg"));
        }
        catch(Exception e){}

        deckBack = new ArrayList<BufferedImage>();
        try{
            for(int i = 0; i < 52; i++) {
                imageBack = ImageIO.read(new File("BACK.jpg"));
                deckBack.add(imageBack);
            }
        }
        catch(Exception e) {}

        class HitButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                setSelection1();
            }
        }

        class StayButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                setSelection2();
            }
        }

        class DoubleDownButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                setSelection3();
            }
        }

        class SplitButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                setSelection4();
                remove(split);
            }
        }

        ActionListener hitListener = new HitButtonListener();
        hit.addActionListener(hitListener);
        ActionListener stayListener = new StayButtonListener();
        stay.addActionListener(stayListener);
        ActionListener doubleDownListener = new DoubleDownButtonListener();
        doubleDown.addActionListener(doubleDownListener);  
        ActionListener splitListener = new SplitButtonListener();
        split.addActionListener(splitListener);

    }

    public void setSelection1(){
        selection = 1;
    }

    public void setSelection2(){
        selection = 2;
    }

    public void setSelection3(){
        selection = 3;
    }

    public void setSelection4(){
        selection = 4;
    }

    public void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        try{
            imageBackground = ImageIO.read(new File("Background-Green-1600x1200.jpg"));
        } catch(Exception e) {}                
        g2.drawImage(imageBackground, 0, 0, null);

        for(int i = 0; i < playerList.size(); i ++){
            int xPos = 150;
            int splitX = 650;
            Player temp = playerList.get(i);
            ArrayList<Card> playerHand = temp.getHand();

            for(int j = 0; j < playerHand.size(); j++){
                Card tempCard = playerHand.get(j);
                BufferedImage image = tempCard.getImage();
                if(temp.isDealer() == true && j == 0 && dealerDone == false){
                    try{
                        image = ImageIO.read(new File("BACK.jpg"));
                    }
                    catch(Exception e){}
                }
                if(temp.isSplitPlayer() == true && j == 0){
                    xPos = splitX +30;
                }        
                else{
                    xPos =  xPos + 30;
                }
                int yPos = temp.getYPos();
                g2.drawImage(image,xPos,yPos,null);

            }
        }

        if(playerScores.size() > 0 && dealerDone == true){
            for(int i = 0; i < playerList.size()-1; i ++){
                Player temp = playerList.get(i);
                if(playerScores.get(i)==1){
                    try{
                        imageEnd = ImageIO.read(new File("blackjack.jpg"));
                    } catch(Exception e){

                    }
                    if(temp.isSplitPlayer() == true){
                        g2.drawImage(imageEnd, 650, 700, null);
                    }
                    else{
                        g2.drawImage(imageEnd, 150, 700, null);
                    }
                }
                if(playerScores.get(i) == 2){
                    try{
                        imageEnd = ImageIO.read(new File("bust.jpg"));
                    } catch(Exception e){

                    }
                    if(temp.isSplitPlayer() == true){
                        g2.drawImage(imageEnd, 650, 700, null);
                    }
                    else{
                        g2.drawImage(imageEnd, 150, 700, null);
                    }

                }
                if(playerScores.get(i) == 3){
                    try{
                        imageEnd = ImageIO.read(new File("push.jpg"));
                    } catch(Exception e){

                    }
                    if(temp.isSplitPlayer() == true){
                        g2.drawImage(imageEnd, 650, 700, null);
                    }
                    else{
                        g2.drawImage(imageEnd, 150, 700, null);
                    }
                }
                if(playerScores.get(i) == 4){
                    try{
                        imageEnd = ImageIO.read(new File("win.jpg"));
                    } catch(Exception e){

                    }
                    if(temp.isSplitPlayer() == true){
                        g2.drawImage(imageEnd, 650, 700, null);
                    }
                    else{
                        g2.drawImage(imageEnd, 150, 700, null);
                    }
                }
                if(playerScores.get(i) == 5){
                    try{
                        imageEnd = ImageIO.read(new File("lose.jpg"));
                    } catch(Exception e){

                    }
                    if(temp.isSplitPlayer() == true){
                        g2.drawImage(imageEnd, 650, 700, null);
                    }
                    else{
                        g2.drawImage(imageEnd, 150, 700, null);
                    }
                }

            }
        }
        for(int i = 0; i < deck.getDeckSize(); i++) {
            g2.drawImage(cardBack, i + 1000, 50, null);
        }
    }

    public void initializePlayers(int playerCount){

        for(int i = 0; i < playerCount ; i++){
            String name = "Player "+(i+1);
            Player temp = new Player(name, false,false);
            playerList.add(temp);
            if(i == playerCount-1){
                playerList.add(dealer);
            }
        } 
    }

    public void clearHand(){
        for(int i = 0; i < playerList.size(); i++)
        {
            Player temp = playerList.get(i);
            temp.eraseHand();
        }
    }

    public void initialDeal(){
        blackJack= false;
        win  = false;
        lose = false;
        push = false;
        bust = false;
        cont = false;
        playerScores = new ArrayList<Integer>();
        if(playerList.size() == 3){
            playerList.remove(1);
        }

        for(int i = 0; i < playerList.size(); i++){
            Player temp = playerList.get(i);
            temp.addToHand(deck.deal());
            temp.addToHand(deck.deal());

            repaint();
        }

    }

    public void gameLogic(){// Condense more
        remove(split);
        add(doubleDown);
        doubleDown.setBounds(1650, 200, 200, 50);
        hasHit = false;
        for(int count = 0; count < playerList.size(); count++){
            Player temp = playerList.get(count);
            selection = 0;
            dealerDone = false;

            //TODO REMOVE SPLIT AFTER HIT
            if(temp.checkSplit() == true && temp.isDealer() == false && hasHit == false){
                remove(doubleDown);
                add(split);
                split.setBounds(1700, 200, 100, 50);
            }

            if(temp.isDealer() == true){
                if(temp.getSum() < 17){
                    temp.addToHand(deck.deal());

                    count--;
                    repaint();
                }   
                else{
                    dealerDone = true;
                    repaint();
                }
            }
            else{   
                if(temp.getSum() < 21){ 
                    while(selection == 0){
                        try{
                            Thread.sleep(100);
                        }
                        catch(Exception e){}
                    }

                    if(selection == 1){
                        temp.addToHand(deck.deal());

                        count--;
                        remove(doubleDown);
                        repaint();
                        hasHit = true;
                    }

                    if(selection == 2){
                        //nothing
                    }

                    if(selection == 3){
                        temp.addToHand(deck.deal()); 

                        repaint();

                    }

                    if(selection == 4){
                        Player splitPlayer = new Player(temp.getPlayerName(),false,true);
                        Card splitCard = temp.getCard(1);
                        temp.removeFromHand(1);
                        splitPlayer.addToHand(splitCard);
                        temp.addToHand(deck.deal());                   
                        splitPlayer.addToHand(deck.deal());                        
                        playerList.add(count+1,splitPlayer);

                        repaint();
                        count--;
                    }

                }
            }
        }
        
            for(int i = 0; i < playerList.size();i++){
            Player temp = playerList.get(i);
            ArrayList<Card> hand = temp.getHand();
            
            if(temp.getSum() == 21 && temp.countAce() == 1 && temp.checkFace() == true && hand.size() == 2) {// ace+face+8+2 counted as blackjack
                playerScores.add(1);
            } /*else if(temp.getSum() == 21 && temp.checkAce() == true && temp.checkFace() == true) {
            //TODO If Player and Dealer get BlackJack PUSH
            }*/ else if(temp.getSum() > 21) {
                playerScores.add(2);
            } else if(temp.getSum() == getDealerSum()) {
                playerScores.add(3);
            } else if (temp.getSum() > getDealerSum() || getDealerSum() > 21) {
                playerScores.add(4);
            } else {
                playerScores.add(5);
            }
            repaint();
        }

    }

    public int getDealerSum(){
        int dealerTotal = 0;
        for(int i = 0; i < playerList.size(); i++) {
            Player temp = playerList.get(i);
            if(temp.isDealer() == true) {
                dealerTotal = temp.getSum();
            } 
        }
        return dealerTotal;
    }

    public void makeContTrue() {
        cont = true;
    }  

    public boolean getCont() {
        return cont;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1900, 1000);
        frame.setTitle("BlackJack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BlackJack game = new BlackJack();

        game.setLayout(null);

        frame.add(game);
        frame.setVisible(true); 
        game.initializePlayers(1);

        class YesButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                game.makeContTrue();
            }
        }

        class NoButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }

        JButton yes = new JButton("CONTINUE");
        JButton no = new JButton("QUIT");

        ActionListener yesListener = new YesButtonListener();
        yes.addActionListener(yesListener);  
        ActionListener noListener = new NoButtonListener();
        no.addActionListener(noListener);  

        boolean cont = true;
        while(cont == true){

            game.initialDeal();
            game.gameLogic();
            game.add(yes);
            yes.setBounds(1650, 500, 100, 100);
            game.add(no);
            no.setBounds(1750, 500, 100, 100);
            while(game.getCont() == false){
                try{
                    Thread.sleep(100);
                }
                catch(Exception e){}
            }
            game.clearHand();
            game.repaint();
            game.remove(yes);
            game.remove(no);

        }

    }
}

//TODO Print end result for first hand split
