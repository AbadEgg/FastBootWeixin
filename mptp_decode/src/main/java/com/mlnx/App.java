package com.mlnx;

import com.mlnx.decode.MpDecode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        String file = "U:\\03230924\\I3N90O12.txt";
        MpDecode mpDecode = new MpDecode();
        mpDecode.decode(file);
    }
}
