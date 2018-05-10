import java.util.*;
import java.lang.*;

/* AI
 *
 * Myanna Harris, Jasmine Jans, and Carol Joplin (jjans@zagmail.gonzaga.edu submitter)
 * 3-10-17
 *
 * Genetic Algorithm
 * Mate class
 */

//class holds the pairing and mating algorithsm for the travelling salesman
//assignment. we implemented Top Down with CX, Top down with PMX, Tournament with Cx
//and Tournmanet with PMX

public class Mate
{
    private    Chromosome MT_father, MT_mother, MT_child1, MT_child2;
    private    int MT_posChild1, MT_posChild2, MT_posLastChild,MT_posFather, MT_posMother,
             MT_numGenes, MT_numChromes;

    public Mate(ArrayList<Chromosome> population, int numGenes, int numChromes)
    {
        MT_numGenes     = numGenes;
        MT_numChromes   = numChromes;
        
        MT_posChild1    = population.size()/2;
        MT_posChild2    = MT_posChild1 + 1;
        MT_posLastChild= population.size() - 1;
        
        for (int i = MT_posLastChild; i >= MT_posChild1; i--)
            population.remove(i);
        
        MT_posFather = 0;
        MT_posMother = 1;
    }
    
    //Simple Top-Down Pairing with CX mating
    public ArrayList<Chromosome> TopDownCX(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            
            boolean done = false;
            char currChar = MT_mother.GetGene(0);
            int currIdx = 0;
            
            // First swap
            MT_child1.SetGene(0,MT_mother.GetGene(0));
            MT_child2.SetGene(0,MT_father.GetGene(0));
            
            // Fill from original parent
            for (int i = 1; i < MT_father.GetNumGenes(); i++) {
                MT_child1.SetGene(i,MT_father.GetGene(i));
                MT_child2.SetGene(i,MT_mother.GetGene(i));
            }
            
            // CX
            while(!done) {
            
            	done = true;
            
            	for (int i = 0; i < MT_child1.GetNumGenes(); i++) {
            	
            		if (currIdx != i && MT_child1.GetGene(i) == currChar) {
            		
            			// Swap
            			MT_child1.SetGene(i,MT_mother.GetGene(i));
            			MT_child2.SetGene(i,MT_father.GetGene(i));
            			
            			currChar = MT_mother.GetGene(i);
            			currIdx = i;
            			
            			done = false;
                        break;
            		}
            	
            	}
            }
                
            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);
            
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }
    
    //Simple Top-Down Pairing with PMX mating
    public ArrayList<Chromosome> TopDownPMX(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            int crossPoint1  = 2;
            int crossPoint2  = 6;
            
            // Fill from original parent
            for (int i = 0; i < MT_father.GetNumGenes(); i++) {
                MT_child1.SetGene(i,MT_father.GetGene(i));
                MT_child2.SetGene(i,MT_mother.GetGene(i));
            }

            // PMX
            MT_child1.SetGene(crossPoint1, MT_mother.GetGene(crossPoint1));
            MT_child2.SetGene(crossPoint1, MT_father.GetGene(crossPoint1));
            
            MT_child1.SetGene(crossPoint2, MT_mother.GetGene(crossPoint2));
            MT_child2.SetGene(crossPoint2, MT_father.GetGene(crossPoint2));
    
            // choose 2 points
            // 8 long
            // points 2, 5
            for (int i = 0; i < MT_child1.GetNumGenes(); i++)
            {
                if (crossPoint1 != i && crossPoint2 != i) {
            
            	if (MT_child1.GetGene(i) == MT_mother.GetGene(crossPoint1)) {
                    if (MT_mother.GetGene(crossPoint2) == MT_father.GetGene(crossPoint1)) {
                        MT_child1.SetGene(i, MT_father.GetGene(crossPoint2));
                    } else {
                        MT_child1.SetGene(i, MT_father.GetGene(crossPoint1));
                    }
            	} else if (MT_child1.GetGene(i) == MT_mother.GetGene(crossPoint2)) {
                    if (MT_mother.GetGene(crossPoint1) == MT_father.GetGene(crossPoint2)) {
                        MT_child1.SetGene(i, MT_father.GetGene(crossPoint1));
                    } else {
                        MT_child1.SetGene(i, MT_father.GetGene(crossPoint2));
                    }
            	}
            	
            	if (MT_child2.GetGene(i) == MT_father.GetGene(crossPoint1)) {
                    if (MT_father.GetGene(crossPoint2) == MT_mother.GetGene(crossPoint1)) {
                        MT_child2.SetGene(i, MT_mother.GetGene(crossPoint2));
                    } else {
                        MT_child2.SetGene(i, MT_mother.GetGene(crossPoint1));
                    }
            	} else if (MT_child2.GetGene(i) == MT_father.GetGene(crossPoint2)) {
                    if (MT_father.GetGene(crossPoint1) == MT_mother.GetGene(crossPoint2)) {
                        MT_child2.SetGene(i, MT_mother.GetGene(crossPoint1));
                    } else {
                        MT_child2.SetGene(i, MT_mother.GetGene(crossPoint2));
                    }
            	}
                }
            }
                
            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);
            
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }

    //Tournament Pairing with CX mating
    public ArrayList<Chromosome> TournamentCX(ArrayList<Chromosome> population, int numPairs)
    {
        Random rnum     = new Random();
        ArrayList<Chromosome> randomPop = new ArrayList<Chromosome>();
        ArrayList<Integer> randomPopIdx = new ArrayList<Integer>();
        
        for (int x = 0; x < 2; x++) {
            randomPop.clear();
            for (int k = 0; k < 8; k ++) {
                int indx = rnum.nextInt(MT_numGenes);
                randomPop.add(population.get(indx));
                randomPopIdx.add(indx);
            }
            
            int least = 0;
            
            for (int k = 1; k < 8; k ++) {
                if (randomPop.get(k).GetCost() < randomPop.get(least).GetCost()) {
                    least = k;
                }
            }
            
            if (x == 0) {
                MT_posFather = randomPopIdx.get(least);
            } else {
                MT_posMother = randomPopIdx.get(least);
            }
        }
        
        for (int j = 0; j < numPairs; j++)
        {   
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
    
            boolean done = false;
            char currChar = MT_mother.GetGene(0);
            int currIdx = 0;
            
            // First swap
            MT_child1.SetGene(0,MT_mother.GetGene(0));
            MT_child2.SetGene(0,MT_father.GetGene(0));
            
            // Fill from original parent
            for (int i = 1; i < MT_father.GetNumGenes(); i++) {
                MT_child1.SetGene(i,MT_father.GetGene(i));
                MT_child2.SetGene(i,MT_mother.GetGene(i));
            }
            
            // CX
            while(!done) {
                
                done = true;
                
                for (int i = 0; i < MT_child1.GetNumGenes(); i++) {
                    
                    if (currIdx != i && MT_child1.GetGene(i) == currChar) {
                        
                        // Swap
                        MT_child1.SetGene(i,MT_mother.GetGene(i));
                        MT_child2.SetGene(i,MT_father.GetGene(i));
                        
                        currChar = MT_mother.GetGene(i);
                        currIdx = i;
                        
                        done = false;
                        break;
                    }
                    
                }
            }

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);

            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            for (int x = 0; x < 2; x++) {
                randomPop.clear();
                for (int k = 0; k < 8; k ++) {
                    int indx = rnum.nextInt(MT_numGenes);
                    randomPop.add(population.get(indx));
                    randomPopIdx.add(indx);
                }

                int least = 0;

                for (int k = 1; k < 8; k ++) {
                    if (randomPop.get(k).GetCost() < randomPop.get(least).GetCost()) {
                        least = k;
                    }
                }

                if (x == 0) {
                    MT_posFather = randomPopIdx.get(least);
                } else {
                    MT_posMother = randomPopIdx.get(least);
                }
            }
        }
        return population;
    }
    
    //Tournament Pairing with PMX mating
    public ArrayList<Chromosome> TournamentPMX(ArrayList<Chromosome> population, int numPairs)
    {
        Random rnum     = new Random();
        ArrayList<Chromosome> randomPop = new ArrayList<Chromosome>();
        ArrayList<Integer> randomPopIdx = new ArrayList<Integer>();
        
        for (int x = 0; x < 2; x++) {
            randomPop.clear();
            for (int k = 0; k < 8; k ++) {
                int indx = rnum.nextInt(MT_numGenes);
                randomPop.add(population.get(indx));
                randomPopIdx.add(indx);
            }
            
            int least = 0;
            
            for (int k = 1; k < 8; k ++) {
                if (randomPop.get(k).GetCost() < randomPop.get(least).GetCost()) {
                    least = k;
                }
            }
            
            if (x == 0) {
                MT_posFather = randomPopIdx.get(least);
            } else {
                MT_posMother = randomPopIdx.get(least);
            }
        }
        
        for (int j = 0; j < numPairs; j++)
        {
            
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            int crossPoint1  = 2;
            int crossPoint2  = 6;

            // Fill from original parent
            for (int i = 0; i < MT_father.GetNumGenes(); i++) {
                MT_child1.SetGene(i,MT_father.GetGene(i));
                MT_child2.SetGene(i,MT_mother.GetGene(i));
            }
            
            // PMX
            MT_child1.SetGene(crossPoint1, MT_mother.GetGene(crossPoint1));
            MT_child2.SetGene(crossPoint1, MT_father.GetGene(crossPoint1));
            
            MT_child1.SetGene(crossPoint2, MT_mother.GetGene(crossPoint2));
            MT_child2.SetGene(crossPoint2, MT_father.GetGene(crossPoint2));
            
            // choose 2 points
            // 8 long
            // points 2, 5
            for (int i = 0; i < MT_child1.GetNumGenes(); i++)
            {
                if (crossPoint1 != i && crossPoint2 != i) {
                    
                    if (MT_child1.GetGene(i) == MT_mother.GetGene(crossPoint1)) {
                        if (MT_mother.GetGene(crossPoint2) == MT_father.GetGene(crossPoint1)) {
                            MT_child1.SetGene(i, MT_father.GetGene(crossPoint2));
                        } else {
                            MT_child1.SetGene(i, MT_father.GetGene(crossPoint1));
                        }
                    } else if (MT_child1.GetGene(i) == MT_mother.GetGene(crossPoint2)) {
                        if (MT_mother.GetGene(crossPoint1) == MT_father.GetGene(crossPoint2)) {
                            MT_child1.SetGene(i, MT_father.GetGene(crossPoint1));
                        } else {
                            MT_child1.SetGene(i, MT_father.GetGene(crossPoint2));
                        }
                    }
                    
                    if (MT_child2.GetGene(i) == MT_father.GetGene(crossPoint1)) {
                        if (MT_father.GetGene(crossPoint2) == MT_mother.GetGene(crossPoint1)) {
                            MT_child2.SetGene(i, MT_mother.GetGene(crossPoint2));
                        } else {
                            MT_child2.SetGene(i, MT_mother.GetGene(crossPoint1));
                        }
                    } else if (MT_child2.GetGene(i) == MT_father.GetGene(crossPoint2)) {
                        if (MT_father.GetGene(crossPoint1) == MT_mother.GetGene(crossPoint2)) {
                            MT_child2.SetGene(i, MT_mother.GetGene(crossPoint1));
                        } else {
                            MT_child2.SetGene(i, MT_mother.GetGene(crossPoint2));
                        }
                    }
                }
            }

            population.add(MT_posChild1,MT_child1);
            population.add(MT_posChild2,MT_child2);

            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            for (int x = 0; x < 2; x++) {
                randomPop.clear();
                for (int k = 0; k < 8; k ++) {
                    int indx = rnum.nextInt(MT_numGenes);
                    randomPop.add(population.get(indx));
                    randomPopIdx.add(indx);
                }

                int least = 0;

                for (int k = 1; k < 8; k ++) {
                    if (randomPop.get(k).GetCost() < randomPop.get(least).GetCost()) {
                        least = k;
                    }
                }

                if (x == 0) {
                    MT_posFather = randomPopIdx.get(least);
                } else {
                    MT_posMother = randomPopIdx.get(least);
                }
            }
        }
        return population;
    }
}
