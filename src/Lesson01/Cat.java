package Lesson01;
// класс описывабщий спортсменов типа "Кот"
public class Cat implements  Athlete{
    private String name; // имя кота
    private int maxJump; // максимальная высота прыжка
    private int maxRun; // максимально пробегаемое расстояние
    private boolean lossGame = false; // статус проигрыша (выбывания из соревнования)

    public Cat() {
        this.name = "Unknown";
        this.maxJump = 0;
        this.maxRun = 0;
    }

    public Cat (String name, int maxJump, int maxRun){
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
    public int toRun() {
        System.out.println("Кот пробежал: " + this.maxRun);
        return maxRun;
    }

    @Override
    public int toJump() {
        System.out.println("Кот перепрыгнул: " + this.maxJump);
        return maxJump;
    }

    @Override
    public void setIsLoss (){
        lossGame = true;
    }
}
