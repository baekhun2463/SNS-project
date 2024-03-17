package snsproject.snsproject.configuration.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import snsproject.snsproject.model.User;
import snsproject.snsproject.service.UserService;
import snsproject.snsproject.util.JwtTokenUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    private final static List<String> TOKEN_IN_PARAM_URLS = List.of("/api/v1/users/alarm/subscribe");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token;

        try {

            if(TOKEN_IN_PARAM_URLS.contains(request.getRequestURI())) {
                token = request.getQueryString().split("=")[1].trim();
            }else {
                final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

                if(header == null || !header.startsWith("Bearer ")) {
                    log.error("Error occurs while getting header");
                    filterChain.doFilter(request, response);
                    return;
                }
                token = header.split(" ")[1].trim();
            }

            if(JwtTokenUtils.isTokenExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            String userName = JwtTokenUtils.getUsername(token, key);

            User user = userService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(RuntimeException e) {
            log.error("Error occurs while validing. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
