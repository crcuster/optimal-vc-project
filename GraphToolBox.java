import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**  
 *
 * @author yaw
 */
public class GraphToolBox {
    static HashSet<Integer> vc = new HashSet<Integer>();
    
    public static void printInfo(Graph inputGraph){
        System.out.println(Arrays.deepToString(inputGraph.getGraph()));
    }
    
    static int[] reverse(int[] arr, int l, int r) {
    int d = (r - l + 1) / 2;
        for (int i = 0; i < d; i++) {
          int t = arr[l + i];
          arr[l + i] = arr[r - i];
          arr[r - i] = t;
        }
        return arr;
    }
      // A function which gives previous permutation of the array and returns true if a permutation exists.
      static boolean prev_permutation(int[] str) { //modified from geeksforgeeks.com
          // Find index of the last element of the string
          int n = str.length - 1;
      
          // Find largest index i such that str[i - 1] > str[i]
          int i = n;
          while (i > 0 && str[i - 1] <= str[i]) {
            i--;
          }
      
          // If string is sorted in ascending order we're at the last permutation
          if (i <= 0) {
            return false;
          }
      
          // Note - str[i..n] is sorted in ascending order Find rightmost element's index that is less than str[i - 1]
          int j = i - 1;
          while (j + 1 <= n && str[j + 1] < str[i - 1]) {
            j++;
          }
      
          // Swap character at i-1 with j
          int temper = str[i - 1];
          str[i - 1] = str[j];
          str[j] = temper;
      
          // Reverse the substring [i..n]
          str = reverse(str, i, str.length - 1);
      
          return true;
        }
     
      // Function to print all the power set
      static void printPowerSet(int[] set, int n, Graph inputGraph)
      {
     
        HashSet<Integer> powSet = new HashSet<Integer>();
        int[] contain = new int[n];
        for (int i = 0; i < n; i++)
          contain[i] = 0;
     
        // Empty subset
        System.out.println();
        for (int i = 0; i < n; i++) {
          contain[i] = 1;
     
          // To avoid changing original 'contain' array creating a copy of it i.e. "Contain"
          int[] Contain = new int[n];
          for (int indx = 0; indx < n; indx++) {
            Contain[indx] = contain[indx];
          }
          // All permutation
          do {
            for (int j = 0; j < n; j++) {
              if (Contain[j] != 0) {
                powSet.add(set[j]);
              }
            }
            //System.out.print("\n");                                                  
            if (vcVerifier(powSet, inputGraph)) {
                vc = new HashSet<Integer>(powSet);
                return; //thank god for garbage collection
            }
            powSet.clear();
     
          } while (prev_permutation(Contain));
        }
      }
    
      public static int[] exactVC(Graph inputGraph) { //creates int array of vertices and send it to powerset function
        int k = inputGraph.getGraph().length;
        int[] vertSet = new int[k];
        for (int i=0; i<k; i++) {
            vertSet[i] = i;
        }
        printPowerSet(vertSet, k, inputGraph);
        return vc.stream().mapToInt(i->i).toArray();
    }
    
        public static boolean vcVerifier(HashSet<Integer> set, Graph inputGraph) { // returns true if is a valid VC
            boolean accept = false;
            int k = inputGraph.getGraph().length; 
            accept = true;
            if (set.size() > k) { //Test that |V'| <= k, reject if not
                accept = false;
            }
            else { //For each edge e = (a, b) in E, Test if a ∈ V′ or b ∈ V′, reject if neither.
                for (int i=0; i<k;i++) { //for each vert in graph
                    if (accept){
                        if (!(set.contains((Integer)i))) { //if vertex not in set, run checks
                            for (int h=0; h<inputGraph.getGraph()[i].length; h++) { //for each neighbor or vertex
                                if (!(set.contains(inputGraph.getGraph()[i][h]))) {
                                    accept = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
             
            return accept;
        }
    
    
    
    
            
        public static int[] inexactVC(Graph inputGraph) { //COMPLEMENT OF VERTEX COVER IS AN INDEPENDENT SET
              //Foreach node, if node is marked, go to next node. If node is unmarked, mark all of its neighbors.
              int[] verts = new int[inputGraph.getGraph().length];
              ArrayList<Integer> nonOptimalVC = new ArrayList<Integer>();
              for (int i=0; i<verts.length; i++) {
                  verts[i] = i;
              }
    
              for (int vert: verts) {
                if (!nonOptimalVC.contains(vert)) { //if vert not in vertex cover, mark all neighbors
                  for (int neighbor: inputGraph.getGraph()[vert]) {
                    if (!nonOptimalVC.contains(neighbor)) {
                      nonOptimalVC.add(neighbor);
                    }
                  }
                }
              }
    
              if (vcVerifier(new HashSet<Integer>(nonOptimalVC), inputGraph)) {
                return nonOptimalVC.stream().mapToInt(i->i).toArray();
              }
    
              else {
                System.out.println("Uh oh, something went wrong..");
                return null;
              }
          }
        
        // return an array containing the vertex numbers of an optimal IS.
        public static int[] optimalIS(Graph inputGraph) { //complement of exactVC
            int vclength;
            vclength = exactVC(inputGraph).length;
            int[] IS = new int[inputGraph.getGraph().length - vclength];
            int isIndex = 0;
            for (int i=0; i < inputGraph.getGraph().length; i++) { //create inverse vertice list of ExactVertexCover
                if (!vc.contains(i)) {
                    IS[isIndex++] = i;
                }
            }
    
            return IS;
        }
        
        // return (in polynomial time) an array containing the vertex numbers of a IS.
        public static int[] inexactIS(Graph inputGraph) {   
          int[] VC = inexactVC(inputGraph);
          int[] IS = new int[inputGraph.getGraph().length - VC.length];
          int ISindex = 0;
          for (int i=0; i < inputGraph.getGraph().length; i++) { //create inverse vertice list of InexactVertexCover
            for (int j=0; j< VC.length; j++) {
              if (i == VC[j]) {
                break;
              }
              else if ( j==VC.length -1) { //if i not found in VC
                IS[ISindex++] = i;
              }
            }
          }
          return IS;
        }
    }