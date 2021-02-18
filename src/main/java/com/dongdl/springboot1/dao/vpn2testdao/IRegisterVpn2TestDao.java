package com.dongdl.springboot1.dao.vpn2testdao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.stereotype.Repository;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/4/1 10:30 UTC+8
 * @description vpn2测试环境
 **/
@Repository
@CacheNamespace
public interface IRegisterVpn2TestDao {

    @Select("")
    @Options(useCache = false,flushCache = Options.FlushCachePolicy.TRUE)
    Integer getAtomServiceSeq();

    @Select("")
    @Options(useCache = false,flushCache = Options.FlushCachePolicy.TRUE)
    Integer getInboundMainSeq();
}
