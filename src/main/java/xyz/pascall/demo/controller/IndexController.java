package xyz.pascall.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pascall.demo.dao.PolityDao;
import xyz.pascall.demo.pojo.Polity;
import xyz.pascall.demo.utils.ExceptionUtil;
import xyz.pascall.demo.utils.JSONResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private PolityDao polityDao;

    @GetMapping("/index")
    public Object index(){
        return "你好， 世界！";
    }

    @GetMapping("/polity/get")
    public Object getPolityById(String id){
        if(StringUtils.isEmpty(id)){
            id = "2";
        }
        Polity polity = new Polity();
        Integer idInt = Integer.parseInt(id);
        polity.setId(idInt);
        List<Polity> polities = polityDao.getPolityById(polity);
        return polities;
    }

    @PostMapping("/polity/add")
    public Object addPolity(@Validated Polity polity, BindingResult result) throws ExceptionUtil {



        if(result.hasErrors()){
            List<ObjectError> error =  result.getAllErrors();
            Map<String, Object> map = new HashMap<>();
            for (ObjectError objectError : error) {
                FieldError fieldError = (FieldError)objectError;
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            //throw new ExceptionUtil("报错了");
            return JSONResult.build(501, "参数校验错误", map);
        }

        return JSONResult.build(200, "校验成功", null);
    }
}
