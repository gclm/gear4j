package club.gclmit.chaos.security.core.social;


import club.gclmit.chaos.web.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 绑定或解绑试图
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/21 5:26 下午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosConnecView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("application/json;charset=UTF-8");
        String message = "绑定成功";
        if (model.get("connections") == null) {
            message = "解绑成功";
        }
        response.getWriter().write(objectMapper.writeValueAsString(Result.ok(message)));
    }
}
