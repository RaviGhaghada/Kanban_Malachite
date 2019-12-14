package boardpackage;

public enum Role {
    WORK_IN_PROGRESS {
        @Override
        public String toString() {
            return "Work in progress";
        }
    },
    BACKLOG {
        @Override
        public String toString(){
            return "Backlog";
        }
    },
    ON_HOLD {
        @Override
        public String toString(){
            return "On hold";
        }
    },
    COMPLETED_WORK {
        @Override
        public String toString(){
            return "Completed Work";
        }
    },
    FOR_INFO_ONLY {
        @Override
        public String toString(){
            return "For info only";
        }
    };

    public static Role fromString(String selectedItem) {
        for (Role role : Role.values()){
            if (role.toString().equals(selectedItem)){
                return role;
            }
        }
        return null;
    }
}