# 问题分析

​	我们会有这样的需求，就是存储用户上次登录的ip地址，那么我们该如何存储到数据库呢？那还不简单，用varchar(15)来存储不就好了，最大的ip也就15位。这样可以。but!! 但是，这样**浪费了空间**不说(用户量很大的话)，**如果要判断某个用户的ip是否在某个ip段**这样也就不好做了。所以啊。我们可以将ip转换成Long类型也只是占用4个字节，而且方便查询和计算。



# 如何转换

其实就是将每个位转换成2进制，然后进行位移就好了，如果转换回来也是这样，往回位移然后取最后的一个字节(一位0-255占一个字节8个二进制位)。

话不多说，上代码：

```java
public class IPUtils {

    private static final Pattern PATTERN = Pattern.compile("^((2([0-4]\\d|5[0-5])|[01]?\\d{1,2})\\.){3}(2([0-4]\\d|5[0-5])|[01]?\\d{1,2})$");

    /**
     * ip转换成long
     *
     * @param ip ip
     * @return long
     */
    public static Long ipToLong(String ip) {
        //校验ip是否正确
        Matcher matcher = PATTERN.matcher(ip);
        if (!matcher.find()) {
            throw new RuntimeException("ip 格式不正确");
        }
        String[] split = ip.split("\\.");
        return (Long.parseLong(split[0]) << 24) + (Long.parseLong(split[1]) << 16)
                + (Long.parseLong(split[2]) << 8) + Long.parseLong(split[3]);
    }

    /**
     * 将long类型转换成ip
     *
     * @param ipLong ip的long类型
     * @return ip
     */
    public static String longToIp(Long ipLong) {
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }

    public static void main(String[] args) {
        System.out.println(ipToLong("127.0.0.1"));
        System.out.println(longToIp(2130706433L));
    }
}
```

如果你对上面的0xFF感到疑惑的话别急，其实你想啊，0xff不就是255的十六进制吗？换行成二进制不就是`1111 1111`吗？那么任何数与它做与运算(`&`)不就是去取这个数的二进制的最后八位吗？所以，我们只是向右位移，然后取最后八个二进制位就还原回来了！！