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
import java.util.Map;

/**
 * @author Kilian Gärtner
 * @since 24.07.2015
 */
public class UnsafeSerializer<Product> {

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

    private final Serializer<Product> serializer;

    public UnsafeSerializer(Serializer<Product> serializer) {
        this.serializer = serializer;
    }

    public Product serialize(Object object) {
        serializer.start();
        if (object == null)
            serializer.onObjectIsNull();
        else
            serializeObject(object);
        return serializer.end();
    }

    private void serializeObject(Object object) {
        ClassSchema schema = ClassSchemaFactory.getInstance().getSchema(object.getClass());

        for (Map.Entry<String, ClassSchema.FieldInformation> fieldOffsets : schema.fieldsByName.entrySet()) {
            Class<?> fieldType = fieldOffsets.getValue().field.getType();
            long fieldOffset = fieldOffsets.getValue().fieldOffset;
            String fieldName = fieldOffsets.getKey();
            if (!fieldType.isPrimitive())
                serializeObject(object, fieldOffset, fieldName);
            else
                serializePrimitive(fieldType, object, fieldOffset, fieldName);
        }
    }

    private void serializePrimitive(Class<?> fieldType, Object object, long fieldOffset, String fieldName) {
        if (boolean.class.equals(fieldType))
            serializer.onBoolean(UNSAFE.getBoolean(object, fieldOffset), fieldName);
        else if (char.class.equals(fieldType))
            serializer.onChar(UNSAFE.getChar(object, fieldOffset), fieldName);
        else if (byte.class.equals(fieldType))
            serializer.onByte(UNSAFE.getByte(object, fieldOffset), fieldName);
        else if (short.class.equals(fieldType))
            serializer.onShort(UNSAFE.getShort(object, fieldOffset), fieldName);
        else if (int.class.equals(fieldType))
            serializer.onInt(UNSAFE.getInt(object, fieldOffset), fieldName);
        else if (long.class.equals(fieldType))
            serializer.onLong(UNSAFE.getLong(object, fieldOffset), fieldName);
        else if (float.class.equals(fieldType))
            serializer.onFloat(UNSAFE.getFloat(object, fieldOffset), fieldName);
        else if (double.class.equals(fieldType))
            serializer.onDouble(UNSAFE.getDouble(object, fieldOffset), fieldName);
    }

    private void serializeObject(Object object, long fieldOffset, String fieldName) {
        serializer.onObject(UNSAFE.getObject(object, fieldOffset), fieldName);
    }
}
