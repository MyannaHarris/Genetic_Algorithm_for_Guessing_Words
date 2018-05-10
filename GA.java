import java.lang.*;
import java.util.*;

/* AI
 *
 * Myanna Harris, Jasmine Jans, and Carol Joplin (jjans@zagmail.gonzaga.edu submitter)
 * 3-10-17
 *
 * Genetic Algorithm
 * GA class
 */

//for changes, see methods marked with "edited" comments
public abstract class GA extends Object
{
 protected int     GA_numChromesInit;
 protected int     GA_numChromes;
 protected int     GA_numGenes;
 protected double  GA_mutFact;
 protected int     GA_numIterations;
 protected ArrayList<Chromosome> GA_pop;
 protected String GA_target;

 public GA(String ParamFile, String target)
    {
        GetParams GP        = new GetParams(ParamFile);
        Parameters P        = GP.GetParameters();
        GA_numChromesInit   = P.GetNumChromesI();
        GA_numChromes       = P.GetNumChromes();
        GA_numGenes         = P.GetNumGenes();
        GA_mutFact          = P.GetMutFact();
        GA_numIterations    = P.GetNumIterations();
        GA_pop              = new ArrayList<Chromosome>();
        GA_target           = target;
        }

 public void DisplayParams()
    {
        System.out.print("Initial Chromosomes:  ");
        System.out.println(GA_numChromesInit);
        System.out.print("Chromosomes: ");
        System.out.println(GA_numChromes);
        System.out.print("Genes: ");
        System.out.println(GA_numGenes);
        System.out.print("Mutation Factor: ");
        System.out.println(GA_mutFact);
        System.out.print("Iterations: ");
        System.out.println(GA_numIterations);
    }

 public void DisplayPop()
    {
        Iterator<Chromosome> itr = GA_pop.iterator();
        System.out.println("Number\tContents\t\tCost");
        
        int chromeNum = 0;
        while (itr.hasNext())
        {
            Chromosome chrome = itr.next();
            System.out.print(chromeNum);
            ++chromeNum;
            System.out.print("\t");
            DisplayChromosome(chrome);
            System.out.println();
        }
    }

 public void DisplayBest(int iterationCt)
    {
        Chromosome chrome = GA_pop.get(0);
        System.out.print("Iteration: ");
        System.out.print(iterationCt);
        System.out.print("\t");
        DisplayChromosome(chrome);
        System.out.println();
    }

 private void DisplayChromosome(Chromosome chrome)
        {
            chrome.DisplayGenes();
            System.out.print("\t\t\t");
            System.out.print(chrome.GetCost());
        }

 protected void SortPop()
    {
        Collections.sort(GA_pop, new CostComparator());
    }

 private class CostComparator implements Comparator <Chromosome>
    {
        int result;
        public int compare(Chromosome obj1, Chromosome obj2)
        {
            result = new Integer( obj1.GetCost() ).compareTo(
            new Integer( obj2.GetCost() ) );
            return result;
        }
    }

 protected void TidyUp()
    {
        int end = GA_numChromesInit - 1;
        while (GA_pop.size() > GA_numChromes)
        {
            GA_pop.remove(end);
            end--;
        }
    }

    //edited: mutate function picks swaps two vals in each gene
 protected void Mutate() 
    {
        int totalGenes  = (GA_numGenes * GA_numChromes);
        int numMutate   = (int) (totalGenes * GA_mutFact);
        Random rnum     = new Random();
        
        for (int i = 0; i < numMutate; i++)
        {
            //position of chromosome to mutate--but not the first one
            //the number generated is in the range: [1..GA_numChromes)
            
            int chromMut = 1 + (rnum.nextInt(GA_numChromes - 1));
            
            int geneMut1 = rnum.nextInt(GA_numGenes); //pos of mutated gene
            int geneMut2 = rnum.nextInt(GA_numGenes); //pos of mutated gene
            
            Chromosome newChromosome = GA_pop.remove(chromMut); //get chromosome
            
            char gene1 = newChromosome.GetGene(geneMut1);
            char gene2 = newChromosome.GetGene(geneMut2);
            
            newChromosome.SetGene(geneMut1,gene2);//mutate it
            newChromosome.SetGene(geneMut2,gene1);//mutate it
            
            GA_pop.add(newChromosome); //add mutated chromosome at the end
        }
        
    }
 
    //edited: creates the initial population based on the given circuit data
 protected void InitPop()
    {
    
        Random rnum = new Random();
        char letter;
        char[] options = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(0);
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        nums.add(6);
        nums.add(7);
        
        for (int index = 0; index < GA_numChromesInit; index++)
        {
            Chromosome Chrom = new Chromosome(GA_numGenes);
            
            Collections.shuffle(nums);
            
            for (int j = 0; j < GA_numGenes; j++)
                { 
                    letter = options[nums.get(j)]; //based on num arraylist and options array
                    Chrom.SetGene(j,letter);
                }
            
            Chrom.SetCost(0);
            GA_pop.add(Chrom);
        }
    }
 protected abstract void ComputeCost();

 //edited: runs the specific mating and pairing algorithms
 protected void Evolve(int type)
    {
        int iterationCt = 0;
        Pair pairs      = new Pair(GA_pop);
        int numPairs    = pairs.SimplePair();
        boolean found   = false;

        while (iterationCt < GA_numIterations)
            {
                Mate mate = new Mate(GA_pop,GA_numGenes,GA_numChromes);
                
                if(type == 0){
                    GA_pop = mate.TopDownCX(GA_pop,numPairs);
                }
                else if(type == 1){
                    GA_pop = mate.TopDownPMX(GA_pop,numPairs);
                }
                else if(type == 2){
                    GA_pop = mate.TournamentCX(GA_pop,numPairs);
                }
                else{
                    GA_pop = mate.TournamentPMX(GA_pop,numPairs);
                }
                
                Mutate();
                
                ComputeCost();
                
                SortPop();
                
                Chromosome chrome = GA_pop.get(0); //get the best guess
                
                DisplayBest(iterationCt); //print it
                
                if (chrome.Equals(GA_target)) //if it's equal to the target, stop
                    break;
                ++iterationCt;
            }
    }

}

