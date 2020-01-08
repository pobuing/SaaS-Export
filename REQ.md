## 业务流程 
* 出口报运：**(DONE)**
  * 有3种状态：1.草稿，不能进行电子报运；2.提交，之后才能进行电子报运以及取消；3.取消：提交之后才可以取消
  * 电子报运之后不允许提交和取消
  * 草稿可以删除，提交之后不允许删除，电子报运之后不允许删除
* 装箱管理 **(DONE)**
  * 装箱与出口报运是一对多的关系，多个报运单装入一个集装箱
  * 装箱之后，写入委托单
* 委托管理 **(DONE)**
  * 委托哪个远洋公司进行运输
  * 出发港口，到达港口（可以通过百度地图查看实时位置）
  * 需要增加收件人（委托之后发送一个邮件，邮件内容就是委托单）
  * 有2种状态：1.草稿：由装箱写入；2.委托：委托之后写入发票管理
* 发票管理 **(DONE)**
  * 有2种状态：1.草稿：由委托写入；2.实收：实际收回尾款
  * 草稿状态下，显款应收金额，实收为0
  * 输入实收金额，点击实收，证明尾款已经收回
  * 实收之后写入财务管理
* 财务管理 **(DONE)**
  * 包含合同号、货物号、数量、单价、金额、税金、客户名称

## 系统代码

* 增加一个系统代码的模块，增加货号、对应的厂家、货号的类型（货物、附件）**(DONE)**
* 修改货物管理
* 修改附件管理



## 厂家信息 **(DONE)**

厂家信息的列表，厂家的增删改查

厂家的名称、厂家对应的是货物还是附件（ctype），厂家的联系信息（电话、联系人、联系地址）



## 统计分析

市场最高价前10名：

==货物的销售单价==

登录IP最多前10名：**(DONE)**

127.0.0.1   10次

127.0.0.2    5次

127.0.0.3    6次



## 指定系统管理员

企业管理中增加一个按钮【指定系统管理员】

指定该企业用户的degree=1，发邮件



## 修改密码 **(DONE)**

原密码、新密码、确认新密码 【确定】



以上内容不包含百度地图都是必做项。

70分



# 实战选做

## 微信登录 **(DONE)**

详见\day14\实战\01-微信登录相关



## 百度地图 **(DONE)**

详见\day14\实战\02-百度地图相关

在委托管理中，出发港口、到达港口（北京到长沙）



## 即时通讯

即时通讯（聊天室，老王进入，张三进入，老王和张三可以聊天）



## 每日反馈 **(DONE)**

* 每个人都有每日反馈的权限
* 操作者对当前的软件操作、流程有什么意见可以提交反馈
* 反馈的信息全部由zhangsan（SaaS平台管理员）接收（接收条件不限：邮件接收、小铃铛接收）
