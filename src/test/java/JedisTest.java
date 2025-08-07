import com.zk.jedis.util.JedisConnectionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class JedisTest {
    private Jedis jedis;

    @BeforeEach
    void setUp() {
        /*jedis = new Jedis("localhost", 6379);*/
        jedis = JedisConnectionFactory.getJedisPool();
        // 本地Redis通常没有密码，所以注释掉auth
        // jedis.auth("123456");
        jedis.select(0);
    }

    @Test
    void name() {
        jedis.set("name", "zhangsan");
        System.out.println(jedis.get("name"));
    }

    @Test
    void testSimpleConnection() {
        // 只测试本地连接
        try (Jedis localJedis = new Jedis("localhost", 6379)) {
            String result = localJedis.ping();
            System.out.println("Ping结果: " + result);
            
            localJedis.set("test", "simple-test");
            String value = localJedis.get("test");
            System.out.println("测试值: " + value);
        } catch (Exception e) {
            System.err.println("连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testDifferentIPs() {
        // 测试localhost连接
        try (Jedis localJedis = new Jedis("localhost", 6379)) {
            localJedis.set("test", "localhost-test");
            System.out.println("localhost 连接成功: " + localJedis.get("test"));
        } catch (Exception e) {
            System.err.println("localhost 连接失败: " + e.getMessage());
            e.printStackTrace();
        }

        // 测试127.0.0.1连接
        try (Jedis localJedis = new Jedis("127.0.0.1", 6379)) {
            localJedis.set("test", "127.0.0.1-test");
            System.out.println("127.0.0.1 连接成功: " + localJedis.get("test"));
        } catch (Exception e) {
            System.err.println("127.0.0.1 连接失败: " + e.getMessage());
            e.printStackTrace();
        }

        // 测试局域网IP连接
        try (Jedis networkJedis = new Jedis("192.168.7.235", 6379)) {
            networkJedis.set("test", "network-192.168.7.235");
            System.out.println("192.168.7.235 连接成功: " + networkJedis.get("test"));
        } catch (Exception e) {
            System.err.println("192.168.7.235 连接失败: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Test
    void testHash() {
        jedis.hset("user", "name", "zhangsan");
        jedis.hset("user", "age", "21");
        Map<String,String> user = jedis.hgetAll("user");
        System.out.println(user);
    }

    @Test
    void testPool() {
        String result = jedis.set("name", "zk");
        String name = jedis.get("name");
        System.out.println(name);
        System.out.println(result);
        }


    @AfterEach
    void tearDown() {
        if (jedis != null) {
            jedis.close();
        }
    }
}