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
package org.cfr.commons.app.transaction;

/**
 * This allows applications greater control over the transaction in which operations may be executed. This really
 * mimicks {@link org.springframework.transaction.support.TransactionTemplate}, however since JIRA doesn't know about
 * Spring and doesn't support transactions we need to have our own implementation of this interface here.
 *
 * @since 1.0
 */
public interface ITransactionTemplate<T> {

    /**
     * Executes the callback, returning the object returned. Any runtime exceptions thrown by the callback are assumed
     * to rollback the transaction.
     *
     * @param action
     *            The callback
     * @return The object returned from the callback
     */
    T execute(ITransactionCallback<T> action);
}
