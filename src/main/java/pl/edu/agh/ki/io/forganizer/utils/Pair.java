package pl.edu.agh.ki.io.forganizer.utils;

//No kurwa iksde xD nie wiedzialem jak inaczej 2 wartosci zwrocic
public class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }
}
