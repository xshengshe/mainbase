package javaS.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

/**
 * 字符流的学习
 * 
 * 基于字符I/O操作的基类: Reader和Writer
 * 
 * 对应的缓存类：BufferedReader和BufferedWriter
 * 
 * @author Evsward
 *
 */
public class CharacterStreamS extends IOBaseS {
    @Test
    /**
     * OutputStreamWriter,字节到字符的转化桥梁，转化过程中需指定编码字符集，否则采用默认字符集。
     */
    public void testWriter() throws IOException {
        // 文件输出流不变
        FileOutputStream fos = new FileOutputStream(root + "HongXing.txt");
        /**
         * 输出流写入类（这是比起字节流多出来的类）专门用来写入字符流，注意字符编码的参数
         * 
         * 如果只保留fos一个参数，编码默认为工作区默认编码，这里是“UTF-8"，
         * 
         * 字节编码为字符 -> 请转到 http://www.cnblogs.com/Evsward/p/huffman.html#ascii编码
         * 
         * 为了保证写入和读取的编码统一，请每次都要指定编码
         * 
         * 输出体系中提供的两个转换流，用于实现将字节流转换成字符流。
         */
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        // 缓存写入类，对应BufferedOutputStream
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("感时花溅泪，恨别鸟惊心");
        bw.close();
        osw.close();
        fos.close();

        /**
         * 终版：将close部分缩短
         */
        BufferedWriter bwA = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(root + "HongXing.txt", true), "UTF-8"));// 注意层级，加入指定编码的参数
        bwA.write("\n烽火连三月，家书抵万金");
        bwA.close();
    }

    @Test
    /**
     * InputStreamReader,字节到字符的转化桥梁，转化过程中需指定编码字符集，否则采用默认字符集。
     */
    public void testReader() throws IOException {
        FileInputStream fis = new FileInputStream(root + "HongXing.txt");
        /**
         * 输出流读取类（这是比起字节流多出来的类）专门用来读取字符流，注意字符编码的参数要与写入时的编码相同，否则会乱码
         * 
         * 输入体系中提供的两个转换流，用于实现将字节流转换成字符流。
         */
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String str;
        while ((str = br.readLine()) != null) {
            logger.info(str);
        }
        br.close();
        isr.close();
        fis.close();

        /**
         * 终版：将close部分缩短
         */
        BufferedReader brA = new BufferedReader(
                new InputStreamReader(new FileInputStream(root + "HongXing.txt"), "UTF-8"));
        String strA;
        while ((strA = brA.readLine()) != null) {
            logger.info(strA);
        }
        brA.close();

        /**
         * 输出： 15:04:07[testReader]: 感时花溅泪，恨别鸟惊心 15:04:07[testReader]:
         * 烽火连三月，家书抵万金
         */
    }

    /**
     * File提供了支持字符读写的封装类：FileWriter和FileReader
     * 所以不必每次都使用InputStreamReader和OutputStreamWriter来转换。
     */

    @Test
    public void testFileWriter() throws IOException {
        FileWriter fw = new FileWriter(root + "HongXing.txt", true);
        fw.write("\n杜甫《春望》");
        fw.close();
    }

    @Test
    public void testFileReader() throws IOException {
        FileReader fr = new FileReader(root + "HongXing.txt");
        // FileReader直接read方法没有readLine方便，所以套上装饰类BufferedReader借它的readLine用一用
        BufferedReader br = new BufferedReader(fr);
        String str;
        while ((str = br.readLine()) != null) {
            logger.info(str);
        }
        br.close();
        fr.close();
    }

    @Test
    /**
     * 测试标准输入输出
     */
    public void testStandardIO() throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = stdin.readLine()) != null && str.length() != 0)
            logger.info(str);
    }
}
