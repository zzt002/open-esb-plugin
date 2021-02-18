package com.dongdl.springboot1.dao.common1A4dao;

import com.dongdl.springboot1.bean.RegisterBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/25 17:15 UTC+8
 * @description 
 **/
public interface IRegisterDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/23 10:02 UTC+8
     * @description post_conf   update is_conf to 1
     * @param atomCode
     * @return
     */
    Integer updatePostConf(@Param("atomCode")String atomCode);

    /**
     * save post_conf
     * @param entity
     * @return
     */
    Integer savePostConf(RegisterBean entity);

    /**
     * list
     * @return
     */
    List<RegisterBean> getPostConfList();

    /**
     * get esb.seq_esb_atomservice.nextval.
     * @return
     */
    Integer getAtomServiceSeq();

    /**
     * get atoservice info by id
     * @param id
     * @return
     */
    Integer getOneAtomService(@Param("id")Integer id);

    /**
     * 原子服务基础信息
     * @param seq
     * @param atomCode
     * @return
     */
    Integer saveAtomService(@Param("seq")Integer seq, @Param("atomCode")String atomCode);

    /**
     * insert new
     * 原子服务参数信息
     * @param atomCode
     * @return
     */
    Integer saveAtoParamConf(@Param("atomCode")String atomCode);

    /**
     * 原子服务日志输出配置
     * @param atomCode
     * @return
     */
    Integer saveAtoLog(@Param("atomCode")String atomCode);

    /**
     * 原子服务HTTP适配信息
     * @param httpType 1-get 2-post
     * @param atomCode
     * @return
     */
    Integer saveAdapterHttp(@Param("httpType")Integer httpType, @Param("atomCode")String atomCode);

    /**
     * 原子服务HTTP适配信息参数列表
     * @param atomCode
     * @return
     */
    Integer saveAdapterHttpParam(@Param("atomCode")String atomCode);

    /**
     * 原子服务部署信息
     * @param atomCode
     * @return
     */
    Integer saveServiceDeployConfOfSubapp(@Param("atomCode")String atomCode);

    /**
     * 服务组件基本信息
     * @param atomCode
     * @return
     */
    Integer saveWorkflowAttr(@Param("atomCode")String atomCode);

    /**
     * 服务组件sdokey
     * @param atomCode
     * @return
     */
    Integer saveWorkflowSdokey(@Param("atomCode")String atomCode);

    /**
     * 服务组件workflow
     * @param atomCode
     * @return
     */
    Integer saveWorkflow(@Param("atomCode")String atomCode);

    /**
     * 服务组件action
     * @param saveLog '0'不保存日志 '1'保存日志
     * @param atomCode
     * @return
     */
    Integer saveAction(@Param("saveLog")String saveLog, @Param("atomCode")String atomCode);

    /**
     * 服务组件action_invoke
     * @param atomCode
     * @return
     */
    Integer saveActionInvoke(@Param("atomCode")String atomCode);

    /**
     * 服务组件sca_atom_sdokey
     * @param atomCode
     * @return
     */
    Integer saveScaAtomSdokey(@Param("atomCode")String atomCode);

    /**
     * esb.seq_esb_inbound_main.nextval
     * @return
     */
    Integer getInboundMainSeq();

    /**
     *
     * @param esbServiceId
     * @return
     */
    Integer getOneInboundMainId(@Param("esbServiceId")Integer esbServiceId);

    /**
     * 服务基本信息
     * @param seq
     * @param atomCode
     * @return
     */
    Integer saveInboundMain(@Param("seq")Integer seq, @Param("atomCode")String atomCode);

    /**
     * 服务http信息
     * 1-get 2-post
     * @param httpType
     * @param atomCode
     * @return
     */
    Integer saveInboundHttp(@Param("httpType")Integer httpType, @Param("atomCode")String atomCode);

    /**
     * 服务部署信息 app
     * @param atomCode
     * @return
     */
    Integer saveServiceDeployConfOfApp(@Param("atomCode")String atomCode);

    /**
     * 服务目录树
     * @param belongId
     * @param atomCode
     * @return
     */
    Integer saveServiceTree(@Param("belongId")Integer belongId, @Param("atomCode")String atomCode);

    /**
     * 服务和原子服务对应关系
     * @param atomCode
     * @return
     */
    Integer saveServiceAtomService(@Param("atomCode")String atomCode);

    @ResultType(java.util.Map.class)
    @Select("")
    List<Map> listTreeDep(@Param("name")String name);

    @Select("")
    Integer getCountAtomServiceByCodeOrName(@Param("atomCode")String atomCode, @Param("portName")String portName);

    @Select("")
    Integer getCountInboundMainByServcode(@Param("servcode")String servcode);

    @Select("")
    Integer getAtomServiceIdByServiceCode(@Param("atomCode")String atomCode);

    @Select("")
    Integer getEsbServiceIdByScaid(@Param("atomCode")String atomCode);

    /**
     * 1.删除原子服务基础信息
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delAtomServiceById(@Param("atomId")Integer atomId);

    /**
     * 2.删除原子服务参数信息
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delAtoParamConfByServiceId(@Param("atomId")Integer atomId);

    /**
     * 3.删除原子服务日志输出配置
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delAtoLogByServiceId(@Param("atomId")Integer atomId);

    /**
     * 4.删除原子服务HTTP适配信息
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delAdapterHttpById(@Param("atomId")Integer atomId);

    /**
     * 5.删除原子服务HTTP适配信息参数列表
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delAdapterHttpParamByServiceId(@Param("atomId")Integer atomId);

    /**
     * 7.删除服务组件基本信息
     * @param atomCode
     * @return
     */
    @Delete("")
    Integer delWorkflowAttrByScaid(@Param("atomCode")String atomCode);

    /**
     * 8.删除服务组件sdokey
     * @param atomCode
     * @return
     */
    @Delete("")
    Integer delWorkflowSdokeyByScaid(@Param("atomCode")String atomCode);

    /**
     * 9.删除服务组件sdokey
     * @param atomCode
     * @return
     */
    @Delete("")
    Integer delWorkflowByScaid(@Param("atomCode")String atomCode);

    /**
     * 10.删除服务组件action
     * @param atomCode
     * @return
     */
    @Delete("")
    Integer delActionByActionid(@Param("atomCode")String atomCode);

    /**
     * 11.删除服务组件action_invoke
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delActionInvokeByServiceId(@Param("atomId")Integer atomId);

    /**
     * 12.删除服务组件sca_atom_sdokey
     * @param atomCodeReg
     * @return
     */
    @Delete("")
    Integer delScaAtomSdokeyByActionid(@Param("atomCodeReg")String atomCodeReg);

    /**
     * 13.删除服务基本信息
     * @param esbServiceId
     * @return
     */
    @Delete("")
    Integer delInboundMainByEsbserviceid(@Param("esbServiceId")Integer esbServiceId);

    /**
     * 14.删除服务http信息
     * @param esbServiceId
     * @return
     */
    @Delete("")
    Integer delInboundHttpByEsbserviceid(@Param("esbServiceId")Integer esbServiceId);

    /**
     * 18.删除服务目录树
     * @param esbServiceId
     * @return
     */
    @Delete("")
    Integer delServiceTreeByCode(@Param("esbServiceId")Integer esbServiceId);

    /**
     * 19.删除服务和原子服务对应关系
     * @param esbServiceId
     * @param atomId
     * @return
     */
    @Delete("")
    Integer delServiceAtomServiceByEsbserviceidAndAtomId(@Param("esbServiceId")Integer esbServiceId, @Param("atomId")Integer atomId);

    /**
     * 删除原子服务部署信息
     * @param atomId
     * @param esbServiceId
     * @return
     */
    @Delete("")
    Integer delServiceDeployConf(@Param("atomId")Integer atomId, @Param("esbServiceId")Integer esbServiceId);

    /**
     * 获取需要删除的已注册列表
     * @return list
     */
    @Select("")
    List<RegisterBean> getPostConfDelList();

    @Update("")
    Integer resetHttpConfByAtomCode(@Param("atomCode")String atomCode);

    @Delete("")
    Integer delPostConfByAtomCode(@Param("atomCode")String atomCode);

    /**
     * 服务目录菜单id
     *
     * @param belongId
     * @return
     */
    @Select("")
    Integer getOneTreeById(@Param("belongId")Integer belongId);

    /**
     * 通过名称查询服务目录二级目录菜单id
     * by name
     * @param belongName
     * @return
     */
    @Select("")
    Integer getBelongIdByBelongName(@Param("belongName")String belongName);
}
