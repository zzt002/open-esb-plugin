package com.dongdl.springboot1.util;

import com.dongdl.springboot1.bean.UserBean;

import java.lang.reflect.Field;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/2/4 17:37 UTC+8
 * @description
 **/
public class BeanUtil {

    /**
     * valid the entity's all fields cannot be blank
     *
     * @param t
     * @param skips
     * @throws IllegalAccessException
     */
    public void validBlank(UserBean t, String... skips) throws IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        A: for (Field f : fields) {
            if (skips != null) {
                for (String skip : skips) {
                    if (f.getName().equals(skip)) {
                        continue A;
                    }
                }
            }
            Object obj = f.get(t);
            if (null == obj || "".equals(String.valueOf(obj).trim())) {
                throw new NullPointerException("Field '" + f.getName() + "' cannot be blank");
            }

        }
    }
}
