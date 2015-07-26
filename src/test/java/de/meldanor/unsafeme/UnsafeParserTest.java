/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Kilian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.meldanor.unsafeme;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kilian Gärtner
 * @since 25.07.2015
 */
public class UnsafeParserTest {

    @Test
    public void testParse() throws Exception {
        String input = "" +
                "aBoolean: true" + System.lineSeparator() +
                "aByte: 2" + System.lineSeparator() +
                "aChar: a" + System.lineSeparator() +
                "aDouble: 2.2" + System.lineSeparator() +
                "aFloat: 1.1" + System.lineSeparator() +
                "aLong: 1073741824" + System.lineSeparator() +
                "aShort: 16384" + System.lineSeparator() +
                "anInt: 1073741824" + System.lineSeparator() +
                "string: Hi" + System.lineSeparator();

        UnsafeParser<String> parser = new UnsafeParser<>(new StringParser());
        AllDataTypesClass parsedObject = parser.parse(input, AllDataTypesClass.class);
        assertEquals(true, parsedObject.aBoolean);
        assertEquals('a', parsedObject.aChar);
        assertEquals(2, parsedObject.aByte);
        assertEquals(16384, parsedObject.aShort);
        assertEquals(1073741824, parsedObject.anInt);
        assertEquals(1073741824L, parsedObject.aLong);
        assertEquals(1.1, parsedObject.aFloat, 0.01);
        assertEquals(2.2, parsedObject.aDouble, 0.01);
        assertEquals("Hi", parsedObject.string);
    }

    @SuppressWarnings("unused")
    private static class AllDataTypesClass {
        private final boolean aBoolean;
        private final char aChar;
        private final byte aByte;
        private final short aShort;
        private final int anInt;
        private final long aLong;
        private final float aFloat;
        private final double aDouble;
        private final String string;

        public AllDataTypesClass(boolean aBoolean, char aChar, byte aByte, short aShort, int anInt, long aLong,
                                 float aFloat, double aDouble, String string) {
            this.aBoolean = aBoolean;
            this.aChar = aChar;
            this.aByte = aByte;
            this.aShort = aShort;
            this.anInt = anInt;
            this.aLong = aLong;
            this.aFloat = aFloat;
            this.aDouble = aDouble;
            this.string = string;
        }
    }
}