package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diboot.core.vo.JsonResult;
import com.diboot.core.vo.Pagination;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User相关Controller
 * @author www.dibo.ltd
 * @version v2.0
 * @date 2019/7/19
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseCrudMappingRestController<User, UserVO> {

    @Autowired
    private UserService userService;

    /***
     * 查询VO的分页数据: ：此例同时示例DTO自动绑定转换为QueryWrapper。
     * <p>
     * url参数示例: /listWithDto?gender=M&orderBy=username
     * </p>
     * @return
     * @throws Exception
     */
    @GetMapping("/listWithDto")
    public JsonResult getVOListWithDTO(UserDto userDto, Pagination pagination, HttpServletRequest request) throws Exception{
        // DTO转换为QueryWrapper，若无@BindQuery注解默认映射为等于=条件，有注解映射为注解条件。
        QueryWrapper<User> queryWrapper = super.buildQueryWrapper(userDto, request);
        // 查询当前页的Entity主表数据
        List entityList = userService.getEntityList(queryWrapper, pagination);
        // 自动转换VO中注解绑定的关联
        List<UserVO> voList = super.convertToVoAndBindRelations(entityList, UserVO.class);
        // 返回结果
        return new JsonResult(voList).bindPagination(pagination);
    }

}
