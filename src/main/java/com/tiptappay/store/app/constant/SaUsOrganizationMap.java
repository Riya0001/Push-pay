package com.tiptappay.store.app.constant;

import com.tiptappay.store.app.model.Hierarchy;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SaUsOrganizationMap {

    private static final Map<String, Integer> SAUS_TERRITORY;
    private static final Map<String, Integer> SAUS_CENTRAL_TERRITORY_DIVISIONS;
    private static final Map<String, Integer> SAUS_EASTERN_TERRITORY_DIVISIONS;
    private static final Map<String, Integer> SAUS_SOUTHERN_TERRITORY_DIVISIONS;
    private static final Map<String, Integer> SAUS_WESTERN_TERRITORY_DIVISIONS;
    private static final Map<String, Integer> SAUS_CENTRAL_TERRITORY_CORPS;
    private static final Map<String, Integer> SAUS_EASTERN_TERRITORY_CORPS;
    private static final Map<String, Integer> SAUS_SOUTHERN_TERRITORY_CORPS;
    private static final Map<String, Integer> SAUS_WESTERN_TERRITORY_CORPS;

    private static final int PARENT_ID_SB = 5499;

    static {

        //territory
        SAUS_TERRITORY = Map.of(
                "The Salvation Army Central Territory", 35341,
                "The Salvation Army Eastern Territory", 6611,
                "The Salvation Army Southern Territory", 35370,
                "The Salvation Army Western Territory", 35371
        );

        //divisions
        SAUS_CENTRAL_TERRITORY_DIVISIONS = Map.ofEntries(
                Map.entry("Salvation Army - North & Central Illinois Division", 35340),
                Map.entry("Salvation Army USA - Western Division", 35342),
                Map.entry("Salvation Army - Indiana Division", 35346),
                Map.entry("Salvation Army - Great Lakes Division", 35347),
                Map.entry("Salvation Army - Kansas/Western Missouri Division", 35349),
                Map.entry("Salvation Army - Midland Division", 35350),
                Map.entry("Salvation Army - Northern Division", 35351),
                Map.entry("Salvation Army - Wisconsin/Upper Michigan Division", 35352)
        );

        SAUS_EASTERN_TERRITORY_DIVISIONS = Map.ofEntries(
                Map.entry("Salvation Army - Northeast Ohio Division", 35356),
                Map.entry("Salvation Army - Eastern Pennsylvania & Delaware Division", 35357),
                Map.entry("Salvation Army - Southern New England Division", 35358),
                Map.entry("Salvation Army - Massachusetts Division", 35359),
                Map.entry("Salvation Army - Northern New England Division", 35360),
                Map.entry("New Jersey Division", 35365),
                Map.entry("Greater New York Division", 35364),
                Map.entry("Western Pennsylvania Area Division", 35367),
                Map.entry("Puerto Rico Division", 35369),
                Map.entry("Salvation Army - Empire State Division (Upstate New York)", 35368),
                Map.entry("Salvation Army - Southwest Ohio Division", 35366)
        );

        SAUS_SOUTHERN_TERRITORY_DIVISIONS = Map.ofEntries(
                Map.entry("Salvation Army - Alabama, Louisiana, Mississippi Division", 35414),
                Map.entry("Salvation Army - Arkansas & Oklahoma Division", 35412),
                Map.entry("Salvation Army - Florida Division", 35479),
                Map.entry("Salvation Army - Kentucky & Tennessee Division", 35439),
                Map.entry("Salvation Army - North and South Carolina Division", 35877),
                Map.entry("Salvation Army - Potomac Division", 35437),
                Map.entry("Salvation Army Savannah Admin Office Georgia Division", 35745)
        );

        SAUS_WESTERN_TERRITORY_DIVISIONS = Map.ofEntries(
                Map.entry("Salvation Army - Cascade Division", 35398),
                Map.entry("Salvation Army - Golden State Division", 35435),
                Map.entry("Salvation Army - Southwest Division", 35510),
                Map.entry("Salvation Army Hawaiian and Pacific Islands Division", 35905)
        );

        //corps
        SAUS_CENTRAL_TERRITORY_CORPS = Map.ofEntries(
                Map.entry("The Salvation Army Indianapolis Area", 7621),
                Map.entry("The Salvation Army Central Territory KWM DHQ", 7622),
                Map.entry("Salvation Army Wisconsin / Upper Michigan", 7623),
                Map.entry("Salvation Army Evansville", 7624),
                Map.entry("Salvation Army - Kansas/Western Missouri Division - Blue Valley Corps", 55696),
                Map.entry("Columbia Corps", 54395),
                Map.entry("Columbus, Indiana", 45339),
                Map.entry("Great Lakes Division/Benton Harbor", 64048),
                Map.entry("Indiana/Princeton", 44837),
                Map.entry("Indiana Division/Fort Wayne", 52393),
                Map.entry("The Salvation Army Indiana/Richmond", 52693),
                Map.entry("Salvation Army- Kansas and Western Missouri Division/ Prospect Corps", 57705),
                Map.entry("Kansas Western Missouri Division/Southland Corps", 42628),
                Map.entry("Salvation Army - Kansas/Western Missouri Division - El Dorado 360 Life Center", 55198),
                Map.entry("Salvation Army - Kansas/Western Missouri Division - Garden City Corps", 42426),
                Map.entry("Salvation Army - Kansas/Western Missouri Division - Northland Corps", 47269),
                Map.entry("Salvation Army-Kansas/Western Missouri Division-Independence Corps", 53694),
                Map.entry("The Salvation Army - KWM - Service Extension", 55796),
                Map.entry("The Salvation Army - KWM Division - Olathe Corps", 53695),
                Map.entry("KWM Wichita Citadel Corps", 55396),
                Map.entry("Kwm/hutchinson, Ks", 55196),
                Map.entry("Midland/Jefferson City", 64449),
                Map.entry("Northern Division/Bismarck", 43732),
                Map.entry("Salvation Army Porter County - Indiana Division", 45139),
                Map.entry("St Joseph Missouri", 43130),
                Map.entry("The Salvation Army St. Paul Eastside", 50491),
                Map.entry("USC Western Division Burlington IA Corps", 51692),
                Map.entry("Salvation Army Western Division / Newton, Iowa", 57505),
                Map.entry("Western/Keokuk", 55697),
                Map.entry("Wisconsin upper Michigan Service Extension", 55197),
                Map.entry("WUM/Milwaukee Citadel", 51291)
        );

        SAUS_EASTERN_TERRITORY_CORPS = Map.ofEntries(
                Map.entry("Salvation Army San Buffalo Area", 7625),
                Map.entry("The Salvation Army - Massillon Corps", 35950),
                Map.entry("Salvation Army Norwalk Corps.", 35665),
                Map.entry("The Salvation Army Greater Rochester", 39715),
                Map.entry("Asbury Park, NJ", 51792),
                Map.entry("New Jersey Ocean County Citadel", 49185),
                Map.entry("Eastern PA / Delaware Division - Chambersburg, PA Corps", 60322),
                Map.entry("EPA- Levitown", 58205),
                Map.entry("Greater New York - Brooklyn Bushwick Corps", 66556),
                Map.entry("Greater New York - Newburgh Corps", 63041),
                Map.entry("Greater New York Division Tarrytown Corps", 62839),
                Map.entry("The Salvation Army, Greater Philadelphia Area Command", 61629),
                Map.entry("NNE - Burlington Corps", 65352),
                Map.entry("NNE - Nashua Corps", 68559),
                Map.entry("Northern New England Derry Corps", 63847),
                Map.entry("Salvation Army-Northern New England Laconia New Hampshire Corps", 69363),
                Map.entry("SNE - The Greater Valley Salvation Army Ansonia", 68961)
        );

        SAUS_SOUTHERN_TERRITORY_CORPS = Map.ofEntries(
                Map.entry("Salvation Army Orlando Area Command", 7618),
                Map.entry("Salvation Army Hampton Roads - Potomac", 7619),
                Map.entry("Salvation Army USA Memphis Area", 7629),
                Map.entry("Salvation Army USA Central Maryland Area", 20197),
                Map.entry("Salvation Army Montgomery Alabama Corps.", 35890),
                Map.entry("Salvation Army - Alabastar Corps.", 35433),
                Map.entry("Salvation Army - Bessemer Salvation Station Corps", 35434),
                Map.entry("Salvation Army - 614 Citadel Corps.", 35442),
                Map.entry("Salvation Army - Jackson MS Corps", 35443),
                Map.entry("Salvation Army of Greater New Orleans", 35472),
                Map.entry("Salvation Army - Dothan, Alabama Corps.", 35498),
                Map.entry("Salvation Army Baton Rouge Corps.", 35889),
                Map.entry("Salvation Army - AlexanderCity", 35638),
                Map.entry("Salvation Army Central Arkansas Area Command", 35416),
                Map.entry("Salvation Army Fort Smith Corps", 35903),
                Map.entry("Salvation Army - Sarasota County Area Command", 35639),
                Map.entry("Salvation Army Tampa Fl Corps.", 35749),
                Map.entry("Salvation Army - Ocala Corps.", 35771),
                Map.entry("Salvation Army - Pensacola Corps.", 35835),
                Map.entry("Salvation Army - Okaloosa & Walton Counties Corps.", 35839),
                Map.entry("The Salvation Army Ft. Lauderdale Area Command", 35893),
                Map.entry("Salvation Army Daytona Beach", 35480),
                Map.entry("Salvation Army - Memphis & the Mid-South", 35440),
                Map.entry("Salvation Army of Greensboro", 35879),
                Map.entry("Salvation Army - Hampton Roads Area Command", 35438),
                Map.entry("Salvation Army - Virginia Peninsula", 35650),
                Map.entry("Salvation Army Central Virginia Area Command", 35654),
                Map.entry("National Capital Area Command", 35765),
                Map.entry("The Salvation Army Fredericksburg, VA", 35875),
                Map.entry("The Salvation Army Central Maryland Area Command", 35876),
                Map.entry("Salvation Army - Gwinnette County Corps.", 35406)
        );

        SAUS_WESTERN_TERRITORY_CORPS = Map.ofEntries(
                Map.entry("The Salvation Army Santa Clara Citadel", 7731),
                Map.entry("The Salvation Army Tehachapi Service Extension", 7732),
                Map.entry("The Salvation Army Sunnyvale Corps", 7733),
                Map.entry("The Salvation Army USA Columbus Area", 7737),
                Map.entry("Salvation Army San Francisco Harbor Light", 7734),
                Map.entry("The Salvation Army San Francisco All-Nations Corps", 7735),
                Map.entry("The Salvation Army Redwood City Corps", 7736),
                Map.entry("The Salvation Army Silicon Valley", 7738),
                Map.entry("The Salvation Army Bishop Service Extension", 7739),
                Map.entry("The Salvation Army San Jose Temple Corps", 7740),
                Map.entry("The Salvation Army San Francisco South of Market", 7741),
                Map.entry("The Salvation Army Modesto Admin", 7742),
                Map.entry("The Salvation Army Ridgecrest Corps", 7743),
                Map.entry("The Salvation Army Bakersfield Corps", 7744),
                Map.entry("The Salvation Army SF Kroc Center", 7745),
                Map.entry("The Salvation Army Hanford Corps", 7746),
                Map.entry("The Salvation Army Gilroy Corps", 7747),
                Map.entry("The Salvation Army Watsonville Corps", 7748),
                Map.entry("The Salvation Army Clovis Corps", 7749),
                Map.entry("The Salvation Army Salinas Corps", 7751),
                Map.entry("The Salvation Army Turlock Corps", 7750),
                Map.entry("The Salvation Army Tulare", 7752),
                Map.entry("Salvation Army - Roseburg Corps.", 35399),
                Map.entry("Salvation Army - Salem Kroc Corps.", 35526),
                Map.entry("Salvation Army Idaho Falls Corps.", 35900),
                Map.entry("Salvation Army Longview Temple Corps", 35902),
                Map.entry("Salvation Army - Silicon Valley Corps.", 35436),
                Map.entry("Salvation Army - Redwood City Corps.", 35501),
                Map.entry("The Salvation Army - Kroc Center & Railton Place", 35777),
                Map.entry("Salvation Army Modesto Corps", 35904),
                Map.entry("The Salvation Army Kroc Center & Railton Place", 36871),
                Map.entry("The Salvation Army Turlock Community Center", 35914),
                Map.entry("The Salvation Army DHQ Headquarters", 35906),
                Map.entry("Salvation Army - Oakland Garden Street Corps.", 35422),
                Map.entry("Salvation Army - Hayward Corps.", 35423),
                Map.entry("Salvation Army Tri-Cities Corps (Newark)", 35901),
                Map.entry("The Salvation Army Mesa Citadel Corps", 35916),
                Map.entry("Salvation Army - Renton, WA Corps.", 35497),
                Map.entry("Salvation Army - Boise Corps.", 35499),
                Map.entry("The Salvation Army Medford Oregon Corps", 35895),
                Map.entry("The Salvation Army Centralia Corps", 36869),
                Map.entry("Salvation Army Portland Corps.", 36907),
                Map.entry("Salvation Army - Caldwell Corps.", 35496),
                Map.entry("Salvation Army Helena Corps.", 35894),
                Map.entry("Salvation Army - Green Valley Corps.", 35511),
                Map.entry("Salvation Army Clark County Administration", 35660)
        );
    }

    private SaUsOrganizationMap() {
    }

    public static Set<String> getAllTerritoryNames() {
        return SAUS_TERRITORY.keySet();
    }

    public static Set<String> getAllDivisionsCentralNames() {
        return SAUS_CENTRAL_TERRITORY_DIVISIONS.keySet();
    }

    public static Set<String> getAllDivisionsEasternNames() {
        return SAUS_EASTERN_TERRITORY_DIVISIONS.keySet();
    }

    public static Set<String> getAllDivisionsSouthernNames() {
        return SAUS_SOUTHERN_TERRITORY_DIVISIONS.keySet();
    }

    public static Set<String> getAllDivisionsWesternNames() {
        return SAUS_WESTERN_TERRITORY_DIVISIONS.keySet();
    }
    public static Set<String> getAllCorpsCentralNames() {
        return SAUS_CENTRAL_TERRITORY_CORPS.keySet();
    }

    public static Set<String> getAllCorpsEasternNames() {
        return SAUS_EASTERN_TERRITORY_CORPS.keySet();
    }


    public static Set<String> getAllCorpsSouthernNames() {
        return SAUS_SOUTHERN_TERRITORY_CORPS.keySet();
    }


    public static Set<String> getAllCorpsWesternNames() {
        return SAUS_WESTERN_TERRITORY_CORPS.keySet();
    }


    public static Hierarchy getHierarchy(String territoryName, String divisionName) {
        Hierarchy hierarchy = new Hierarchy();
        hierarchy.setParent(PARENT_ID_SB);
        hierarchy.setChild(SAUS_TERRITORY.getOrDefault(territoryName, -1));

        int grandChild = SAUS_CENTRAL_TERRITORY_DIVISIONS.getOrDefault(divisionName, -1);
        if (grandChild == -1) {
            grandChild = SAUS_EASTERN_TERRITORY_DIVISIONS.getOrDefault(divisionName, -1);
        }
        if (grandChild == -1) {
            grandChild = SAUS_SOUTHERN_TERRITORY_DIVISIONS.getOrDefault(divisionName, -1);
        }
        if (grandChild == -1) {
            grandChild = SAUS_WESTERN_TERRITORY_DIVISIONS.getOrDefault(divisionName, -1);
        }

        hierarchy.setGrandChild(grandChild);

        return hierarchy;
    }
    public static int getDivisionIdByName(String name) {
        String normalizedInput = name.toLowerCase().trim().replaceAll("[^a-z0-9]", "");
        for (Map<String, Integer> map : List.of(
                SAUS_CENTRAL_TERRITORY_DIVISIONS,
                SAUS_EASTERN_TERRITORY_DIVISIONS,
                SAUS_SOUTHERN_TERRITORY_DIVISIONS,
                SAUS_WESTERN_TERRITORY_DIVISIONS
        )) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String normalizedKey = entry.getKey().toLowerCase().trim().replaceAll("[^a-z0-9]", "");
                if (normalizedKey.equals(normalizedInput)) {
                    return entry.getValue();
                }
            }
        }
        return 0;
    }


}
