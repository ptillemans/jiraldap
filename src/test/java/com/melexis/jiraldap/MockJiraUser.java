package com.melexis.jiraldap;

public class MockJiraUser  implements JiraUser {
        private String name;
        private String fullName;
        private String email;

        MockJiraUser(String name) {
            this.name = name;
        }

        public MockJiraUser(LdapUser user) {
            this(user.getUid());
            setEmail(user.getEmail());
            setFullName(user.getName());
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockJiraUser that = (MockJiraUser) o;

        return !(email != null ? !email.equals(that.email) : that.email != null) && !(fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) && !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public String getName() {
            return name;
        }

        public String getFullName() {
            return fullName;
        }

        public void store() throws JiraLdapException {
            // do nothing
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
}