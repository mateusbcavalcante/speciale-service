package br.com.a2dm.spdm.utils;

public class AuthUtils<AuthKey, AuthValue> {

        private AuthKey authKey;
        private AuthValue authValue;

        public AuthUtils(AuthKey authKey, AuthValue authValue) {
            this.authKey = authKey;
            this.authValue = authValue;
        }

        public AuthKey getAuthKey() {
            return this.authKey;
        }

        public AuthValue getAuthValue() {
            return this.authValue;
        }
}
