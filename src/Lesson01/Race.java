package Lesson01;

public class Race {

    public static void main(String[] args) {
        Barrier[] raceBarriers = new Barrier[5];
        Athlete[] raceAthletes = new Athlete[3];

        raceBarriers[0] = new Track("Дорожка №1", 20);
        raceBarriers[1] = new Wall("Стена №1", 3);
        raceBarriers[2] = new Track("Дорожка №2", 30);
        raceBarriers[3] = new Wall("Стена №2", 7);
        raceBarriers[4] = new Track("Дорожка №3", 40);

        raceAthletes[0] = new Cat("Барсик",5,25);
        raceAthletes[1] = new Human("Евгений",4,35);
        raceAthletes[2] = new Robot("Verter",3,50);

        for (Barrier barriers : raceBarriers){
            for (Athlete athlete : raceAthletes) {
                if (!athlete.isLoss()){
                    barriers.doAction(athlete);
                    System.out.println("=====================");
                }
            }
        }
    }
}
