# 右墨后端API文档

## 概述

遵循前后端分离的思想，所以前端Web服务器和后端分开存放，文件目录如下



1. API遵循RESTful设计，通过域名来决定调用的API种类。
2. 用户身份验证采用token机制，用户登录时通过用户名和密码获取token，之后的身份验证均使用token进行。
3. 返回结果为JSON文件，正确的返回结果均位于`result`，如果出现错误一般会返回`message`，其中会包含错误信息，以及success，为请求是否成功的判断字段

**URL示例**

`http://182.92.57.178:5000/movies/[Movie_id]`的示例是`http://182.92.57.178:5000/movies/1`

`http://182.92.57.178:5000/books/<int:book_id>/scroes`的示例是`http://182.92.57.178:5000/books/1/scroes`

## 无需token的API



### 用户相关

#### 注册

HTTP方法：`POST`

请求URL：`http://39.106.230.20:8090/register`

请求参数

| 参数     | 是否可选 | 类型   | 范围      | 说明     |
| -------- | -------- | ------ | --------- | -------- |
| username | 否       | string | 1-20      | 用户名   |
| password | 否       | string | 1-20      | 用户密码 |
| email    | 否       | string | 符合email | 邮箱     |
| phone    | 否       | string | 11        | 手机号   |

举例：

```json
{
    "username": "txy123",
    "password": "txy123",
    "email": "txy@qq.com",
    "phone": "177777777"
}
```

**返回说明**

注册成功：

```json
{
	"success": true,
	"message": "用户注册成功！"
}
```

注册失败：

```json
{
	"success": false,
	"message": "用户保存失败！"
}
```



#### 登录

HTTP方法：`POST`

请求URL：`http://39.106.230.20:8090/login`

请求参数

| 参数     | 是否可选 | 类型   | 范围 | 说明     |
| -------- | -------- | ------ | ---- | -------- |
| name     | 否       | string | 1-20 | 用户名   |
| password | 否       | string | 1-20 | 用户密码 |

**返回说明**

返回参数：

登录成功

```json
{
	"success": true,
	"message": "用户登录成功！",
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJiaXJ0aGRheSI6IjIwMDAtMDYtMTYiLCJwaG9uZSI6IjE4ODAxMjg1NTM0IiwiZGVzY3JpcHRpb24iOiLkuIDkuKrlrZfvvIzlsLHmmK_luIXvvIEiLCJhdmF0YXIiOiIvc3JjL2FhYSIsImV4cCI6MTU5NzczNTQ4OCwidXNlcmlkIjoiMSIsImVtYWlsIjoiaGJ5YW5nQHFxLmNvbSIsInVzZXJuYW1lIjoieWhiIn0.7fgoiAw90YvZLXKGme9-0qJPQhhku9vA7mTto5ul8y4"
}
```



| 参数  | 类型   | 说明                        |
| ----- | ------ | --------------------------- |
| token | string | 用于身份认证，有效时间7天。 |

登录失败

```json
{
	"success": false,
	"message": "用户登录失败，用户名或者密码错误！"
}
```





### 文档相关



#### 根据id获取文档

**请求说明**

HTTP方法：`GET`

请求URL：`http://39.106.230.20:8090/document/[int:docid]`  （请求的文档id在路径中）

**返回说明**

返回参数

请求成功

| 参数     | 类型    | 值             | 说明               |
| -------- | ------- | -------------- | ------------------ |
| success  | boolean | True           | 请求成功标识       |
| contents | 对象    | 对应的document | 查找的document对象 |

`document`参数

| 参数           | 类型   | 说明                                         |
| -------------- | ------ | -------------------------------------------- |
| docid          | int    | 文档id                                       |
| creatorid      | int    | 文档创建者                                   |
| title          | string | 文档名字                                     |
| content        | string | 文档内容                                     |
| creattime      | Date   | 文档创建时间                                 |
| lastedittime   | Date   | 文档创建                                     |
| editcount      | int    | 文档编辑次数                                 |
| lastedituserid | string | 最后一次编辑此文档的用户                     |
| auth           | int    | 文档的权限：1可读，3可写，7可操作，8团队文档 |
| teamauth       | int    | 文档的团队权限：1可读，3可写，7可操作        |
| istrash        | int    | 是否被放入回收站：1放入，0没放入             |

请求失败：

| 参数     | 类型    | 值    | 说明               |
| -------- | ------- | ----- | ------------------ |
| success  | boolean | false | 请求成功标识       |
| contents | 对象    | null  | 未查找到相应的文档 |



### 团队相关



#### 邀请成员

**请求说明**

HTTP方法：`Post`

请求URL：`http://39.106.230.20:8090/team/inviteMember`

请求参数

| 参数       | 是否可选 | 类型   | 范围 | 说明   |
| ---------- | -------- | ------ | ---- | ------ |
| invitename | 否       | string | 无   | 用户名 |
| teamid     | 否       | int    | 无   | 团队号 |

**返回说明**

返回参数：

成功：

| 参数    | 类型    | 值         | 说明         |
| ------- | ------- | ---------- | ------------ |
| success | boolean | true       | 请求成功标识 |
| message | string  | 邀请成功！ | 邀请成功信息 |

失败：

| 参数    | 类型    | 值         | 说明         |
| ------- | ------- | ---------- | ------------ |
| success | boolean | false      | 请求失败标识 |
| message | string  | 邀请失败！ | 邀请失败信息 |





## 需要token的API



### 概述

token均通过headers传入，参数名为`token`

**token错误时返回的信息具体见每个接口返回的内容**





### 用户相关



#### 获取用户信息

**请求说明**

HTTP方法：`GET`

请求URL：`http://39.106.230.20:8090/user/info`

**返回说明**

| 参数        | 类型    | 说明           |
| ----------- | ------- | -------------- |
| userid      | Integer | 用户id         |
| username    | string  | 用户名         |
| password    | string  | 用户密码       |
| phone       | String  | 手机号         |
| birthday    | Date    | 用户生日       |
| email       | string  | 用户邮箱       |
| avatar      | String  | 用户头像的url  |
| description | String  | 用户个性签名？ |



#### 用户信息修改（非密码字段）

**请求说明**

HTTP方法：PUT

请求URL：`http://39.106.230.20:8090/user/mod_info`

请求参数

| 参数        | 类型   | 说明           |
| ----------- | ------ | -------------- |
| phone       | string | 手机号         |
| birthday    | Date   | 用户生日       |
| email       | string | 用户邮箱       |
| avatar      | string | 用户头像的url  |
| description | string | 用户个性签名？ |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                            | 说明         |
| ------- | ------- | ----------------------------- | ------------ |
| message | string  | modify user info successfully | 成功修改信息 |
| success | Boolean | True                          | 请求成功标识 |

请求失败：

| 参数    | 类型    | 值          | 说明          |
| ------- | ------- | ----------- | ------------- |
| message | string  | token error | token验证错误 |
| success | boolean | false       | 请求错误标识  |





#### 密码修改

HTTP方法：`Put`

请求URL：`http://39.106.230.20:8090/user/mod_password`

请求参数

| 参数         | 是否可选 | 类型   | 范围 | 说明   |
| ------------ | -------- | ------ | ---- | ------ |
| old_password | 否       | string | 未定 | 旧密码 |
| new_password | 否       | string | 未定 | 新密码 |

**返回说明**

返回参数

修改成功：

| 参数    | 具体内容                      | 类型    | 说明         |
| ------- | ----------------------------- | ------- | ------------ |
| message | modify user info successfully | string  | 成功修改密码 |
| success | true                          | boolean | 请求成功标识 |

修改失败：

| 参数    | 具体内容    | 类型    | 说明          |
| ------- | ----------- | ------- | ------------- |
| message | token error | string  | token验证错误 |
| success | false       | Boolean | 请求错误标识  |

or

| 参数    | 具体内容                  | 类型    | 说明         |
| ------- | ------------------------- | ------- | ------------ |
| message | the old password is wrong | string  | 旧密码不匹配 |
| success | false                     | Boolean | 请求错误标识 |

or

| 参数    | 具体内容            | 类型    | 说明          |
| ------- | ------------------- | ------- | ------------- |
| message | user does not exist | string  | token验证错误 |
| success | false               | Boolean | 请求错误标识  |





### 文档相关



#### 创建个人文档

**请求说明**

HTTP方法：Post

请求Url：`http://39.106.230.20:8090/document`

请求参数

| 参数     | 类型    | 说明         | 是否可选       |
| -------- | ------- | ------------ | -------------- |
| Document | Boolean | document对象 | 是，可不带参数 |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                       | 说明           |
| ------- | ------- | ------------------------ | -------------- |
| success | Boolean | true                     | 请求成功的标识 |
| message | string  | create file successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                   | 说明           |
| ------- | ------- | -------------------- | -------------- |
| success | Boolean | False                | 请求失败的标识 |
| message | string  | failed to create doc | 请求失败的信息 |





#### 获取用户创建文档列表

**请求说明**

HTTP方法：`Get`

请求URL：`http://39.106.230.20:8090/document/creator`

**返回说明**

返回参数

请求成功：

| 参数     | 类型    | 值        | 说明                                              |
| -------- | ------- | --------- | ------------------------------------------------- |
| success  | Boolean | true      | 请求成功的标识                                    |
| contents | List    | documents | 保存doc的列表,doc对象的信息具体见：根据id获取文档 |

请求失败：

| 参数    | 类型    | 值          | 说明           |
| ------- | ------- | ----------- | -------------- |
| success | Boolean | false       | 请求成功的标识 |
| message | String  | token error | token验证失败  |





#### 修改文档信息

**请求说明**

HTTP方法：`put`

请求URL:  `http://39.106.230.20:8090/document/`

请求参数

| 参数     | 类型    | 说明         | 是否可选             |
| -------- | ------- | ------------ | -------------------- |
| Document | Boolean | document对象 | 否，必须要带document |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                      | 说明           |
| ------- | ------- | ----------------------- | -------------- |
| success | Boolean | true                    | 请求成功的标识 |
| message | string  | modify doc successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                   | 说明           |
| ------- | ------- | -------------------- | -------------- |
| success | Boolean | False                | 请求失败的标识 |
| message | string  | failed to modify doc | 请求失败的信息 |



#### 更改文档名称

**请求说明**

HTTP方法：`Put`

请求Url：`http://39.106.230.20:8090/document/mod_title`

请求参数：

| 参数  | 类型    | 说明           | 是否可选         |
| ----- | ------- | -------------- | ---------------- |
| docid | Integer | 待更改的文档id | 否，必须带此参数 |
| title | string  | 待更改的文件名 | 否，必须带此参数 |



**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                            | 说明           |
| ------- | ------- | ----------------------------- | -------------- |
| success | Boolean | true                          | 请求成功的标识 |
| message | string  | modify doc title successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                     | 说明           |
| ------- | ------- | ---------------------- | -------------- |
| success | Boolean | false                  | 请求失败的标识 |
| message | string  | failed to modify title | 请求失败的信息 |





#### 更改文档个人权限

**请求说明**

HTTP方法：`Put`

请求Url：`http://39.106.230.20:8090/document/mod_auth`

请求参数：

| 参数  | 类型    | 说明           | 是否可选         |
| ----- | ------- | -------------- | ---------------- |
| docid | Integer | 待更改的文档id | 否，必须带此参数 |
| auth  | Integer | 待更改的权限   | 否，必须带此参数 |



**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                           | 说明           |
| ------- | ------- | ---------------------------- | -------------- |
| success | Boolean | true                         | 请求成功的标识 |
| message | string  | modify doc auth successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                        | 说明           |
| ------- | ------- | ------------------------- | -------------- |
| success | Boolean | false                     | 请求失败的标识 |
| message | string  | failed to modify doc auth | 请求失败的信息 |







#### 更改文档团队权限

**请求说明**

HTTP方法：`Put`

请求Url：`http://39.106.230.20:8090/document/mod_teamauth`

请求参数：

| 参数     | 类型    | 说明             | 是否可选         |
| -------- | ------- | ---------------- | ---------------- |
| docid    | Integer | 待更改的文档id   | 否，必须带此参数 |
| teamauth | Integer | 待更改的团队权限 | 否，必须带此参数 |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                           | 说明           |
| ------- | ------- | ---------------------------- | -------------- |
| success | Boolean | true                         | 请求成功的标识 |
| message | string  | modify doc auth successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                  | 说明           |
| ------- | ------- | ------------------- | -------------- |
| success | Boolean | false               | 请求失败的标识 |
| message | string  | doc does not exists | 文件不存在     |

or:

| 参数    | 类型    | 值                        | 说明           |
| ------- | ------- | ------------------------- | -------------- |
| success | Boolean | false                     | 请求失败的标识 |
| message | string  | failed to modify doc auth | 请求失败的信息 |





#### 将文档移入垃圾箱

**请求说明**

HTTP方法：`Delete`

Url:`http://39.106.230.20:8090/document/{int:docid}`

请求参数：请求参数包含在`url`中

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                         | 说明           |
| ------- | ------- | -------------------------- | -------------- |
| success | Boolean | true                       | 请求成功的标识 |
| message | string  | move to trash successfully | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                  | 说明           |
| ------- | ------- | ------------------- | -------------- |
| success | Boolean | false               | 请求失败的标识 |
| message | string  | doc does not exists | 文件不存在     |

or:

| 参数    | 类型    | 值                                     | 说明           |
| ------- | ------- | -------------------------------------- | -------------- |
| success | Boolean | false                                  | 请求失败的标识 |
| message | string  | haven't authority to move doc to trash | 请求失败的信息 |

or:

| 参数    | 类型    | 值                      | 说明           |
| ------- | ------- | ----------------------- | -------------- |
| success | Boolean | false                   | 请求失败的标识 |
| message | string  | failed to move to trash | 请求失败的信息 |



#### 永久删除文档

**请求说明**

HTTP方法：`Delete`

Url:`http://39.106.230.20:8090/document/permanent/{int:docid}`

请求参数：请求参数包含在`url`中

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                                  | 说明           |
| ------- | ------- | ----------------------------------- | -------------- |
| success | Boolean | true                                | 请求成功的标识 |
| message | string  | successfully delete doc permanently | 请求成功的信息 |

请求失败：

| 参数    | 类型    | 值                  | 说明           |
| ------- | ------- | ------------------- | -------------- |
| success | Boolean | false               | 请求失败的标识 |
| message | string  | doc does not exists | 文件不存在     |

or:

| 参数    | 类型    | 值                                          | 说明           |
| ------- | ------- | ------------------------------------------- | -------------- |
| success | Boolean | false                                       | 请求失败的标识 |
| message | string  | haven't authority to delete doc permanently | 请求失败的信息 |

or:

| 参数    | 类型    | 值                           | 说明           |
| ------- | ------- | ---------------------------- | -------------- |
| success | Boolean | false                        | 请求失败的标识 |
| message | string  | failed to delete permanently | 请求失败的信息 |



#### 	清空回收站

**请求说明**

HTTP:`Delete`

Url:`http://39.106.230.20:8090/document/permanent/all`

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                       | 说明           |
| ------- | ------- | ------------------------ | -------------- |
| success | Boolean | true                     | 请求成功的标识 |
| message | string  | successfully clear trash | 清空回收站成功 |

请求失败：

| 参数    | 类型    | 值                    | 说明           |
| ------- | ------- | --------------------- | -------------- |
| success | Boolean | false                 | 请求失败的标识 |
| message | string  | failed to clear trash | 清空回收站失败 |





#### 查找用户的回收站文档

**请求说明**

HTTP：`GET`

URL:`http://39.106.230.20:8090/document/trash`







#### 	恢复在回收站中的文件

**请求说明**

HTTP:`Delete`

Url:`http://39.106.230.20:8090/document/recover/{int:docid}`

请求参数：docid在url路径中。

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                              | 说明           |
| ------- | ------- | ------------------------------- | -------------- |
| success | Boolean | true                            | 请求成功的标识 |
| message | string  | recover from trash successfully | 清空回收站成功 |

请求失败：

| 参数    | 类型    | 值                    | 说明           |
| ------- | ------- | --------------------- | -------------- |
| success | Boolean | false                 | 请求失败的标识 |
| message | string  | failed to clear trash | 清空回收站失败 |

or:

| 参数    | 类型    | 值                               | 说明           |
| ------- | ------- | -------------------------------- | -------------- |
| success | Boolean | false                            | 请求失败的标识 |
| message | string  | haven't authority to recover doc | 没有恢复的权限 |

or:

| 参数    | 类型    | 值                  | 说明           |
| ------- | ------- | ------------------- | -------------- |
| success | Boolean | false               | 请求失败的标识 |
| message | string  | doc does not exists | 文件不存在     |





#### 判断用户是否收藏了文档

**请求说明**

HTTP：Get

Url：`http：http://39.106.230.20:8090/document/fav/{int:docid}`

请求参数：在url中

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值   | 说明           |
| ------- | ------- | ---- | -------------- |
| success | Boolean | true | 请求成功的标识 |
| isFav   | Boolean | True | 用户以收藏     |

Or:

| 参数    | 类型    | 值    | 说明           |
| ------- | ------- | ----- | -------------- |
| success | Boolean | true  | 请求成功的标识 |
| isFav   | Boolean | false | 用户未收藏     |

请求失败：

| 参数    | 类型    | 值    | 说明         |
| ------- | ------- | ----- | ------------ |
| success | Boolean | false | 请求失败标识 |
| isFav   | Boolean | false | 无效         |







#### 增加用户的收藏文档

**请求说明**

HTTP：`Post`

Url: `http://39.106.230.20:8090/document/fav` 

请求参数：

| 参数  | 类型    | 说明           | 是否可选         |
| ----- | ------- | -------------- | ---------------- |
| docid | Integer | 待收藏的文档id | 否，必须带此参数 |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                            | 说明           |
| ------- | ------- | ----------------------------- | -------------- |
| success | Boolean | true                          | 请求失败的标识 |
| message | string  | Add to favorites successfully | 清空回收站失败 |

请求失败：

| 参数    | 类型    | 值                                | 说明                 |
| ------- | ------- | --------------------------------- | -------------------- |
| success | Boolean | false                             | 请求失败的标识       |
| message | string  | already add this doc to favorites | 此文档已经在收藏夹中 |

Or:

| 参数    | 类型    | 值                         | 说明           |
| ------- | ------- | -------------------------- | -------------- |
| success | Boolean | false                      | 请求失败的标识 |
| message | string  | failed to add to favorites | 出现了其他错误 |



#### 删除用户的收藏文档

**请求说明**

HTTP：`Delete`

Url: `http://39.106.230.20:8090/document/fav` 

请求参数：

| 参数  | 类型    | 说明               | 是否可选         |
| ----- | ------- | ------------------ | ---------------- |
| docid | Integer | 待删除的收藏文档id | 否，必须带此参数 |

**返回说明**

返回参数：

请求成功：

| 参数    | 类型    | 值                                 | 说明           |
| ------- | ------- | ---------------------------------- | -------------- |
| success | Boolean | true                               | 请求失败的标识 |
| message | string  | remove from favorites successfully | 移出收藏夹成功 |

请求失败：

| 参数    | 类型    | 值                             | 说明                 |
| ------- | ------- | ------------------------------ | -------------------- |
| success | Boolean | false                          | 请求失败的标识       |
| message | string  | failed to remove from favorite | 此文档已经在收藏夹中 |



### 获取个人收藏文档

**请求说明**

HTTP:`Get`

Url:`http://39.106.230.20:8090/document/fav` 

请求参数：无

**返回说明**

返回参数：

请求成功：

| 参数     | 类型    | 值                         | 说明               |
| -------- | ------- | -------------------------- | ------------------ |
| success  | Boolean | true                       | 请求成功的标识     |
| message  | string  | get favorites successfully | 请求成功           |
| contents | List    | 保存document的列表         | 保存document的列表 |

请求失败：

| 参数    | 类型    | 值                      | 说明           |
| ------- | ------- | ----------------------- | -------------- |
| success | Boolean | false                   | 请求失败的标识 |
| message | string  | failed to get favorites | 请求失败的标识 |



### 获取个人最近浏览文档

**请求说明**

HTTP:`Get`

Url:`http://39.106.230.20:8090/document/view` 

请求参数：无

**返回说明**

返回参数：

请求成功：

| 参数     | 类型    | 值                    | 说明               |
| -------- | ------- | --------------------- | ------------------ |
| success  | Boolean | true                  | 请求成功的标识     |
| message  | string  | get view successfully | 请求成功的信息     |
| contents | List    | 保存document的列表    | 保存document的列表 |

请求失败：

| 参数    | 类型    | 值                     | 说明           |
| ------- | ------- | ---------------------- | -------------- |
| success | Boolean | false                  | 请求失败的标识 |
| message | string  | failed to get view doc | 请求失败的信息 |



### 团队相关



#### 创建团队

**请求说明**

HTTP方法：`Post`

请求URL：`http://39.106.230.20:8090/team/create`

请求参数：

| 参数     | 是否可选 | 类型   | 范围 | 说明     |
| -------- | -------- | ------ | ---- | -------- |
| teaminfo | 否       | string | 无   | 团队信息 |
| teamname | 否       | string | 无   | 团队名   |

**返回说明**

返回参数：

成功：

| 参数    | 类型    | 值             | 说明             |
| ------- | ------- | -------------- | ---------------- |
| success | boolean | true           | 请求成功标识     |
| message | string  | 创建团队成功！ | 创建团队成功信息 |

失败：

| 参数    | 类型    | 值             | 说明             |
| ------- | ------- | -------------- | ---------------- |
| success | boolean | false          | 请求失败标识     |
| message | string  | 创建团队失败！ | 创建团队失败信息 |







