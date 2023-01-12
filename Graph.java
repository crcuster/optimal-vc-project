import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 *
 * @author yaw
 */
public class Graph {

    private int[][] graph; // Must be of the form [vertex][vertexNeighbor1, vertexNeighbor2, ...]

    public Graph(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();

            if (line.startsWith("numVert")) {
                graph = new int[Integer.parseInt(line.substring(line.indexOf(" ") + 1))][];
                line = br.readLine();
                line = br.readLine();

                while (line != null) {
                    String[] values = line.split(" ");
                    int vertexNum = Integer.parseInt(values[0]);
                    int numNeighbors = Integer.parseInt(values[1]);
                    graph[vertexNum] = new int[numNeighbors];
                    for (int i = 0; i < numNeighbors; i++) {
                        graph[vertexNum][i] = Integer.parseInt(values[2 + i]);
                    }
                    line = br.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Try putting file in same filder as src folder.");
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
    }

    public int[][] getGraph() {
        
        return graph;
    }
}
