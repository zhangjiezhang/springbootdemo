package xyz.pascall.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pascall.demo.pojo.Polity;
import xyz.pascall.demo.utils.JSONResult;

@RestController
@RequestMapping("/")
public class SessionRedisController extends BasicController{

	Log log = LogFactory.getLog(SessionRedisController.class);
	
	@GetMapping("/putSession.json")
	public Object putSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		log.info(session.getClass());
		log.info(session.getId());
		Polity polity = new Polity();
		polity.setId(2520001);
		polity.setName("团员");
		polity.setStatus(0);

		String token = saveUser(polity);
		return JSONResult.build(200, "成功", token);
	}

	@GetMapping("/getSession.json")
	public Object getSession(String token) {

		Object result = getUser(token);
		if(result instanceof Boolean){
			return JSONResult.build(500, "登录超时或还未登录", null);
		}
		Polity polity = (Polity)result;
		System.err.println(polity);
		return JSONResult.build(200, "成功", polity);
	}

	@GetMapping("/setSession.json")
	public Object setSession(String token) {
		redis.expire(USER_REDIS_SESSION+":"+token, 20);
		return JSONResult.build(200, "成功", null);
	}
}
