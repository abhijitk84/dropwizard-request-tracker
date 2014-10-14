import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class RequestTrackerServletFilter implements Filter {
	// Use a supplier so we only generate id's when they're needed
	private static final IdSupplier ID_SUPPLIER = new IdSupplier();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Optional<String> requestId = Optional.fromNullable(httpServletRequest.getHeader("X-Request-Tracker"));
		MDC.put("Request-Tracker", requestId.or(ID_SUPPLIER));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
