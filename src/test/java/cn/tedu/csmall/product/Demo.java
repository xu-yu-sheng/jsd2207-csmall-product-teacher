package cn.tedu.csmall.product;

import java.io.IOException;

public class Demo {

    void a1() throws IOException {
        throw new IOException();
    }

    void a2() {
        throw new RuntimeException();
    }

    void b11() {
        try {
            a1();
        } catch (IOException e) {

        }
    }

    void b12() throws IOException {
        a1();
    }

    void b2() {
        a2();
    }

}
