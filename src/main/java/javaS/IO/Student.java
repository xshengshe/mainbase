package javaS.IO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Serializable为标记接口，表示这个类的对象可以被序列化。
 * 
 * @author Evsward
 *
 */
public class Student extends IOBaseS implements Serializable {
    /**
     * 类中的声明
     * 
     * transient和static的变量不会被序列化
     */

    /**
     * 序列号：避免重复序列化
     * 如果serialVersionUID被修改，反序列化会失败。
     * 当程序试图序列化一个对象时，会先检查该对象是否已经被序列化过，只有该对象从未（在本次虚拟机中）被序列化，系统才会将该对象转换成字节序列并输出。
     */
    private static final long serialVersionUID = -6861464712478477441L;
    private long id;
    private String name;
    private int age;
    private transient double height;

    public Student() {
    }

    public Student(long id, String name, int age, double height) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public void write(RandomAccessFile raf) throws IOException {
        raf.writeLong(id);
        raf.writeUTF(name);// 采用UTF的编码方式写入字符串
        raf.writeInt(age);
        raf.writeDouble(height);
    }

    /**
     * 要严格按照写入顺序读取，这也是ORM的意义
     * 
     * @param raf
     * @throws IOException
     */
    public void read(RandomAccessFile raf) throws IOException {
        this.id = raf.readLong();
        this.name = raf.readUTF();
        this.age = raf.readInt();
        this.height = raf.readDouble();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id:");
        sb.append(this.id);
        sb.append(" ");
        sb.append("name:");
        sb.append(this.name);
        sb.append(" ");
        sb.append("age:");
        sb.append(this.age);
        sb.append(" ");
        sb.append("height:");
        sb.append(this.height);
        return sb.toString();
    }

    /**
     * 相当于重写了ObjectOutputStream.writeObject方法，ObjectOutputStream写入该对象的时候会调用该方法
     * 
     * 作用：可以在序列化过程中，采用自定义的方式对数据进行加密
     * 
     * 参考源码：
     * 
     * public final void writeObject(Object obj) throws IOException {
            if (enableOverride) {// 如果发现参数Object有重写该方法，则去执行重写的方法，否则继续执行本地方法。
                writeObjectOverride(obj);
                return;
            }
            try {
                writeObject0(obj, false);
            } catch (IOException ex) {
                if (depth == 0) {
                    writeFatalException(ex);
                }
                throw ex;
            }
        }
     * 
     * 
     * readObject方法的分析同上。
     * 
     * @param out
     * @throws IOException
     */
    private final void writeObject(ObjectOutputStream out) throws IOException {
        logger.info("Start writing data to Object.");
        out.writeLong(this.id);
        /**
         * 下面的writeObject是StringBuffer源码中的：
         * 
         * readObject is called to restore the state of the StringBuffer from a stream.
            private synchronized void writeObject(java.io.ObjectOutputStream s)
                    throws java.io.IOException {
                java.io.ObjectOutputStream.PutField fields = s.putFields();
                fields.put("value", value);
                fields.put("count", count);
                fields.put("shared", false);
                s.writeFields();
            }
         */
        out.writeObject(new StringBuffer(name));
        out.writeInt(this.age);
        out.writeDouble(this.height);// 这里重写以后，就忽略了transient的设置
    }

    private final void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        logger.info("Start reading data to Object.");
        this.id = in.readLong();
        /**
         * 下面的readObject是StringBuffer源码中的：
         * 
         *  readObject is called to restore the state of the StringBuffer from a stream.
            private void readObject(java.io.ObjectInputStream s)
                    throws java.io.IOException, ClassNotFoundException {
                java.io.ObjectInputStream.GetField fields = s.readFields();
                value = (char[])fields.get("value", null);
                count = fields.get("count", 0);
            }
         */
        this.name = ((StringBuffer) in.readObject()).toString();
        this.age = in.readInt();
        this.height = in.readDouble();
    }

}
