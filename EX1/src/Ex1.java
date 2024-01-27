
import java.util.Arrays;
import java.lang.Math;

/**
 * Introduction to Computer Science, Ariel University, Ex1 (manual Example + a Template for your solution)
 * See: https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * Ex1 Bulls & Cows - Automatic solution.

 * **** ReadMe ****
 * This program is the player side in the gamer Bulls & Cows.
 * This program can crack any 2-6 digits number(results and average below)

  **** General Solution (algorithm) ****
  The general idea is trying to learn from one guess result on all the others compare to him.
  by doing that we can eliminate huge amount of numbers without sending guesses to the server.

 * Add your explanation here:
  Explaining the algorithm step by step (4 digit case):
  1. For the beginning we sent the first number '0000':
     for this guess there is two options, the first is that we have Bulls=0 and Cows=0, the second is Bull!=0 or cows!=0.

  2.The first case (B=0&C=0) mean that any number that contain any of the numbers 0000 is invalid guess,('1023','0153','0008'..),
     with that idea we can eliminate about 19% from the options.

  3.The second case (B!=0 or C!=0) means that the number have something in common with the code (can be 1 Bull or 2 Cows or any other option it doesn't matter),
     The idea is that only the numbers that have the same result in common with the GUESS can be a valid option. ALL the others are invalid ,we can eliminate them.

 * 4.How do we execute the compare for the second case?
     we're using the function "BC_compare", this function takes the guess and its personal result and compares between the guess to all other valid options.
     from this compare we get an alternative result.
     Now we compare the two results. If there is the same bulls and cows it stays a valid option but if not this guess is eliminated.

     Example: the code is: '3456', and my guess is: '3222', in this case the result should be B=1 C=0.
     If we follow the program, then only the numbers that have B=1 and C=0 with '3222' can be valid options (5255, 3666,9442...)
     buf if some number do not have the same bulls and cows that the guess have with the code it can be eliminated ( '4444','3299','3222','7932'...)
     (At this point the code is still valid).

 * 5. The elimination proses executed by using boolean array at the size of the options (for 2 digits: 10^2=100, for 4: 10^4=10,000)
      After we have the Array we fill it in a true value. When we need to invalidate guess we mark the index as false,
      Example: the number '0000' is now invalid, so we mark the index "0000" (which it 0) as false .
      Every time we need a valid option we search for the first true index in the array.

 * **** Results ****
 * Make sure to state the average required guesses
 * for 2,3,4,5,6 digits code:

 * Average required guesses 2: 7
 * Average required guesses 3: 8
 * Average required guesses 4: 8
 * Average required guesses 5: 9
 * Average required guesses 6: 9

 */
public class Ex1 {


    public static final String Title = "Ex1 demo: auto Bulls & Cows game";

    public static void main(String[] args) {
        BP_Server game = new BP_Server();   // Starting the "game-server"
        long myID = 206847584;             // Your ID should be written here
        int numOfDigits =6;                // Number of digits [2,6]
        game.startGame(myID, numOfDigits);  // Starting a game
        System.out.println(Title + " with code of " + numOfDigits + " digits");

        autoEx1Game(game); // you should implement this function )and any additional required functions).

        //manualEx1Game(game);

    }
/*
    public static void manualEx1Game(BP_Server game) {
        Scanner sc = new Scanner(System.in);
        int ind = 1;      // Index of the guess
        int numOfDigits = game.getNumOfDigits();
        double max = Math.pow(10, numOfDigits);
        while (game.isRunning()) {           // While the game is running (the code has not been found yet
            System.out.println(ind + ") enter a guess: ");
            int g = sc.nextInt();
            if (g >= 0 && g < max) {
                int[] guess = toArray(g, numOfDigits); // int to digit array
                int[] res = game.play(guess); // Playing a round and getting the B,C
                if (game.isRunning()) {     // While the game is running
                    System.out.println(ind + ") B: " + res[0] + ",  C: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                    ind += 1;               // Increasing the index
                }
            } else {
                System.out.println("ERR: wrong input, try again");
            }
        }
        System.out.println(game.getStatus());
    }
*/

    /**
     * "toArray" function takes integer and converts it to an array at the chosen size.
        This function keeps the correct order of the digits.
     * @param a    - a natural number (as a guess)
     * @param size - number of digits (to handle the 00 case).
     * @return an array of digits
     */
    private static int[] toArray(int a, int size) {
        int[] c = new int[size];

        for (int j = (c.length - 1); j >= 0; j--) {
            c[j] = a % 10;
            a = a / 10;
        }
        return c;
    }

    /**
     * "ArrayMaker" - This function creates a Boolean array.
        the size of the array is 10^size.
     * @param size - number of digits.
     * @return - Boolean array at the size of 10^size.
     */
 static boolean[] ArrayMaker(int size){
        boolean[]arr;
        double s=Math.pow(10,size);
        arr=new boolean[(int)s];
        Arrays.fill(arr, true);     //init the array with true values
        return arr;
    }

    /**
     * The function "FirstTrue" checks what is the first true value in the Boolean Array.
      Explanation:
      This function returns the index value of the first True value in the Boolean array,
      (This value actually is going to be the next guess.)
     * @param arr - boolean array
     * @return ans - the first true index
     */
   static int FirstTrue(boolean[] arr){
        int ans=-1;
        for(int i=0;i<arr.length;i++){
            if (arr[i]){
                 ans=i;
                break;
            }
        }
       return ans;              //returns the first true index
   }

    /**
     * The function "No_BNo_C" is a filter for a guess that gives us no Bulls and no Cows.
      Explanation:
      When a guess gives us No Bulls and No Cows,
      that means that all the numbers that contain the same digits anywhere are not valid and can be eliminated.
      We're searching for all numbers that contain the same digits (using the BC_Compare function),
      and mark there index as false.
     * @param wg - the guess that send to the game
     * @param BArray - the boolean array
     * @return - updated boolean array
     */
   static boolean[] No_BNo_C (int[] wg, boolean[] BArray, int num){ //problem for later
       int[] digArr;
       for (int i=0;i<BArray.length; i++){
           digArr= toArray(i, num);
           int[] AltRes= BC_Compare(digArr,wg);
           int Altr=0;
           Altr=AltRes[0]+AltRes[1];                //if there is any common between the guess to other numbers
           if(Altr>0){
               BArray[i]=false;                     //mark the index as invalid
           }
       }
      return BArray;
    }// End No_BNo_C

    /**
     * "MyFilter" function filters all the numbers that can't be the code.
      Explanation:
      the function compares between two arrays (Altres & WgRes),
      if one of the digits is different from the other in the same index,
      than the function changes the value in the boolean array (BoolARR) to False at the same index.
     * @param BoolArr - the boolean array
     * @param wg - the guess that send to the game
     * @param WgRes - the result that return from sending wg to the game (B & C)
     */
    static void MyFilter(boolean[]BoolArr,int[] wg,int[] WgRes, int numberOfDigits){
        int Altr=0, myres=0;
       for (int i=0; i<BoolArr.length;i++){
           if(BoolArr[i]){

               int[] currentArr= toArray(i,numberOfDigits);         //making an array from every integer that still true
               int[] AltRes= BC_Compare(currentArr,wg);             //compares for B and C between current array and my wise guess

               if(AltRes[0]!=WgRes[0] || AltRes[1]!=WgRes[1]){      //if the tow result doesn't match the number is eliminated
                   BoolArr[i]=false;
               }
           }
       }
    }//End MyFilter

    /**
     * "BC_Compare" - This function takes two arrays and compares between them for Bulls and Cows.
        The function returns the amount of bulls and cows.
     * * @param wg
     * @param currArr - temporary array that changes with the next valid index.
     * @return - Bulls & Cow
     */
    static int[] BC_Compare(int[] wg, int[] currArr) {
        int bulls = 0;
        int cows = 0;

        boolean[] Gb1 = new boolean[wg.length];
        boolean[] Gb2 = new boolean[currArr.length];


        for (int i = 0; i < wg.length; i++) {       // Count bulls (matching digits in the same position)
            if (wg[i] == currArr[i]) {
                bulls++;
                Gb1[i] = true;                      //this sells can't be count as cow
                Gb2[i] = true;
            }
        }

        for (int i = 0; i < wg.length; i++) {       // Count cows (matching digits in different positions)
            for (int j = 0; j < currArr.length; j++) {
                if (!Gb1[i] && !Gb2[j] && wg[i] == currArr[j]) {
                    cows++;
                    Gb1[i] = true;                  //this sells can't be count as cow again
                    Gb2[j] = true;
                    break;                          // Exit inner loop after finding a matching cow
                }
            }
        }
        return new int[]{bulls, cows};
    }

        public static int digits=0;
        public static int runs=0;
        /**
         * This function solves the Bulls & Cows game automatically.
           AutoEx1Game function manages the game solvig, it starts and ends the game,
           the function sends the actual guesses to the server.
         */
        public static void autoEx1Game (BP_Server game){
            int numOfDigits = game.getNumOfDigits();
            digits=numOfDigits;
           boolean[] BoolArr;
           int guess=0;
            int[]  wiseGuss = new int[numOfDigits];
            int[] res = new int[2];
            int  ind = 1;

            BoolArr=ArrayMaker(numOfDigits); //make the boolean array
            while(game.isRunning()){
                guess=FirstTrue(BoolArr);       //getting the first true index;
                wiseGuss=toArray(guess, numOfDigits);
                res= game.play(wiseGuss);

               if (game.isRunning()) {
                   System.out.println(" B: " + res[0] + ",  C: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                    ind++;
                }

               runs++;          //global parameter for avg

                if(res[0]==0 && res[1]==0){                         //no Bulls or Cows
                    BoolArr=No_BNo_C(wiseGuss,BoolArr, numOfDigits);
                }
                else {
                    MyFilter(BoolArr, wiseGuss, res, numOfDigits);
                }
        }//game running
                System.out.println(game.getStatus() );

        }//end auto

}//end class
