package com.alibaba.json.test.bvt.parser;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CoderResult;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.UTF8Decoder;

public class IOUtilsTest extends TestCase {

    public void test_error_0() throws Exception {
        Exception error = null;
        try {
            IOUtils.decode(JSON.UTF8_CharsetEncoder, ByteBuffer.wrap("abc".getBytes("UTF-8")),
                           CharBuffer.wrap(new char[0]));
        } catch (Exception ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
    
    public void test_error_1() throws Exception {
        Exception error = null;
        try {
            IOUtils.decode(new MockCharsetDecoder(), ByteBuffer.wrap("abc".getBytes("UTF-8")),
                           CharBuffer.wrap(new char[10]));
        } catch (Exception ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }
    
    public void test_error_2() throws Exception {
        Exception error = null;
        try {
            IOUtils.decode(new MockCharsetDecoder2(), ByteBuffer.wrap("abc".getBytes("UTF-8")),
                           CharBuffer.wrap(new char[10]));
        } catch (Exception ex) {
            error = ex;
        }
        Assert.assertNotNull(error);
    }

    public static class MockCharsetDecoder extends UTF8Decoder {

        @Override
        protected CoderResult implFlush(CharBuffer out) {
            return CoderResult.OVERFLOW;
        }
    }
    
    public static class MockCharsetDecoder2 extends UTF8Decoder {

        @Override
        protected CoderResult implFlush(CharBuffer out) {
            return CoderResult.unmappableForLength(1);
        }
    }
}