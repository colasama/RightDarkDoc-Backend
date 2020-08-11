use rightdarkdoc;


-- 1.用户表
create table User(
	userid int PRIMARY key auto_increment,
	username VARCHAR(50) not null,
	password VARCHAR(50) not null,
	phone varchar(20),
	birthday datetime,		
	email varchar(200),												
	avatar VARCHAR(200),								-- 头像url
	description varchar(2000)							-- 个人描述
);


-- 2.文档表
create table Document(
	docid int PRIMARY key auto_increment,												
	creatorid INT REFERENCES `USER`(userid),			-- 创建者id
	title VARCHAR(200) not null,						-- 文档标题
	content TEXT,									    -- 文档内容
	creattime datetime,									-- 创建时间，默认为插入时间
	lastedittime datetime,							    -- 最后修改时间， 默认为插入时间
	editcount int not null DEFAULT 1,					-- 修改次数
	lastedituserid int REFERENCES `USER`(userid),		-- 最后修改用户userid																					
	auth INT,										    -- 文档权限 1(可删除等操作) 1(可修改) 1(可评论) 1(可读)    1111
	teamauth INT,										-- 团队文档权限
	istrash TINYINT DEFAULT 0							-- 垃圾桶中 0(不在), 1(在)
);


-- 3.团队表
create table Team(
	teamid int PRIMARY key auto_increment,
	teamname VARCHAR(50) not null,
	creatorid INT REFERENCES `USER`(userid),			-- 团队创建者id
	teaminfo varchar(1000)								-- 团队信息
);


-- 4.评论表
create table `Comment`(
	comid int PRIMARY key auto_increment,
	docid int REFERENCES `Document`(docid),			    -- 文档id
	userid int REFERENCES `USER`(userid),				-- 用户id
	content varchar(2000) 								-- 评论内容
);


-- 5.用户最近浏览文档
create table User_Doc_View(
	`userid` int REFERENCES `USER`(userid),				-- 用户id
	`docid` int REFERENCES `Document`(docid) 			-- 文档id
);


-- 用户收藏文档
create table User_Doc_Fav(
	userid int REFERENCES `USER`(userid),				-- 用户id
	docid int REFERENCES `Document`(docid)			    -- 文档id
);


-- 团队成员表
create table User_Team(
	userid int REFERENCES `USER`(userid),				-- 用户id
	teamid int REFERENCES `Team`(teamid),				-- 文档id
	iscreator int									    -- 判断是否是该团队的创建者 0， 1				（后续添加其他团队角色后可改为团队权限）
);

-- 团队文档表
create table Team_Document(
	teamid int REFERENCES `Team`(teamid),				-- 团队id
	docid int REFERENCES `Document`(docid)			    -- 文档id
)


-- 用户消息通知表
create table Message(
	userid int REFERENCES `USER`(userid),				-- 用户id
	content varchar(2000), 								-- 评论内容
	isread TINYINT										-- 是否已读0, 1
);

show tables;