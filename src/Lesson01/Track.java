package Lesson01;
// класс осписывабщий препятствия типа беговой дорожки
public class Track implements Barrier {
    private String name;
    private int length;

    public Track (String name, int length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public boolean doAction (Athlete runner) {
        System.out.println("Препятствие: " + this.name);
        System.out.println("Длинной " + this.length);
        int runLength = runner.toRun();
        if (runLength >= this.length) {
            System.out.println(runner.getName() + " преодолел препятствие!");
            return true;
        } else {
            System.out.println(runner.getName() + " не прошел препятствие!");
            System.out.println("И выбывает из соревнования !!!");
            runner.setIsLoss();
            return false;
        }
    }
}
