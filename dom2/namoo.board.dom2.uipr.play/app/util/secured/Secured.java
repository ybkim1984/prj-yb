package util.secured;

import controllers.routes;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		ctx.flash().put("error", "로그인하세요.");
		return redirect(routes.Application.loginForm());
	}
	
}
