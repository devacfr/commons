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

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.inject.Named;

import org.cfr.commons.util.Assert;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class Names {

    private Names() {
    }

    public static Named named(final String name) {
        return new NamedImpl(name);
    }

    static class NamedImpl implements javax.inject.Named, Serializable {

        private static final long serialVersionUID = 0;

        private final String value;

        public NamedImpl(final String value) {
            this.value = Assert.checkNotNull(value, "value");
        }

        @Override
        public String value() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return (127 * "value".hashCode()) ^ value.hashCode();
        }

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Named)) {
                return false;
            }

            Named other = (Named) o;
            return value.equals(other.value());
        }

        @Override
        public String toString() {
            return "@" + Named.class.getName() + "(value=" + value + ")";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Named.class;
        }

    }
}
