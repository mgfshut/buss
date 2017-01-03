package com.rhtop.buss.common.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
* 登录过滤器
* @author mgf
*
*/
public class UserLoginFormFilter extends FormAuthenticationFilter {
	static final private String VERIFICATION_CODE = "VERIFICATION_CODE";
	static final private String MOBILE_CODE = "MOBILE_CODE";


	private SessionKickout kickout = null;
	private Boolean enableVerificationCode = false;
	private Boolean enableMobileCode = false;
	private Boolean saveRequest = true;
	@Autowired
	private DefaultFilterFactoryBean filterFactoryBean;

	private String verificationCodeKey = VERIFICATION_CODE;
	private String mobileCodeKey = MOBILE_CODE;
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		UserLoginToken ut = new UserLoginToken();
		try{
			ut.setHost(this.getHost(request));
			ut.setPassword(this.getPassword(request).toCharArray());
			ut.setRememberMe(this.isRememberMe(request));
			ut.setUsername(this.getUsername(request));
			ut.setMobileCodePass(this.getMobileCodePass(request));
			ut.setVerifitionCodePass(this.getVerifitionCodePass(request));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		loadPermission(true);
		return ut;
	}

	private void loadPermission(Boolean forceReload) {
		try {
			filterFactoryBean.reloadPermission(forceReload);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException("加载权限失败");
		}
	}

	private boolean getVerifitionCodePass(ServletRequest request) {
		if(!this.enableVerificationCode) return true;
		String code = request.getParameter(this.getVerificationCodeKey());
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String sessionRandCode =
				(String) httpRequest.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		return sessionRandCode != null && sessionRandCode.equals(code);
	}

	private boolean getMobileCodePass(ServletRequest request) {
		if(!this.enableMobileCode) return true;
		String code = request.getParameter(this.getMobileCodeKey());
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String mobileCode =
				(String) httpRequest.getSession().getAttribute(this.getMobileCodeKey());
		return mobileCode != null && mobileCode.equals(code);
	}

	public Boolean getEnableVerificationCode() {
		return enableVerificationCode;
	}

	public void setEnableVerificationCode(Boolean enableVerificationCode) {
		this.enableVerificationCode = enableVerificationCode;
	}

	public Boolean getEnableMobileCode() {
		return enableMobileCode;
	}

	public void setEnableMobileCode(Boolean enableMobileCode) {
		this.enableMobileCode = enableMobileCode;
	}

	public String getVerificationCodeKey() {
		return verificationCodeKey;
	}

	public void setVerificationCodeKey(String verificationCodeKey) {
		this.verificationCodeKey = verificationCodeKey;
	}

	public String getMobileCodeKey() {
		return mobileCodeKey;
	}

	public void setMobileCodeKey(String mobileCodeKey) {
		this.mobileCodeKey = mobileCodeKey;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		if(!saveRequest) WebUtils.getAndClearSavedRequest(request);
		this.loadPermission(false);
		if(kickout != null){
			if(!kickout.isAccessAllowed(getSubject(request, response))) return false;
		}
		boolean ret = super.onAccessDenied(request, response);
		return ret;
	}
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		if(kickout != null) kickout.isAccessAllowed(getSubject(request, response));
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		return super.onLoginFailure(token, e, request, response);
	}

	@Override
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		if(ae instanceof VaildCodeAuthException){
			request.setAttribute(getFailureKeyAttribute(), "验证码错误");
		}
		else if(ae instanceof VaildMobileAuthException){
			request.setAttribute(getFailureKeyAttribute(), "手机验证码错误");
		}
		else{
			request.setAttribute(getFailureKeyAttribute(), "用户名或密码错误");
		}
	}

	public Boolean getSaveRequest() {
		return saveRequest;
	}

	public void setSaveRequest(Boolean saveRequest) {
		this.saveRequest = saveRequest;
	}

	public SessionKickout getKickout() {
		return kickout;
	}

	public void setKickout(SessionKickout kickout) {
		this.kickout = kickout;
	}
}
