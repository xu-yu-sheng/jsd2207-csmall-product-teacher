package cn.tedu.csmall.product.web;

/**
 * 业务状态码
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
public enum ServiceCode {

    OK(100),
    ERR_CONFLICT(200),
    ERR_NOT_FOUND(300);

    private Integer value;

    private ServiceCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
