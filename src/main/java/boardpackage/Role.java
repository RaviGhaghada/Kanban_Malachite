package boardpackage;

/**
 * Enum for the Role element of columns.
 *
 * @Author Mariam Ahmed, Ravi Ghaghada, Manvi Jain, Roozhina (Rojina) Nejad, and Marek Grzesiuk
 * @Version December 2019
 */
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

    /**
     * Converts string to Role enum.
     * @param selectedItem as a string
     * @return role as an enum object
     */
    public static Role fromString(String selectedItem) {
        for (Role role : Role.values()){
            if (role.toString().equals(selectedItem)){
                return role;
            }
        }
        return null;
    }
}