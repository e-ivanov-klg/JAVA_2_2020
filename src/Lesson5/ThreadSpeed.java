package Lesson5;

public class ThreadSpeed {
    static final int size = 10000000;
    static final int h = size/2;
    static  float[] arr = new float[size];

    private static float[] fillArray (float[] array) {
        for (int i = 0; i < array.length; i++){
            array[i] = 1;
        }
        return array;
    }

    private static void calculateArray (float[] array){
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        arr = fillArray(arr);
        long start = System.currentTimeMillis();
        System.out.println("Single thread Start: " + start);
        calculateArray(arr);
        long finishTime = System.currentTimeMillis() - start;
        System.out.println("Single thread Time: " + finishTime);
        start = System.currentTimeMillis();
        System.out.println("Multi thread Start: " + start);
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        System.arraycopy(arr,0,arr1,0,h);
        System.arraycopy(arr, h, arr2,0,h);
        Thread thread1 = new Thread(()-> calculateArray(arr1));
        Thread thread2 = new Thread(()-> calculateArray(arr2));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(arr1,0, arr,0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        finishTime = System.currentTimeMillis() - start;
        System.out.println("Multi thread Time: " + finishTime);


    }
}
