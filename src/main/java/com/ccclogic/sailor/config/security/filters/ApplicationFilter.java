package com.ccclogic.sailor.config.security.filters;


import com.ccclogic.sailor.config.ErrorCodeMessages;
import com.ccclogic.sailor.exceptions.ClientException;
import com.ccclogic.sailor.exceptions.ErrorResponse;
import com.ccclogic.sailor.exceptions.ResourceNotFoundException;
import com.ccclogic.sailor.exceptions.ServiceException;
import com.ccclogic.sailor.util.CallCenterUtil;
import com.ccclogic.sailor.util.JsonUtil;
import com.ccclogic.sailor.util.RequestUtil;
import com.ccclogic.sailor.util.StringUtil;
import com.ccclogic.sailor.util.enums.ContentType;
import com.ccclogic.sailor.util.errorcodes.CallCenterErrorCodes;
import com.ccclogic.sailor.util.errorcodes.GenericErrorCodes;
import com.ccclogic.sailor.util.errorcodes.RequestErrorCodes;
import com.ccclogic.sailor.util.logger.Debug;
import com.ccclogic.sailor.util.logger.Info;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApplicationFilter extends OncePerRequestFilter {

    private String[] excludeUrls = null;
    private String[] includeUrls = null;
    private ErrorCodeMessages errorCodeMessages;

    public ApplicationFilter(ErrorCodeMessages errorCodeMessages) {
        this.errorCodeMessages = errorCodeMessages;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setIncludeUrls(String[] includeUrls) {
        this.includeUrls = includeUrls;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ccId = RequestUtil.parseFromPath(request.getRequestURI()).get("callcenters");
        try {
            if(request.getRequestURI().startsWith("/api/v1/app")){
                filterChain.doFilter(request, response);
                return;
            }

            //  If request is OPTIONS, let it pass through. CCId can be null for admin/supervisor.
            if (!StringUtils.isBlank(ccId) && !HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {

                Long parsedCallcenterId = Longs.tryParse(ccId);
                Preconditions.checkArgument(parsedCallcenterId != null && parsedCallcenterId > 0, CallCenterErrorCodes.callCenterIdInvalid);
//                boolean callcenterExists = multiTenantDatasourceProvider.exists(CallCenterUtil.generateCallCenterSchemaNameById(Long.parseLong(ccId)));
                boolean callcenterExists = true;
                if (!callcenterExists) {
                    throw new ResourceNotFoundException(CallCenterErrorCodes.callCenterNoFound);
                }

                log.info("setting schema context for ccid :: " + ccId);
                CallCenterUtil.setCCSchemaInContext(ccId);
                CallCenterUtil.setCCIdInContext(ccId);
            }

            filterChain.doFilter(request, response);

        } catch (AccessDeniedException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_FORBIDDEN, ex);
        } catch (AuthenticationException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_UNAUTHORIZED, ex);
        } catch (ClientException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_BAD_REQUEST, ex);
        } catch (ServiceException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex);
        } catch (ResourceNotFoundException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_NOT_FOUND, ex);
        } catch (IllegalArgumentException ex) {
            flushErrorMessage(request, response, HttpServletResponse.SC_BAD_REQUEST, ex);
        } catch (RequestRejectedException ex) {
            ErrorResponse errorResponse = new ErrorResponse(RequestErrorCodes.invalidRequestUrl, errorCodeMessages.getMessage(RequestErrorCodes.invalidRequestUrl));
            flushErrorMessage(request, response, HttpServletResponse.SC_BAD_REQUEST, ex, errorResponse);
        } catch (Exception ex) {
            ErrorResponse unknownErrorResponse = new ErrorResponse(GenericErrorCodes.unknowError,
                    errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
            flushErrorMessage(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex,
                    unknownErrorResponse);
        }
    }

    private void flushErrorMessage(HttpServletRequest request, HttpServletResponse response,
                                   int responseStatus, Exception ex) {
        flushErrorMessage(request, response, responseStatus, ex, null);
    }

    private void flushErrorMessage(HttpServletRequest request, HttpServletResponse response,
                                   int responseStatus, Exception ex, ErrorResponse errorResponse) {
        try {
            log.error(errorCodeMessages.getMessage(ex.getMessage()), ex);
            if (errorResponse == null) {
                if (!StringUtil.isValid(ex.getMessage())) {
                    errorResponse = new ErrorResponse(GenericErrorCodes.unknowError, errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
                } else {
                    errorResponse = new ErrorResponse(ex.getMessage(), errorCodeMessages.getMessage(ex.getMessage()));
                }
            }

            if ((request.getParameter("debug") != null && "true".equals(request.getParameter("debug")))) {
                errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
                response.getWriter().println(JsonUtil.convertObjectWithViewToJsonString(errorResponse, Debug.class));
            } else {
                response.getWriter().println(JsonUtil.convertObjectWithViewToJsonString(errorResponse, Info.class));
            }

            response.setHeader("Content-Type", ContentType.json.getValue() + ";charset=UTF-8");
            response.setStatus(responseStatus);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("Error while flushing error message", e);
            ErrorResponse unknownErrorResponse = new ErrorResponse(GenericErrorCodes.unknowError,
                    errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
            try {
                response.getWriter().println(JsonUtil.convertObjectWithViewToJsonString(unknownErrorResponse, Info.class));
                response.setHeader("Content-Type", ContentType.json.getValue() + ";charset=UTF-8");
                response.setStatus(responseStatus);
                response.flushBuffer();
            } catch (IOException e1) {
                log.error("Error while flushing error message", e);
            }
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (excludeUrls != null) {
            for (String urlRegex : includeUrls) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(urlRegex);
                if (antPathRequestMatcher.matches(request)) {
                    return false;
                }
            }
            for (String urlRegex : excludeUrls) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(urlRegex);
                if (antPathRequestMatcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }

}
