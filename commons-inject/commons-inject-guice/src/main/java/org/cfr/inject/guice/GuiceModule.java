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

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;
import org.cfr.inject.Module;
import org.cfr.inject.guice.support.LifecycleProcessor;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class GuiceModule extends AbstractModule {

    /**
     *
     */
    private final Module subModule;

    private final LifecycleProcessor lifecycleProcessor = new LifecycleProcessor();

    /**
     *
     * @param module
     */
    public GuiceModule(@Nonnull final Module module) {
        this.subModule = Assert.checkNotNull(module, "module");
    }

    @Override
    protected void configure() {

        bindListener(Matchers.any(), new LifeCyleListener());
        bind(LifecycleProcessor.class).toInstance(lifecycleProcessor);

        this.subModule.configure(new GuiceBinder(binder()));

    }

    /**
     *
     * @author devacfr<christophefriederich@mac.com>
     *
     */
    private class LifeCyleListener implements TypeListener {

        @Override
        public <I> void hear(final TypeLiteral<I> injectableType, final TypeEncounter<I> encounter) {

            final Class<? super I> type = injectableType.getRawType();
            lifecycleProcessor.findLifecycleMetadata(type);
        }
    }

    private class PreDestroyShutdownHook implements Runnable {

        @Override
        public void run() {

        }
    }

}
