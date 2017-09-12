import java.util.HashMap;

public enum State {

    ALABAMA("Alabama"),
    ALASKA("Alaska"),
    ARIZONA("Arizona"),
    ARKANSAS("Arkansas"),
    CALIFORNIA("California"),
    COLORADO("Colorado"),
    CONNECTICUT("Connecticut"),
    DELAWARE("Delaware"),
    FLORIDA("Florida"),
    GEORGIA("Georgia"),
    HAWAII("Hawaii"),
    IDAHO("Idaho"),
    ILLINOIS("Illinois"),
    INDIANA("Indiana"),
    IOWA("Iowa"),
    KANSAS("Kansas"),
    KENTUCKY("Kentucky"),
    LOUISIANA("Louisiana"),
    MAINE("Maine"),
    MARYLAND("Maryland"),
    MASSACHUSETTS("Massachusetts"),
    MICHIGAN("Michigan"),
    MINNESOTA("Minnesota"),
    MISSISSIPPI("Mississippi"),
    MISSOURI("Missouri"),
    MONTANA("Montana"),
    NEBRASKA("Nebraska"),
    NEVADA("Nevada"),
    NEWHAMPSHIRE("New Hampshire"),
    NEWJERSEY("New Jersey"),
    NEWMEXICO("New Mexico"),
    NEWYORK("New York"),
    NORTHCAROLINA("North Carolina"),
    NORTHDAKOTA("North Dakota"),
    OHIO("Ohio"),
    OKLAHOMA("Oklahoma"),
    OREGON("Oregon"),
    PENNSYLVANIA("Pennsylvania"),
    RHODEISLAND("Rhode Island"),
    SOUTHCAROLINA("South Carolina"),
    SOUTHDAKOTA("South Dakota"),
    TENNESSEE("Tennessee"),
    TEXAS("Texas"),
    UTAH("Utah"),
    VERMONT("Vermont"),
    VIRGINIA("Virginia"),
    WASHINGTON("Washington"),
    WESTVIRGINIA("West Virginia"),
    WISCONSIN("Wisconsin"),
    WYOMING("Wyoming");

    String symbol;

    State(String symbol) { this.symbol = symbol; }

    public String toString() { return symbol; }

    private static HashMap<String,State> db = makeDB();

    private static HashMap<String,State> makeDB() {
	HashMap<String,State> db = new HashMap<>();
	for(State s : State.values())
	    db.put(s.symbol, s);
	return db;
    }
		   
    public static State getState(String name) {
	return db.get(name);
    }
}