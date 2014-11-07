/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Named;
import javax.inject.Qualifier;

import org.cfr.commons.testing.TestCase;
import org.junit.Test;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class KeyTest extends TestCase {

    @Test
    public void getKey() {
        Key<Foo> key = Key.get(Foo.class);
        assertNotNull(key);
        assertNull(key.getAnnotation());
        assertNull(key.getAnnotationType());
        assertEquals(Foo.class, key.getType());

        assertEquals("Key{type=class org.cfr.inject.KeyTest$Foo, annotationType=null, annotation=null}", key.toString());

        assertEquals(Key.get(Foo.class), key);
        assertEquals(Key.get(Foo.class).hashCode(), key.hashCode());
    }

    @Test
    public void getKeyWithAnnotationType() {
        Key<Foo> key = Key.get(Foo.class, RedName.class);
        assertNotNull(key);
        assertNull(key.getAnnotation());
        assertNotNull(key.getAnnotationType());
        assertEquals(Foo.class, key.getType());

        assertEquals("Key{type=class org.cfr.inject.KeyTest$Foo, annotationType=interface org.cfr.inject.KeyTest$RedName, annotation=null}",
            key.toString());

        assertEquals(Key.get(Foo.class, RedName.class), key);

        assertEquals(Key.get(Foo.class, RedName.class).hashCode(), key.hashCode());
    }

    @Test
    public void getKeyWithAnnotation() {
        Key<Foo> key = Key.get(Foo.class, Names.named("foo"));
        assertNotNull(key);
        assertNotNull(key.getAnnotation());
        assertNotNull(key.getAnnotationType());
        assertEquals(Foo.class, key.getType());

        assertEquals("Key{type=class org.cfr.inject.KeyTest$Foo, annotationType=interface javax.inject.Named, annotation=@javax.inject.Named(value=foo)}",
            key.toString());

        assertEquals(Key.get(Foo.class, Names.named("foo")), key);

        assertEquals(Key.get(Foo.class, Names.named("foo")).hashCode(), key.hashCode());
    }

    @Test
    public void getKeyWithQualifier() {
        Key<Foo> key = Key.get(Foo.class, Red.class);
        assertNotNull(key);
        assertNull(key.getAnnotation());
        assertNotNull(key.getAnnotationType());
        assertEquals(Foo.class, key.getType());

        assertEquals("Key{type=class org.cfr.inject.KeyTest$Foo, annotationType=interface org.cfr.inject.KeyTest$Red, annotation=null}",
            key.toString());

        assertEquals(Key.get(Foo.class, Red.class), key);
        assertFalse(Key.get(Foo.class, Blue.class).equals(key));

        assertEquals(Key.get(Foo.class, Red.class).hashCode(), key.hashCode());
        assertFalse(Key.get(Foo.class, Blue.class).hashCode() == key.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getKeyWithNullType() {
        Key.get(null);
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface Red {
    }

    @Named
    @Retention(RetentionPolicy.RUNTIME)
    @interface RedName {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface Blue {
    }

    public static class Foo {

    }
}
