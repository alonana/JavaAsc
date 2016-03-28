package com.javaasc.ssh;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;

public class MyPaswordAuthenticator implements PasswordAuthenticator {
    public boolean authenticate(String username, String password, ServerSession session)
            throws PasswordChangeRequiredException {
        return true;
    }
}
