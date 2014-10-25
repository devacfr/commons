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
 * A simple callback that needs to be provided with an action to run in the doInTransaction method. It is assumed that
 * if anything goes wrong, doInTransaction will throw a RuntimeException if anything goes wrong, and the calling
 * transactionTemplate will roll back the transaction.
 *
 * @since 1.0
 */
public interface ITransactionCallback<T> {

    /**
     * Runs an action in a transaction and returns a optional value.
     *
     * @param transactionStatus
     *            associated transaction status
     * @return Optional result of the operation. May be null
     * @throws RuntimeException
     *             if anything went wrong. The caller will be responsible for rolling back.
     */
    T doInTransaction(TransactionStatus transactionStatus);
}
