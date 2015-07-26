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
 * @since 24.07.2015
 */
public class UnsafeSerializerTest {

    @Test
    public void testSimpleClass() throws Exception {
        UnsafeSerializer<String> stringSerializer = new UnsafeSerializer<>(new StringSerializer());

        Human objectOne = new Human("Felix", 18, 60.7);
        Human objectTwo = new Human("Tim", 1, 5.2);
        Human nonHuman = new Human("Jesus", -1, 3.141592653589793);

        assertEquals("" +
                        "age: 18" + System.lineSeparator() +
                        "mass: 60.7" + System.lineSeparator() +
                        "name: Felix" + System.lineSeparator(),
                stringSerializer.serialize(objectOne));

        assertEquals("" +
                        "age: 1" + System.lineSeparator() +
                        "mass: 5.2" + System.lineSeparator() +
                        "name: Tim" + System.lineSeparator(),
                stringSerializer.serialize(objectTwo));

        assertEquals("" +
                        "age: -1" + System.lineSeparator() +
                        "mass: 3.141592653589793" + System.lineSeparator() +
                        "name: Jesus" + System.lineSeparator(),
                stringSerializer.serialize(nonHuman));

        assertEquals("null", stringSerializer.serialize(null));
    }

    @SuppressWarnings("unused")
    private static class Human {
        private final String name;
        private int age;
        private double mass;

        public Human(String name, int age, double mass) {
            this.name = name;
            this.age = age;
            this.mass = mass;
        }
    }

    @Test
    public void testAllDatatypesSerializer() throws Exception {

        UnsafeSerializer<String> stringSerializer = new UnsafeSerializer<>(new StringSerializer());
        AllDataTypesClass objectOne = new AllDataTypesClass(true, 'a', (byte) 64, (short) 16384, 1073741824,
                1073741824L, 1.1F, 2.2F, "Hi");


        assertEquals("" +
                "aBoolean: true" + System.lineSeparator() +
                "aByte: 64" + System.lineSeparator() +
                "aChar: a" + System.lineSeparator() +
                "aDouble: 2.200000047683716" + System.lineSeparator() +
                "aFloat: 1.1" + System.lineSeparator() +
                "aLong: 1073741824" + System.lineSeparator() +
                "aShort: 16384" + System.lineSeparator() +
                "anInt: 1073741824" + System.lineSeparator() +
                "string: Hi" + System.lineSeparator(), stringSerializer.serialize(objectOne));
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