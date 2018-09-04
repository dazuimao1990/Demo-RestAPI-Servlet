package com.topica.demojspservlet.utils.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;

public class MemcachedClientUtil {

  private static MemcachedClient memcachedClient;

  static{
    try {
      memcachedClient = new MemcachedClient(new InetSocketAddress("localhost", 11211));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static MemcachedClient getMemcachedClient(){
    return memcachedClient;
  }
}
