import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Functions {

	static ArrayList<Integer> playerCards = new ArrayList<>();
	static ArrayList<Integer> dealerCards = new ArrayList<>();
	static ArrayList<Integer> deck = new ArrayList<>();
	static Scanner sc = new Scanner(System.in);
	static int playerTotal;
	static int dealerTotal;
	static boolean playerBust = false;
	static boolean dealerBust = false;
	static boolean playerBlackjack = false;
	static boolean dealerBlackjack = false;

	// Desteyi oluşturur
	
	public static void createDeck() {
		for (int i = 1; i <= 13; i++) {
			for (int j = 0; j < 4; j++) {
				deck.add(i);
			}
		}
	}

	// Desteyi karıştırır
	
	public static void shuffleDeck() {
		Collections.shuffle(deck);
	}

	// Desteden kartları dağıtır
	
	public static void dealCards() {
		// Oyuncuya ilk 2 kart verir
		
		playerCards.add(deck.get(0));
		deck.remove(0);
		playerCards.add(deck.get(0));
		deck.remove(0);

		// Bayiye ilk 2 kart verir
		
		dealerCards.add(deck.get(0));
		deck.remove(0);
		dealerCards.add(deck.get(0));
		deck.remove(0);

		// Eğer oyuncunun ilk 2 kartının toplamı 21 ise, oyuncu blackjack yaptı
		if (calculateTotal(playerCards) == 21) {
			playerBlackjack = true;
		}

		// Eğer bayinin ilk 2 kartının toplamı 21 ise, bayi blackjack yaptı
		if (calculateTotal(dealerCards) == 21) {
			dealerBlackjack = true;
		}
	}

	public static void playerTurn() {
		// Oyuncunun kartlarının toplamını hesaplalar
		
		playerTotal = calculateTotal(playerCards);

		// Oyuncunun stand seçeneğine göre hareket eder
		while (true) {
			System.out.println("Your cards: " + playerCards);
			System.out.println("Your total: " + playerTotal);
			System.out.println("Hit or stand? (H/S)");
			String input = sc.nextLine();

			if (input.equalsIgnoreCase("H")) {
				// Oyuncu kart istiyor, desteden bir kart ekler
				playerCards.add(deck.get(0));
				deck.remove(0);

				// Oyuncunun kartlarının toplamını yeniden hesaplar
				playerTotal = calculateTotal(playerCards);

				// Oyuncunun 21'i aştı mı kontrol eder
				if (playerTotal > 21) {
					playerBust = true;
					break;
				}
			} else if (input.equalsIgnoreCase("S")) {
				// Oyuncu kart istemiyor, turunu bitirir
				break;
			} else {
				System.out.println("Invalid input. Please enter H or S.");
			}
		}
	}

	public static void dealerTurn() {
		// Bayinin kartlarının toplamını hesaplar
		dealerTotal = calculateTotal(dealerCards);

		// Bayinin stand seçeneğine göre hareket eder
		while (true) {
			if (dealerTotal < 17) {
				// Bayi, 17'ten düşük bir toplamı varsa, kart ister
				dealerCards.add(deck.get(0));
				deck.remove(0);

				// Bayinin kartlarının toplamını yeniden hesapla
				dealerTotal = calculateTotal(dealerCards);

				// Bayinin 21'i aştı mı kontrol et
				if (dealerTotal > 21) {
					dealerBust = true;
					break;
				}
			} else {
				// Bayi, 17 veya daha yüksek bir toplamı varsa, kart istemez
				break;
			}
		}
	}

	public static void determineWinner() {
		// Öncelikle, oyuncunun ve bayinin 21'i aşıp aşmadığını kontrol et
		if (playerBust) {
			System.out.println("You bust! Dealer wins.");
		} else if (dealerBust) {
			System.out.println("Dealer busts! You win.");
		} else {
			// Eğer hiçbiri 21'i aşmıyorsa, kart toplamlarını karşılaştır
			if (playerTotal > dealerTotal) {
				System.out.println("You win! Your total: " + playerTotal + ", Dealer total: " + dealerTotal);
			} else if (dealerTotal > playerTotal) {
				System.out.println("Dealer wins! Your total: " + playerTotal + ", Dealer total: " + dealerTotal);
			} else {
				System.out.println("It's a tie! Your total: " + playerTotal + ", Dealer total: " + dealerTotal);
			}
		}
	}

	public static int calculateTotal(ArrayList<Integer> cards) {
		int total = 0;
		int aceCount = 0;

		// Tüm kartların değerlerini topla
		for (int card : cards) {
			if (card == 1) {
				// 1 değerli kartlar (Aslar) sayısını tut
				aceCount++;
			} else if (card > 10) {
				// 10 veya daha yüksek değerli kartlar (Valetler, Kadınlar, Krallar) 10
				// değerinde sayılır
				total += 10;
			} else {
				// Diğer kartlar, kendi değerleri olarak sayılır
				total += card;
			}
		}

		// Asları ekle
		while (aceCount > 0) {
			if (total + 11 <= 21) {
				// Eğer mümkünse, Asların 11 değerinde sayılmasına izin ver
				total += 11;
			} else {
				// Asların 1 değerinde sayılmasına izin ver
				total += 1;
			}
			aceCount--;
		}

		return total;
	}

}
