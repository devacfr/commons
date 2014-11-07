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
package org.cfr.inject.guice;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import org.cfr.commons.testing.TestCase;
import org.cfr.inject.AbstractModule;
import org.cfr.inject.Injector;
import org.cfr.inject.Key;
import org.cfr.inject.Names;
import org.cfr.inject.Scope;
import org.junit.Test;

import com.google.inject.CreationException;
import com.google.inject.Stage;
import com.google.inject.spi.HasDependencies;
import com.google.inject.util.Providers;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class GuiceTest extends TestCase {

    private final B b = new B();

    private final C c = new C();

    private final D d = new D();

    private final E e = new E();

    @Override
    public void setUp() throws Exception {
        J.nextInstanceId = 0;
        K.nextInstanceId = 0;
    }

    @Test
    public void testInject() {
        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).toInstance(b);
                bind(C.class).toInstance(c);
                bind(D.class).toInstance(d);
                bind(E.class).toInstance(e);
                bind(A.class);
            }
        });

        final A a = injector.getInstance(A.class);
        assertSame(b, a.b);
        assertSame(c, a.c);
        assertSame(d, a.d);
        assertSame(e, a.e);
    }

    @Test
    public void testQualifiedInject() {
        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).annotatedWith(Names.named("jodie")).toInstance(b);
                bind(C.class).annotatedWith(Red.class).toInstance(c);
                bind(D.class).annotatedWith(RED).toInstance(d);
                bind(E.class).annotatedWith(Names.named("jesse")).toInstance(e);
                bind(F.class);
            }
        });

        final F f = injector.getInstance(F.class);
        assertSame(b, f.b);
        assertSame(c, f.c);
        assertSame(d, f.d);
        assertSame(e, f.e);
    }

    @Test
    public void testProviderInject() {
        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).annotatedWith(Names.named("jodie")).toInstance(b);
                bind(C.class).toInstance(c);
                bind(D.class).annotatedWith(RED).toInstance(d);
                bind(E.class).toInstance(e);
                bind(G.class);
            }
        });

        final G g = injector.getInstance(G.class);
        assertSame(b, g.bProvider.get());
        assertSame(c, g.cProvider.get());
        assertSame(d, g.dProvider.get());
        assertSame(e, g.eProvider.get());
    }

    @Test
    public void testScopeAnnotation() {
        final TestScope scope = new TestScope();

        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).in(scope);
                bind(C.class).in(TestScoped.class);
                bindScope(TestScoped.class, scope);
            }
        });

        final B b = injector.getInstance(B.class);
        assertSame(b, injector.getInstance(B.class));
        assertSame(b, injector.getInstance(B.class));

        final C c = injector.getInstance(C.class);
        assertSame(c, injector.getInstance(C.class));
        assertSame(c, injector.getInstance(C.class));

        final H h = injector.getInstance(H.class);
        assertSame(h, injector.getInstance(H.class));
        assertSame(h, injector.getInstance(H.class));

        scope.reset();

        assertNotSame(b, injector.getInstance(B.class));
        assertNotSame(c, injector.getInstance(C.class));
        assertNotSame(h, injector.getInstance(H.class));
    }

    @Test
    public void testSingleton() {
        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).in(Singleton.class);
            }
        });

        final B b = injector.getInstance(B.class);
        assertSame(b, injector.getInstance(B.class));
        assertSame(b, injector.getInstance(B.class));

        final J j = injector.getInstance(J.class);
        assertSame(j, injector.getInstance(J.class));
        assertSame(j, injector.getInstance(J.class));
    }

    @Test
    public void testEagerSingleton() {
        DI.createInjector(Stage.PRODUCTION, new AbstractModule() {

            @Override
            protected void configure() {
                bind(J.class);
                bind(K.class).in(Singleton.class);
            }
        });

        assertEquals(1, J.nextInstanceId);
        assertEquals(1, K.nextInstanceId);
    }

    // @Test
    // public void testScopesIsSingleton() {
    // Injector injector = Guices.createInjector(new AbstractModule() {
    //
    // @Override
    // protected void configure() {
    // bind(J.class);
    // bind(K.class).in(Singleton.class);
    // }
    // });
    //
    // assertTrue(Scopes.isSingleton(injector.getBinding(J.class)));
    // assertTrue(Scopes.isSingleton(injector.getBinding(K.class)));
    // }

    @Test
    public void testInjectingFinalFieldsIsForbidden() {
        try {
            DI.createInjector(new AbstractModule() {

                @Override
                protected void configure() {
                    bind(L.class);
                }
            });
            fail();
        } catch (final CreationException expected) {
            assertContains(expected.getMessage(), "1) Injected field " + L.class.getName() + ".b cannot be final.");
        }
    }

    @Test
    public void testInjectingAbstractMethodsIsForbidden() {
        try {
            DI.createInjector(new AbstractModule() {

                @Override
                protected void configure() {
                    bind(M.class);
                }
            });
            fail();
        } catch (final CreationException expected) {
            assertContains(expected.getMessage(), "1) Injected method " + AbstractM.class.getName()
                    + ".setB() cannot be abstract.");
        }
    }

    @Test
    public void testInjectingMethodsWithTypeParametersIsForbidden() {
        try {
            DI.createInjector(new AbstractModule() {

                @Override
                protected void configure() {
                    bind(N.class);
                }
            });
            fail();
        } catch (final CreationException expected) {
            assertContains(expected.getMessage(), "1) Injected method " + N.class.getName()
                    + ".setB() cannot declare type parameters of its own.");
        }
    }

    @Test
    public void testInjectingMethodsWithNonVoidReturnTypes() {
        DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(P.class);
            }
        });
    }

    @Test
    public void testBindProviderClass() {
        final Injector injector = DI.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(B.class).toProvider(BProvider.class);
                bind(B.class).annotatedWith(Names.named("1")).toProvider(BProvider.class);
                bind(B.class).annotatedWith(Names.named("2")).toProvider(BProvider.class);
                bind(B.class).annotatedWith(Names.named("3")).toProvider(BProvider.class);
            }
        });

        injector.getInstance(Key.get(B.class));
        injector.getInstance(Key.get(B.class, Names.named("1")));
        injector.getInstance(Key.get(B.class, Names.named("2")));
        injector.getInstance(Key.get(B.class, Names.named("3")));
    }

    @Test
    public void testGuicify330Provider() {
        final Provider<String> jsr330Provider = new Provider<String>() {

            @Override
            public String get() {
                return "A";
            }

            @Override
            public String toString() {
                return "jsr330Provider";
            }
        };

        final com.google.inject.Provider<String> guicified = Providers.guicify(jsr330Provider);
        assertEquals("guicified(jsr330Provider)", guicified.toString());
        assertEquals("A", guicified.get());

        // when you guicify the Guice-friendly, it's a no-op
        assertSame(guicified, Providers.guicify(guicified));

        assertFalse(guicified instanceof HasDependencies);
    }

    // public void testGuicifyWithDependencies() {
    // Provider<String> jsr330Provider = new Provider<String>() {
    //
    // @Inject
    // double d;
    //
    // int i;
    //
    // @Inject
    // void injectMe(final int i) {
    // this.i = i;
    // }
    //
    // @Override
    // public String get() {
    // return d + "-" + i;
    // }
    // };
    //
    // final com.google.inject.Provider<String> guicified = Providers.guicify(jsr330Provider);
    // assertTrue(guicified instanceof HasDependencies);
    // Set<Dependency<?>> actual = ((HasDependencies) guicified).getDependencies();
    // validateDependencies(actual, jsr330Provider.getClass());
    //
    // Injector injector = Guices.createInjector(new AbstractModule() {
    //
    // @Override
    // protected void configure() {
    // bind(String.class).toProvider(guicified);
    // bind(int.class).toInstance(1);
    // bind(double.class).toInstance(2.0d);
    // }
    // });
    //
    // Binding<String> binding = injector.getBinding(String.class);
    // assertEquals("2.0-1", binding.getProvider().get());
    // validateDependencies(actual, jsr330Provider.getClass());
    // }

    static class A {

        final B b;

        @Inject
        C c;

        D d;

        E e;

        @Inject
        A(final B b) {
            this.b = b;
        }

        @Inject
        void injectD(final D d, final E e) {
            this.d = d;
            this.e = e;
        }
    }

    static class B {
    }

    static class C {
    }

    static class D {
    }

    static class E {
    }

    static class F {

        final B b;

        @Inject
        @Red
        C c;

        D d;

        E e;

        @Inject
        F(@Named("jodie") final B b) {
            this.b = b;
        }

        @Inject
        void injectD(@Red final D d, @Named("jesse") final E e) {
            this.d = d;
            this.e = e;
        }
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface Red {
    }

    public static final Red RED = new Red() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return Red.class;
        }

        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Red;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    };

    static class G {

        final Provider<B> bProvider;

        @Inject
        Provider<C> cProvider;

        Provider<D> dProvider;

        Provider<E> eProvider;

        @Inject
        G(@Named("jodie") final Provider<B> bProvider) {
            this.bProvider = bProvider;
        }

        @Inject
        void injectD(@Red final Provider<D> dProvider, final Provider<E> eProvider) {
            this.dProvider = dProvider;
            this.eProvider = eProvider;
        }
    }

    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestScoped {
    }

    static class TestScope implements Scope {

        private int now = 0;

        @Override
        public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
            return new com.google.inject.Provider<T>() {

                private T value;

                private int snapshotTime = -1;

                @Override
                public T get() {
                    if (snapshotTime != now) {
                        value = unscoped.get();
                        snapshotTime = now;
                    }
                    return value;
                }
            };
        }

        public void reset() {
            now++;
        }
    }

    @TestScoped
    static class H {
    }

    @Singleton
    static class J {

        static int nextInstanceId = 0;

        int instanceId = nextInstanceId++;
    }

    static class K {

        static int nextInstanceId = 0;

        int instanceId = nextInstanceId++;
    }

    static class L {

        @Inject
        final B b = null;
    }

    static abstract class AbstractM {

        @Inject
        abstract void setB(B b);
    }

    static class M extends AbstractM {

        @Override
        void setB(final B b) {
        }
    }

    static class N {

        @Inject
        <T> void setB(final B b) {
        }
    }

    static class P {

        @Inject
        B setB(final B b) {
            return b;
        }
    }

    static class BProvider implements Provider<B> {

        @Override
        public B get() {
            return new B();
        }
    }

}
