package org.cfr.commons.app.transaction;

import org.cfr.commons.app.transaction.ITransactionCallback;
import org.cfr.commons.app.transaction.ITransactionTemplate;
import org.cfr.commons.app.transaction.TransactionStatus;
import org.cfr.commons.testing.EasyMockTestCase;
import org.junit.Test;


public class TransactionTest extends EasyMockTestCase {

    int inc = 0;

    @Test
    public void callbackTransaction() {

        final TransactionStatus status = mock(TransactionStatus.class);
        ITransactionCallback<Object> callback = new ITransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                inc++;
                return null;
            }
        };
        ITransactionTemplate<Object> template = new ITransactionTemplate<Object>() {

            @Override
            public Object execute(ITransactionCallback<Object> action) {
                return action.doInTransaction(status);
            }
        };

        template.execute(callback);
        assertEquals(1, inc);

    }
}
