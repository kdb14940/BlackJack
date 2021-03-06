import java.util.*;
public class Deck {
	private ArrayList<Card> deck;
	
	public Deck (){
		deck = new ArrayList<Card>();
		fillDeck(deck);
	}
	
	public void fillDeck(ArrayList<Card> deck){
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= 13;j++){
				Card temp = new Card(i,j);
				deck.add(temp);
			}
		}
	}
	
	public void printDeck(){
		for (int i = 0; i < deck.size(); i++){
			Card temp = deck.get(i);
			System.out.println(temp.getSuit() + " " + temp.getValue());
		}
	}
	
	public int getDeckSize(){
	    return deck.size();
	   }
	
	public Card deal(){
		Random generator = new Random();
	        int rand = generator.nextInt(deck.size()); 
	        Card temp = deck.get(rand);
	        deck.remove(rand);
		if(deck.size() == 0 )
                fillDeck(deck);
	        return temp;
		   }
	
	public static void main(String[] args){
		Deck deck  = new Deck();
		deck.printDeck();
		}
	}

