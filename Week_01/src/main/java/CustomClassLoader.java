import org.apache.commons.io.FileUtils;
import java.io.*;
import java.lang.reflect.Method;

/*
The purpose of this class is to implement custom methods to:
1: read in file
2: convert byte
3: instantiate obj

reference: https://blog.csdn.net/qq_20641565/article/details/78744677
 */
public class CustomClassLoader extends ClassLoader{
    public static void main(String[] args) throws ClassNotFoundException{
        CustomClassLoader cl = new CustomClassLoader();
        Class<?> hello = cl.findClass("Hello");
        try {
            Object o = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(o);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        //1: read in the file
        File file = new File("src/main/resources/Hello.xlass");
        byte[] bytes = new byte[0];
        try {
            bytes = readInFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2: convert each byte to be value = 255 - oldValue
        byte[] converted  = convert(bytes);
        //3: get class
        Class<?> c = getClass(className, converted);
        return c;
    }

    private Class<?> getClass(String className, byte[] converted) {
        // invoke ClassLoader.defineClass
        Class<?> c = defineClass(className, converted, 0, converted.length);
        resolveClass(c);
        return c;
    }

    private byte[] convert(byte[] bytes) {
        byte[] res = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            res[i] = (byte) (255 - bytes[i]);
        }
        return res;
    }

    private byte[] readInFile(File file) throws IOException {
        byte[] filedata =  FileUtils.readFileToByteArray(file);
        return filedata;
    }
}
