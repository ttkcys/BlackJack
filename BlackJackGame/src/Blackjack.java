
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Blackjack extends Functions {

	public static void main(String[] args) {

		createDeck();
		shuffleDeck();
		dealCards();

		while (true) {

			// Oyuncu

			playerTurn();
			if (playerBust || playerBlackjack) {
				break;
			}

			// Bayi
			dealerTurn();
			if (dealerBust || dealerBlackjack) {
				break;
			}

			// Kazanan
			determineWinner();
			break;
		}
	}

}