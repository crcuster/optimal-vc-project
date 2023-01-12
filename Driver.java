import java.util.Arrays;

public class Driver {


    public static void main(String[] args) {
        System.out.println("Driver");
        Graph testGraph = new Graph("graphTest.txt");
      //  GraphToolBox.exactVC(testGraph);           //test graph
       int[] inexactIS = GraphToolBox.inexactIS(testGraph);
        System.out.println(Arrays.toString(inexactIS));
       // int[] exactIS = GraphToolBox.optimalIS(testGraph);
        //System.out.println(Arrays.toString(exactIS));
        //int[] exactVC = GraphToolBox.exactVC(testGraph);
        //System.out.println(Arrays.toString(exactVC));
        
      
     
       
        }





}
