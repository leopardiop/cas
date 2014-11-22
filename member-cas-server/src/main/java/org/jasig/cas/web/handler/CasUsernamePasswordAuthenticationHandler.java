package org.jasig.cas.web.handler;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.web.PasswordParser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Created by peng.luwei on 2014/11/3.
 */
public class CasUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler{

    private PasswordParser parser;

    private String sql;

    private JdbcTemplate jdbcTemplate;


    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {

        final String username = credentials.getUsername();
        String password = credentials.getPassword();

        System.out.println("password = " + password);
        System.out.println("username = " + username);

        /*final String dbPassword = jdbcTemplate.queryForObject(this.sql,String.class,new Object[]{username});
*/
        /*if (StringUtils.hasText(username) && StringUtils.hasText(password)
                && parser.parse(username,password).equals(dbPassword)) {
            log.debug("User [" + username+ "] was successfully authenticated.");
            return true;
        }*/
        log.debug("User [" + username + "] failed authentication");
        return false;
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParser(PasswordParser parser) {
        this.parser = parser;
    }
}
