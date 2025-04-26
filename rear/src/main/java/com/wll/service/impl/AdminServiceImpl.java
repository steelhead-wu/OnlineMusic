package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.Admin;
import com.wll.mapper.AdminMapper;
import com.wll.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-04-24
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    /**
     * @param username a username, must be not null
     * @return true if username exist else false
     */
    private boolean checkUniqueUsername(String username) {
        Admin admin = getOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username));
        return Objects.isNull(admin);
    }


    public Admin login(Admin loginAdmin) {
        return getOne(new LambdaQueryWrapper<Admin>()
                .eq(Objects.nonNull(loginAdmin.getUsername()), Admin::getUsername, loginAdmin.getUsername())
                .eq(Objects.nonNull(loginAdmin.getPassword()), Admin::getPassword, loginAdmin.getPassword()));
    }

    public boolean registry(Admin registryAdmin) {
        // check username and password are not null
        if (StringUtils.isEmpty(registryAdmin.getUsername()) || StringUtils.isEmpty(registryAdmin.getPassword())) {
            return false;
        }
        // check username and password not exist whitespace
        if (StringUtils.isExistWhiteSpace(registryAdmin.getUsername())
                || StringUtils.isExistWhiteSpace(registryAdmin.getPassword())) {
            return false;
        }
        // check username is unique
        if (!checkUniqueUsername(registryAdmin.getUsername())) {
            return false;
        }

        return save(registryAdmin);
    }
}
