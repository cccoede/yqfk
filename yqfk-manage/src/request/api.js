import { post,get} from './http'

//登录
export const userlogin = (p) => post('/login', p);

//修改密码
export const userpassword = (p) => post('/password', p);

//退出系统
export const userloginout = (p) => post('/loginout', p);

//个人中心获取用户信息
export const getUserinfo = (p) => post('/getUserinfo', p);

//个人信息修改
export const userinfoUpdate = (p) => post('/userinfoUpdate', p);

//用户列表
export const usermanagelist = (p) => post('/searchList', p);

//重置密码
export const passwordreset = (p) => get('/passwordreset', p);

//禁用或者启动账户
export const resetstatus = (p) => get('/statusreset', p);


//用户注册
export const userregister = (p) => post('/register', p);

//首页用户信息
export const homeuserinfo = (p) => post('/home/userinfo', p);


//疫情信息列表
export const gonggaolist = (p) => post('/gonggao/searchList', p);

//新增疫情信息
export const gonggaoadd = (p) => post('/gonggao/add', p);

//根据id删除疫情信息
export const gonggaodel = (p) => get('/gonggao/delete', p);

//根据id获取疫情信息
export const gonggaoByid = (p) => get('/gonggao/getById', p);

//根据id编辑疫情信息公告
export const gonggaoupdate = (p) => post('/gonggao/update', p);


//用户信息列表
export const userlist = (p) => post('/user/searchList', p);

//新增用户信息
export const useradd = (p) => post('/user/add', p);

//根据id删除用户信息
export const userdel = (p) => get('/user/delete', p);

//根据id获取用户信息
export const userByid = (p) => get('/user/getById', p);

//根据id编辑用户信息
export const userupdate = (p) => post('/user/update', p);


//疫苗接种信息列表
export const yimiaolist = (p) => post('/yimiao/searchList', p);

//新增疫苗接种信息
export const yimiaoadd = (p) => post('/yimiao/add', p);

//根据id删除疫苗接种信息
export const yimiaodel = (p) => get('/yimiao/delete', p);

//根据id获取疫苗接种信息
export const yimiaoByid = (p) => get('/yimiao/getById', p);

//根据id编辑疫苗接种信息
export const yimiaoupdate = (p) => post('/yimiao/update', p);


//核酸检测信息列表
export const hesuanlist = (p) => post('/hesuan/searchList', p);

//新增核酸检测信息
export const hesuanadd = (p) => post('/hesuan/add', p);

//根据id删除核酸检测信息
export const hesuandel = (p) => get('/hesuan/delete', p);

//根据id获取核酸检测信息
export const hesuanByid = (p) => get('/hesuan/getById', p);

//根据id编辑核酸检测信息
export const hesuanupdate = (p) => post('/hesuan/update', p);


//报备信息列表
export const baobeilist = (p) => post('/baobei/searchList', p);

//新增报备信息
export const baobeiadd = (p) => post('/baobei/add', p);

//根据id删除报备信息
export const baobeidel = (p) => get('/baobei/delete', p);

//根据id获取报备信息
export const baobeiByid = (p) => get('/baobei/getById', p);

//根据id编辑报备信息
export const baobeiupdate = (p) => post('/baobei/update', p);


//疫苗接种统计
export const yimiaotjlist = (p) => post('/yimiaotj/searchList', p);

//疫苗接种统计
export const yimiaotjtongji = (p) => post('/yimiaotj/tongji', p);

//核酸检测统计
export const hesuantjlist = (p) => post('/hesuantj/searchList', p);

//核酸检测统计
export const hesuantjtongji = (p) => post('/hesuantj/tongji', p);

//根据id复核阳性
export const hesuanupdate2 = (p) => post('/hesuan/update2', p);

//根据id复核阴性
export const hesuanupdate3 = (p) => post('/hesuan/update3', p);

//导入核酸数据
export const hesuanexceladd = (p) => post('/hesuan/exceladd', p);

//导出核酸数据
export const hesuanexcelout = (p) => post('/hesuan/excelout', p);




