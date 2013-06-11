/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

public class Constants {
    public static final int ERR_INVALID_IMAGE=         1;
    public static final int ERR_NO_GD=                 2;
    public static final int ERR_IMAGE_NOT_EXISTS=      3;
    public static final int ERR_DIRECTORY_READONLY=    4;
    public static final int ERR_UPLOAD_FAILURE=        5;
    public static final int ERR_INVALID_PARAM=         6;
    public static final int ERR_INVALID_IMAGE_TYPE=    7;

    /* 插件相关常数 */
    public static final int ERR_COPYFILE_FAILED=       1;
    public static final int ERR_CREATETABLE_FAILED=    2;
    public static final int ERR_DELETEFILE_FAILED=     3;

    /* 商品属性类型常数 */
    public static final int ATTR_TEXT=                 0;
    public static final int ATTR_OPTIONAL=             1;
    public static final int ATTR_TEXTAREA=             2;
    public static final int ATTR_URL=                  3;

    /* 会员整合相关常数 */
    public static final int ERR_USERNAME_EXISTS=       1; // 用户名已经存在
    public static final int ERR_EMAIL_EXISTS=          2; // Email已经存在
    public static final int ERR_INVALID_USERID=        3; // 无效的user_id
    public static final int ERR_INVALID_USERNAME=      4; // 无效的用户名
    public static final int ERR_INVALID_PASSWORD=      5; // 密码错误
    public static final int ERR_INVALID_EMAIL=         6; // email错误
    public static final int ERR_USERNAME_NOT_ALLOW=    7; // 用户名不允许注册
    public static final int ERR_EMAIL_NOT_ALLOW=       8; // EMAIL不允许注册

    /* 加入购物车失败的错误代码 */
    public static final int ERR_NOT_EXISTS=            1; // 商品不存在
    public static final int ERR_OUT_OF_STOCK=          2; // 商品缺货
    public static final int ERR_NOT_ON_SALE=           3; // 商品已下架
    public static final int ERR_CANNT_ALONE_SALE=      4; // 商品不能单独销售
    public static final int ERR_NO_BASIC_GOODS=        5; // 没有基本件
    public static final int ERR_NEED_SELECT_ATTR=      6; //需要用户选择属性

    /* 购物车商品类型 */
    public static final int CART_GENERAL_GOODS=        0; // 普通商品
    public static final int CART_GROUP_BUY_GOODS=      1; // 团购商品
    public static final int CART_AUCTION_GOODS=        2; // 拍卖商品
    public static final int CART_SNATCH_GOODS=         3; // 夺宝奇兵

    /* 订单状态 */
    public static final int ORDER_UNCONFIRMED=            0; // 未确认
    public static final int ORDER_CONFIRMED=              1; // 已确认
    public static final int ORDER_CANCELED=               2; // 已取消
    public static final int ORDER_INVALID=                3; // 无效
    public static final int ORDER_RETURNED=               4; // 退货

    /* 支付类型 */
    public static final int PAY_ORDER=                 0; // 订单支付
    public static final int PAY_SURPLUS=               1; // 会员预付款

    /* 配送状态 */
    public static final int SHIPPING_UNSHIPPED=              0; // 未发货
    public static final int SHIPPING_SHIPPED=                1; // 已发货
    public static final int SHIPPING_RECEIVED=               2; // 已收货
    public static final int SHIPPING_PREPARING=              3; // 备货中

    /* 支付状态 */
    public static final int PAY_UNPAYED=                0; // 未付款
    public static final int PAY_PAYING=                 1; // 付款中
    public static final int PAY_PAYED=                  2; // 已付款

    /* 综合状态 */
    public static final int CS_AWAIT_PAY=              100; // 待付款：货到付款且已发货且未付款，非货到付款且未付款
    public static final int CS_AWAIT_SHIP=             101; // 待发货：货到付款且未发货，非货到付款且已付款且未发货
    public static final int CS_FINISHED=               102; // 已完成：已确认、已付款、已发货

    /* 缺货处理 */
    public static final int OOS_WAIT=                  0; // 等待货物备齐后再发
    public static final int OOS_CANCEL=                1; // 取消订单
    public static final int OOS_CONSULT=               2; // 与店主协商

    /* 帐户明细类型 */
    public static final int SURPLUS_SAVE=              0; // 为帐户冲值
    public static final int SURPLUS_RETURN=            1; // 从帐户提款

    /* 评论状态 */
    public static final int COMMENT_UNCHECKED=         0; // 未审核
    public static final int COMMENT_CHECKED=           1; // 已审核或已回复(允许显示)
    public static final int COMMENT_REPLYED=           2; // 该评论的内容属于回复

    /* 红包发放的方式 */
    public static final int BONUS_SEND_BY_USER=         0; // 按用户发放
    public static final int BONUS_SEND_BY_GOODS=        1; // 按商品发放
    public static final int BONUS_SEND_BY_ORDER=        2; // 按订单发放
    public static final int BONUS_SEND_BY_PRINT=        3; // 线下发放

    /* 广告的类型 */
    public static final int AD_IMAGE=                    0; // 图片广告
    public static final int AD_FALSH=                  1; // flash广告
    public static final int AD_CODE=                   2; // 代码广告
    public static final int AD_TEXT=                   3; // 文字广告

    /* 是否需要用户选择属性 */
    public static final int ATTR_NOT_NEED_SELECT=      0; // 不需要选择
    public static final int ATTR_NEED_SELECT=          1; // 需要选择

    /* 用户中心留言类型 */
    public static final int MSG_MESSAGE=                 0; // 留言
    public static final int MSG_COMPLAINT=               1; // 投诉
    public static final int MSG_ENQUIRY=                 2; // 询问
    public static final int MSG_CUSTOME=                 3; // 售后
    public static final int MSG_BUY=                     4; // 求购
    public static final int MSG_BUSINESS=                5; // 商家

    /* 团购活动状态 */
    public static final int GROUPBUY_PRE_START=             0; // 未开始
    public static final int GROUPBUY_UNDER_WAY=             1; // 进行中
    public static final int GROUPBUY_FINISHED=              2; // 已结束
    public static final int GROUPBUY_SUCCEED=               3; // 团购成功（可以发货了）
    public static final int GROUPBUY_FAIL=                  4; // 团购失败

    /* 红包是否发送邮件 */
    public static final int BONUS_NOT_MAIL=            0;
    public static final int BONUS_MAIL_SUCCEED=        1;
    public static final int BONUS_MAIL_FAIL=           2;

    /* 商品活动类型 */
    public static final int GOODSACT_SNATCH=                0;
    public static final int GOODSACT_GROUP_BUY=             1;
    public static final int GOODSACT_AUCTION=               2;
    public static final int GOODSACT_POINTS_BUY=             3;

    /* 帐号变动类型 */
    public static final int ACCOUNT_SAVING=                0;     // 帐户冲值
    public static final int ACCOUNT_DRAWING=               1;     // 帐户提款
    public static final int ACCOUNT_ADJUSTING=             2;     // 调节帐户
    public static final int ACCOUNT_OTHER=                99;     // 其他类型

    /* 密码加密方法 */
    public static final int PWD_MD5=                   1;  //md5加密方式
    public static final int PWD_PRE_SALT=              2;  //前置验证串的加密方式
    public static final int PWD_SUF_SALT=              3;  //后置验证串的加密方式

    /* 文章分类类型 */
    public static final int COMMON_CAT=                1; //普通分类
    public static final int SYSTEM_CAT=                2; //系统默认分类
    public static final int INFO_CAT=                  3; //网店信息分类
    public static final int UPHELP_CAT=                4; //网店帮助分类分类
    public static final int HELP_CAT=                  5; //网店帮助分类

    /* 活动状态 */
    public static final int ACT_PRE_START=                 0; // 未开始
    public static final int ACT_UNDER_WAY=                 1; // 进行中
    public static final int ACT_FINISHED=                  2; // 已结束
    public static final int ACT_SETTLED=                   3; // 已处理

    /* 验证码 */
    public static final int CHECK_REGISTER=          1; //注册时使用验证码
    public static final int CHECK_LOGIN=             2; //登录时使用验证码
    public static final int CHECK_COMMENT=           4; //评论时使用验证码
    public static final int CHECK_ADMIN=             8; //后台登录时使用验证码
    public static final int CHECK_LOGIN_FAIL=       16; //登录失败后显示验证码

    /* 优惠活动的优惠范围 */
    public static final int RANGE_ALL=                   0; // 全部商品
    public static final int RANGE_CATEGORY=              1; // 按分类选择
    public static final int RANGE_BRAND=                 2; // 按品牌选择
    public static final int RANGE_GOODS=                 3; // 按商品选择

    /* 优惠活动的优惠方式 */
    public static final int FEE_GOODS=                 0; // 送赠品或优惠购买
    public static final int FEE_PRICE=                 1; // 现金减免
    public static final int FEE_DISCOUNT=              2; // 价格打折优惠

    /* 评论条件 */
    public static final int COMMENT_LOGIN=             1; //只有登录用户可以评论
    public static final int COMMENT_CUSTOM=            2; //只有有过一次以上购买行为的用户可以评论
    public static final int COMMENT_BOUGHT=            3; //只有购买够该商品的人可以评论

    /* 减库存时机 */
    public static final int SDT_SHIP=                  0; // 发货时
    public static final int SDT_PLACE=                 1; // 下订单时

    /* 加密方式 */
    public static final int ENCRYPT_ZC=                1; //zc加密方式

    /* 商品类别 */
    public static final int GOODS_REAL=                    1; //实体商品
    public static final int GOODS_CARD=                    0; //虚拟卡

    /* 积分兑换 */
    public static final int P_TO_POINTS=                      0; //兑换到商城消费积分
    public static final int P_FROM_POINTS=                    1; //用商城消费积分兑换
    public static final int P_TO_RANK=                      2; //兑换到商城等级积分
    public static final int P_FROM_RANK=                    3; //用商城等级积分兑换

    /* 支付宝商家账户 */
    public static final String ALIPAY_AUTH= "gh0bis45h89m5mwcoe85us4qrwispes0";
    public static final String ALIPAY_ID= "2088002052150939";

    /* 添加feed事件到UC的TYPE*/
    public static final int BUY_GOODS=                 1; //购买商品
    public static final int COMMENT_GOODS=             2; //添加商品评论
}
