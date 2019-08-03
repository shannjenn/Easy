package com.jen.easyui.util;

import java.util.Calendar;
import java.util.regex.Pattern;

public class IDCardUtil {

    /**
     *      * 校验身份证
     *      * 
     *      * @param idCard
     *      * @return 校验通过返回true，否则返回false
     *     
     */
    public static boolean isIDCard(String idCard) {
        if (idCard == null || idCard.length() == 0 || idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)";
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * @param id .
     * @return .
     */
    public static boolean isIDCardNo(String id) {
        if (id == null)
            return false;
        id = id.toUpperCase();
        if (id.length() != 15 && id.length() != 18) {
            return false;
        }
        int y = 0, m = 0, d = 0;
        if (id.length() == 15) {
            y = Integer.parseInt("19" + id.substring(6, 8), 10);
            m = Integer.parseInt(id.substring(8, 10), 10);
            d = Integer.parseInt(id.substring(10, 12), 10);
        } else if (id.length() == 18) {
            if (id.indexOf("X") >= 0 && id.indexOf("X") != 17) {
                return false;
            }
            char verifyBit = 0;
            int sum = (id.charAt(0) - '0') * 7 + (id.charAt(1) - '0') * 9 + (id.charAt(2) - '0') * 10
                    + (id.charAt(3) - '0') * 5 + (id.charAt(4) - '0') * 8 + (id.charAt(5) - '0') * 4
                    + (id.charAt(6) - '0') * 2 + (id.charAt(7) - '0') * 1 + (id.charAt(8) - '0') * 6
                    + (id.charAt(9) - '0') * 3 + (id.charAt(10) - '0') * 7 + (id.charAt(11) - '0') * 9
                    + (id.charAt(12) - '0') * 10 + (id.charAt(13) - '0') * 5 + (id.charAt(14) - '0') * 8
                    + (id.charAt(15) - '0') * 4 + (id.charAt(16) - '0') * 2;
            sum = sum % 11;
            switch (sum) {
                case 0:
                    verifyBit = '1';
                    break;
                case 1:
                    verifyBit = '0';
                    break;
                case 2:
                    verifyBit = 'X';
                    break;
                case 3:
                    verifyBit = '9';
                    break;
                case 4:
                    verifyBit = '8';
                    break;
                case 5:
                    verifyBit = '7';
                    break;
                case 6:
                    verifyBit = '6';
                    break;
                case 7:
                    verifyBit = '5';
                    break;
                case 8:
                    verifyBit = '4';
                    break;
                case 9:
                    verifyBit = '3';
                    break;
                case 10:
                    verifyBit = '2';
                    break;

            }

            if (id.charAt(17) != verifyBit) {
                return false;
            }
            y = Integer.parseInt(id.substring(6, 10), 10);
            m = Integer.parseInt(id.substring(10, 12), 10);
            d = Integer.parseInt(id.substring(12, 14), 10);
        }

        int currentY = Calendar.getInstance().get(Calendar.YEAR);

        /*
         * if(isGecko){ currentY += 1900; }
         */
        if (y > currentY || y < 1870) {
            return false;
        }
        if (m < 1 || m > 12) {
            return false;
        }
        if (d < 1 || d > 31) {
            return false;
        }
        return true;
    }
}
