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

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Kilian Gärtner
 * @since 25.07.2015
 */
public class UnsafeParser<Input> {

    final static Unsafe UNSAFE;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Can't get unsafe object!", e);
        }
    }

    private final Parser<Input> parser;

    public UnsafeParser(Parser<Input> parser) {
        this.parser = parser;
    }

    public <T> T parse(Input input, Class<? extends T> outputClass) {
        ClassSchema schema = ClassSchemaFactory.getInstance().getSchema(outputClass);

        List<FieldValue> fieldValues = parser.parseFieldValues(schema, input);

        try {
            @SuppressWarnings("unchecked") T rawInstance = (T) UNSAFE.allocateInstance(outputClass);
            fillValues(rawInstance, schema, fieldValues);
            return rawInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void fillValues(Object rawInstance, ClassSchema schema, List<FieldValue> fieldValues) {
        for (FieldValue fieldValue : fieldValues) {
            String fieldName = fieldValue.name;
            long offset = schema.fieldsByName.get(fieldName).fieldOffset;

            if (fieldValue instanceof BooleanFieldValue)
                UNSAFE.putBoolean(rawInstance, offset, ((BooleanFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof CharFieldValue)
                UNSAFE.putChar(rawInstance, offset, ((CharFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof ByteFieldValue)
                UNSAFE.putByte(rawInstance, offset, ((ByteFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof ShortFieldValue)
                UNSAFE.putShort(rawInstance, offset, ((ShortFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof IntFieldValue)
                UNSAFE.putInt(rawInstance, offset, ((IntFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof LongFieldValue)
                UNSAFE.putLong(rawInstance, offset, ((LongFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof FloatFieldValue)
                UNSAFE.putFloat(rawInstance, offset, ((FloatFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof DoubleFieldValue)
                UNSAFE.putDouble(rawInstance, offset, ((DoubleFieldValue) fieldValue).getValue());
            else if (fieldValue instanceof ObjectFieldValue)
                UNSAFE.putObject(rawInstance, offset, ((ObjectFieldValue) fieldValue).getValue());
        }
    }
}
