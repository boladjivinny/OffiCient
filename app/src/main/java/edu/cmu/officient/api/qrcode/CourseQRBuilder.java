/*
 *
 *  * @author Segla Boladji Vinny Trinite Adjibi
 *  * AndrewID : vadjibi
 *  * Program : MSIT
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

/*
 *
 *  * @author Solange Iyubu
 *  * AndrewID : siyubu
 *  * Program : ECE
 *  *
 *  * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */

package edu.cmu.officient.api.qrcode;

import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import edu.cmu.officient.exception.BuilderException;

public class CourseQRBuilder extends QRBuilder {
    public CourseQRBuilder(){
        super();
    }

    public QRGEncoder build(){
        BuilderException exception = QRBuilderExceptionFactory.getBuilderException(this);
        try {
            if (exception != null) {
                throw exception;
            }
        }
        catch (BuilderException e) {
            e.fix();
        }
        String text = String.format(Locale.getDefault(), "OWNER_ID=%d\nID=%d\nNAME=%s\nTYPE=%s", getOwnerId(), getId(), getName(), getType());
        return new QRGEncoder(text, null, QRGContents.Type.TEXT, 1000);
    }
}