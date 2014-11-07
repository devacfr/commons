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

import org.cfr.inject.binding.ConstantBindingBuilder;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class GuiceConstantBindingBuilder implements ConstantBindingBuilder {

    private final com.google.inject.binder.ConstantBindingBuilder delagate;

    /**
     *
     */
    protected GuiceConstantBindingBuilder(@Nonnull final com.google.inject.binder.ConstantBindingBuilder builder) {
        this.delagate = builder;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final String value) {
        delagate.to(value);

    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final int value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final long value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final boolean value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final double value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final float value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final short value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final char value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final byte value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void to(final Class<?> value) {
        delagate.to(value);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public <E extends Enum<E>> void to(final E value) {
        delagate.to(value);
    }

}
