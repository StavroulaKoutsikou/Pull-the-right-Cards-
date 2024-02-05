//Stavroula Koutsikou 4396
//Simon Gkoni 4342


import java.util.*;

public class Minimax {
    
    public static int depth;



    public static int minimax(int[] state, int depth, int currentPlayer, int[] B, boolean isMaximizingPlayer) {
        // Check if we have reached the depth of the search tree or if we have reached a target state
        if (depth == 0 || isTerminal(state)) {
            return evaluate(state, currentPlayer); 
        }
    
        if (isMaximizingPlayer) { // If it is the turn of the maximum player
            int value = Integer.MIN_VALUE; // Initialize the value with the minimum possible number
            for (int i = 1; i < state.length; i++) {
                if (state[i] > 0) {
                    for (int j = 1; j <= B[i-1] && j <= state[i]; j++) {
                        int[] newState = state.clone(); // // Create a copy of the current state
                        newState[i] -= j;
                        int currentValue = minimax(newState, depth - 1, 2, B, false);
                        value = Math.max(value, currentValue); // Select the maximum between the current value and the new value
                    }
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE; // Initialize the value with the maximum possible number
            for (int i = 1; i < state.length; i++) {
                if (state[i] > 0) {
                    for (int j = 1; j <= B[i-1] && j <= state[i]; j++) {
                        int[] newState = state.clone(); // Create a copy of the current state
                        newState[i] -= j;
                        int currentValue = minimax(newState, depth - 1, 1, B, true);
                        value = Math.min(value, currentValue); 
                    }
                }
            }
            return value;
        }
    }

    public static int evaluate(int[] state, int currentPlayer) {
        int sum = 0; 
        for (int i = 1; i < state.length; i++) { // Crossing the state table
             sum += state[i]; 
        }
        return sum; // Return of the final sum as a situation evaluation value
    }
    
        



    public static int makeMove(int[] state, int currentPlayer, int[] B) {
        int value = Integer.MIN_VALUE; // Initialize the value variable with the minimum possible value of int
        int chosenGroup = -1; 
        int chosenCards = -1; 
        for (int i = 1; i < state.length; i++) { // Crossing the state table
            if (state[i] > 0) {// Check if the number of elements in group i is greater than 0
                for (int j = 1; j <= B[i-1] && j <= state[i]; j++) { // crossing the possible moves, from 1 to the minimum from B[i-1] and state[i]
                    int[] newState = state.clone(); 
                    newState[i] -= j; 
                    int currentValue = minimax(newState, (depth-1), 2, B, false); // Calculate the current value of the situation using the minimax function
    
                    if (currentValue > value) { // Check if the current value is greater than the current maximum value
                        value = currentValue; 
                    chosenGroup = i; 
                    chosenCards = j; 
                }
            }
        }
    }
    System.out.println("Player " + currentPlayer + " removes " + chosenCards + " cards from group " + chosenGroup); 
    state[chosenGroup] -= chosenCards;
    return currentPlayer == 1 ? 2 : 1; // Return the next player's number (1 or 2), depending on the current player
}






    public static void updatePlayer(int[] state, int currentPlayer, int[] B) {
        int winner = play(state, currentPlayer, B); // Call the play function to check the final winner
        if (winner == 1) { // Check if player 1 is the winner
            System.out.println("Player 1 wins!"); // Print message that player 1 has won
        } else { // If player 1 is not the winner, then it is player 2
            System.out.println("Player 2 wins!"); // Print message that player 2 has won
        }
    }

    





    public static int play(int[] state, int currentPlayer, int[] B) {
        if (isTerminal(state)) { 
            if (currentPlayer == 1) { // If the player is 1
                return -1; // Return the value -1, indicating that player 1 has lost
            } else { // If the player is the 2
                return 1; // Return value 1, indicating that player 2 has lost
            }
        } else if (currentPlayer == 1) { // If it is player 1's turn
            int value = Integer.MIN_VALUE; // Initialize the value variable with the minimum possible value
            for (int i = 1; i < state.length; i++) {
                if (state[i] > 0) { 
                    for (int j = 1; j <= B[i-1] && j <= state[i]; j++) { // Crossing the possible moves for player 1
                        int[] newState = state.clone(); 
                        newState[i] -= j; 
                        int currentValue = play(newState, 2, B); // Recursive call of the play function for the next round with player 2
                        value = Math.max(value, currentValue); // Update the value value with the maximum of value and currentValue
                    }
                }
            }
            return value; 

        } else { // If it is player 2's turn
            int value = Integer.MAX_VALUE; // Initialize the value variable with the maximum possible value
            for (int i = 1; i < state.length; i++) {
                if (state[i] > 0) { 
                    for (int j = 1; j <= B[i-1] && j <= state[i]; j++) { // Crossing the possible moves for player 2
                        int[] newState = state.clone(); 
                        newState[i] -= j; 
                        int currentValue = play(newState, 1, B); // Recursive call of the play function for the next round with player 1
                        value = Math.min(value, currentValue); // Update the value value with the minimum of value and currentValue
                    }
                }
            }
            return value; 
        }
    }







    public static boolean isTerminal(int[] state) {
        int sum = 0; 
        for (int i = 1; i < state.length; i++) { 
            sum += state[i]; // Add the value of each state element to the sum variable
        }
        return sum == 0; // Return true if the sum is equal to 0, otherwise return false
    }






    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int M, K;
            System.out.println("Enter the number of cards (M):");
            M = sc.nextInt(); // User input of the number of M cards
            System.out.println("Enter the number of groups (K):");
            K = sc.nextInt(); // User input of the number of groups K

            int[] A = new int[K]; // Create a table A of size K for the number of cards in each group
            int[] B = new int[K]; // Create a table B of size K for the maximum number of cards that can be removed from each group
            int sumA = 0; // Variable for calculating the total number of cards in groups A

            for (int i = 0; i < K; i++) {
                System.out.println("Enter the number of cards in group " + (i + 1) + " (A" + (i + 1) + "):");
                A[i] = sc.nextInt(); // Enter the number of cards in group i by the user
                sumA += A[i]; // Calculation of the total number of cards in groups A
                System.out.println("Enter the maximum number of cards that can be removed from group in each round"  + " (B" + (i + 1) + "):");
                B[i] = sc.nextInt(); // Enter the maximum number of cards that can be removed in each round
                if (B[i] > A[i] && sumA != M) { // Check if B[i] is greater than A[i] AND the total value of sumA is not equal to M
                    System.err.println("Error: B" + (i + 1) + " must be less than A" + (i + 1) + " OR the sum of A[i] is not equal to M");
                    System.exit(1); 
                }
            }

            int currentPlayer = 1;
            try (Scanner scanner = new Scanner(System.in)) {
                for (int i = 0; i < K; i++) { // Read the data of table A by the user
                    System.out.print("Number of cards in group " + (i+1) + ": ");
                    A[i] = scanner.nextInt();
                }
            }

            // Initialize the game state
            int[] state = new int[K+1];
            state[0] = M;
            for (int i = 1; i <= K; i++) {
                state[i] = A[i-1];
            }

            int winner = play(state, currentPlayer, B); 
            if (winner == 1) { 
                System.out.println("Player 2 wins!"); // Print "Player 1 wins!" message if the winner is 1
            } else {
                System.out.println("Player 1 wins!"); // Print "Player 2 wins!" message if the winner is 2
            }

        }
    }



}
