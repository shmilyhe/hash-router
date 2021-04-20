package io.github.shmilyhe.hashing;


import java.nio.charset.Charset;


public final class SafeEncoder {
  static Charset utf8 =Charset.forName("utf-8");
  private SafeEncoder(){
    throw new InstantiationError( "Must not instantiate this class" );
  }

  public static byte[][] encodeMany(final String... strs) {
    byte[][] many = new byte[strs.length][];
    for (int i = 0; i < strs.length; i++) {
      many[i] = encode(strs[i]);
    }
    return many;
  }

  public static byte[] encode(final String str) {
      if (str == null) {
        throw new RuntimeException("value  cannot be null");
      }
      return str.getBytes(utf8);
  }

  public static String encode(final byte[] data) {
      return new String(data, utf8);
 
  }
}
