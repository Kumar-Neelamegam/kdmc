package kdmc_kumar.Utilities_Others;

import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation1 {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{10}";
    private static final String PHONE_REGEX1 = "\\d{10}";
    private static final String PHONE_REGEX2 = "\\d{11}";
    private static final String PINCODE_REGEX = "\\d{6}";
    private static final String PIN_REGEX = "\\d{4}";
    private static final String PIN_REGEX5 = "\\d{5}";
    private static final String NAME_REGEX = "^[a-zA-Z \\./-]*$";
    private static final String DOC_NAME_REGEX = "^[a-zA-Z /-]*$";
    private static final String NEWPATIENT_NAME_REGEX = "^[a-zA-Z \\./-]*$";
    private static final String APLHANUMERIC_REGEX = "^[a-zA-Z0-9-\\./-]*$";
    private static final String APLHANUMERIC_PATREG_REGEX = "[-0-9A-Za-z.,#@/() ]+";
    private static final String NUMERIC_REGEX = "^[0-9-\\]";
    private static final String APLHABETS_REGEX = "[A-Za-z ]+";
    //
    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "10 Digit";
    private static final String PHONE1_MSG = "10 Digit";
    private static final String PHONE2_MSG = "11 Digit";
    private static final String PINCODE_MSG = "6 Digit";
    private static final String PIN_NUMBER = "4 Digit";
    private static final String PIN_NUMBER5 = "5 Digit";
    private static final String NAME_MSG = "Only Alphabets";
    private static final String APLHANUMERIC_MSG = "Only AlphaNumeric";
    private static final String NUMERIC_MSG = "Only Numeric";

    public Validation1() {
    }


    // ////////////////////////////////////////////////////////////////////////////////////
    // call this method when you need to check phone number validation
    public static boolean isName(EditText editText, boolean required) {
        return isValid(editText, NAME_REGEX, NAME_MSG, required);
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {

        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {

        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // call this method when you need to check phone number validation
    public static boolean isPinNumber(EditText editText, boolean required) {

        return isValid(editText, PIN_REGEX, PIN_NUMBER, required);
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////
    // call this method when you need to check phone number validation
    public static boolean isPinNumber5(EditText editText, boolean required) {

        return isValid(editText, PIN_REGEX5, PIN_NUMBER5, required);
    }

    // ////////////////////////////////////////////////////////////////////////////////////

    // call this method when you need to check phone number validation
    public static boolean NewpatientisName(EditText editText, boolean required) {
        return isValid(editText, NEWPATIENT_NAME_REGEX, NAME_MSG, required);
    }

    public static boolean isAlphaNumeric(EditText editText, boolean required) {
        return isValid(editText, APLHANUMERIC_REGEX, APLHANUMERIC_MSG, required);
    }
    // call this method when you need to check phone number validation


    //If text present it will check validation
//******************************************************************************	
    // to check whether the text is null and pattern matches same...
    public static boolean isPincode(EditText editText, boolean required) {
        return isValid1(editText, PINCODE_REGEX, PINCODE_MSG, required);
    }

    // call this method when you need to check email validation
    public static boolean isEmailAddressChk(EditText editText, boolean required) {

        return isValid1(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check email validation
    public static boolean isMobileNumber(EditText editText, boolean required) {

        return isValid1(editText, PHONE_REGEX1, PHONE1_MSG, required);
    }

    // call this method when you need to check email validation
    public static boolean isLandline(EditText editText, boolean required) {

        return isValid1(editText, PHONE_REGEX2, PHONE2_MSG, required);
    }

    public static boolean ifisAlphaNumeric(EditText editText, boolean required) {
        return isValid1(editText, APLHANUMERIC_REGEX, APLHANUMERIC_MSG, required);
    }

    public static boolean ifisAlphaNumericPatreg(EditText editText, boolean required) {
        return isValid1(editText, APLHANUMERIC_PATREG_REGEX, APLHANUMERIC_MSG, required);
    }

    public static boolean ifisName(EditText editText, boolean required) {
        return isValid1(editText, NAME_REGEX, NAME_MSG, required);
    }

    public static boolean ifisAlphabetsOnly(EditText editText, boolean required) {
        return isValid1(editText, APLHABETS_REGEX, NAME_MSG, required);
    }


    /*public static boolean ifisNumeric(EditText editText, boolean required) {
        return isValid1(editText, NUMERIC_REGEX, NUMERIC_MSG, required);
    }*/
    // ////////////////////////////////////////////////////////////////////////////////////
    // return true if the input field is valid, based on the parameter passed
    private static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText))

            return false;

        if (!hasText(editText))
            return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {

            editText.requestFocus();
            editText.setError(errMsg);

            return false;
        }

        return true;
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            editText.requestFocus();
            return false;
        }

        return true;
    }


    // ////////////////////////////////////////////////////////////////////////////////////
    // return true if the input field is valid, based on the parameter passed
    private static boolean isValid1(EditText editText, String regex,
                                    String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        /*
		 * // clearing the error, if it was previously set by some other values
		 * editText.setError(null);
		 */

        // pattern doesn't match so returning false
        if (!editText.getText().toString().trim().equalsIgnoreCase("")) {
            if (Pattern.matches(regex, text)) {
                editText.setError(null);
                editText.clearFocus();

            } else {
                editText.setError(errMsg);
                editText.requestFocus();
                return false;
            }
        }

        return true;
    }

}