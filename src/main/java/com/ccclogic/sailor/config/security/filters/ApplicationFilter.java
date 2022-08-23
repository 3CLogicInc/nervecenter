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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ErrorCodeMessages errorCodeMessages;
    private String[] excludeCcIdUrls = null;

    public ApplicationFilter(ErrorCodeMessages errorCodeMessages) {
        this.errorCodeMessages = errorCodeMessages;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ccId = RequestUtil.parseFromPath(request.getPathInfo()).get("callcenters");
        try {
            //  If request is OPTIONS, let it pass through. CCId can be null for admin/supervisor.
            if (!StringUtils.isBlank(ccId) && !HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {

                Long parsedCallcenterId = Longs.tryParse(ccId);
                Preconditions.checkArgument(parsedCallcenterId != null && parsedCallcenterId > 0, CallCenterErrorCodes.callCenterIdInvalid);
//                boolean callcenterExists = multiTenantDatasourceProvider.exists(CallCenterUtil.generateCallCenterSchemaNameById(Long.parseLong(ccId)));
                boolean callcenterExists = true;
                if (!callcenterExists) {
                    throw new ResourceNotFoundException(CallCenterErrorCodes.callCenterNoFound);
                }

                logger.info("setting schema context for ccid :: " + ccId);
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
            logger.error(errorCodeMessages.getMessage(ex.getMessage()), ex);
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
            logger.error("Error while flushing error message", e);
            ErrorResponse unknownErrorResponse = new ErrorResponse(GenericErrorCodes.unknowError,
                    errorCodeMessages.getMessage(GenericErrorCodes.unknowError));
            try {
                response.getWriter().println(JsonUtil.convertObjectWithViewToJsonString(unknownErrorResponse, Info.class));
                response.setHeader("Content-Type", ContentType.json.getValue() + ";charset=UTF-8");
                response.setStatus(responseStatus);
                response.flushBuffer();
            } catch (IOException e1) {
                logger.error("Error while flushing error message", e);
            }
        }

    }

}
