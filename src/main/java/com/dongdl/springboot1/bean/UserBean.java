package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.util.MD5Utils;
import com.dongdl.springboot1.util.StringUtil;
import lombok.Data;
import lombok.ToString;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 15:17 UTC+8
 * @description
 */
@Data
@ToString
@TableName("esb_user")
public class UserBean extends BaseBean {

    private static final long serialVersionUID = -3299886975045430596L;

    private String account;
    private String password;
    private String name;

    public void valid() {
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new MessageException("账户或密码不能为空");
        }
        account = account.trim();
        password = password.trim();
    }

    public void encodePwd() {
        password = MD5Utils.str2MD5(password);
    }
}
