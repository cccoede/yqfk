package com.web.entity;

import lombok.Data;
import java.util.List;

/*
主菜单栏
 */

@Data
public class Menu {

    private String path;

    private String name;

    private String label;

    private String icon;

    private String url;

    private List<SubMenu> children;



}
