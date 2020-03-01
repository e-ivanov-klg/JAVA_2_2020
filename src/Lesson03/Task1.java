package Lesson03;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        String[] carModel = {"Audi", "BMW", "Lexus", "Toyota", "Nissan",
                            "Honda", "Mitsubishi", "Infinity", "Dodj", "Jaguar",
                            "Lexus", "Hyundai", "KIA", "Toyota", "Lincoln",
                            "Infinity", "Ford", "LADA", "VolksWagen", "Fiat"
                            };
        Map<String, Integer> carModelList = new HashMap<>();
        int prevSize;
        for (String models : carModel) {

            prevSize = carModelList.size();
            carModelList.put(models,1);
            if (prevSize == carModelList.size()) {
                carModelList.replace(models, carModelList.get(models) + 1);
            }
        }
        System.out.println(carModelList);
    }

}
