package cn.tedu.csmall.product.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPrincipal implements Serializable {

    private Long id;
    private String username;

}
