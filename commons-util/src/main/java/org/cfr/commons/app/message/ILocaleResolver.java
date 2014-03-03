package org.cfr.commons.app.message;

import java.util.Locale;
import java.util.Set;

/**
 * This interface is responsible for resolving the current locale.
 *
 * @since 2.0
 */
public interface ILocaleResolver {

    /**
     * Given a request, resolve the {@link Locale} that should be used in internationalization and localization.
     * The locale should be determined by first checking the remote users preferences, then checking the preferred 
     * locale as specified in the request and finally defaulting to the system locale if no preferred locale is set.  
     * 
     * @param request Request to check 
     * @return Locale to be used in i18n and l10n. {@link Locale#getDefault()} if none found.
     */
    Locale getLocale(Object request);

    /**
     * Returns a set of all the supported locales by the host application. This is all the language packs that
     * are installed.
     *
     * @return an unmodifiable set of all the supported locales by the host application. Must contain at least one locale.
     */
    Set<Locale> getSupportedLocales();
}