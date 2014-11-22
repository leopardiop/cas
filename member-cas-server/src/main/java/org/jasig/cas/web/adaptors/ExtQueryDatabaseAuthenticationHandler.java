package org.jasig.cas.web.adaptors;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.util.encoder.DesHelper;
import org.jasig.cas.web.PasswordParser;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by peng.luwei on 2014/11/3.
 */
public class ExtQueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    @NotNull
    private String sql;

    private PasswordParser parser;

    protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        String username = getPrincipalNameTransformer().transform(credentials.getUsername()).toLowerCase();
        String password = credentials.getPassword();

        try {
            final Map<String,Object> map = getJdbcTemplate().queryForMap(sql,new Object[]{username,username,username});

            String dbPassword = map.get("password")+"";
            String id = map.get("id")+"";

            password = DesHelper.strDec(password,username,username,null);

            String encryptedPassword = this.parser.parse(password+id);

            return dbPassword.equals(encryptedPassword);
        } catch (final IncorrectResultSizeDataAccessException e) {
            // this means the username was not found.
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param sql The sql to set.
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }

    public PasswordParser getParser() {
        return parser;
    }

    public void setParser(PasswordParser parser) {
        this.parser = parser;
    }
}
