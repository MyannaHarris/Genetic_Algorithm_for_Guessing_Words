/* AI
*
* Myanna Harris, Jasmine Jans, and Carol Joplin (jjans@zagmail.gonzaga.edu submitter)
* 3-10-17
*
* Genetic Algorithm
* TSPTst class
*/

/*
 Usage: java TSPtst <paramFile> <0> <type of mating and pairing alg (0,1,2,3)>
 Example: java TSPtst param.dat 0 1
    Using the parameter file, param.dat, try to find the shortest path between 8 cities
*/

import java.lang.*;

public class TSPTst
{

 public static void main(String args[])
    {
        // 3 args are examplained
        if (args.length >= 3) {
            //create a new TSP object
            TSP tsp = new TSP(args[0],args[1]);
            
            System.out.println();
            tsp.DisplayParams(); //Uncomment to display the contents of the parameter file
            //tsp.DisplayPop(); //Uncomment to display the population before evolution
            
            //call evolve to find the solution to the TSP
            tsp.Evolve(Integer.parseInt(args[2]));
            
            //tsp.DisplayPop(); //Uncomment to display the population after evolution
            System.out.println();
        } else {
            System.out.println("Not enough arguments");
        }
    }
}

