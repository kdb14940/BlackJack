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
    private Player dealer;
    private int xPos;
    private int yPos;

    private int selection;
    private boolean blackJack,push,bust,win,lose = false;
    JButton hit = new JButton("HIT");
    JButton stay = new JButton("STAY");
    JButton doubleDown = new JButton("DOUBLE DOWN");
    JButton split = new JButton("SPLIT");
    public BlackJack(){
        deck = new Deck();
        playerList = new ArrayList<Player>();
        dealer = new Player("Dealer",true);
        xPos = 0;
        yPos = 0;
        selection = 0;
        add(hit);
        add(stay);
        add(doubleDown);
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
        selection =4;
    }
    
    public void paintComponent (Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        for(int i = 0; i < playerList.size(); i ++){
            int xPos = 150;
            Player temp = playerList.get(i);
            ArrayList<Card> playerHand = temp.getHand();
            for(int j = 0; j < playerHand.size(); j++){
                Card tempCard = playerHand.get(j);
                BufferedImage image = tempCard.getImage();
                if(temp.isDealer() == true && j == 0){
                    try{
                        image = ImageIO.read(new File("BACK.jpg")); // comment out if need to test
                    }
                    catch(Exception e){}
                }
                if(j == 0){
                if(j == 0){
                    xPos =  xPos;// fix for graphical update glitch
                }
                else{
                    xPos =  xPos + (15*playerHand.size());
                }
                int yPos = temp.getYPos();
                g2.drawImage(image,xPos,yPos,null);
            }
            if(blackJack == true){
                g2.drawString("BLACKJACK!",650,500);
            }
            if(bust == true){
                g2.drawString("BUST",650,500);
            }
            if(push == true){
                g2.drawString("PUSH!",650,500);
            }
            if(win == true){
                g2.drawString("WIN!",650,500);
            }
            if(lose == true){
                g2.drawString("LOSE!",650,500);
            }
        }
    }

   public void initializePlayers(int playerCount){
        for(int i = 0; i < playerCount ; i++){
            String name = "Player "+(i+1);
            Player temp = new Player(name, false);
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
        for(int i = 0; i < playerList.size(); i++){
            Player temp = playerList.get(i);
            temp.addToHand(deck.deal());
            
            temp.addToHand(deck.deal());
            repaint();
        }

    }

    public void gameLogic(){// Condense more
        Scanner in = new Scanner(System.in);
        for(int count = 0; count < playerList.size(); count++){
            Player temp = playerList.get(count);
            selection = 0;
            if(temp.isDealer() == true){

                if(temp.getSum() < 17){
                    temp.addToHand(deck.deal());
                    repaint();
                }   
            }
            else{   
                if(temp.getSum() > 21){
                    temp.printDeck();
                } else if (temp.getSum() == 21 && temp.countAce() == 1 && temp.checkFace() == true) {
                    temp.printDeck();
                }
                else {

                    if(temp.getSum() < 21 && temp.checkSplit() == true){ 
                        temp.printDeck();
                        System.out.println("Enter 1 for hit");
                        System.out.println("Enter 2 for stay");
                        System.out.println("Enter 3 for split");

                        int selection = in.nextInt();

                        if(selection == 1){
                            temp.addToHand(deck.deal());
                            count--;
                        }
                        if(selection == 2){
                            //nothing
                        }
                        if(selection == 3){
                            Player split = new Player(temp.getPlayerName(),false);
                            Card splitCard = temp.getCard(1);
                            temp.removeFromHand(1);
                            split.addToHand(splitCard);
                            temp.addToHand(deck.deal());
                            temp.printDeck();
                            split.addToHand(deck.deal());
                            playerList.add(count+1,split);
                        }
                    }

                    // else only hit or stay
                    else {

                        if(temp.getSum() < 21){ 
                            while(selection == 0){
                                try{
                                    Thread.sleep(500);
                                }
                                catch(Exception e){}
                            }
                            if(selection == 1){
                               temp.addToHand(deck.deal());
                               count--;
                               repaint();
                            }

                            if(selection == 2){
                                //nothing
                            }
                            if(selection == 3){
                                temp.addToHand(deck.deal()); 
                                repaint();
                                
                                
                            }
                            
                           

                        }

                    }
                }
            }
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

    public void scoring(){
        for(int i = 0; i < playerList.size() - 1; i++) {
            Player temp = playerList.get(i);
            ArrayList<Card> hand = temp.getHand();

            if(temp.getSum() == 21 && temp.countAce() == 1 && temp.checkFace() == true && hand.size() == 2) {// ace+face+8+2 counted as blackjack
               blackJack = true;
            } /*else if(temp.getSum() == 21 && temp.checkAce() == true && temp.checkFace() == true) {
            //TODO If Player and Dealer get BlackJack PUSH
            }*/ else if(temp.getSum() > 21) {
                bust = true;
            } else if(temp.getSum() == getDealerSum()) {
                push = true;
            } else if (temp.getSum() > getDealerSum() || getDealerSum() > 21) {
                win = true;
            } else {
                lose = true;
            }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(750, 750);
        frame.setTitle("BlackJack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BlackJack game = new BlackJack();

        frame.add(game);
        frame.setVisible(true); 
        game.initializePlayers(1);
        boolean cont = true;

        while(cont == true){
            game.initialDeal();
            game.gameLogic();
            game.scoring();

            Scanner answer = new Scanner(System.in);
            System.out.println("\nWould you like to play again? (y/n)");
            if(answer.nextLine().equalsIgnoreCase("Y")){
                cont = true;
                game.clearHand();
            }
            else cont = false;
        }

    }
}
