/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi
 *  * AndrewID : vadjibi
 *  * Program : MSIT
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.model;

import android.content.ContentValues;

import java.util.Properties;

public interface Scannable {
    boolean isInRange();
    boolean userCanAccess(int id);
    String getType();
    String getLocalDatabaseName();
    ContentValues getStorableData();
}
