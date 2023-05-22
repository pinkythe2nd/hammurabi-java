package com.company;

import java.util.Scanner;

public class Game {
    private boolean gameOver;
    private int year, starved, people, acres, stores, ratFood, price,
            acresPlanted, immigrants, howManyBushels, bushels, totalStarved, plagueChance;
    private final Scanner scanner;

    public Game() {
        this.gameOver = false;
        this.year = 1;
        this.people = 50;
        this.acres = 1000;
        this.stores = 1000;
        this.acresPlanted = 0;
        this.price = 0;
        this.scanner = new Scanner(System.in);
    }

    private int randomNum(int Max, int Min) {
        return Min + (int) (Math.random() * ((Max + 1 - Min) + 1));
    }

    private void reportOutcome() {
        System.out.println("Hamurabi: I beg to report to you, in year " + year + " " + starved +
                " people starved, and " + immigrants + " came to this city");
    }

    private void reportPlague() {
        people = people / 2;
        System.out.println("A horrible plague struck! Half the population died!");
        plagueChance = 0;
    }

    private void reportPopulation() {
        System.out.println("The population is now " + people);
    }

    private void reportHarvest() {
        System.out.println("The city now owns " + acres + " acres of farmland." + "\nyou harvested " + bushels +
                " bushels. \nRats ate " + ratFood + " bushels.\nyou now have " + stores +
                " bushels in store.");
    }

    private int input() {
        int userInput = 0;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                if ((userInput = scanner.nextInt()) >= 0) {
                    break;
                }
            }
            System.out.println("Hammurabi: I cannot do what you wish. Get yourself another steward!!!");
            System.exit(0);
        }
        return userInput;
    }

    private void tradeLand() {
        this.price = randomNum(26, 17);
        System.out.println("Land is trading at " + price + " bushels per acre. How many acres do you wish to buy?");
        int userInput;
        while (true) {
            if (((userInput = input()) * price) <= stores) {
                break;
            }
            System.out.println("Hammurabi: Think again. You have only " + stores + " bushels of grain, now then,");
        }
        acres += userInput;
    }

    private void feedThePeople() {
        System.out.println("How many bushels do you wish to feed to your people?");
        while (true) {
            if ((howManyBushels = input()) <= stores) {
                break;
            }
            System.out.println("Hammurabi: Think again. You have only " + stores + " bushels of grain, now then,");
        }
        stores -= howManyBushels;
    }

    private void plantCrops() {
        System.out.println("How many acres do you wish to plant with seed? it costs 2 bushels per acre.");
        int userInput;
        while (true) {
            if ((userInput = input()) > acres) {
                System.out.println("Hammurabi: Think again. You own only " + acres + " acres of land, now then,");
            } else if (userInput * 2 > stores) {
                System.out.println("Hammurabi: Think again. You have only " + stores + " bushels of grain, now then");
            } else if (userInput / 10 > people) {
                System.out.println("Hammurabi: Think again. you have only " + people + " people to tend the fields!\n" +
                        "now then,");
            } else {
                acresPlanted = userInput;
                stores -= acresPlanted;
                break;
            }
        }
    }

    private void harvestCrops() {
        if (randomNum(0, 100) <= 50) {
            ratFood = (stores / 10) * 5;
            stores -= ratFood;
        }
        bushels = acresPlanted * randomNum(6, 1);
        stores += bushels;
    }

    private void populationChange() {
        immigrants = randomNum(6, 1) * (20 * acres + stores) / people / 100 + 1;
        starved = ((20 * people) - howManyBushels) / 20;
    }

    private void evaluateTenYearPerformance() {
        System.out.println(totalStarved);
        int landPerPerson = Math.round((float) acres / people);
        int percentStarved = (totalStarved / 10);
        System.out.println("In your ten year term of office, " + percentStarved +
                " percent of the population starved per year on average, and a total of " +
                totalStarved + " people died!\nYou started with 10 acres per person and ended with " +
                landPerPerson + " acres per person\n");
        if (percentStarved > 33 || landPerPerson < 7) {
            System.out.println("Due to this extreme mismanagement you have not only\n" +
                    "been impeached and thrown out of office but you have\n" +
                    "also been declared national fink!!!");
        } else if (percentStarved > 10 || landPerPerson < 9) {
            System.out.println("Your heavy handed performance smacks of Nero and Ivan IV\n" +
                    "The people (remaining) find you an unpleasant ruler, and\n" +
                    "frankly hate your guts!!");
        } else if (percentStarved > 3 || landPerPerson < 10) {
            int integer = randomNum((people / 10) * 8, 1);
            System.out.println("Your performance could have been somewhat better, but really wasn’t too bad at all.\n"
                    + integer + " people would dearly like to see you assassinated but we all have our trivial problems.");
        } else {
            System.out.println("A fantastic performance!! Charlemagne, Disraeli, and\n" +
                    "Jefferson could not have done better!”\n");
        }
        gameOver = true;
    }


    private void evaluatePerformance() {
        if (randomNum(100, 1) < plagueChance) {
            reportPlague();
        } else if (people <= 0) {
            System.out.println("you have failed your citizens\nall citizens have starved under your disastrous " +
                    "leadership");
            gameOver = true;
        } else if (((float) starved / people * 100) >= 45) {
            System.out.println("You starved " + starved + " people in one year!!\nDue to this extreme mismanagement you" +
                    " have not only been impeached and thrown out of office but you have also been declared" +
                    " national fink!!!");
            gameOver = true;
        } else if (year == 10) {
            evaluateTenYearPerformance();
        }

        totalStarved += ((float) starved / people) * 100;
        people -= starved;
        people += immigrants;
        year += 1;
        plagueChance += 15;
    }

    public void gameLoop() {
        while (!gameOver) {
            reportPopulation();
            reportHarvest();
            tradeLand();
            feedThePeople();
            plantCrops();
            harvestCrops();
            populationChange();

            evaluatePerformance();

            if (!gameOver) {
                reportOutcome();
            }
        }
    }
}

