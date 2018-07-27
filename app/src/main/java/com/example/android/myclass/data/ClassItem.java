package com.example.android.myclass.data;

public class ClassItem {

    public int id;
    public String className;

    public ClassItem(int id, String classNamme) {
        this.id = id;
        this.className = classNamme;
    }

    @Override
    public String toString() {
        return className;
    }
}
