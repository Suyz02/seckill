package com.xxxx.seckill.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.IsMobile;

public class IsMobileValitation implements ConstraintValidator<IsMobile,String> {

    private boolean req = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        req = constraintAnnotation.required();
    }

    //使用正则表达式验证手机号码
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (req){
            return ValitationUtil.isMobile(value);
        }else {
            if (!StringUtils.hasLength(value)){
                return true;
            }else {
                return ValitationUtil.isMobile(value);
            }
        }
    }
}
