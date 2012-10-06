package com.tedwon.jmx;

/**
 * Sample HelloMBean Interface.
 *
 * @author <a href=mailto:iamtedwon@gmail.com">Ted Won</a>
 * @version 0.1
 */
public interface HelloMBean {

    //-----------
    // operations
    //-----------

    public void sayHello();

    public int add(int x, int y);

    //-----------
    // attributes
    //-----------

    // a read-only attribute called Name of type String
    public String getName();

    // a read-write attribute called CacheSize of type int
    public int getCacheSize();

    public void setCacheSize(int size);

}
