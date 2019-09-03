/*
SQLyog Ultimate v11.22 (64 bit)
MySQL - 5.6.39 : Database - tzsc_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tzsc_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tzsc_db`;

/*Table structure for table `admin_tb` */

DROP TABLE IF EXISTS `admin_tb`;

CREATE TABLE `admin_tb` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_name` varchar(10) NOT NULL,
  `a_pwd` varchar(32) NOT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin_tb` */

insert  into `admin_tb`(`a_id`,`a_name`,`a_pwd`) values (1,'superadmin','1db83d6eb63e03302171c3b027bdeb24');

/*Table structure for table `comment_tb` */

DROP TABLE IF EXISTS `comment_tb`;

CREATE TABLE `comment_tb` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论的编号',
  `c_content` text NOT NULL COMMENT '评论的内容',
  `u_id` int(11) NOT NULL COMMENT '谁评论的',
  `g_id` int(11) NOT NULL COMMENT '对哪条商品的评论',
  `c_time` varchar(40) NOT NULL,
  PRIMARY KEY (`c_id`),
  KEY `cfor` (`u_id`),
  KEY `for1` (`g_id`),
  CONSTRAINT `cfor` FOREIGN KEY (`u_id`) REFERENCES `users_tb` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `for1` FOREIGN KEY (`g_id`) REFERENCES `goods_tb` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4;

/*Data for the table `comment_tb` */

insert  into `comment_tb`(`c_id`,`c_content`,`u_id`,`g_id`,`c_time`) values (1,'我去，这还需要拿来买吗',12,137,'1462964455147'),(2,'便宜点咯 帮我送到寝室',12,137,'1462964478429'),(4,'你发的啊',12,137,'1462967112374'),(5,'【出价】￥1 ( 手机：15519099928 )',12,136,'1462967887811'),(6,'这么好的东西干嘛要卖了！',14,138,'1463167685024'),(7,'【出价】￥30 ( 手机：15685188016 )',14,136,'1463167709509'),(8,'马上毕业了才要卖的，不想带走太多东西',12,138,'1463201559245'),(9,'过期没有哟',12,137,'1463202208790'),(10,'【出价】￥35 ( 手机：15519099928 )',12,140,'1463213213812'),(11,'【出价】￥31 ( 手机：15519099928 )',12,136,'1463213234782'),(12,'【出价】￥36 ( 手机：15685188016 )',14,140,'1463213811355'),(13,'觉对物超所值',14,141,'1463216144185'),(14,'看着还不错的样子',12,141,'1463217143298'),(15,'多少钱呢',14,147,'1463317576472'),(16,'真不钱呀， 太好了，给我啊',14,147,'1463317651114'),(17,'【出价】￥9 ( 手机：15685188016 )',14,144,'1463318716558'),(18,'真不要钱',12,147,'1463383961180'),(19,'叶子正在枯萎，说明要开花了，要的快入手',12,150,'1463825520957'),(20,'送一株橡皮树',12,150,'1463825585020'),(21,'【出价】￥248 ( 手机：15519099928 )',12,145,'1463963601859'),(22,'多少买的呀？',12,157,'1464254852392'),(23,'【出价】￥15 ( 手机：15685188016 )',14,150,'1464255509938'),(24,'【出价】￥5 ( 手机：15519099928 )',12,144,'1464260019514'),(26,'没人要？',12,155,'1464261289775'),(27,'这是啥？',36,161,'1464270445932'),(28,'求送',36,156,'1464270572169'),(29,'还有一本星火英语的大学英语词汇分频语境记忆',12,164,'1464274491640'),(30,'全都免费送了',12,164,'1464274517360'),(31,'可扫描 彩色打印 复印  打印照片',12,145,'1464275174249'),(32,'求求求',39,156,'1464278776791'),(33,'求送',39,154,'1464278868902'),(34,'跪求',41,156,'1464281509144'),(35,'消息加一',42,156,'1464283419637'),(36,'已经送出去了，下午来拿。如果下午没有来就继续送，拿了之后就下架',12,159,'1464311770809'),(37,'之前没有注意看，要的请选择联系卖家  打电话或发短信给我，我告诉你具体位置，你来去',12,156,'1464333299001'),(38,'等你来拿',12,154,'1464333339533'),(39,'这个好难考过呀',12,166,'1464333422550'),(40,'还在吗',44,154,'1464337777250'),(41,'还在的，需要的点击“联系卖家”可以打电话发短信给我',12,154,'1464354982400'),(42,'先联系先得',12,154,'1464355114122'),(43,'【出价】￥30 ( 手机：18636585611 )',54,144,'1552467394109'),(44,'hao',54,144,'1552467407873'),(45,'【出价】￥50 ( 手机：18636585611 )',54,176,'1552472352507'),(46,'【出价】￥51 ( 手机：18636585611 )',54,176,'1552472365135');

/*Table structure for table `goods_tb` */

DROP TABLE IF EXISTS `goods_tb`;

CREATE TABLE `goods_tb` (
  `g_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_title` varchar(80) NOT NULL DEFAULT ' ' COMMENT '标题',
  `u_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `g_price` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `g_oldPrice` int(11) NOT NULL DEFAULT '0' COMMENT '原价，0表示免费',
  `g_publishType` smallint(6) NOT NULL DEFAULT '0' COMMENT '发布的类型,0表示发布新品，1表示拍卖，2表示免费送',
  `g_state` bit(1) NOT NULL DEFAULT b'0' COMMENT '审核状态,0表示未卖出，1表示已经卖出了',
  `g_desc` text NOT NULL COMMENT '商品的描述',
  `g_pinkage` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否包邮，0不包邮，1包邮',
  `g_urgent` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是紧急商品，0表示不紧急，1表示紧急',
  `g_address` varchar(20) NOT NULL DEFAULT ' ',
  `g_browCount` smallint(6) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `g_time` varchar(40) NOT NULL DEFAULT ' ' COMMENT '发布时间',
  `t_id` int(11) NOT NULL DEFAULT '0',
  `g_deadline` varchar(32) DEFAULT NULL COMMENT '截至时间',
  `g_nice` smallint(6) NOT NULL DEFAULT '0' COMMENT '点赞数量',
  PRIMARY KEY (`g_id`),
  KEY `u_id` (`u_id`),
  KEY `tidfor` (`t_id`),
  CONSTRAINT `idfor` FOREIGN KEY (`u_id`) REFERENCES `users_tb` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tidfor` FOREIGN KEY (`t_id`) REFERENCES `type_tb` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;

/*Data for the table `goods_tb` */

insert  into `goods_tb`(`g_id`,`g_title`,`u_id`,`g_price`,`g_oldPrice`,`g_publishType`,`g_state`,`g_desc`,`g_pinkage`,`g_urgent`,`g_address`,`g_browCount`,`g_time`,`t_id`,`g_deadline`,`g_nice`) values (118,'56465',12,5,54,0,'','54','','\0','丹桂苑4栋',4,'1456312080000',0,'null',0),(119,'地方还是',12,1111,1111,1,'','阿萨德嘎嘎过撒旦噶','','\0','樱花苑1栋',2,'1458817680000',1,'1465523820000',0),(120,'哈哈',12,20,28,1,'','力量力量力量','','\0','樱花苑2栋',3,'1461496080000',6,'1465524300000',0),(121,'四大噶',12,1234,12,1,'','撒旦噶改改','','\0','樱花苑1栋',1,'1462846196972',0,'1465524540000',0),(122,'是东方红',12,112,213,1,'','的萨嘎会跟','','\0','樱花苑1栋',1,'1462846557697',0,'1462932900000',0),(123,'发货脚后跟',12,321,546,1,'','ui哦硅谷看空间','\0','\0','樱花苑1栋',4,'1462846928731',4,'1462933260000',0),(124,'123',12,123,123,1,'','123','','\0','樱花苑1栋',2,'1461496080000',1,'1462933440000',0),(125,'改',12,1,5,1,'','睡觉了','','\0','丹桂苑2栋',8,'1462847419192',0,'1468117740000',0),(126,'过来了',12,0,0,2,'','可怜巴巴','\0','\0','樱花苑1栋',3,'1462884900840',2,'null',0),(127,'见',12,0,0,2,'','她','\0','\0','樱花苑1栋',3,'1462884919956',3,'null',0),(128,'她',12,0,0,2,'','喔','\0','\0','樱花苑1栋',3,'1462884934005',0,'null',0),(129,'3',12,0,0,2,'','9','\0','\0','樱花苑1栋',1,'1462884947786',4,'null',0),(130,'9',12,0,0,2,'','图','\0','\0','樱花苑1栋',6,'1462884973197',0,'null',0),(131,'再',12,0,0,2,'','就','\0','\0','樱花苑1栋',10,'1462884989296',5,'null',0),(132,'了',12,0,0,2,'','见','\0','\0','樱花苑1栋',2,'1462885008694',0,'null',0),(136,'1',12,1,1,1,'','1','','\0','樱花苑1栋',26,'1462943904019',6,'1465579020000',0),(137,'山花纯牛奶3元',12,3,3,0,'','快来','','','樱花苑2栋',17,'1462963168746',7,'null',0),(138,'正版联想键盘，堪比机械键盘，只要89',12,89,175,0,'\0','非常好用，有重量','','','樱花苑2栋',24,'1462969326794',3,'null',0),(140,'还剩墙纸，最低价35',12,35,200,1,'\0','前段时间对寝室进行改造，由于计算错误还剩一半的墙纸，够贴一个寝室，要的快出价，谁高卖给谁','','\0','樱花苑2栋',15,'1463202543125',11,'1465880700000',0),(141,'软考程序员资料免费送了',14,0,114,2,'\0','本人已经通过程序员考试，现在资料便宜卖了，要的赶紧下手喔','\0','','樱花苑1栋',24,'1463213536928',8,'null',0),(142,'测试',14,0,0,2,'','测试','\0','\0','其他(校内)',5,'1463220539619',10,'null',0),(143,'希捷1T移动硬盘，最低价350',12,350,520,1,'\0','大四学长马上毕业了，一年前买的希捷移动硬盘，速度超快的，要的赶紧入手','','\0','樱花苑2栋',10,'1463233578181',3,'1465911720000',0),(144,'夏天神器，小风扇99新，低价出售，只因学长要走了',12,5,42,1,'\0','去年有段时间太热了卖来没几天就又不热了，基本没有用几次，99新','','','樱花苑2栋',29,'1463233804638',10,'1465912020000',0),(145,'出个自用打印机，马上毕业不想带走，谁给得高就卖给谁',12,248,560,1,'\0','牌子是佳能的，自己改装了一个联供墨盒（200+买的喔），一起卖了，低价248','','\0','樱花苑2栋',15,'1463234045643',3,'1465912200000',0),(146,'8G的U盘，只要15元',12,15,55,0,'\0','里面有老毛桃启动盘，一个WIN10系统，要的快入手了','','\0','樱花苑1栋',8,'1463234206146',3,'null',0),(147,'花架免费送了，只送给爱花的人',12,0,0,2,'\0','大四学长马上走了，花架免费送，送给爱花之人，要的联系我','\0','\0','樱花苑2栋',28,'1463234326773',11,'null',0),(148,'套装免费送了',12,0,0,2,'','真的是免费送的没哦','\0','\0','樱花苑2栋',3,'1463814561527',4,'null',0),(149,'法国兰便宜出了，正在花期',12,20,60,0,'\0','正如照片所见，花苞已经很大了，正值花期，今年只有两束花，以前很多的。欢迎爱花之人来买……','\0','','樱花苑2栋',14,'1463825098480',11,'null',0),(150,'彼岸花（曼沙朱华），底价35，绝对正品',12,35,120,1,'\0','这就是彼岸花，绝对正宗，假一赔十，底价35，谁给得高就卖给谁','\0','\0','樱花苑2栋',16,'1463825459831',11,'1466503620000',0),(151,'马上离校了，吊兰免费送',12,0,0,2,'\0','马上要繁衍后代了，要的快来联系我，有三株，送三个人','\0','\0','樱花苑2栋',12,'1463825771524',11,'null',0),(152,'出售公务员辅导教材',17,80,110,0,'\0','最新公务员辅导教材','','\0','樱花苑2栋',7,'1464249810538',0,'null',0),(153,'台式电脑出售，高配的哦',12,999,2599,0,'\0','8g加1t内存，主频3.79G，三星显示器','','','樱花苑2栋',7,'1464252777958',2,'null',0),(154,'学长免费送芦荟了',12,0,0,2,'\0','过几天走了，送给爱花之人，要的联系我','\0','\0','樱花苑2栋',23,'1464253538475',11,'null',0),(155,'吊兰和花架一起送了',12,0,0,2,'\0','还有一个白色的','\0','\0','樱花苑2栋',7,'1464253698790',11,'null',0),(156,'送一个联想的键盘，质感很好哦，毕业了不想带走',12,0,0,2,'\0','想要的快联系我，后天答辩完就走了','\0','\0','樱花苑2栋',13,'1464254117048',3,'null',0),(157,'古筝',24,300,0,0,'\0','送古筝调音器','\0','\0','樱花苑1栋',14,'1464254422132',11,'null',0),(158,'送化妆品收纳盒',18,0,0,2,'\0','去年买的，毕业了不方便带走免费送人。如果有人喜欢就联系我拿走吧。','\0','\0','樱花苑1栋',10,'1464256702945',0,'null',0),(159,'软考程序员资料，考过了抵英语四级，计算机三级',12,0,0,2,'\0','软考程序员的考试用书【自己网上买要100+】，考过了这个毕业了可以抵绩点、抵计算机三级、抵英语四级等，还没达到毕业条件的同学快去联系','\0','\0','樱花苑2栋',17,'1464262345797',8,'null',0),(160,'送人性的弱点和公仔',12,0,0,2,'\0','要的快联系我，先到先得','\0','\0','樱花苑2栋',6,'1464262543395',8,'null',0),(161,'金立E7免费送了，屏幕坏的换一个就行',12,0,0,2,'','坏了之后就换手机了，闲置的没用','\0','\0','樱花苑2栋',9,'1464263477836',1,'null',0),(162,'计算器出卖',35,8,0,0,'\0','可便宜啦','','\0','樱花苑1栋',8,'1464268875869',5,'null',0),(163,'电子秤和桌子一起出只要10',14,10,80,0,'\0','绝对物超所值','\0','\0','樱花苑2栋',13,'1464273523354',3,'null',0),(164,'四级没过的，过来  送你',12,0,0,2,'\0','基本上没用过，要的联系我','\0','\0','樱花苑1栋',9,'1464274320231',8,'null',0),(165,'迷你小台灯',29,3,0,0,'\0','即插即用，方便携带','','\0','公租房',3,'1464311308534',0,'null',0),(166,'软件水平考试， 中级， 软件设计师',14,30,130,0,'\0','软件设计师教程和软件设计师2009-2012试题分析与解答。8成新， 另外，可送软考初级程序员教程和习题集。有需要的联系','\0','\0','樱花苑1栋',7,'1464312435464',8,'null',0),(167,'六级考研词汇',50,5,0,0,'\0','六级5元一本  考研真题  10元  预测卷5元未开封','\0','\0','翠竹苑1栋',6,'1464333754228',8,'null',0),(176,'1111',54,100,1,1,'','qqq','\0','\0','玉兰苑2栋',1,'1552472333422',5,'1552553820000',0),(177,'12121212',55,100,0,0,'\0','121212121','','\0','嘉园A座',6,'1554970050535',1,'null',0);

/*Table structure for table `paiprice_tb` */

DROP TABLE IF EXISTS `paiprice_tb`;

CREATE TABLE `paiprice_tb` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_maxPrice` int(11) NOT NULL,
  `g_id` int(11) NOT NULL COMMENT '哪个商品',
  `u_id` int(11) NOT NULL COMMENT '谁给的价',
  PRIMARY KEY (`p_id`),
  KEY `puf` (`u_id`),
  KEY `pgf` (`g_id`),
  CONSTRAINT `pgf` FOREIGN KEY (`g_id`) REFERENCES `goods_tb` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `puf` FOREIGN KEY (`u_id`) REFERENCES `users_tb` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `paiprice_tb` */

insert  into `paiprice_tb`(`p_id`,`p_maxPrice`,`g_id`,`u_id`) values (1,1,136,12),(2,30,136,14),(3,35,140,12),(4,31,136,12),(5,36,140,14),(6,6,144,14),(7,248,145,12),(8,15,150,14),(9,5,144,12),(10,30,144,54),(11,50,176,54),(12,51,176,54);

/*Table structure for table `pic_url_tb` */

DROP TABLE IF EXISTS `pic_url_tb`;

CREATE TABLE `pic_url_tb` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片的id',
  `g_id` int(11) NOT NULL,
  `p_url` varchar(200) NOT NULL,
  PRIMARY KEY (`p_id`),
  KEY `picgid` (`g_id`),
  CONSTRAINT `picgid` FOREIGN KEY (`g_id`) REFERENCES `goods_tb` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;

/*Data for the table `pic_url_tb` */

insert  into `pic_url_tb`(`p_id`,`g_id`,`p_url`) values (77,118,'/users/15519099928/15519099928_1462844232342.png'),(78,119,'/users/15519099928/15519099928_1462845474450.png'),(79,119,'/users/15519099928/15519099928_1462845474580.png'),(80,120,'/users/15519099928/15519099928_1462845962710.png'),(81,120,'/users/15519099928/15519099928_1462845962890.png'),(82,121,'/users/15519099928/15519099928_1462846197217.png'),(83,121,'/users/15519099928/15519099928_1462846197400.png'),(84,122,'/users/15519099928/15519099928_1462846557868.png'),(85,122,'/users/15519099928/15519099928_1462846557951.png'),(86,122,'/users/15519099928/15519099928_1462846558035.png'),(87,123,'/users/15519099928/15519099928_1462846928867.png'),(88,123,'/users/15519099928/15519099928_1462846928973.png'),(89,123,'/users/15519099928/15519099928_1462846929056.png'),(90,124,'/users/15519099928/15519099928_1462847116028.png'),(91,124,'/users/15519099928/15519099928_1462847116148.png'),(92,125,'/users/15519099928/15519099928_1462847419569.png'),(93,125,'/users/15519099928/15519099928_1462847419830.png'),(94,126,'/users/15519099928/15519099928_1462884901331.png'),(95,127,'/users/15519099928/15519099928_1462884920430.png'),(96,128,'/users/15519099928/15519099928_1462884934421.png'),(97,129,'/users/15519099928/15519099928_1462884948144.png'),(98,130,'/users/15519099928/15519099928_1462884973668.png'),(99,131,'/users/15519099928/15519099928_1462884989780.png'),(100,132,'/users/15519099928/15519099928_1462885009091.png'),(101,136,'/users/15519099928/15519099928_1462943904707.png'),(102,137,'/users/15519099928/15519099928_1462963173996.png'),(103,137,'/users/15519099928/15519099928_1462963178902.png'),(104,138,'/users/15519099928/15519099928_1462969327622.png'),(105,138,'/users/15519099928/15519099928_1462969328309.png'),(107,140,'/users/15519099928/15519099928_1463202543938.png'),(108,140,'/users/15519099928/15519099928_1463202544531.png'),(109,140,'/users/15519099928/15519099928_1463202545219.png'),(110,141,'/users/15685188016/15685188016_1463213537834.png'),(111,141,'/users/15685188016/15685188016_1463213538537.png'),(112,141,'/users/15685188016/15685188016_1463213539162.png'),(113,141,'/users/15685188016/15685188016_1463213540022.png'),(114,142,'/users/15685188016/15685188016_1463220540666.png'),(115,142,'/users/15685188016/15685188016_1463220541728.png'),(116,143,'/users/15519099928/15519099928_1463233582384.png'),(117,143,'/users/15519099928/15519099928_1463233582900.png'),(118,143,'/users/15519099928/15519099928_1463233583493.png'),(119,144,'/users/15519099928/15519099928_1463233805419.png'),(120,144,'/users/15519099928/15519099928_1463233806044.png'),(121,144,'/users/15519099928/15519099928_1463233806685.png'),(122,145,'/users/15519099928/15519099928_1463234046112.png'),(123,145,'/users/15519099928/15519099928_1463234046705.png'),(124,145,'/users/15519099928/15519099928_1463234047190.png'),(125,146,'/users/15519099928/15519099928_1463234206771.png'),(126,147,'/users/15519099928/15519099928_1463234327679.png'),(127,148,'/users/15519099928/15519099928_1463814562199.png'),(128,148,'/users/15519099928/15519099928_1463814562886.png'),(129,148,'/users/15519099928/15519099928_1463814563371.png'),(130,149,'/users/15519099928/15519099928_1463825099105.png'),(131,149,'/users/15519099928/15519099928_1463825099777.png'),(132,149,'/users/15519099928/15519099928_1463825100449.png'),(133,149,'/users/15519099928/15519099928_1463825101183.png'),(134,149,'/users/15519099928/15519099928_1463825101933.png'),(135,150,'/users/15519099928/15519099928_1463825460362.png'),(136,150,'/users/15519099928/15519099928_1463825460831.png'),(137,150,'/users/15519099928/15519099928_1463825461315.png'),(138,151,'/users/15519099928/15519099928_1463825773196.png'),(139,151,'/users/15519099928/15519099928_1463825773883.png'),(140,152,'/users/20122205012021/20122205012021_1464249811022.png'),(141,153,'/users/15519099928/15519099928_1464252778333.png'),(142,154,'/users/15519099928/15519099928_1464253539193.png'),(143,154,'/users/15519099928/15519099928_1464253543819.png'),(144,155,'/users/15519099928/15519099928_1464253701587.png'),(145,155,'/users/15519099928/15519099928_1464253704493.png'),(146,156,'/users/15519099928/15519099928_1464254117611.png'),(147,156,'/users/15519099928/15519099928_1464254118736.png'),(148,157,'/users/15519093324/15519093324_1464254423570.png'),(149,158,'/users/13608547692/13608547692_1464256703336.png'),(150,159,'/users/15519099928/15519099928_1464262346500.png'),(151,159,'/users/15519099928/15519099928_1464262347047.png'),(152,159,'/users/15519099928/15519099928_1464262347547.png'),(153,160,'/users/15519099928/15519099928_1464262543910.png'),(154,161,'/users/15519099928/15519099928_1464263478274.png'),(155,161,'/users/15519099928/15519099928_1464263478633.png'),(156,162,'/users/20122205012002/20122205012002_1464268876541.png'),(157,163,'/users/15685188016/15685188016_1464273523839.png'),(158,163,'/users/15685188016/15685188016_1464273527620.png'),(159,163,'/users/15685188016/15685188016_1464273528276.png'),(160,164,'/users/15519099928/15519099928_1464274320747.png'),(161,164,'/users/15519099928/15519099928_1464274321278.png'),(162,165,'/users/18302573571/18302573571_1464311316862.png'),(163,166,'/users/15685188016/15685188016_1464312436026.png'),(164,166,'/users/15685188016/15685188016_1464312436370.png'),(165,166,'/users/15685188016/15685188016_1464312436760.png'),(166,166,'/users/15685188016/15685188016_1464312437245.png'),(167,166,'/users/15685188016/15685188016_1464312437792.png'),(168,167,'/users/15685192952/15685192952_1464333755697.png'),(169,167,'/users/15685192952/15685192952_1464333757275.png'),(170,176,'/users/18636585611/18636585611_1552472333669.png');

/*Table structure for table `type_tb` */

DROP TABLE IF EXISTS `type_tb`;

CREATE TABLE `type_tb` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(20) NOT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `type_tb` */

insert  into `type_tb`(`t_id`,`t_name`) values (1,'电动车'),(2,'手机'),(3,'电脑'),(4,'男装'),(5,'女装'),(6,'数码'),(7,'电器'),(8,'运动健身'),(9,'书籍'),(10,'生活娱乐'),(11,'其他'),(13,'自行车');

/*Table structure for table `users_tb` */

DROP TABLE IF EXISTS `users_tb`;

CREATE TABLE `users_tb` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_no` char(14) NOT NULL COMMENT '学号',
  `u_phone` char(11) NOT NULL COMMENT '用户电话',
  `u_nickname` varchar(28) NOT NULL COMMENT '昵称',
  `u_sex` bit(1) NOT NULL DEFAULT b'1' COMMENT '1为男 0为女',
  `u_pwd` char(32) NOT NULL COMMENT '密码',
  `u_time` varchar(40) NOT NULL DEFAULT ' ' COMMENT '注册时间',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `users_tb` */

insert  into `users_tb`(`u_id`,`u_no`,`u_phone`,`u_nickname`,`u_sex`,`u_pwd`,`u_time`) values (12,'20121035','15519099928','跳蚤哥','','c26be8aaf53b15054896983b43eb6a65','1461244998039'),(13,'20131001','15519099921','可以取多大的名字，中文有多','\0','2497a7d9315fe5cf35214f7054837df0','1461244998039'),(14,'20141051','15685188016','小橘子','\0','c26be8aaf53b15054896983b43eb6a65','1461244998039'),(15,'20151001','15519099922','xiaomi','\0','2497a7d9315fe5cf35214f7054837df0','1461244998039'),(16,'20151002','15529099923','15529099923','\0','66bc093ea8b5615d3f66c590bd478c83','1463201040547'),(17,'20122021','13329674456','13329674456','','bc51dc8001432e6dec23a6bf61a3caa7','1464249674191'),(18,'20121038','13608547692','if you','\0','c7e5c7d03f75ff2a7b69afa6fa04b9e0','1464250128341'),(19,'20121033','18798615495','18798615495','\0','8d8abf636ef5cdacb99e717dcb19cf3d','1464250246437'),(20,'20121014','15519176321','15519176321','','c26be8aaf53b15054896983b43eb6a65','1464250252625'),(21,'20131006','18683107593','Helpdl','','3feb2be0a9fa4a51a8acbac24e85d2be','1464251833001'),(22,'20121044','15519548072','15519548072','','56f30585e26b674e33354b7c50c9d80b','1464252024989'),(23,'20121022','15519097174','15519097174','\0','a4a054f3a69bc1ef1138694400d5ed4e','1464253535615'),(24,'20121031','15519093324','15519093324','\0','c26be8aaf53b15054896983b43eb6a65','1464253985811'),(25,'20121034','18786727550','18786727550','','c26be8aaf53b15054896983b43eb6a65','1464261024223'),(26,'20121037','15685170312','15685170312','','13001f8db19ffeaa5049e0a131c949db','1464261084115'),(27,'20142016','13017473884','百花深处住着老情人','\0','c7f6732b818b8b593a8a5be23ec61f60','1464261867507'),(28,'20131023','15519057674','15519057674','','c26be8aaf53b15054896983b43eb6a65','1464262292484'),(29,'20121026','18302573571','18302573571','\0','c26be8aaf53b15054896983b43eb6a65','1464262470034'),(35,'20122002','13599039260','13599039260','\0','63222b4c57d53d13edf37e7000b908d7','1464268148636'),(36,'20131034','15285981974','15285981974','','c16feacb9fcc4f0f2459b8eb05e59870','1464270332102'),(37,'20131003','15761627406','15761627406','\0','89e5cbbd3de5d90d27d42e978e8a1562','1464278449972'),(38,'20131009','13087838312','13087838312','','63fc0de31e7f95a25eff184c162e37bf','1464278571224'),(39,'20131010','15761611172','15761611172','\0','5abddce7d88f713942e24cc8e827700c','1464278602037'),(40,'20131037','15519020701','15519020701','\0','6faae7f3a9d38b8036c0880c853d1710','1464279533416'),(41,'20131038','13158031745','13158031745','\0','47cb0dfb0a9c3ab9e9c552921c95ad03','1464281398220'),(42,'20160523','15168791359','不放弃不后退','','aba951897e484b340d0557e74f6dd60a','1464282897344'),(43,'20121019','15519093614','15519093614','','df9c6919736a0c05402ee44232421a81','1464306981914'),(44,'20142021','13027867659','13027867659','\0','61b586a1faebffb2e02cd33ceff961ab','1464311352050'),(45,'20141010','13017480384','鸟慧慧','\0','a76a6f67f45084f895285cdadd70ff89','1464312105520'),(46,'20131008','13017472757','13017472757','\0','fa89baaa2dbe6870ea9bca6e7c5337cf','1464313524531'),(47,'12345612','15989013277','15989013277','','c26be8aaf53b15054896983b43eb6a65','1464316454296'),(48,'20131033','15761620787','15761620787','\0','6dbfc1b9de6276a703d2c4e466788b76','1464318890050'),(49,'20131027','18302598972','18302598972','\0','432a0b99b4aaae4c00d61c459b790be3','1464324838720'),(50,'20122060','15685192952','15685192952','\0','f598fe98924c6dcc8d91ec1a47a1ed3a','1464333374080'),(51,'20150127','18386123747','18386123747','','401a3e3aa4bc6ea7631543f6965c58be','1464335227025'),(52,'20131047','18722893687','18722893687','\0','e5658172c1ad5fccc429cb38ff7c69fd','1464338619081'),(54,'20151212','18636585611','18636585611','','9015f798a1bbb6d0d109df74d21214b5','1552457644994'),(55,'15121211','15601116562','15601116562','','2a810c88e3cb947e85bbab2728102f0d','1554969153561'),(56,'10111222','12345678977','12345678977','','e4db1c8495a62591d43f49d592ffbe00','1554971598075');

/*Table structure for table `version_tb` */

DROP TABLE IF EXISTS `version_tb`;

CREATE TABLE `version_tb` (
  `v_id` int(11) NOT NULL AUTO_INCREMENT,
  `v_code` smallint(6) NOT NULL,
  `v_apkUrl` varchar(256) NOT NULL,
  `v_time` char(40) NOT NULL,
  PRIMARY KEY (`v_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `version_tb` */

insert  into `version_tb`(`v_id`,`v_code`,`v_apkUrl`,`v_time`) values (1,1,'kgkjgkjgkjlghkj','1411201887471'),(2,2,'http://115.29.148.100:8080/tzsc/apk/app-debug.apk','1463201887471'),(4,2,'http://115.29.148.100:8080/tzsc/apk/app-debug.apk','1463201923050');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
