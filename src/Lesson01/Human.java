package Lesson01;
// Клас описывающий спортсменов Любдей
public class Human implements Athlete  {
    private String name;
    private int maxJump;
    private int maxRun;
    private boolean lossGame = false;

    public Human() {
        this.name = "Unknown";
        this.maxJump = 0;
        this.maxRun = 0;
    }

    public Human (String name, int maxJump, int maxRun){
        this.name = name;
        this.maxJump = maxJump;
        this.maxRun = maxRun;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLossGame (){
        lossGame = true;
    }

    @Override
    public boolean isLoss () {
        return lossGame;
    }

    @Override
    public int toJump () {
        System.out.println("Человек прыгнул: " + this.maxJump);
        return maxJump;
    }

    @Override
    public int toRun () {
        System.out.println("Человек пробежал: " + this.maxRun);
        return maxRun;
    }

    @Override
    public void setIsLoss (){
        lossGame = true;
    }
}
