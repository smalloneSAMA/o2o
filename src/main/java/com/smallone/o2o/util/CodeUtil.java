package com.smallone.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 *
 * @author smallone
 * @created 2019--11--13--13:54
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if(verifyCodeActual == null && verifyCodeActual.equals(verifyCodeExpected)){
            return false;
        }
        return true;
    }

}
