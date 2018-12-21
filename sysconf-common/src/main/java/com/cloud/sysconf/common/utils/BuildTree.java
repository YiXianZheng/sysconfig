package com.cloud.sysconf.common.utils;

import com.cloud.sysconf.common.dto.HeaderInfoDto;
import com.cloud.sysconf.common.dto.SysMenuDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther zyx
 * @Date 2018/7/26 20:05
 * @Description:
 */
public class BuildTree {

    /**
     * 创建菜单树形结构
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> SysMenuDto build(List<SysMenuDto> nodes) {

        if(nodes == null){
            return null;
        }
        List<SysMenuDto> topNodes = new ArrayList<SysMenuDto>();

        for (SysMenuDto children : nodes) {

            String pid = children.getParentId();
            if (HeaderInfoDto.AUTH_PLATFORM_SYSTEM.equals(pid) || HeaderInfoDto.AUTH_AGENT_SYSTEM.equals(pid)
                    || HeaderInfoDto.AUTH_MERCHANT_SYSTEM.equals(pid)) {
                topNodes.add(children);
                continue;
            }

            for (SysMenuDto parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);

                    continue;
                }
            }
        }

        SysMenuDto root = new SysMenuDto();
        root.setId("000000");
        root.setParentId("");
        root.setChildren(topNodes);
        root.setName("我的菜单");

        return root;
    }

    /**
     * 创建菜单树形结构  显示系统
     * @param nodes
     * @return
     */
    public static List<SysMenuDto> buildMenu(List<SysMenuDto> nodes) {

        if(nodes == null){
            return null;
        }
        List<SysMenuDto> res = new ArrayList<>();

        for (SysMenuDto children : nodes) {

            children.setKey(children.getId());
            String pid = children.getParentId();
            if ("000000".equals(pid)) {
                res.add(children);
                continue;
            }

            for (SysMenuDto parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {

                    parent.getChildren().add(children);

                    continue;
                }
            }
        }

        for (SysMenuDto children : res) {

            children.setKey(children.getId());
            String pid = children.getParentId();
            if (HeaderInfoDto.AUTH_PLATFORM_SYSTEM.equals(pid) || HeaderInfoDto.AUTH_AGENT_SYSTEM.equals(pid)
                    || HeaderInfoDto.AUTH_MERCHANT_SYSTEM.equals(pid)) {
                res.add(children);
                continue;
            }

            for (SysMenuDto parent : res) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {

                    parent.getChildren().add(children);

                    continue;
                }
            }
        }

        return res;
    }

}
