package com.company;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        /*
        Constant parameters

        Set them small for easier debugging; change to current model parameters later
        Rates a "per-year"
         */

        //Population parameter; Each language is 50/50
        //10 is just a small easier number to debug with. Could just put like, 10,000, 100,000, etc
        int totalPopulationParameter = 150;
        Integer languageAPopulation = totalPopulationParameter/2;
        Integer languageBPopulation = totalPopulationParameter/2;

        //Death rate parameter (for both)
        //Percentage/100=decimal value for calculations
        //ToDo: Put constants into variables for easier parameter adjustment; Make variables be just percentage values.
        Double deathRate = 0.8678/100.0;

        //Immigration rate; ToDo: Add Sine constant?
        Double immigrationRate = 5.0/100.0;

        //Emmigration rate;
        Double emmigrationRateA = 2.0/100.0;
        Double emmigrationRateB = 0.5/100.0;


        //Generation amount
        int maximumGenerationLimit = 5;

        //Generation counter
        int generationCounter = 1;

        //Birth rate (for both)
        Double birthRate = 1.2/100;

        //Switching probabilities
        Double langAtoB = 0.45;
        Double langBtoA = 0.55; //A is slightly more advantageous language

        //Year counter
        int yearCounter = 0;

        //Population trackers; Every number recorded corresponds to "one year" or a loop of the while-loop
        //Will have 5 generations * 20 year per generation = 100 recordings, for example.
        ArrayList<Integer> languageAByTheYear = new ArrayList<>();
        ArrayList<Integer> languageBByTheYear = new ArrayList<>();


        //---------------------------------Simulation starts here-------------------------------------------------------
        //Troubles: Population adjustments then language adoption calculations, or the other way around?
        while(generationCounter <= maximumGenerationLimit){
            yearCounter++;

            //Adjust the population; Simple addition and subtraction from given rates and population total
            languageAPopulation = populationAdjust(birthRate,deathRate,immigrationRate,emmigrationRateA,languageAPopulation);
            languageBPopulation = populationAdjust(birthRate,deathRate,immigrationRate,emmigrationRateB,languageBPopulation);

            //ToDo: Language adjustments (correspond to the population)

            //ToDo: A way to store information, and what information?
            // Idea; Store everything sequentially and transport it to excel ¯\_(ツ)_/¯

            //Track our new population of each language after adjusting for language conversion and pop. adjustments
            languageAByTheYear.add(languageAPopulation);
            languageBByTheYear.add(languageBPopulation);

            //Generation counter; The time lapse of our simulation
            if (yearCounter%20 == 0) {// If 20 years have passed, we've gone up a generation
               // System.out.println("A generation has passed!");
                generationCounter++;

                //Simple formatting if-statement; Can ignore; If set parameter to 5 generations, will prevent
                //   from printing "Current generation: 6"
                if (generationCounter<=maximumGenerationLimit) {
                    //System.out.println("Current generation: " + generationCounter);
                }
            }



        }



    }

    //Adjusts the population for a given language population
    private static Integer populationAdjust(Double birthRate, Double deathRate, Double immigrationRate, Double emmigrationRate,Integer languagePop) {
        //Temporary variable
        Integer newPopulation = Integer.valueOf(languagePop);

        //Population +/- from births and deaths
        int totalDead = calculateDead(deathRate, languagePop);
        int totalBorn = calculateBirth(birthRate,languagePop);

        //Population +/- from immigration/emigration
        //OK I used calculateDead() for both but it's really because after seeing that calculateDead and calculateBirth
        // are really the same function just with different parameters, then just repeat same operations for both
        // totalImmigrated and totalEmigrated. Their addition/subtraction is handled in the next newPopulation assignment...
        int totalImmigrated =  calculateDead(immigrationRate,languagePop);
        int totalEmigrated = calculateBirth(emmigrationRate, languagePop);

        //Calculate net population gain
        //...here v v v
        newPopulation = newPopulation - totalDead - totalEmigrated + totalBorn + totalImmigrated;

        return newPopulation;
    }

    //Calculate the amount dead for a given population and death rate
    private static int calculateDead(Double deathRate, Integer languagePop) {

        //Calculate net loss; Magnitude
        double amountDied = languagePop * deathRate;
        //Convert to integer
        double ceilDied = Math.ceil(amountDied);
        int deadRoundedUp = (int) ceilDied;
        return deadRoundedUp;
    }


    //Calculate the amount born for a given population and birth rate
    //Note to self: OK this does the exact same thing as calculateDeath
    private static int calculateBirth(Double birthRate, Integer languagePop) {

        //Calculate net gain; Magnitude
        double amountBorn = languagePop * birthRate;
        //Convert to integer
        double ceilBorn = Math.ceil(amountBorn);
        int bornRoundedUp = (int) ceilBorn;
        return bornRoundedUp;
    }

}
