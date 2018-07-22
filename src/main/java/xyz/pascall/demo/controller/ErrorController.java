package xyz.pascall.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import xyz.pascall.demo.utils.JSONResult;

/**
 * 全局错误同一处理
 */
@Controller
public class ErrorController extends BasicErrorController{

	public ErrorController(ServerProperties serverProperties) {
		super(new DefaultErrorAttributes(), serverProperties.getError());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 出现异常，并且返回的类型为页面时：
	 * 		页面为：/error.btl
	 * 		参数为：error、status、message、timestamp、path
	 */
	@Override
	public ModelAndView errorHtml(HttpServletRequest request,
			HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
				request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
		model = this.getErrorInfo(model);
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		
		
		if(modelAndView == null) {
			modelAndView = new ModelAndView("/error.btl", model);
		}
		modelAndView.setViewName("/error.btl");
		return modelAndView;
	}

	/**
	 * 出现异常，并且返回的类型为非页面时：
	 * 		参数为：error、status、message、timestamp、path
	 */
	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request,
				isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		body = this.getErrorInfo(body);
		ResponseEntity<Map<String, Object>> entity = new ResponseEntity<>(body, status);
		return entity;
	}

	/**
	 * 拼接错误信息
	 * 		status, msg, data, ok:null
	 * @param body
	 * @return
	 */
	private Map<String, Object> getErrorInfo(Map<String, Object> body){
		System.err.println(body);
		Map<String, Object> map = new HashMap<>();
		int status = (Integer) body.get("status");
		map.put("status", status);
		map.put("data", body.get("path"));
		String msg = "未知";
		if(status == 404){
			msg = "没有找到指定路径";
		}else if(status == 405){
			msg = "你的请求方法不被允许";
		}else if(status == 500){
			msg = "服务器出错";
		}else if(status == 502){
			msg = "拦截器拦截到用户token出错";
			map.put("data", body.get("message"));
		}else if(status == 555){
			msg = "异常抛出信息";
			map.put("data", body.get("message"));
		}
		map.put("msg", msg);
		map.put("ok", null);
		System.err.println(map);
		// status,
		return map;
	}
}
