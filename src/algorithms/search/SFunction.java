package algorithms.search;

/**
 * 查找算法的泛型接口
 * 
 * @author Evsward
 *
 * @param <Key>
 * @param <Value>
 */
public interface SFunction<Key, Value> {

    public void put(Key key, Value val);// 插入

    public int size();// 获取表的长度

    public Iterable<Key> keys();// 迭代表内所有key

    public Value get(Key key);// 查找某key的值
}
