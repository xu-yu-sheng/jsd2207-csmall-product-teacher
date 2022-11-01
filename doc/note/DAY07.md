# 43. 关于服务器端响应的结果（续）

为了更加规范管理响应结果中的“状态码”，在根包下创建`web.ServiceCode`枚举类型，此类型用于穷举可能使用到的业务状态码，并为每个业务状态码设置一个对应的数值结果：

```java
package cn.tedu.csmall.product.web;

/**
 * 业务状态码
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public enum ServiceCode {

    /**
     * 成功
     */
    OK(20000),
    /**
     * 错误：数据不存在
     */
    ERR_NOT_FOUND(40400),
    /**
     * 错误：数据冲突
     */
    ERR_CONFLICT(40900);

    private Integer value;

    private ServiceCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
```

然后，在`JsonResult`类中，添加自定义的静态方法，用于快速创建类的对象，并且，通过静态方式的名称（例如`ok`、`fail`等）更好的表现语义，必要的情况下，还可以对这些方法重载，使得方法的调用更加灵活：

```java
package cn.tedu.csmall.product.web;

import cn.tedu.csmall.product.ex.ServiceException;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    /**
     * 状态码
     */
    private Integer state;
    /**
     * 操作失败时的描述文本
     */
    private String message;

    public static JsonResult ok() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = ServiceCode.OK.getValue();
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }

}
```

并且，需要调整`ServiceException`类型的构造方法，应该在抛出异常时，除了封装异常的描述文本（`message`），还应该定义此异常对应的业务状态码：

```java
public class ServiceException extends RuntimeException {

    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }
}
```

并且，在业务层中，抛出异常时，要封装以上数据（业务状态码和描述文本），例如：

```java
if (count > 0) {
    // 是：相册名称已经被占用，添加相册失败，抛出异常
    String message = "添加相册失败，相册名称已经被占用！";
    log.debug(message);
    throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
}
```

后续，在处理异常时，将返回`JsonResult`类型的对象，例如：

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.debug("创建全局异常处理器对象：GlobalExceptionHandler");
    }

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        log.debug("处理请求的方法抛出了ServiceException，将统一处理");
        return JsonResult.fail(e);
    }

}
```

当然，处理请求的方法也使用`JsonResult`作为方法的返回值类型，例如：

```java
@ApiOperation("添加相册")
@ApiOperationSupport(order = 100)
@PostMapping("/add-new")
public JsonResult addNew(AlbumAddNewDTO albumAddNewDTO) {
    log.debug("开始处理【添加相册】的请求，参数：{}", albumAddNewDTO);
    albumService.addNew(albumAddNewDTO);
    log.debug("添加相册成功！");
    return JsonResult.ok();
}
```

# 44. 关于检查请求参数

所有请求参数都应该对数据的基本格式进行检查，例如相册的名称至少由4个字符组成等等。

此类检查可以在客户端直接进行，但是，对于服务器端而言，客户端的检查结果应该视为“不信任的”！因为：

- 可能某些请求是绕过了客户端的检查的
- 客户端软件可能存在被篡改
- 客户端软件可能不是最新版本，检查数据的规则与服务器端期望的并不一致
- 其它原因

所以，**服务器端必须在接收到请求参数的第一时间就对请求参数的基本格式进行检查！**

注意：既然服务器端对所有请求参数都做了检查，客户端仍需要检查请求参数的基本格式！因为：

- 以上分析的“不信任”客户端检查结果的原因，大多是小概率事件，如果客户端能执行检查，将可以“过滤”掉绝大部分请求参数格式错误的请求，以减轻服务器端的压力，提升用户体验（因为检查结果能迅速体现出来）





