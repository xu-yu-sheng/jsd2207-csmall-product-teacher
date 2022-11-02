# 查询相册列表--Service

在`IAlbumService`中添加抽象方法：

```java
List<AlbumListItemVO> list();
```

在`AlbumServiceImpl`中实现：

```java
@Override
public List<AlbumListItemVO> list() {
    log.debug("开始处理【查询相册列表】的业务，无参数");
    return albumMapper.list();
}
```

在`AlbumServiceTests`中测试：

```java
@Test
void list() {
    List<?> list = service.list();
    log.debug("查询列表完成，列表中的数据的数量：{}", list.size());
    for (Object item : list) {
        log.debug("{}", item);
    }
}
```

# 查询相册列表--Controller

首先，需要在`JsonResult`中添加新的属性，用于封装“响应到客户端的数据”：

```java
/**
 * 操作成功时响应的数据
 */
private Object data;
```

并且，为了便于封装以上属性的值，还推荐在`JsonResult`中添加新的静态方法（同时调整原有的方法）：

```java
public static JsonResult ok() {
    return ok(null);
}

public static JsonResult ok(Object data) {
    JsonResult jsonResult = new JsonResult();
    jsonResult.state = ServiceCode.OK.getValue();
    jsonResult.data = data;
    return jsonResult;
}
```

在`AlbumController`中添加处理请求的方法：

```java
// http://localhost:8080/albums
@ApiOperation("查询相册列表")
@ApiOperationSupport(order = 420)
@GetMapping("")
public JsonResult list() {
    log.debug("开始处理【查询相册列表】的请求，无参数");
    List<AlbumListItemVO> list = albumService.list();
    return JsonResult.ok(list);
}
```

完成后，重启项目，可以通过API文档来查看并测试访问。

# 基于Spring JDBC的事务管理

事务（Transaction）：是关系型数据库中一种能够保障多个写操作（增、删、改）要么全部成功，要么全部失败的机制。

在基于Spring JDBC的项目中，只需要在业务方法上添加`@Transactional`注解，即可使得此方法是**事务性**的。

```mysql
UPDATE 存款表 SET 余额=余额+50000 WHERE 账户='刘苍松';

UPDATE 存款表 SET 余额=余额-50000 WHERE 账户='刘国斌';
```







