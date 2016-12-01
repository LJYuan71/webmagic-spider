# webmagic-spider
基于WebMagic写的一个小爬虫,用于爬Cnnvd和seebug网的数据（本来也想爬cnvd的，但是它的反爬虫技术太高了，自己能力不够，如果有大神可以爬的话，想可以交流一下）。
* 1.MySQL数据库DDL在/sql/文件下
* 2.数据库配置、日志输出和Mybatis配置文件在src/main/resources下
* 3.定时器在都统一在io.webmagic.common.timer中，可以改成main方法执行
