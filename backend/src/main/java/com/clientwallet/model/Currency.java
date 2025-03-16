package com.clientwallet.model;

/**
 * Currency is an enumeration representing the different types of currencies supported in the wallet system.
 * <p>
 *  Currently, the system supports the following currencies:
 *  <ul>
 *   <li><b>CZK</b> - Czech Koruna</li>
 *   <li><b>EUR</b> - Euro</li>
 *  </ul>
 * This enum is used throughout the application to ensure consistency when referring to currency types.
 */
public enum Currency {

  /**
   * Czech Koruna (CZK) - The official currency of the Czech Republic.
   */
  CZK,

  /**
   * Euro (EUR) - The official currency of the Eurozone, used by many European Union countries.
   */
  EUR
}
