package org.geektime.week01;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author Lingjun Jiang
 * @date 2020/10/20
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> helloClass = helloClassLoader.findClass("Hello");
        try {
            Object obj = helloClass.newInstance();
            Method method = helloClass.getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + name + ".xlass");

        try {
            byte[] bytes = resourceAsStream.readAllBytes();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
            return clazz;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
