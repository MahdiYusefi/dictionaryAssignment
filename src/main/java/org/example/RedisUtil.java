package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class RedisUtil {

    private static final Map<String, JedisPool> pools = new ConcurrentHashMap<>();

    public static final ThreadLocal<Map<String, Jedis>> clients = new ThreadLocal<>();


    private RedisUtil() {

    }


    public static Jedis getWorker()  {

        long begin = System.currentTimeMillis();
        Jedis jedis = buildInstance("instance");
        long end = System.currentTimeMillis();

        return jedis;



    }

    private static Jedis buildInstance(String instanceKey)  {


        if (clients.get() == null) {
            Map<String, Jedis> jedises = new ConcurrentHashMap<>();
            return init(instanceKey, jedises);
        } else {
            Map<String, Jedis> jedises = clients.get();
            if (jedises.containsKey(instanceKey)) {
                return jedises.get(instanceKey);
            } else {
                return init(instanceKey, jedises);
            }


        }

    }

    private static Jedis init(String instanceKey, Map<String, Jedis> jedises)  {
        JedisPool pool = getPool(instanceKey);
        Jedis jedis = pool.getResource();
        jedises.put(instanceKey, jedis);
        clients.set(jedises);
        int activeConnections = pool.getNumActive();

        return jedis;
    }

    public static void clearCurrentJedis() {
        try {
            if (clients.get() != null) {
                for (Map.Entry<String, Jedis> jedis : clients.get().entrySet()) {

                    if (jedis.getValue().isConnected()) {
                        try {
                            jedis.getValue().close();
                        } catch (JedisException e) {

                        } catch (Exception e) {
                        }
                    }

                }
                clients.get().clear();
                clients.set(null);
            }
        } catch (Exception e) {
        }
    }

    public static JedisPool getPool(String instanceKey) {
        if (!pools.containsKey(instanceKey)) {
            System.out.println("initializing pool " + instanceKey);


            String remoteServer = "localhost";
            Integer remotePort = 6379;

            JedisPool pool = initPool(remoteServer, remotePort);
            pools.put(instanceKey, pool);
        }

        return pools.get(instanceKey);

    }

    private static JedisPool initPool(String remoteServer, Integer remotePort) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setMaxTotal(500);
        poolConfig.setBlockWhenExhausted(false);
        poolConfig.setTestWhileIdle(true);
        return new JedisPool(poolConfig, remoteServer, remotePort, Protocol.DEFAULT_TIMEOUT);
    }

    private static boolean isSlaveSync(Jedis jedis) {
        String replication = jedis.info("replication");
        return replication.contains("master_link_status:up") && replication.contains("master_last_io_seconds_ago:0") && !replication.contains("master_link_down_since_seconds");

    }


}
