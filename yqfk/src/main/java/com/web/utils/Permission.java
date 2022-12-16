package com.web.utils;

import com.web.entity.Menu;
import com.web.entity.SubMenu;
import java.util.ArrayList;
import java.util.List;

public class Permission {

    //管理员权限
    public static List<Menu>  getManagePermission(){

        List<Menu> list = new ArrayList<Menu>();

        Menu bean1 = new Menu();
        bean1.setPath("/home");
        bean1.setName("home");
        bean1.setLabel("首页");
        bean1.setIcon("s-home");
        bean1.setUrl("home/index.vue");
        list.add(bean1);

        Menu bean2 = new Menu();
        bean2.setPath("/gonggao");
        bean2.setName("gonggao");
        bean2.setLabel("疫情信息管理");
        bean2.setIcon("notebook-1");
        bean2.setUrl("gonggao/index.vue");
        list.add(bean2);

        Menu bean3 = new Menu();
        bean3.setPath("/userlist");
        bean3.setName("userlist");
        bean3.setLabel("工作人员管理");
        bean3.setIcon("user-solid");
        bean3.setUrl("user/userlist.vue");
        list.add(bean3);



        Menu bean6 = new Menu();
        bean6.setPath("/usermanage");
        bean6.setName("usermanage");
        bean6.setLabel("账户管理");
        bean6.setIcon("user-solid");
        bean6.setUrl("usermanage/index.vue");
        list.add(bean6);

        Menu bean7 = new Menu();

        bean7.setLabel("疫情管理");
        bean7.setIcon("location");

        List<SubMenu> children = new ArrayList<>();

        SubMenu subMenu11 = new SubMenu();
        subMenu11.setPath("/userlist2");
        subMenu11.setName("userlist2");
        subMenu11.setLabel("易感人群管理");
        subMenu11.setUrl("user/userlist2.vue");
        children.add(subMenu11);

        SubMenu subMenu12 = new SubMenu();
        subMenu12.setPath("/yimiaolist");
        subMenu12.setName("yimiaolist");
        subMenu12.setLabel("疫苗接种管理");
        subMenu12.setUrl("yimiao/yimiaolist.vue");
        children.add(subMenu12);

        SubMenu subMenu13 = new SubMenu();
        subMenu13.setPath("/hesuanlist");
        subMenu13.setName("hesuanlist");
        subMenu13.setLabel("核酸检测管理");
        subMenu13.setUrl("hesuan/hesuanlist.vue");
        children.add(subMenu13);

        SubMenu subMenu15 = new SubMenu();
        subMenu15.setPath("/hesuanlist3");
        subMenu15.setName("hesuanlist3");
        subMenu15.setLabel("检测阳性复核");
        subMenu15.setUrl("hesuan/hesuanlist3.vue");
        children.add(subMenu15);

        SubMenu subMenu14 = new SubMenu();
        subMenu14.setPath("/baobeilist2");
        subMenu14.setName("baobeilist2");
        subMenu14.setLabel("报备信息查询");
        subMenu14.setUrl("baobei/baobeilist2.vue");
        children.add(subMenu14);


        bean7.setChildren(children);
        list.add(bean7);


        Menu bean8 = new Menu();

        bean8.setLabel("工作统计");
        bean8.setIcon("menu");

        List<SubMenu> children3 = new ArrayList<>();

        SubMenu subMenu31 = new SubMenu();
        subMenu31.setPath("/yimiaotjlist");
        subMenu31.setName("yimiaotjlist");
        subMenu31.setLabel("疫苗接种统计");
        subMenu31.setUrl("yimiaotj/yimiaotjlist.vue");
        children3.add(subMenu31);

        SubMenu subMenu32 = new SubMenu();
        subMenu32.setPath("/hesuantjlist");
        subMenu32.setName("hesuantjlist");
        subMenu32.setLabel("核酸检测统计");
        subMenu32.setUrl("hesuantj/hesuantjlist.vue");
        children3.add(subMenu32);

        bean8.setChildren(children3);
        list.add(bean8);

        return list;
    }



    //工作人员权限
    public static List<Menu>  getUserPermission(){

        List<Menu> list = new ArrayList<Menu>();

        Menu bean1 = new Menu();
        bean1.setPath("/home");
        bean1.setName("home");
        bean1.setLabel("首页");
        bean1.setIcon("s-home");
        bean1.setUrl("home/index");
        list.add(bean1);

        Menu bean2 = new Menu();
        bean2.setPath("/gonggao");
        bean2.setName("gonggao");
        bean2.setLabel("疫情信息查询");
        bean2.setIcon("notebook-1");
        bean2.setUrl("gonggao/gonggaolist.vue");
        list.add(bean2);

        Menu bean3 = new Menu();
        bean3.setPath("/userlist2");
        bean3.setName("userlist2");
        bean3.setLabel("易感人群管理");
        bean3.setIcon("s-goods");
        bean3.setUrl("user/userlist2.vue");
        list.add(bean3);

        Menu bean4 = new Menu();
        bean4.setPath("/yimiaolist");
        bean4.setName("yimiaolist");
        bean4.setLabel("疫苗接种管理");
        bean4.setIcon("s-cooperation");
        bean4.setUrl("yimiao/yimiaolist.vue");
        list.add(bean4);

        Menu bean5 = new Menu();
        bean5.setPath("/hesuanlist");
        bean5.setName("hesuanlist");
        bean5.setLabel("核酸检测管理");
        bean5.setIcon("s-order");
        bean5.setUrl("hesuan/hesuanlist.vue");
        list.add(bean5);

        Menu bean6 = new Menu();
        bean6.setPath("/baobeilist2");
        bean6.setName("baobeilist2");
        bean6.setLabel("报备信息查询");
        bean6.setIcon("user-solid");
        bean6.setUrl("baobei/baobeilist2.vue");
        list.add(bean6);

        return list;
    }

    //易感人群权限
    public static List<Menu>  getVipPermission(){

        List<Menu> list = new ArrayList<Menu>();

        Menu bean1 = new Menu();
        bean1.setPath("/home");
        bean1.setName("home");
        bean1.setLabel("首页");
        bean1.setIcon("s-home");
        bean1.setUrl("home/index");
        list.add(bean1);

        Menu bean2 = new Menu();
        bean2.setPath("/gonggao");
        bean2.setName("gonggao");
        bean2.setLabel("疫情信息查询");
        bean2.setIcon("notebook-1");
        bean2.setUrl("gonggao/gonggaolist.vue");
        list.add(bean2);

        Menu bean3 = new Menu();
        bean3.setPath("/yimiaolist2");
        bean3.setName("yimiaolist2");
        bean3.setLabel("疫苗接种查询");
        bean3.setIcon("s-goods");
        bean3.setUrl("yimiao/yimiaolist2.vue");
        list.add(bean3);

        Menu bean4 = new Menu();
        bean4.setPath("/hesuanlist2");
        bean4.setName("hesuanlist2");
        bean4.setLabel("核酸检测查询");
        bean4.setIcon("s-order");
        bean4.setUrl("hesuan/hesuanlist2.vue");
        list.add(bean4);

        Menu bean5 = new Menu();
        bean5.setPath("/baobeilist");
        bean5.setName("baobeilist");
        bean5.setLabel("报备管理");
        bean5.setIcon("tickets");
        bean5.setUrl("baobei/baobeilist.vue");
        list.add(bean5);

        return list;
    }

}
