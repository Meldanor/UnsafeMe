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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kilian Gärtner
 * @since 24.07.2015
 */
class ClassSchema {

    final Map<String, FieldInformation> fieldsByName;

    ClassSchema(final Class<?> clazz) {
        this.fieldsByName = Collections.unmodifiableMap(readFields(clazz));
    }

    private Map<String, FieldInformation> readFields(final Class<?> clazz) {

        Map<String, FieldInformation> map = new LinkedHashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        // because of of no guaranteed order, we order the fields by name
        Arrays.sort(fields, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        for (Field field : fields) {
            System.out.println(field.getName());
            long fieldOffset = UnsafeSerializer.UNSAFE.objectFieldOffset(field);
            map.put(field.getName(), new FieldInformation(field, fieldOffset));
        }

        return map;
    }

    public Class<?> getTypeOf(String fieldName) {
        return fieldsByName.get(fieldName).field.getType();
    }


    class FieldInformation {
        final Field field;
        final long fieldOffset;

        public FieldInformation(Field field, long fieldOffset) {
            this.field = field;
            this.fieldOffset = fieldOffset;
        }
    }
}
