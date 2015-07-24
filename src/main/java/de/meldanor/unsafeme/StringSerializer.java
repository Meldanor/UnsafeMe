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

/**
 * @author Kilian Gärtner
 * @since 24.07.2015
 */
public class StringSerializer implements Serializer<String> {

    private StringBuilder sBuilder;

    @Override
    public void start() {
        this.sBuilder = new StringBuilder();
    }

    @Override
    public String end() {
        return sBuilder.toString();
    }

    @Override
    public void onBoolean(boolean value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onChar(char value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onByte(byte value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onShort(short value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onInt(int value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onLong(long value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onFloat(float value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onDouble(double value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onObject(Object value, String fieldName) {
        sBuilder.append(fieldName).append(": ").append(value);
        newLine();
    }

    @Override
    public void onObjectIsNull() {
        sBuilder.append(String.valueOf((String) null));
    }

    private void newLine() {
        sBuilder.append(System.lineSeparator());
    }
}
