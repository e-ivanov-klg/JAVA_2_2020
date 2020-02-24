package Lesson01;
// класс описывающий спортсменов роботов
public class Robot implements Athlete{
    private String name;
    private int maxJump;
    private int maxRun;
    private boolean lossGame = false;

    public Robot() {
        this.name = "Unknown";
        this.maxJump = 0;
        this.maxRun = 0;
    }

    public Robot (String name, int maxJump, int maxRun){
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
        System.out.println("Робот прыгнул: " + this.maxJump);
        return maxJump;
    }
    @Override
    public int toRun () {
        System.out.println("Робот пробежал: " + this.maxRun);
        return maxRun;
    }

    @Override
    public void setIsLoss (){
        lossGame = true;
    }

}
