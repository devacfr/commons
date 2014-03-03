package org.cfr.commons.util.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.cfr.commons.testing.EasyMockTestCase;
import org.cfr.commons.util.collection.CollectionUtil;
import org.cfr.commons.util.collection.Consumer;
import org.cfr.commons.util.collection.FilteredIterator;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class CollectionUtilTest extends EasyMockTestCase {

    private final static String NONAME = "noname";

    @Test
    public void foreachIterator() {
        Consumer<Foo> incNameConsumer = new Consumer<Foo>() {

            private int inc = 0;

            @Override
            public void consume(Foo element) {
                inc++;
                element.name = (element.name == null ? NONAME + inc : element.name + inc);
            }
        };

        List<Foo> list = createList();
        CollectionUtil.foreach(list.iterator(), incNameConsumer);

        assertEquals(NONAME + "1", list.get(0).name);
        assertEquals("jule" + "4", list.get(3).name);
        assertEquals(NONAME + "10", list.get(9).name);
    }

    @Test
    public void foreachIterable() {
        Consumer<Foo> incNameConsumer = new Consumer<Foo>() {

            private int inc = 0;

            @Override
            public void consume(Foo element) {
                inc++;
                element.name = (element.name == null ? NONAME + inc : element.name + inc);
            }
        };

        List<Foo> list = createList();
        CollectionUtil.foreach(list, incNameConsumer);

        assertEquals(NONAME + "1", list.get(0).name);
        assertEquals("jule" + "4", list.get(3).name);
        assertEquals(NONAME + "10", list.get(9).name);
    }

    @Test
    public void foreachNullIterable() {
        CollectionUtil.foreach((Iterable<Object>) null, null);
    }

    @Test
    public void toListIterator() {
        List<Foo> list = createList();
        assertEquals(list, CollectionUtil.toList(list.iterator()));
    }

    @Test
    public void toListIterable() {
        List<Foo> list = createList();
        assertEquals(list, CollectionUtil.toList(list));
    }

    @Test
    public void transformIterator() {
        Function<Foo, String> extractName = new Function<Foo, String>() {

            @Override
            public String apply(Foo input) {
                return input.name;
            }
        };
        List<Foo> list = createList();
        List<String> expected = Arrays.asList(NONAME, NONAME, NONAME, "jule", NONAME, "ana", NONAME, NONAME, NONAME, NONAME);
        List<String> result = CollectionUtil.transform(list.iterator(), extractName);
        assertEquals(expected, result);
    }

    @Test
    public void transformIterable() {
        Function<Foo, String> extractName = new Function<Foo, String>() {

            @Override
            public String apply(Foo input) {
                return input.name;
            }
        };
        List<Foo> list = createList();
        List<String> expected = Arrays.asList(NONAME, NONAME, NONAME, "jule", NONAME, "ana", NONAME, NONAME, NONAME, NONAME);
        List<String> result = CollectionUtil.transform(list, extractName);
        assertEquals(expected, result);
    }

    @Test
    public void transformEmptyIterable() {
        List<String> result = CollectionUtil.transform((List<Object>) null, (Function<Object, String>) null);
        assertTrue(result.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void transformEmptyIterator() {
        CollectionUtil.transform((Iterator<Object>) null, (Function<Object, String>) null);
    }

    @Test
    public void filterIterator() {
        Predicate<Foo> nonameFilter = new Predicate<Foo>() {

            @Override
            public boolean apply(Foo input) {
                return NONAME.equals(input.name);
            }
        };
        Iterator<Foo> result = CollectionUtil.filter(createList().iterator(), nonameFilter);
        List<Foo> list = Lists.newArrayList(result);
        assertEquals(8, list.size());
    }

    @Test()
    public void filterRemoveItemFromIterator() {
        Predicate<Foo> juleFilter = new Predicate<Foo>() {

            @Override
            public boolean apply(Foo input) {
                return "jule".equals(input.name);
            }
        };
        Iterator<Foo> result = CollectionUtil.filter(createList().iterator(), juleFilter);
        assertTrue(result instanceof FilteredIterator);
        try {
            result.remove();
            fail("Operation not supported");
        } catch (IllegalStateException ex) {
        }
        assertTrue(result.hasNext());
        // recall status has been calculated yet
        assertTrue(result.hasNext());
        // select next element
        Foo foo = result.next();
        assertNotNull(foo);
        //remove next element
        try {
            result.remove();
        } catch (UnsupportedOperationException ex) {
        }
        assertFalse(result.hasNext());
        try {
            result.next();
            fail("no such element");
        } catch (NoSuchElementException ex) {
        }

    }

    @Test
    public void filterIterable() {
        Predicate<Foo> nonameFilter = new Predicate<Foo>() {

            @Override
            public boolean apply(Foo input) {
                return NONAME.equals(input.name);
            }
        };
        Iterable<Foo> result = CollectionUtil.filter(createList(), nonameFilter);
        List<Foo> list = Lists.newArrayList(result);
        assertEquals(8, list.size());
        assertEquals("[noname, noname, noname, noname, noname, noname, noname, noname]", result.toString());
    }

    @Test
    public void sortCollection() {
        Comparator<Foo> comparatorName = new Comparator<Foo>() {

            @Override
            public int compare(Foo o1, Foo o2) {
                return o1.name.compareTo(o2.name);
            }

        };
        List<Foo> result = CollectionUtil.sort(createList(), comparatorName);
        assertEquals(createSortedList(), result);
    }

    @Test
    public void sortEmptyCollection() {
        List<Foo> result = CollectionUtil.sort(Collections.<Foo> emptyList(), null);
        assertEquals(Collections.<Foo> emptyList(), result);
    }

    private List<Foo> createList() {
        return Arrays.asList(new Foo(), new Foo(), new Foo(), new Foo("jule"), new Foo(), new Foo("ana"), new Foo(), new Foo(), new Foo(), new Foo());
    }

    private List<Foo> createSortedList() {
        return Arrays.asList(new Foo("ana"), new Foo("jule"), new Foo(), new Foo(), new Foo(), new Foo(), new Foo(), new Foo(), new Foo(), new Foo());
    }

    public static class Foo {

        public String name;

        public Foo() {
            this.name = NONAME;
        }

        public Foo(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            return name.equals(((Foo) obj).name);
        }

        @Override
        public String toString() {
            return name;
        }

    }
}
