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

import java.util.List;

/**
 * @author Kilian Gärtner
 * @since 24.07.2015
 */
public interface Parser<Input> {

    List<FieldValue> parseFieldValues(ClassSchema classSchema, Input input);

    BooleanFieldValue onBoolean(Input rawData, String fieldName);

    CharFieldValue onChar(Input rawData, String fieldName);

    ByteFieldValue onByte(Input rawData, String fieldName);

    ShortFieldValue onShort(Input rawData, String fieldName);

    IntFieldValue onInt(Input rawData, String fieldName);

    LongFieldValue onLong(Input rawData, String fieldName);

    FloatFieldValue onFloat(Input rawData, String fieldName);

    DoubleFieldValue onDouble(Input rawData, String fieldName);

    ObjectFieldValue onObject(Input rawData, String fieldName);

    default BooleanFieldValue of(boolean value, String fieldName) {
        return new BooleanFieldValue(value, fieldName);
    }

    default CharFieldValue of(char value, String fieldName) {
        return new CharFieldValue(value, fieldName);
    }

    default ByteFieldValue of(byte value, String fieldName) {
        return new ByteFieldValue(value, fieldName);
    }

    default ShortFieldValue of(short value, String fieldName) {
        return new ShortFieldValue(value, fieldName);
    }

    default IntFieldValue of(int value, String fieldName) {
        return new IntFieldValue(value, fieldName);
    }

    default LongFieldValue of(long value, String fieldName) {
        return new LongFieldValue(value, fieldName);
    }

    default FloatFieldValue of(float value, String fieldName) {
        return new FloatFieldValue(value, fieldName);
    }

    default DoubleFieldValue of(double value, String fieldName) {
        return new DoubleFieldValue(value, fieldName);
    }

    default ObjectFieldValue of(Object value, String fieldName) {
        return new ObjectFieldValue(value, fieldName);
    }
}
