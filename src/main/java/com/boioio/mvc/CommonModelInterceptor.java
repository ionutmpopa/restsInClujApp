package com.boioio.mvc;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebi on 4/19/17.
 */
@Component
public class CommonModelInterceptor implements HandlerInterceptor {

    @ModelAttribute
    public void getCommonModel(ModelAndView model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;
        boolean isUser = false;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addObject("user", currentUserName);

            List<String> roles = new LinkedList();

            for (String role : roles) {
                if (role.equalsIgnoreCase("admin")) {
                    isAdmin = true;
                } else {
                    isUser = true;
                }
            }
            for (GrantedAuthority auth:
            authentication.getAuthorities()) {
                roles.add(auth.getAuthority());
            }

            model.addObject("roles", roles);
            model.addObject("isAdmin", isAdmin);
            model.addObject("isUser", isUser);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }

        boolean isRedirectView = modelAndView.getView() instanceof RedirectView;
        boolean isViewObject = modelAndView.getView() != null;
        boolean viewNameStartsWithRedirect = (modelAndView.getViewName() == null ? true
                : modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));

        if (modelAndView.hasView()
                && ((isViewObject && !isRedirectView) || (!isViewObject && !viewNameStartsWithRedirect))) {

            getCommonModel(modelAndView, request);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
