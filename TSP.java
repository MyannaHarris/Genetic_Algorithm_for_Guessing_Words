import java.util.*;
import java.lang.*;
import java.io.File;

/* AI
 *
 * Myanna Harris, Jasmine Jans, and Carol Joplin (jjans@zagmail.gonzaga.edu submitter)
 * 3-10-17
 *
 * Genetic Algorithm
 * TSP class
 */

//TSP class that represents the traveling salesman problem

public class TSP extends GA
{
 private int TSP_target;
 private ArrayList<Integer> matrix;

    //constructor takes in a fille name and target
 public TSP(String fileName, String target)
    {
        super(fileName,target);
        TSP_target = 0;
        //8 cities in the problem
        GA_numGenes = 8;
            if (8 != GA_numGenes)
            {
                System.out.println("Error: Target size differs from number of genes");
                DisplayParams();
                System.exit(1);
            }
        
        //read the file that has the costs between the cities in it
        File newFile = new File("TableOfCosts.txt");
        matrix = new ArrayList<>();
        
        try{
            Scanner in = new Scanner(newFile);
            
            while(in.hasNextLine()){
                int i = in.nextInt();
                matrix.add(i);
            }
            
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //create the initial population
        InitPop();
    }

//calls methods from GA
 public void InitPop()
    {
        super.InitPop();
        ComputeCost();
        SortPop();
        TidyUp();
    }

 public void DisplayParams()
    {
        System.out.print("Target: ");
        System.out.println(TSP_target);
        super.DisplayParams();
    }

    //computes the cost of a circuit by computing the distance between adjacent cities
 protected void ComputeCost()
    {
        HashMap indices = new HashMap<Character, Integer>();
        indices.put('a',0);
        indices.put('b',1);
        indices.put('c',2);
        indices.put('d',3);
        indices.put('e',4);
        indices.put('f',5);
        indices.put('g',6);
        indices.put('h',7);
        
        //go through the population
        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            int before = (int)indices.get((Character)chrom.GetGene(0));
            //go through each circuit and calculate the cost between each adjacent city
            for (int j = 1; j < GA_numGenes; j++){
                int next = (int)indices.get((Character)chrom.GetGene(j));
                cost += matrix.get(before * 8 + next);
                before = next;
            }
            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
    }
 //in earlier versions (as on ada) Evolve() from GA is here
 }

