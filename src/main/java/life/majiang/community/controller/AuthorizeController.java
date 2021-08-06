package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired(required = false)
    private UserMapper userMapper;
//
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);//5ef1314640c129e68f62
        accessTokenDTO.setClient_secret(clientSecret);//6ba808b82f7820ce0c0b42085f3aec649f70eaef
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);//http://localhost:8888/callback
        accessTokenDTO.setState(state);
//        githubProvider.getAccessToken(accessTokenDTO);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser =githubProvider.getUser(accessToken);
        if(githubUser!=null){
//shift+f6(更改所有代码)
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";

        }else {
            return "redirect:/";
            //登录失败，重新登录
        }

    }
}
