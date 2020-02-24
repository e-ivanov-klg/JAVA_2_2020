package Lesson01;
//класс описывающий препятсие типа Стна
public class Wall implements Barrier{
    private String name;
    private int hight;

    public Wall (String name, int hight) {
        this.name = name;
        this.hight = hight;
    }

    @Override
    public boolean doAction (Athlete jumper) {
        System.out.println("Препятствие: " + this.name);
        System.out.println("Высотой " + this.hight);
        int runLength = jumper.toJump();
        if (runLength >= this.hight) {
            System.out.println(jumper.getName() + " преодолел препятствие !");
            return true;
        } else {
            System.out.println(jumper.getName() + " не прошел препятсвие !");
            System.out.println("И выбывает из соревнования !!!");
            jumper.setIsLoss();
            return false;
        }
    }
}
