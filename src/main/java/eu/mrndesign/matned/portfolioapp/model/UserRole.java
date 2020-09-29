package eu.mrndesign.matned.portfolioapp.model;

import javax.persistence.Entity;

@Entity
public class UserRole extends BaseEntity{

    private String roleName;

    public UserRole() {
    }

    public UserRole(Role roleName){
        this.roleName = "ROLE_"+roleName.name();
    }

    public static UserRole apply(Role roleName){
        return new UserRole(roleName);
    }

    public String getRoleName() {
        return roleName;
    }

    public enum Role {
        ADMIN,
        USER;

        public String roleName(){
            return "ROLE_" + this.name();
        }
    }
}
