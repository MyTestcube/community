package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.dto.GithubProvider;
import life.majiang.community.provider.dto.UFileResult;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserService userService;

//    @GetMapping("/callback/{type}")
//    public String newCallback(@PrathVariable(name = "type") String type,
//                              @RequestParam(name = "code") String code,
//                              @RequestParam(name = "state", required = false) String state,
//                              HttpServletRequest request,
//                              HttpServletResponse response) {
//        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
//        LoginUserInfo loginUserInfo = userStrategy.getUser(code, state);
//        if (loginUserInfo != null && loginUserInfo.getId() != null) {
//            User user=new User();
//            String token=UUID.randomUUID().toString();
//            user.setToken(token);
//            user.setName(loginUserInfo.getName());
//            user.setType(type);
//            UFileResult fileResult=null;
//            try{
//                fileResult = uFileService,upload()
//            }
//        }
//
//    }

    //(required = false)
    @GetMapping("/callback")
    public String callback(
//            @RequestParam(name = "type") String type,
                           @RequestParam(name = "code") String code,
                           @RequestParam(name = "state", required = false) String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
//        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);//5ef1314640c129e68f62
        accessTokenDTO.setClient_secret(clientSecret);//6ba808b82f7820ce0c0b42085f3aec649f70eaef
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);//http://localhost:8888/callback
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
//shift+f6(更改所有代码)
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            UFileResult fileResult = null;
            try {
//                fileResult = uFileService.upload(githubUser.getAvatarUrl());
                user.setAvatarUrl(fileResult.getFileUrl());
            } catch (Exception e) {
                user.setAvatarUrl(githubUser.getAvatarUrl());
            }
            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("toke", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            response.addCookie(cookie);

            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";

        } else {
//            log.error("callback get github error,{}",githubUser);
            return "redirect:/";
            //登录失败，重新登录
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
