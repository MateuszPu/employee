package net.guides.springboot2.springboot2jpacrudexample.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
class LoggerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String ipAddress = request.getRemoteAddr();
		String url = request.getRequestURI();
		String method = request.getMethod();
		log.info("[INFO] Request {} to {} requested by {} at {}", method, url, ipAddress, LocalDateTime.now().atZone(ZoneId.of("UTC")));
		return super.preHandle(request, response, handler);
	}
}
