package com.jcommerce.core.util;




/**
 * The base class for identifier generators that use a UUID algorithm. This
 * class implements the algorithm, subclasses define the identifier
 * format.
 */
public class UUIDGenerator
{
    /**
     *
     */
    public UUIDGenerator()
    {
    }

    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     */
    protected int getJVM()
    {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected short getCount()
    {
        synchronized (UUIDGenerator.class)
        {
            if (counter < 0) counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
//    protected int getIP()
//    {
//        return IP;
//    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime()
    {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    /**
     *
     * @return
     */
    protected int getLoTime()
    {
        return (int) System.currentTimeMillis();
    }

//    private static final int IP;
    private static short counter = (short) 0;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

//    static
//    {
//        int ipadd;
//        try
//        {
//            ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
//        }
//        catch (Exception e)
//        {
//            ipadd = 0;
//        }
//        IP = ipadd;
//    }

}






