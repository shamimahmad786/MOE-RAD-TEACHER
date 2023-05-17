package com.example.MOERADTEACHER.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.example.MOERADTEACHER.common.exceptions.UserNotAuthorizedException;

//@Configuration
//@Service

@Controller
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomFilter implements Filter {

	String token = null;
	String username=null;

	@Autowired
	RestService restService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("########## Initiating Custom filter ##########");
	}


	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		 HttpServletResponse res = (HttpServletResponse) response;
		String userName;
		
		System.out.println("called");
		res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Origin", "*");
	res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
	res.setHeader("Access-Control-Allow-Headers", "*");
	res.setHeader("Access-Control-Max-Age", "180");

		// System.out.println("request url--->"+req.getRemoteHost());
		
		HeaderMapRequestWrapper requestWrapper=null;
		
		try {
//			throw new UserNotAuthorizedException("Data Tempered");
		}catch(Exception ex) {
//			throw new UserNotAuthorizedException("Data Tempered");
		}finally {
//			throw new UserNotAuthorizedException("Data Tempered");
		}
		
		
		if(req.getHeader("access-control-request-headers")==null || true) {
			
			System.out.println(req.getRequestURI());
			
		
		if(!req.getRequestURI().contains("getMaster") &&  !req.getRequestURI().contains("uploadDocument") && !req.getRequestURI().contains("uploadProfileImage") && !req.getRequestURI().contains("getProfileImage")) {
//		// System.out.println("in if--->"+req.getRequestURI());
		XSSRequestWrapper wrappedRequest1 = new XSSRequestWrapper(req);
		String body = wrappedRequest1.getBody();
		String clientIP = wrappedRequest1.getRemoteHost();
		int clientPort = request.getRemotePort();
		String uri = wrappedRequest1.getRequestURI();

		String x_headers = req.getHeader("X-HEADERS");

		if (body != null && x_headers != null) {

//			// System.out.println(req.getRequestURI() + ":::::::::::::::::::::" + body);

			StringBuilder sb = new StringBuilder();
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(body.getBytes());
				byte[] bytes = md.digest();
				for (int i = 0; i < bytes.length; i++) {
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

//			// System.out.println(sb.toString() + "::::::::::::::::::Before Match::::::::::::::::::::" + x_headers);
			if (sb.toString().equalsIgnoreCase(x_headers)) {
				// System.out.println("Match hashing");

			} else {
				// System.out.println("Not match hashing");
//				throw new UserNotAuthorizedException("Data Tempered");
			}

		}

		 requestWrapper = new HeaderMapRequestWrapper(wrappedRequest1);
		}else {
//			// System.out.println("In else condtition--->");
//			 request.getRequestDispatcher("/api/exception/exception").forward(request, response);
		 requestWrapper = new HeaderMapRequestWrapper(req);
		}
		Enumeration headerNames = req.getHeaderNames();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
//		  while (headerNames.hasMoreElements()) {
//	            String key = (String) headerNames.nextElement();
//	            String value = req.getHeader(key);
//	           // System.out.println(key+"-----"+value);
//	        }
		
//		LOGGER.info("::::::::::::::::::::::::::::" + req.getRequestURI() + ":::::::::::::::::::::::::::::::::::");
		token = req.getHeader("authorization");
		username =req.getHeader("username");
		
//		System.out.println("username--->"+username);
		
		// System.out.println(":::::Request Url-"+req.getRequestURI()+"::::::Token-"+token);
		
		
		try {
			if (!req.getRequestURI().contains("getDocumentByTeacherId")
					&& !req.getRequestURI().contains("getProfileImage")
					&& !req.getRequestURI().contains("uploadProfileImage")
					&& !req.getRequestURI().contains("deleteDocumentByTeacherIdAndName")
					&& !req.getRequestURI().contains("getDocumentByTeacherId")
					&& !req.getRequestURI().contains("uploadDocument")
					&& !req.getRequestURI().contains("resetPassword")
					&& !req.getRequestURI().contains("create-kvuser")
					&& !req.getRequestURI().contains("get-usercradential") && !req.getRequestURI().contains("downloadDocument")) {
				if (token != null) {
//					// System.out.println("in if condition--->"+restService.getPostsPlainJSON(token));
//					if (restService.getPostsPlainJSON(token) != null) {
						if (true) {
						requestWrapper.addHeader("username", username);
//						requestWrapper.addHeader("username",
//								restService.getPostsPlainJSON(token).getBody().getUser_name());
					} else {
						requestWrapper.addHeader("username", username);
//						 request.getRequestDispatcher("/api/exception/exception").forward(request, response);
//						throw new UserNotAuthorizedException("User unauthenticated");
				
					}
				} else {
					// System.out.println("in if condition 1");
//					 request.getRequestDispatcher("/api/exception/exception").forward(request, response);
					
//					 return false;
					requestWrapper.addHeader("username", "XYZ");
//					throw new UserNotAuthorizedException("User unauthenticated");
				}
			}else if(req.getRequestURI().contains("downloadDocument")) {
				
			} else {
//				// System.out.println("in else condition request forwarding");
//				request.getRequestDispatcher("/api/exception/exception").forward(request, response);
				requestWrapper.addHeader("username", username);
//				throw new UserNotAuthorizedException("User unauthenticated");
			}

		} catch (Exception ex) {
			
			requestWrapper.addHeader("username", "tokenFail");
			LOGGER.error(":::::::::::Error in check token:::::::::::::::::");
			LOGGER.error("message", ex);
			ex.printStackTrace();
//			request.getRequestDispatcher("/api/exception/exception").forward(request, response);
//			throw new UserNotAuthorizedException("User unauthenticated");

		}
		
//		response.setHeader("Access-Control-Allow-Origin", "http://10.25.26.251:4200,https://demopgi.udiseplus.gov.in,https://pgi.udiseplus.gov.in");
		res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
//	        response.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.setHeader("Access-Control-Max-Age", "180");
		chain.doFilter(requestWrapper, res);
		}else {
			chain.doFilter(req, res);
		}
		
		
//		 return;
	}

	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {

		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<String, String>();

		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			for (String name : headerMap.keySet()) {
				names.add(name);
			}
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = Collections.list(super.getHeaders(name));
			if (headerMap.containsKey(name)) {
				values.add(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}

	}

	private void logPostOrPutRequestBody(HttpServletRequest httpRequest) throws IOException {
		if (Arrays.asList("POST", "PUT").contains(httpRequest.getMethod())) {

			try {

				String characterEncoding = httpRequest.getCharacterEncoding();
				Charset charset = Charset.forName(characterEncoding);
				String bodyInStringFormat = readInputStreamInStringFormat(httpRequest.getInputStream(), charset);

//				// System.out.println("bodyInStringFormat---->" + bodyInStringFormat);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private String readInputStreamInStringFormat(InputStream stream, Charset charset) throws IOException {
		final StringBuilder bodyStringBuilder = new StringBuilder();
		try {
			final int MAX_BODY_SIZE = 1024;

			if (!stream.markSupported()) {
				stream = new BufferedInputStream(stream);
			}

			stream.mark(MAX_BODY_SIZE + 1);
			final byte[] entity = new byte[MAX_BODY_SIZE + 1];
			final int bytesRead = stream.read(entity);

			if (bytesRead != -1) {
				bodyStringBuilder.append(new String(entity, 0, Math.min(bytesRead, MAX_BODY_SIZE), charset));
				if (bytesRead > MAX_BODY_SIZE) {
					bodyStringBuilder.append("...");
				}
			}
			stream.reset();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bodyStringBuilder.toString();
	}

}