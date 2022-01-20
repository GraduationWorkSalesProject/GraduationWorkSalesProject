package GraduationWorkSalesProject.graduation.com.config.jwt;

import GraduationWorkSalesProject.graduation.com.exception.InvalidAuthorizationHeaderException;
import GraduationWorkSalesProject.graduation.com.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        log.debug("[Token] {}", requestTokenHeader);

        try {
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer "))
                throw new InvalidAuthorizationHeaderException();
            final String jwtToken = requestTokenHeader.substring(7);
            final String username = jwtTokenUtil.getUsernameFromAccessToken(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateAccessToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (InvalidAuthorizationHeaderException e) {
            request.setAttribute("errorCode", INVALID_AUTHORIZATION_HEADER);
        } catch (ExpiredJwtException e) {
            log.warn("JWT Access Token has expired");
            request.setAttribute("errorCode", EXPIRED_ACCESS_TOKEN);
        } catch (JwtException e) {
            log.warn("Unable to get JWT Token");
            request.setAttribute("errorCode", INVALID_JWT);
        }

        filterChain.doFilter(request, response);
    }
}
