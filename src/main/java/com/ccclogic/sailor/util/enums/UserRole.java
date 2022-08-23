package com.ccclogic.sailor.util.enums;

public enum UserRole {
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR", 1L),
    ADMINISTRATOR("ROLE_ADMINISTRATOR", 1L),
    ROLE_OWNER("ROLE_OWNER", 2L),
    OWNER("ROLE_OWNER", 2L),
    ROLE_SUPERVISOR("ROLE_SUPERVISOR", 3L),
    ROLE_SUPERVISOR_LESS("SUPERVISOR", 3L),
    SUPERVISOR("SUPERVISOR", 3L),
    ROLE_AGENT("ROLE_AGENT", 4L),
    ROLE_AGENT_LESS("AGENT", 4L),
    AGENT("AGENT", 4L),
    ROLE_CENTER_SUPERVISOR("ROLE_CENTER_SUPERVISOR", 5L),
    ROLE_SERVICE_NODE("ROLE_SERVICE_NODE", 6L),
    SERVICE_NODE("SERVICE_NODE", 6L),
    ROLE_SERVICE_NODE_LESS("SERVICENODE", 6L);

    private String value;
    private Long roleId;

    private UserRole(String value, Long roleId){
        this.value = value;
        this.roleId = roleId;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public static Long getRoleId(String role){
        for (UserRole role1: values()){
            if(role1.getValue().equalsIgnoreCase(role)){
                return role1.getRoleId();
            }
        }
        
        throw new IllegalArgumentException("Invalid UserRole Provided");
    }

    public Long getRoleId(){
        return this.roleId;
    }
    
    public static UserRole getUserRole(Long roleId) {
        
        for (UserRole userRole : values()) {
            if (userRole.getRoleId().equals(roleId)) return userRole;
        }
        
        throw new IllegalStateException("Invalid roleId provided");
    }
    
    public static UserRole getUserRoleForView(Long roleId) {
        
        switch (roleId.intValue()) {
            case 1:
                return UserRole.ROLE_ADMINISTRATOR;
            case 2:
                return UserRole.OWNER;
            case 3:
                return UserRole.SUPERVISOR;
            case 4:
                return UserRole.AGENT;
            default:
                throw new IllegalStateException("Invalid roleId provided");
        }
    }


}
