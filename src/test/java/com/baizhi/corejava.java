package com.baizhi;

import org.junit.Test;

public class corejava {
    @Test
    public void Test1() {
      /*  long a = 21312412412L;
        Long b = 1241241241L;
        double c = 184564231.2143733563461254D;
        float d  = 156746615.4342F;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);*/
       /* char aa = 65;
        char bb ='A';
        int cc = bb;
        char dd = 77;
        System.out.println(aa);
        System.out.println(bb);
        System.out.println(cc);
        System.out.println(dd);*/
        //冒泡法
       /*int[] a = {2,5,3,1,7,8,0,9};
        for (int i = 1; i <a.length-1; i++) {
            for (int j = 0; j < a.length-i; j++) {
                if (a[j]>a[j+1]){
                    int c =a[j];
                    a[j] = a[j+1];
                    a[j+1] = c;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }*/
        /*int[] a = {2,5,3,1,7,8,0,9};
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
        int [] b;
        b = new int [3];*/
        int[] a = {2, 5, 3, 1, 7, 8, 0, 9};
        for (int i = 0; i < a.length; i++) {

        }
    }

    @Test
    public void test2() {
       /* Animal animal = new Dog();
        animal.eat();
        ((Dog) animal).shout();*/
        /*Dog dog = new Dog();
        dog.eat();
        */
        Animal animal = new Animal();
        animal.eat();
        Dog dog = new Dog();
        boolean b = dog instanceof Animal;
        boolean b1 = animal instanceof Dog;
        System.out.println(b);
        System.out.println(b1);
    }
}
