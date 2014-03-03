package org.cfr.commons.app.message;

import java.io.Serializable;

/**
 * Encapsulates a message before it has been resolved via an I18N resolver
 *
 * @since 2.0
 */
public interface IMessage extends Serializable {

    /**
     * @return the i18n message key
     */
    String getKey();

    /**
     * @return the arguments to insert into the resolved message
     */
    Serializable[] getArguments();
}
