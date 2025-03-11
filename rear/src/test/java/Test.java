import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisClientConfig;

import java.time.Duration;
import java.util.List;

/**
 * @time 2025/2/25 19:23 周二
 */


public class Test {


    public static void main(String[] args) throws InterruptedException {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
                new RedisStandaloneConfiguration("127.0.0.1", 6379)
        );
        jedisConnectionFactory.afterPropertiesSet();


        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();

        ListOperations<String, Integer> stringIntegerListOperations = redisTemplate.opsForList();

//        redisTemplate.execute(new SessionCallback<List<Object>>() {
//            @Override
//            public List<Object> execute(RedisOperations operations) throws DataAccessException {
//                operations.multi();
//                operations.opsForValue().set("name", 12);
//                return operations.exec();
//            }
//        });

    }
}
