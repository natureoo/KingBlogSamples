package king.blog.web;

import king.blog.common.exception.BusinessException;
import king.blog.common.resp.AppResponse;
import king.blog.common.validates.Add;
import king.blog.common.validates.Update;
import king.blog.model.Dog;
import king.blog.service.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by kinginblue on 2017/4/10.
 */
@RestController
@RequestMapping(value = "/dogs", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class DogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DogController.class);

    @Autowired
    private DogService dogService;

    /**
     * 手动处理 Service 层异常和数据校验异常的示例
     *
     * 当使用了 @Validated + @RequestBody 注解但是没有在绑定的数据对象后面跟上 Errors 类型的参数声明的话，Spring MVC 框架会抛出 MethodArgumentNotValidException 异常。
     * @param dog
     * @return
     */
    @PostMapping(value = "")
//    AppResponse add(@Validated(Add.class) @RequestBody Dog dog, Errors errors){
    AppResponse add(@Validated(Add.class) @RequestBody Dog dog){
        AppResponse resp = new AppResponse();
        try {
            // 数据校验
//            BSUtil.controllerValidate(errors);

            // 执行业务
            Dog newDog = dogService.save(dog);

            // 返回数据
            resp.setData(newDog);

        }catch (BusinessException e){
            LOGGER.error(e.getMessage(), e);
            resp.setFail(e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            resp.setFail("操作失败！");
        }
        return resp;
    }

    /**
     * 使用 GlobalExceptionHandler 全局处理 Controller 层异常的示例
     *
     * @param dog
     * @return
     */
    @PatchMapping(value = "")
    AppResponse update(@Validated(Update.class) @RequestBody Dog dog){
//    AppResponse update(@Validated(Update.class) @RequestBody Dog dog, @ModelAttribute("user") String user, Date date){
//        System.out.println("user:" + user);
//        System.out.println("date:" + date);
        AppResponse resp = new AppResponse();

        // 执行业务
        Dog newDog = dogService.update(dog);

        // 返回数据
        resp.setData(newDog);

        return resp;
    }

    @PostMapping(value = "model")
    AppResponse update( @ModelAttribute("user") String user, Date date){
        System.out.println("user:" + user);
        System.out.println("date:" + date);
        AppResponse resp = new AppResponse();
        return resp;
    }

}
