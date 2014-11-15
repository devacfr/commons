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
 * Representation of the status of a transaction.
 * <p>
 * Transactional code can use this to retrieve status information, and to programmatically request a rollback (instead
 * of throwing an exception that causes an implicit rollback).
 * </p>
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since
 */
public interface TransactionStatus {

    /**
     * @return Returns whether the present transaction is new (else participating in an existing transaction, or
     *         potentially not running in an actual transaction in the first place).
     */
    boolean isNewTransaction();

    /**
     * Set the transaction rollback-only. This instructs the transaction manager that the only possible outcome of the
     * transaction may be a rollback, as alternative to throwing an exception which would in turn trigger a rollback.
     */
    void setRollbackOnly();

    /**
     * @return Returns whether the transaction has been marked as rollback-only (either by the application or by the
     *         transaction infrastructure).
     */
    boolean isRollbackOnly();

    /**
     * Flush the underlying session to the datastore, if applicable.
     */
    void flush();

    /**
     * @return Returns whether this transaction is completed, that is, whether it has already been committed or rolled
     *         back.
     */
    boolean isCompleted();

}