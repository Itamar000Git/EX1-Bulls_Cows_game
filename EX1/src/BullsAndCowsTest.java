import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.fail;

public class BullsAndCowsTest {

    /**
     * Test the full game 100 times.
     */
    @Test
    public void testAutoGame() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Testing Automatic Game - Run " + (i + 1) + ":");
            // Create a mock BP_Server object
            BP_Server mockServer = new BP_Server();
            mockServer.startGame(987654321, 2); // Example ID and number of digits

                Ex1.autoEx1Game(mockServer);
        }
        System.out.println("for: "+ Ex1.digits+" the avg is: "+Ex1.runs/100);
    }

    /**
     * Test for "ArrayMaker" function.
       Checks if all the array is true.
     */
    @Test
    public void testArrayMaker(){
        int size=4,counter=0;
        boolean[]testarr;
       testarr= Ex1.ArrayMaker(size);
       for (int i=0; i<10000;i++){
           if(testarr[i]){
               counter++;
           }
       }
        Assertions.assertEquals(counter, 10000);
    }

    /**
     *
     */
    @Test
    public void testFirstTrue(){
        boolean[] BArrtest= {false,false,false,false,true,false,true,true,true,true};
        int anstest=-1, counter=0;
        anstest=Ex1.FirstTrue(BArrtest);
        for (int i=(anstest-1); i>=0;i--){
            if(!BArrtest[i]){
                counter++;
            }
        }
        Assertions.assertEquals(counter,anstest);
    }

    /**
     * The function "testNo_BNo_C" eliminates all the numbers that have at least 1 Bull or ! Cow with "wgtest".
     */
    @Test
    public void testNo_BNo_C(){
    int[] wgtest={0,0,0,0};
    boolean [] BArraytest=new boolean[10000];
    Arrays.fill(BArraytest,true);
    int numtest = 4;
    BArraytest=Ex1.No_BNo_C(wgtest,BArraytest,numtest);
    Assertions.assertEquals(BArraytest[999],false);
    }

    /**
     * The function "testMyFilter" checks if the result of WGTest match to the alternative result.
     */
    @Test
    public void testMyFilter(){
        int[] wgtest={1,2,3,0}, wgResTest={1,1};
        boolean [] BArraytest=new boolean[10000];
        Arrays.fill(BArraytest,true);
        int numtest = 4;
       Ex1.MyFilter(BArraytest,wgtest,wgResTest,numtest);

        Assertions.assertEquals(BArraytest[1399],true);
    }

    /**
     *The function "testBC_Compare" checks if "BC_Compare" count correctly the Bulls and the cows
     */
    @Test
    public void testBC_Compare(){
        int[]WGTest={1,2,3,4},CurrArr={0,9,2,4}, AltresTest=new int[2],RealRes={1,1};
        AltresTest= Ex1.BC_Compare(WGTest,CurrArr);

        Assertions.assertArrayEquals(AltresTest, RealRes);


    }


}
