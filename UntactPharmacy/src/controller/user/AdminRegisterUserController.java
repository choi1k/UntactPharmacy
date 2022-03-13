package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.User;
import model.service.ExistingUserException;
import model.service.UserManager;

public class AdminRegisterUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AdminRegisterUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User(
			request.getParameter("user_id"),
			request.getParameter("user_password"),
			request.getParameter("user_name"),
			request.getParameter("email"),
			request.getParameter("phone"),
			request.getParameter("gender"),
			request.getParameter("birth_date"),
			request.getParameter("symptom"));
		
		
		
        log.debug("Create User : {}", user);

		try {
			UserManager manager = UserManager.getInstance();
			manager.create(user);
	        return "redirect:/admin/list";	// 성공 시 사용자 리스트 화면으로 redirect
	        
		} catch (ExistingUserException e) {	// 예외 발생 시 회원가입 form으로 forwarding
            request.setAttribute("registerFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("user", user);
			return "/admin/registerForm.jsp";
		}
    }
}
