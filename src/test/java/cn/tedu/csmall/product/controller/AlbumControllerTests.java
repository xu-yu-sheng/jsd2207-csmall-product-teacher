package cn.tedu.csmall.product.controller;

import cn.tedu.csmall.product.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerTests {

    @Autowired
    MockMvc mockMvc; // 模拟的客户端，可以发请求、拿响应结果

    String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiYXV0aG9yaXRpZXNKc29uU3RyaW5nIjoiW3tcImF1dGhvcml0eVwiOlwiL2Ftcy9hZG1pbi9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL2Ftcy9hZG1pbi9kZWxldGVcIn0se1wiYXV0aG9yaXR5XCI6XCIvYW1zL2FkbWluL3JlYWRcIn0se1wiYXV0aG9yaXR5XCI6XCIvYW1zL2FkbWluL3VwZGF0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvYWxidW0vYWRkLW5ld1wifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvYWxidW0vZGVsZXRlXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9hbGJ1bS9yZWFkXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9hbGJ1bS91cGRhdGVcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2JyYW5kL2FkZC1uZXdcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2JyYW5kL2RlbGV0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvYnJhbmQvcmVhZFwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvYnJhbmQvdXBkYXRlXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9jYXRlZ29yeS9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9jYXRlZ29yeS9kZWxldGVcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2NhdGVnb3J5L3JlYWRcIn0se1wiYXV0aG9yaXR5XCI6XCIvcG1zL2NhdGVnb3J5L3VwZGF0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcGljdHVyZS9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9waWN0dXJlL2RlbGV0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcGljdHVyZS9yZWFkXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9waWN0dXJlL3VwZGF0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcHJvZHVjdC9hZGQtbmV3XCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9wcm9kdWN0L2RlbGV0ZVwifSx7XCJhdXRob3JpdHlcIjpcIi9wbXMvcHJvZHVjdC9yZWFkXCJ9LHtcImF1dGhvcml0eVwiOlwiL3Btcy9wcm9kdWN0L3VwZGF0ZVwifV0iLCJleHAiOjE2Njk2OTMzMTAsInVzZXJuYW1lIjoicm9vdCJ9.eMohGLCcpVe6XnTEbWq9mTWrT5HWZWSr_LIoPh9frb0";

    @Test
    @Sql(scripts = {"classpath:sql/truncate_all_tables.sql",
            "classpath:sql/insert_all_test_data.sql"})
    @Sql(scripts = "classpath:sql/truncate_all_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteSuccessfully() throws Exception {
        // 测试数据
        Long id = 1L;
        // 请求路径，注意：不需要写协议、主机名、端口号，即不需要 http://localhost:9080
        String url = "/albums/" + id + "/delete";
        // 执行测试
        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", jwt)
            // 反复调用 .param() 可以提交多个请求参数
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                    .jsonPath("state")
                    .value(ServiceCode.OK.getValue()))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql(scripts = {"classpath:sql/truncate_all_tables.sql"})
    void deleteFailBecauseNotFound() throws Exception {
        // 测试数据
        Long id = 1L;
        // 请求路径，注意：不需要写协议、主机名、端口号，即不需要 http://localhost:9080
        String url = "/albums/" + id + "/delete";
        // 执行测试
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", jwt)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("state")
                        .value(ServiceCode.ERR_NOT_FOUND.getValue()))
                .andDo(MockMvcResultHandlers.print());
    }

}
