https://blog.csdn.net/qq_28165595/article/details/102768244?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-1
集成springsession之前

这里简单搭建了两个服务client1和cilent2,我们在这连个服务中提供两个接口，如下
@RequestMapping("/springSessionTest")
@RestController
public class SpringSessionController {
 
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }
 
    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("request Url"));
        return map;
    }
}
client2中的接口和client1代码是一样的，这里就不重复粘贴了。两个服务的端口分别是9002和9003。这里我们把两个服务启动成功之后，我们先访问client1的test接口，然后在访问client1中sessions接口来看下具体session打印。client1中的sessions接口响应如下

{
"sessionId":"6C3E8502E1DFA9FB5595D8EEE848A900",
"message":"http://localhost:9002/springSessionTest/test"
}
接下来我们依次访问client2接口中的test接口和sessions接口，得到的响应如下

{
"sessionId":"B6DEB7D49CCA3AA239FA593555605C4B",
"message":"http://localhost:9003/springSessionTest/test"
}
可以看到两个session是相互独立的。
集成springsession之后

首先我们在两个服务中引入相关的依赖

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
 </dependency>
然后在配置文件中加上redis配置

spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.database=3
spring.redis.password=12345
spring.redis.timeout=5000
再在两个服务的启动类上加上@EnableRedisHttpSession注解，开启spring session功能，两个服务正常启动之后，重复上面操作，先访问client1的test和sessions接口，接口响应如下

{
"sessionId":"5fdd9b56-70ab-4722-81f6-8c4af721a1e6",
"message":"http://localhost:9002/springSessionTest/test"
}
接下来我们再访问client2服务中的test，sessions接口，接口响应如下

{
"sessionId":"5fdd9b56-70ab-4722-81f6-8c4af721a1e6",
"message":"http://localhost:9003/springSessionTest/test"
}
可以看到两个sessionId是一样的，所以client1和client2的共享session是实现了的，网上好多小伙伴说，你这种实现方式是两个服务部署在同一台机器上，如果把两个服务分别部署在两台机器上，上面的测试效果就不是这样了，如果遇到这种情况的小伙伴可能需要确认下自己测试的两台机器上的时间是不是一致，如果不是的话，就可能会导致上面的测试失败。