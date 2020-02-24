package Lesson01;

// Интерфейс описывающий поведение характерное для всех спортсменов
public interface Athlete {
    int toRun(); // метод бега
    int toJump(); // метод прыжка
    String getName(); // метод возвращающий имя спортсмена
    boolean isLoss (); // метод возвращает статус спортсмена (false выбыл из соревнования)
    void setIsLoss(); // устанавливает статус спортсмена
}
