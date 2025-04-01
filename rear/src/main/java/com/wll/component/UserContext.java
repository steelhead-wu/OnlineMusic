//package com.wll.component;
//
///**
// * @time 2025/3/21 8:55 周五
// */
//
//import com.wll.pojo.User;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Component
//public class UserContext {
//
////    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
////
////    public void setCurrentUser(User user) {
////        currentUser.set(user);
////    }
////
////    public User getCurrentUser() {
////        return currentUser.get();
////    }
////
////    public void clear() {
////        currentUser.remove();
////    }
//
//    public Long getCurrentUser() {
//        // 获取当前认证信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            // 获取用户信息
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                return Long.valueOf(((UserDetails) principal).getUsername());
//            } else {
//                return Long.valueOf(principal.toString()); // 如果 principal 是字符串（如匿名用户）
//            }
//        }
//
//        return null; // 未认证用户
//    }
//
//
//
////    public Long getCurrentUser() {
////        // 获取当前请求的上下文
////        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
////        if (requestAttributes != null) {
////            // 从请求属性中获取用户 ID
////            return (Long) requestAttributes.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
////        }
////        return null;
////    }
////
////    public void setCurrentUser(Long userId) {
////        // 获取当前请求的上下文
////        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
////        if (requestAttributes != null) {
////            // 将用户 ID 存储到请求属性中
////            requestAttributes.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
////        }
////    }
//
//}